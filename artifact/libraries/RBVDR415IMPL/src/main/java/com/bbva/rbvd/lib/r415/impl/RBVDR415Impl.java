package com.bbva.rbvd.lib.r415.impl;

import com.bbva.apx.exception.business.BusinessException;
import com.bbva.pisd.dto.contract.search.ReceiptSearchCriteria;
import com.bbva.pisd.dto.insurancedao.entities.PaymentPeriodEntity;
import com.bbva.rbvd.dto.cicsconnection.icr2.ICMRYS2;
import com.bbva.rbvd.dto.cicsconnection.icr2.ICR2Request;
import com.bbva.rbvd.dto.cicsconnection.icr2.ICR2Response;
import com.bbva.rbvd.dto.insrncsale.events.CreatedInsrcEventDTO;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyDTO;
import com.bbva.rbvd.dto.preformalization.dao.QuotationDAO;
import com.bbva.rbvd.dto.preformalization.util.ConstantsUtil;
import com.bbva.rbvd.dto.preformalization.util.RBVDMessageError;
import com.bbva.rbvd.lib.r415.impl.business.CreatedInsuranceEventBusiness;
import com.bbva.rbvd.lib.r415.impl.business.ICR2Business;
import com.bbva.rbvd.lib.r415.impl.service.api.ConsumeInternalService;
import com.bbva.rbvd.lib.r415.impl.service.dao.IContractDAO;
import com.bbva.rbvd.lib.r415.impl.service.dao.IParticipantDAO;
import com.bbva.rbvd.lib.r415.impl.service.dao.IQuotationDAO;
import com.bbva.rbvd.lib.r415.impl.service.dao.impl.ContractDAOImpl;
import com.bbva.rbvd.lib.r415.impl.service.dao.impl.ParticipantDAOImpl;
import com.bbva.rbvd.lib.r415.impl.service.dao.impl.QuotationDAOImpl;
import com.bbva.rbvd.lib.r415.impl.util.ConvertUtil;
import com.bbva.rbvd.lib.r415.impl.util.ValidationUtil;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		try {
			// No tocar por error de modulos
			ReceiptSearchCriteria receiptSearchCriteria = new ReceiptSearchCriteria();
			LOGGER.info("RBVDR415Impl - executeLogicPreFormalization() - receiptSearchCriteria para que no salga error xd: {}", receiptSearchCriteria);

			LOGGER.info("RBVDR415Impl - executeLogicPreFormalization() - requestBody: {}", requestBody);

			boolean validateExist = this.pisdR226.executeFindQuotationIfExistInContract(requestBody.getQuotationNumber());
			ValidationUtil.validateQuotationExistsInContract(validateExist);

			evaluateIfPaymentIsRequired(requestBody);

			IQuotationDAO quotationDAO = new QuotationDAOImpl(this.pisdR601);
			QuotationDAO quotationDetail = quotationDAO.getQuotationDetails(requestBody.getQuotationNumber());
			LOGGER.info("RBVDR415Impl - executeLogicPreFormalization() - quotationDAO: {}", quotationDetail);

			ValidationUtil.validateObjectIsNull(quotationDetail,
					RBVDMessageError.QUOTATION_NOT_EXIST.getAdviceCode(),
					RBVDMessageError.QUOTATION_NOT_EXIST.getMessage());

			String frequencyType = this.applicationConfigurationService.getProperty(requestBody.getInstallmentPlan().getPeriod().getId());
			PaymentPeriodEntity paymentPeriod = this.pisdR226.executeFindPaymentPeriodByType(frequencyType);
			LOGGER.info("RBVDR415Impl - executeLogicPreFormalization() - paymentPeriod: {}", paymentPeriod);

			ValidationUtil.validateObjectIsNull(paymentPeriod,
					RBVDMessageError.PAYMENT_PERIOD_NOT_EXIST.getAdviceCode(),
					RBVDMessageError.PAYMENT_PERIOD_NOT_EXIST.getMessage());

			ICR2Request icr2Request = ICR2Business.mapRequestFromPreformalizationBody(requestBody);
			ICR2Response icr2Response = rbvdR047.executePreFormalizationContract(icr2Request);

			LOGGER.info("RBVDR415Impl - executeLogicPreFormalization() - icr2Response: {}", icr2Response);

			ValidationUtil.checkHostAdviceErrors(icr2Response);

			String hostBranchId = icr2Response.getIcmrys2().getOFICON();
			requestBody.getBank().getBranch().setId(hostBranchId);
			setSaleChannelIdFromBranchId(requestBody, hostBranchId);

			boolean isEndorsement = ValidationUtil.validateEndorsement(requestBody);

			//Inserta contrato
			IContractDAO contractDAO = new ContractDAOImpl(this.pisdR226);
			contractDAO.insertInsuranceContract(requestBody, quotationDetail, icr2Response, isEndorsement, paymentPeriod);

			IParticipantDAO participantDAO = new ParticipantDAOImpl(this.pisdR012, this.applicationConfigurationService);

			//Busca roles por producto y plan
			List<Map<String, Object>> rolesFromDB = participantDAO.getRolesByProductIdAndModality(
					quotationDetail.getInsuranceProductId(), requestBody.getProduct().getPlan().getId());
			LOGGER.info("RBVDR415Impl - executeLogicPreFormalization() - rolesFromDB: {}", rolesFromDB);

			if(!isEmpty(rolesFromDB) && !isEmpty(requestBody.getParticipants())){
				//Registra participantes
				participantDAO.insertInsuranceParticipants(requestBody, rolesFromDB, icr2Response.getIcmrys2().getNUMCON());
			}

			filltOutputTrx(requestBody,icr2Response.getIcmrys2(),quotationDetail);

			//llamar al evento para que avise a DWP que ya se contrató
			String flagCallEvent = this.applicationConfigurationService.getDefaultProperty(
					"flag.callevent.createinsured.for.preemision",ConstantsUtil.N_VALUE);
			if(flagCallEvent.equalsIgnoreCase(ConstantsUtil.S_VALUE)){
				ConsumeInternalService consumeInternalService = new ConsumeInternalService(this.internalApiConnectorImpersonation);
				Integer httpStatusCode = consumeInternalService.callEventUpsilonToUpdateStatusInDWP(
						CreatedInsuranceEventBusiness.createRequestCreatedInsuranceEvent(requestBody));
				LOGGER.info("RBVDR415Impl - executeLogicPreFormalization() - callEventUpsilonToUpdateStatusInDWP - httpStatusCode: {}",httpStatusCode);
			}

			LOGGER.info("RBVDR415Impl - executeLogicPreFormalization() - output: {}", requestBody);
			LOGGER.info("RBVDR415Impl - executeLogicPreFormalization() - Fin del proceso");
			return requestBody;
		}catch (BusinessException tuex){
			LOGGER.error("RBVDR415Impl - executeLogicPreFormalization() - BusinessException: {}", tuex.getMessage());
			this.addAdviceWithDescription(tuex.getAdviceCode(),tuex.getMessage());
			return null;
		}
	}

	private void filltOutputTrx(PolicyDTO policyDTO,ICMRYS2 icmrys2,QuotationDAO quotationDAO){
		policyDTO.setId(getContractFrontIcr2Response(icmrys2));
		policyDTO.getProduct().setName(quotationDAO.getInsuranceProductDesc());
		policyDTO.setOperationDate(ConvertUtil.convertStringDateWithTimeFormatToDate(icmrys2.getFECCTR()));
		policyDTO.getValidityPeriod().setEndDate(ConvertUtil.convertStringDateWithDateFormatToDate(icmrys2.getFECFIN()));
	}

	private String getContractFrontIcr2Response(ICMRYS2 icmrys2) {
		return icmrys2.getNUMCON().substring(0, 4) +
				icmrys2.getNUMCON().substring(4, 8) +
				icmrys2.getNUMCON().charAt(8) +
				icmrys2.getNUMCON().charAt(9) +
				icmrys2.getNUMCON().substring(10);
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
