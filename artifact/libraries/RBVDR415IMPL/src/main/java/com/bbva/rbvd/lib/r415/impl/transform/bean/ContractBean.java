package com.bbva.rbvd.lib.r415.impl.transform.bean;

import com.bbva.pisd.dto.insurancedao.entities.PaymentPeriodEntity;
import com.bbva.rbvd.dto.cicsconnection.icr2.ICMRYS2;
import com.bbva.rbvd.dto.cicsconnection.icr2.ICR2Response;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyDTO;
import com.bbva.rbvd.dto.insrncsale.policy.RelatedContractDTO;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDProperties;
import com.bbva.rbvd.dto.preformalization.dao.ContractDAO;
import com.bbva.rbvd.dto.preformalization.dao.QuotationDAO;
import com.bbva.rbvd.dto.preformalization.util.ConstantsUtil;
import com.bbva.rbvd.lib.r415.impl.util.ConvertUtil;
import org.joda.time.LocalDate;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Objects.nonNull;

public class ContractBean {

    private ContractBean(){}


    public static ContractDAO buildInsuranceContract(PolicyDTO input, QuotationDAO quotationDAO,
                                                     ICR2Response icr2Response, boolean isEndorsement, PaymentPeriodEntity paymentPeriod) {

        ContractDAO contractDao = new ContractDAO();
        ICMRYS2 icmrys2 = icr2Response.getIcmrys2();
        String currentDate = ConvertUtil.generateCorrectDateFormat(new LocalDate());

        contractDao.setEntityId(icmrys2.getNUMCON().substring(0, 4));
        contractDao.setBranchId(icmrys2.getNUMCON().substring(4, 8));
        contractDao.setIntAccountId(icmrys2.getNUMCON().substring(10));
        contractDao.setFirstVerfnDigitId(icmrys2.getNUMCON().substring(8, 9));
        contractDao.setSecondVerfnDigitId(icmrys2.getNUMCON().substring(9, 10));
        contractDao.setPolicyQuotaInternalId(input.getQuotationNumber());
        contractDao.setInsuranceProductId(quotationDAO.getInsuranceProductId());
        contractDao.setInsuranceModalityType(input.getProduct().getPlan().getId());
        contractDao.setInsuranceCompanyId(ConvertUtil.getBigDecimalValue(input.getInsuranceCompany().getId()));
        contractDao.setCustomerId(input.getHolder().getId());
        contractDao.setDomicileContractId(getDomicileContractId(input.getRelatedContracts()));
        contractDao.setIssuedReceiptNumber(ConvertUtil.getBigDecimalValue(input.getInstallmentPlan().getTotalNumberInstallments()));
        contractDao.setPaymentFrequencyId(paymentPeriod.getPaymentFrequencyId());
        contractDao.setPremiumAmount(ConvertUtil.getBigDecimalValue(input.getFirstInstallment().getPaymentAmount().getAmount()));
        contractDao.setSettlePendingPremiumAmount(ConvertUtil.getBigDecimalValue(input.getTotalAmount().getAmount()));
        contractDao.setCurrencyId(input.getInstallmentPlan().getPaymentAmount().getCurrency());
        contractDao.setInstallmentPeriodFinalDate(currentDate);
        contractDao.setInsuredAmount(ConvertUtil.getBigDecimalValue(input.getInsuredAmount().getAmount()));
        contractDao.setCtrctDisputeStatusType(input.getSaleChannelId());
        contractDao.setEndorsementPolicyIndType((isEndorsement) ? ConstantsUtil.S_VALUE : ConstantsUtil.N_VALUE);
        contractDao.setInsrncCoContractStatusType(ConstantsUtil.StatusContract.PENDIENTE.getValue());
        contractDao.setContractStatusId(ConstantsUtil.StatusContract.PENDIENTE.getValue());
        contractDao.setCreationUserId(input.getCreationUser());
        contractDao.setUserAuditId(input.getUserAudit());
        contractDao.setInsurPendingDebtIndType((input.getFirstInstallment().getIsPaymentRequired())
                ? ConstantsUtil.N_VALUE : ConstantsUtil.S_VALUE);
        contractDao.setContractManagerBranchId(input.getBank().getBranch().getId());
        contractDao.setContractInceptionDate(currentDate);
        contractDao.setSettlementFixPremiumAmount(ConvertUtil.getBigDecimalValue(input.getTotalAmount().getAmount()));
        contractDao.setBiometryTransactionId(input.getIdentityVerificationCode());
        contractDao.setEndLinkageDate(ConvertUtil.generateCorrectDateFormat(
                ConvertUtil.convertDateToLocalDate(input.getInstallmentPlan().getMaturityDate())));
        contractDao.setInsuranceContractStartDate(ConvertUtil.generateCorrectDateFormat(
                ConvertUtil.convertDateToLocalDate(input.getValidityPeriod().getStartDate())));
        contractDao.setValidityMonthsNumber(quotationDAO.getContractDurationType().equals("A")
                ? quotationDAO.getContractDurationNumber().multiply(BigDecimal.valueOf(12))
                : quotationDAO.getContractDurationNumber());
        contractDao.setTotalDebtAmount((input.getFirstInstallment().getIsPaymentRequired())
                ? BigDecimal.ZERO : ConvertUtil.getBigDecimalValue(input.getFirstInstallment().getPaymentAmount().getAmount()));
        contractDao.setAutomaticDebitIndicatorType((input.getPaymentMethod().getPaymentType().equals(ConstantsUtil.PAYMENT_METHOD_VALUE))
                ? ConstantsUtil.S_VALUE : ConstantsUtil.N_VALUE);


        if (nonNull(input.getBusinessAgent())) {
            contractDao.setInsuranceManagerId(input.getBusinessAgent().getId());
        }

        if (nonNull(input.getPromoter())) {
            contractDao.setInsurancePromoterId(input.getPromoter().getId());
        }

        contractDao.setPrevPendBillRcptsNumber(getPrevPendBillRcptsNumber(input, quotationDAO));

        return contractDao;
    }

    private static String getDomicileContractId(List<RelatedContractDTO> relatedContractList){
        if(CollectionUtils.isEmpty(relatedContractList)) return null;
        return relatedContractList.get(0).getContractDetails().getContractId();
    }

    private static BigDecimal getPrevPendBillRcptsNumber(PolicyDTO preformalizationBody, QuotationDAO emissionDao) {

        BigDecimal prevPendBillRcptsNumber;
        String productId = preformalizationBody.getProduct().getId();
        boolean isPaymentRequired = preformalizationBody.getFirstInstallment().getIsPaymentRequired();
        Long totalNumberInstallments = preformalizationBody.getInstallmentPlan().getTotalNumberInstallments();

        if (productId.equals(RBVDProperties.INSURANCE_PRODUCT_TYPE_VIDA_EASYYES.getValue())
                || productId.equals(RBVDProperties.INSURANCE_PRODUCT_TYPE_VIDA_2.getValue())) {
            prevPendBillRcptsNumber = emissionDao.getContractDurationNumber();
        } else {
            prevPendBillRcptsNumber = BigDecimal.valueOf(isPaymentRequired ? totalNumberInstallments - 1 : totalNumberInstallments);
        }

        return prevPendBillRcptsNumber;
    }

}
