package com.bbva.rbvd.lib.r415.impl.transform.bean;

import com.bbva.pisd.dto.insurancedao.entities.PaymentPeriodEntity;
import com.bbva.rbvd.dto.cicsconnection.icr2.ICMRYS2;
import com.bbva.rbvd.dto.cicsconnection.icr2.ICR2Response;
import com.bbva.rbvd.dto.insrncsale.dao.InsuranceContractDAO;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyDTO;
import com.bbva.rbvd.dto.insrncsale.policy.RelatedContractDTO;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDProperties;
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


    public static InsuranceContractDAO buildInsuranceContract(PolicyDTO input, QuotationDAO quotationDAO,
                                                              ICR2Response icr2Response, boolean isEndorsement, PaymentPeriodEntity paymentPeriod) {

        InsuranceContractDAO contractDao = new InsuranceContractDAO();
        ICMRYS2 icmrys2 = icr2Response.getIcmrys2();
        String currentDate = ConvertUtil.generateCorrectDateFormat(new LocalDate());

        contractDao.setEntityId(icmrys2.getNUMCON().substring(0, 4)); //0011
        contractDao.setBranchId(icmrys2.getNUMCON().substring(4, 8)); //0284
        contractDao.setIntAccountId(icmrys2.getNUMCON().substring(10)); //4000023990
        contractDao.setFirstVerfnDigitId(icmrys2.getNUMCON().substring(8, 9)); //7
        contractDao.setSecondVerfnDigitId(icmrys2.getNUMCON().substring(9, 10)); //1
        contractDao.setPolicyQuotaInternalId(input.getQuotationNumber()); //4000023990
        contractDao.setInsuranceProductId(quotationDAO.getInsuranceProductId()); //13
        contractDao.setInsuranceModalityType(input.getProduct().getPlan().getId()); //01
        contractDao.setInsuranceCompanyId(ConvertUtil.getBigDecimalValue(input.getInsuranceCompany().getId())); //1
        contractDao.setCustomerId(input.getHolder().getId()); //20859410
        contractDao.setDomicileContractId(getDomicileContractId(input.getRelatedContracts())); //tamal
        contractDao.setIssuedReceiptNumber(ConvertUtil.getBigDecimalValue(input.getInstallmentPlan().getTotalNumberInstallments())); //1
        contractDao.setPaymentFrequencyId(paymentPeriod.getPaymentFrequencyId()); //3
        contractDao.setPremiumAmount(ConvertUtil.getBigDecimalValue(input.getFirstInstallment().getPaymentAmount().getAmount())); //4956.63
        contractDao.setSettlePendingPremiumAmount(ConvertUtil.getBigDecimalValue(input.getTotalAmount().getAmount())); //4956.63
        contractDao.setCurrencyId(input.getInstallmentPlan().getPaymentAmount().getCurrency()); //PEN
        contractDao.setInstallmentPeriodFinalDate(currentDate);
        contractDao.setInsuredAmount(ConvertUtil.getBigDecimalValue(input.getInsuredAmount().getAmount())); //10000
        contractDao.setCtrctDisputeStatusType(input.getSaleChannelId()); //PC
        contractDao.setEndorsementPolicyIndType((isEndorsement) ? ConstantsUtil.S_VALUE : ConstantsUtil.N_VALUE); //N
        contractDao.setInsrncCoContractStatusType(ConstantsUtil.StatusContract.PENDIENTE.getValue()); //PEN
        contractDao.setContractStatusId(ConstantsUtil.StatusContract.PENDIENTE.getValue()); //PEN
        contractDao.setCreationUserId(input.getCreationUser()); //userxd
        contractDao.setUserAuditId(input.getUserAudit()); //userxd
        contractDao.setInsurPendingDebtIndType((input.getFirstInstallment().getIsPaymentRequired())
                ? ConstantsUtil.N_VALUE : ConstantsUtil.S_VALUE); //S
        contractDao.setContractManagerBranchId(input.getBank().getBranch().getId()); //0284
        contractDao.setContractInceptionDate(currentDate);
        contractDao.setSettlementFixPremiumAmount(ConvertUtil.getBigDecimalValue(input.getTotalAmount().getAmount())); //total
        contractDao.setBiometryTransactionId(input.getIdentityVerificationCode()); //codigo raro xd
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
