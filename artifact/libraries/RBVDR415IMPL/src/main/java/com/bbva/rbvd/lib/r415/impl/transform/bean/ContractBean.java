package com.bbva.rbvd.lib.r415.impl.transform.bean;

import com.bbva.rbvd.dto.cicsconnection.icr3.ICMRYS3;
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
        contractDao.setPremiumAmount(ConvertUtil.getBigDecimalValue(response.getFirstInstallment().getPaymentAmount().getAmount()));
        contractDao.setSettlePendingPremiumAmount(ConvertUtil.getBigDecimalValue(response.getTotalAmount().getAmount()));
        contractDao.setCurrencyId(response.getInstallmentPlan().getPaymentAmount().getCurrency());
        contractDao.setInstallmentPeriodFinalDate(currentDate);
        contractDao.setInsuredAmount(ConvertUtil.getBigDecimalValue(response.getInsuredAmount().getAmount()));
        contractDao.setCtrctDisputeStatusType(response.getSaleChannelId());
        contractDao.setEndorsementPolicyIndType((payloadStore.getIsEndorsement()) ? ConstantsUtil.S_VALUE : ConstantsUtil.N_VALUE);
        contractDao.setInsrncCoContractStatusType(ConstantsUtil.StatusContract.PENDIENTE.getValue());
        contractDao.setContractStatusId(ConstantsUtil.StatusContract.PENDIENTE.getValue());
        contractDao.setCreationUserId(response.getCreationUser());
        contractDao.setUserAuditId(response.getUserAudit());
        contractDao.setInsurPendingDebtIndType((response.getFirstInstallment().getIsPaymentRequired())
                ? ConstantsUtil.N_VALUE : ConstantsUtil.S_VALUE);
        contractDao.setContractManagerBranchId(response.getBank().getBranch().getId());
        contractDao.setContractInceptionDate(currentDate);
        contractDao.setSettlementFixPremiumAmount(ConvertUtil.getBigDecimalValue(response.getTotalAmount().getAmount()));
        contractDao.setBiometryTransactionId(response.getIdentityVerificationCode());
        contractDao.setEndLinkageDate(ConvertUtil.generateCorrectDateFormat(
                ConvertUtil.convertDateToLocalDate(response.getInstallmentPlan().getMaturityDate())));
        contractDao.setInsuranceContractStartDate(ConvertUtil.generateCorrectDateFormat(
                ConvertUtil.convertDateToLocalDate(response.getValidityPeriod().getStartDate())));
        contractDao.setValidityMonthsNumber(quotationDAO.getContractDurationType().equals("A")
                ? quotationDAO.getContractDurationNumber().multiply(BigDecimal.valueOf(12))
                : quotationDAO.getContractDurationNumber());
        contractDao.setTotalDebtAmount((response.getFirstInstallment().getIsPaymentRequired())
                ? BigDecimal.ZERO : ConvertUtil.getBigDecimalValue(response.getFirstInstallment().getPaymentAmount().getAmount()));
        contractDao.setAutomaticDebitIndicatorType((response.getPaymentMethod().getPaymentType().equals(ConstantsUtil.PAYMENT_METHOD_VALUE))
                ? ConstantsUtil.S_VALUE : ConstantsUtil.N_VALUE);


        if (nonNull(response.getBusinessAgent())) {
            contractDao.setInsuranceManagerId(response.getBusinessAgent().getId());
        }

        if (nonNull(response.getPromoter())) {
            contractDao.setInsurancePromoterId(response.getPromoter().getId());
        }

        contractDao.setPrevPendBillRcptsNumber(getPrevPendBillRcptsNumber(response, quotationDAO));

        return contractDao;
    }

    private static String getDomicileContractId(List<RelatedContractDTO> relatedContractList){
        if(CollectionUtils.isEmpty(relatedContractList)) return null;
        return relatedContractList.get(0).getContractDetails().getNumber();
    }

    private static BigDecimal getPrevPendBillRcptsNumber(PolicyDTO preformalizationBody, QuotationDAO quotationDAO) {

        BigDecimal prevPendBillRcptsNumber;
        boolean isPaymentRequired = preformalizationBody.getFirstInstallment().getIsPaymentRequired();
        Long totalNumberInstallments = preformalizationBody.getInstallmentPlan().getTotalNumberInstallments();

        if ("VIDA".equals(quotationDAO.getInsuranceBusinessName())) {
            prevPendBillRcptsNumber = quotationDAO.getContractDurationNumber();
        } else {
            prevPendBillRcptsNumber = BigDecimal.valueOf(isPaymentRequired ? totalNumberInstallments - 1 : totalNumberInstallments);
        }

        return prevPendBillRcptsNumber;
    }

}
