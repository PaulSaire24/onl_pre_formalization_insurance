package com.bbva.rbvd.lib.r415.impl;

import com.bbva.pisd.dto.contract.search.ReceiptSearchCriteria;
import com.bbva.pisd.dto.insurancedao.entities.PaymentPeriodEntity;
import com.bbva.rbvd.dto.cicsconnection.icr2.ICMRYS2;
import com.bbva.rbvd.dto.cicsconnection.icr2.ICR2Request;
import com.bbva.rbvd.dto.cicsconnection.icr2.ICR2Response;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyDTO;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDErrors;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDProperties;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDValidation;
import com.bbva.rbvd.dto.preformalization.dao.QuotationDAO;
import com.bbva.rbvd.dto.preformalization.util.ConstantsUtil;
import com.bbva.rbvd.lib.r415.impl.business.ICR2Business;
import com.bbva.rbvd.lib.r415.impl.service.dao.IContractDAO;
import com.bbva.rbvd.lib.r415.impl.service.dao.IParticipantDAO;
import com.bbva.rbvd.lib.r415.impl.service.dao.impl.ContractDAOImpl;
import com.bbva.rbvd.lib.r415.impl.service.dao.impl.ParticipantDAOImpl;
import com.bbva.rbvd.lib.r415.impl.transform.bean.QuotationBean;
import com.bbva.rbvd.lib.r415.impl.util.ValidationUtil;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * The RBVDR415Impl class...
 */
public class RBVDR415Impl extends RBVDR415Abstract {

	private static final Logger LOGGER = LoggerFactory.getLogger(RBVDR415Impl.class);

	/**
	 * The executeLogicPreFormalization method...
	 */

	@Override
	public PolicyDTO executeLogicPreFormalization(PolicyDTO requestBody) {
		// No tocar por error de modulos
		ReceiptSearchCriteria receiptSearchCriteria = new ReceiptSearchCriteria();
		LOGGER.info("RBVDR415Impl - executeLogicPreFormalization() - receiptSearchCriteria para que no salga error xd: {}", receiptSearchCriteria);

		LOGGER.info("RBVDR415Impl - executeLogicPreFormalization() - requestBody: {}", requestBody);

		validatePolicyExists(requestBody.getQuotationNumber());

		evaluateIfPaymentIsRequired(requestBody);

		Map<String, Object> quotationIdArgument = Collections.singletonMap(
				RBVDProperties.FIELD_POLICY_QUOTA_INTERNAL_ID.getValue(),requestBody.getQuotationNumber());

		//DEVUELVE MUCHOS CAMPOS, REEMPLAZAR POR OTRO QUERY. Usar lib_dao_insurance_quotation
		Map<String, Object> contractRequiredFields = pisdR012.executeGetASingleRow(
				RBVDProperties.DYNAMIC_QUERY_FOR_INSURANCE_CONTRACT.getValue(), quotationIdArgument);

		String frequencyType = this.applicationConfigurationService.getProperty(requestBody.getInstallmentPlan().getPeriod().getId());
		PaymentPeriodEntity paymentPeriod = this.pisdR226.executeFindPaymentPeriodByType(frequencyType);

		if (isEmpty(contractRequiredFields)) {
			throw RBVDValidation.build(RBVDErrors.NON_EXISTENT_QUOTATION);
		}

		QuotationDAO quotationDAO = QuotationBean.transformQuotationMapToBean(contractRequiredFields);

		ICR2Request icr2Request = ICR2Business.mapRequestFromPreformalizationBody(requestBody);
		ICR2Response icr2Response = rbvdR047.executePreFormalizationContract(icr2Request);

		String hostBranchId = icr2Response.getIcmrys2().getOFICON();
		requestBody.getBank().getBranch().setId(hostBranchId);
		setSaleChannelIdFromBranchId(requestBody, hostBranchId);

		boolean isEndorsement = ValidationUtil.validateEndorsement(requestBody);

		//Inserta contrato
		IContractDAO contractDAO = new ContractDAOImpl(this.pisdR226);
		contractDAO.insertInsuranceContract(requestBody, quotationDAO, icr2Response, isEndorsement, paymentPeriod);

		IParticipantDAO participantDAO = new ParticipantDAOImpl(this.pisdR012);
		//Busca roles por producto y plan
		List<Map<String, Object>> rolesFromDB = participantDAO.getRolesByProductIdAndModality(
				quotationDAO.getInsuranceProductId(), requestBody.getProduct().getPlan().getId());

		//falta enviar el applicationconfiguration servcie al participant
		if(!isEmpty(rolesFromDB) && !isEmpty(requestBody.getParticipants())){
			//Registra participantes
			participantDAO.insertInsuranceParticipants(requestBody, rolesFromDB, icr2Response.getIcmrys2().getNUMCON());
		}

		//Mapeo de campos de salida de trx
		String contractId = getContractFrontIcr2(icr2Response.getIcmrys2());
		filltOutputTrx(requestBody,contractId,quotationDAO);

		return requestBody;
	}

	private void filltOutputTrx(PolicyDTO policyDTO,String contractId,QuotationDAO quotationDAO){
		policyDTO.setId(contractId);
		policyDTO.getProduct().setName(quotationDAO.getInsuranceProductDesc());
	}

	private String getContractFrontIcr2(ICMRYS2 icmrys2) {
		return icmrys2.getNUMCON().substring(0, 4) +
				icmrys2.getNUMCON().substring(4, 8) +
				icmrys2.getNUMCON().charAt(8) +
				icmrys2.getNUMCON().charAt(9) +
				icmrys2.getNUMCON().substring(10);
	}

	public void validatePolicyExists(String quotation) {
		boolean validateExist = this.pisdR226.executeFindQuotationIfExistInContract(quotation);
		if (validateExist) {
			throw RBVDValidation.build(RBVDErrors.POLICY_ALREADY_EXISTS);
		}
	}

	public void evaluateIfPaymentIsRequired(PolicyDTO requestBody) {
		DateTimeZone dateTimeZone = DateTimeZone.forID(ConstantsUtil.TimeZone.LIMA_TIME_ZONE);

		DateTime currentLocalDate = new DateTime(new Date(), dateTimeZone);
		Date currentDate = currentLocalDate.toDate();

		dateTimeZone = DateTimeZone.forID(ConstantsUtil.TimeZone.GMT_TIME_ZONE);
		LocalDate startLocalDate = new LocalDate(requestBody.getValidityPeriod().getStartDate(), dateTimeZone);
		Date startDate = startLocalDate.toDateTimeAtStartOfDay().toDate();

		requestBody.getFirstInstallment().setIsPaymentRequired(!startDate.after(currentDate));
	}


	public void setSaleChannelIdFromBranchId(PolicyDTO requestBody, String branchId) {
		String tlmktValue = this.applicationConfigurationService.getProperty(ConstantsUtil.ApxConsole.KEY_TLMKT_CODE);
		if (tlmktValue.equals(branchId)) {
			LOGGER.info("***** RBVDR415Impl - setSaleChannelIdFromBranchId | It's TLMKT Channel *****");
			requestBody.setSaleChannelId("TM");
		}
	}
}
