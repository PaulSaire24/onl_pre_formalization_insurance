package com.bbva.rbvd.lib.r415.impl.transform.bean;

import com.bbva.rbvd.dto.cicsconnection.icr3.ICMRYS3;
import com.bbva.rbvd.dto.insrncsale.commons.ValidityPeriodDTO;
import com.bbva.rbvd.dto.insrncsale.policy.FirstInstallmentDTO;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyDTO;
import com.bbva.rbvd.dto.insrncsale.policy.RelatedContractDTO;
import com.bbva.rbvd.dto.preformalization.dao.ContractDAO;
import com.bbva.rbvd.dto.preformalization.dao.QuotationDAO;
import com.bbva.rbvd.dto.preformalization.util.ConstantsUtil;
import com.bbva.rbvd.lib.r415.impl.transfer.PayloadStore;
import com.bbva.rbvd.lib.r415.impl.util.ConvertUtil;
import org.joda.time.LocalDate;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Objects.nonNull;

public class ContractBean {

    private ContractBean(){}


    public static ContractDAO buildInsuranceContract(PayloadStore payloadStore) {

        ContractDAO contractDao = new ContractDAO();
        ICMRYS3 icmrys3 = payloadStore.getIcr3Response().getIcmrys3();
        String currentDate = ConvertUtil.generateCorrectDateFormat(new LocalDate());
        PolicyDTO response = payloadStore.getResposeBody();
        QuotationDAO quotationDAO = payloadStore.getQuotationDAO();

        contractDao.setEntityId(icmrys3.getNUMCON().substring(0, 4));
        contractDao.setBranchId(icmrys3.getNUMCON().substring(4, 8));
        contractDao.setIntAccountId(icmrys3.getNUMCON().substring(10));
        contractDao.setFirstVerfnDigitId(icmrys3.getNUMCON().substring(8, 9));
        contractDao.setSecondVerfnDigitId(icmrys3.getNUMCON().substring(9, 10));
        contractDao.setPolicyQuotaInternalId(response.getQuotationNumber());
        contractDao.setInsuranceProductId(quotationDAO.getInsuranceProductId());
        contractDao.setInsuranceModalityType(response.getProduct().getPlan().getId());
        contractDao.setInsuranceCompanyId(ConvertUtil.getBigDecimalValue(response.getInsuranceCompany().getId()));
        contractDao.setCustomerId(response.getHolder().getId());
        contractDao.setDomicileContractId(getDomicileContractId(response.getRelatedContracts()));
        contractDao.setIssuedReceiptNumber(BigDecimal.ZERO);
        contractDao.setPaymentFrequencyId(payloadStore.getPaymentFrequencyId());
        contractDao.setPremiumAmount(ConvertUtil.getBigDecimalValue(response.getInstallmentPlan().getPaymentAmount().getAmount()));
        contractDao.setSettlePendingPremiumAmount(ConvertUtil.getBigDecimalValue(response.getTotalAmount().getAmount()));
        contractDao.setCurrencyId(response.getInstallmentPlan().getPaymentAmount().getCurrency());
        contractDao.setInstallmentPeriodFinalDate(currentDate);
        contractDao.setInsuredAmount(ConvertUtil.getBigDecimalValue(response.getInsuredAmount().getAmount()));
        contractDao.setCtrctDisputeStatusType(response.getSaleChannelId());
        contractDao.setEndorsementPolicyIndType(ConstantsUtil.N_VALUE);
        contractDao.setInsrncCoContractStatusType(ConstantsUtil.StatusContract.PENDIENTE.getValue());
        contractDao.setContractStatusId(ConstantsUtil.StatusContract.PENDIENTE.getValue());
        contractDao.setCreationUserId(response.getCreationUser());
        contractDao.setUserAuditId(response.getUserAudit());
        contractDao.setContractManagerBranchId(response.getBank().getBranch().getId());
        contractDao.setContractInceptionDate(currentDate);
        contractDao.setSettlementFixPremiumAmount(ConvertUtil.getBigDecimalValue(response.getTotalAmount().getAmount()));
        contractDao.setBiometryTransactionId(response.getIdentityVerificationCode());
        contractDao.setEndLinkageDate(ConvertUtil.generateCorrectDateFormat(
                ConvertUtil.convertDateToLocalDate(response.getInstallmentPlan().getMaturityDate())));
        contractDao.setInsuranceContractStartDate(getContractStarDate(response.getValidityPeriod()));
        contractDao.setValidityMonthsNumber(quotationDAO.getContractDurationType().equals("A")
                ? quotationDAO.getContractDurationNumber().multiply(BigDecimal.valueOf(12))
                : quotationDAO.getContractDurationNumber());

        contractDao.setAutomaticDebitIndicatorType((response.getPaymentMethod().getPaymentType().equals(ConstantsUtil.PAYMENT_METHOD_VALUE))
                ? ConstantsUtil.S_VALUE : ConstantsUtil.N_VALUE);

        setFieldsFromFirstInstallment(response.getFirstInstallment(),contractDao);

        if (nonNull(response.getBusinessAgent())) {
            contractDao.setInsuranceManagerId(response.getBusinessAgent().getId());
        }

        if (nonNull(response.getPromoter())) {
            contractDao.setInsurancePromoterId(response.getPromoter().getId());
        }

        contractDao.setPrevPendBillRcptsNumber(getPrevPendBillRcptsNumber(response));

        return contractDao;
    }

    private static void setFieldsFromFirstInstallment(FirstInstallmentDTO firstInstallment,ContractDAO contractDao){
        if(firstInstallment != null){
            contractDao.setInsurPendingDebtIndType((firstInstallment.getIsPaymentRequired()) ? ConstantsUtil.N_VALUE : ConstantsUtil.S_VALUE);
            contractDao.setTotalDebtAmount((firstInstallment.getIsPaymentRequired()) ? BigDecimal.ZERO : ConvertUtil.getBigDecimalValue(firstInstallment.getPaymentAmount().getAmount()));
        }else{
            contractDao.setInsurPendingDebtIndType(null);
            contractDao.setTotalDebtAmount(null);
        }
    }

    private static String getContractStarDate(ValidityPeriodDTO validityPeriod){
        if(validityPeriod != null){
            return ConvertUtil.generateCorrectDateFormat(ConvertUtil.convertDateToLocalDate(validityPeriod.getStartDate()));
        }
        return null;
    }

    private static String getDomicileContractId(List<RelatedContractDTO> relatedContractList){
        if(CollectionUtils.isEmpty(relatedContractList)) return null;
        return relatedContractList.get(0).getContractDetails().getNumber();
    }

    private static BigDecimal getPrevPendBillRcptsNumber(PolicyDTO requestBody) {
        BigDecimal prevPendBillRcptsNumber = BigDecimal.ZERO;
        Long totalNumberInstallments = requestBody.getInstallmentPlan().getTotalNumberInstallments();

        if(requestBody.getFirstInstallment() != null && totalNumberInstallments != null){
            boolean isPaymentRequired = requestBody.getFirstInstallment().getIsPaymentRequired();
            prevPendBillRcptsNumber = BigDecimal.valueOf(isPaymentRequired ? totalNumberInstallments - 1 : totalNumberInstallments);
        }

        return prevPendBillRcptsNumber;
    }

}
