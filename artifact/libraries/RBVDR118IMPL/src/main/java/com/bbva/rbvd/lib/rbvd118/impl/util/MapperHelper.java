package com.bbva.rbvd.lib.rbvd118.impl.util;

import com.bbva.elara.configuration.manager.application.ApplicationConfigurationService;
import com.bbva.pisd.dto.insurancedao.entities.PaymentPeriodEntity;
import com.bbva.rbvd.dto.cicsconnection.icr2.ICMRYS2;
import com.bbva.rbvd.dto.cicsconnection.icr2.ICR2Response;
import com.bbva.rbvd.dto.insrncsale.dao.InsuranceContractDAO;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDProperties;
import com.bbva.rbvd.dto.preformalization.dao.QuotationDAO;
import com.bbva.rbvd.dto.preformalization.dto.InsuranceDTO;
import org.joda.time.LocalDate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

public class MapperHelper {
    private static final String GMT_TIME_ZONE = "GMT";
    private static final String S_VALUE = "S";
    private static final String N_VALUE = "N";
    private static final String PAYMENT_METHOD_VALUE = "DIRECT_DEBIT";
    ApplicationConfigurationService applicationConfigurationService;

    //Borrar este método
    public Map<String, Object> createSingleArgument(String argument, String parameterName) {
        Map<String, Object> mapArgument = new HashMap<>();
        if (RBVDProperties.FIELD_POLICY_PAYMENT_FREQUENCY_TYPE.getValue().equals(parameterName)) {
            String frequencyType = applicationConfigurationService.getProperty(argument);
            mapArgument.put(parameterName, frequencyType);
            return mapArgument;
        }
        mapArgument.put(parameterName, argument);
        return mapArgument;
    }

    //Revisar este método, Las validaciones en métodos a parte.
    //Este método mover a otra clase
    public InsuranceContractDAO buildInsuranceContract(InsuranceDTO preformalizationBody, QuotationDAO emissionDao,
                                   ICR2Response icr2Response, Boolean isEndorsement, PaymentPeriodEntity paymentPeriod) {
        InsuranceContractDAO contractDao = new InsuranceContractDAO();
        ICMRYS2 icmrys2 = icr2Response.getIcmrys2();
        String currentDate = ConvertUtil.generateCorrectDateFormat(LocalDate.now());

        contractDao.setEntityId(icmrys2.getNUMCON().substring(0, 4));
        contractDao.setBranchId(icmrys2.getNUMCON().substring(4, 8));
        contractDao.setIntAccountId(icmrys2.getNUMCON().substring(10));
        contractDao.setFirstVerfnDigitId(icmrys2.getNUMCON().substring(8, 9));
        contractDao.setSecondVerfnDigitId(icmrys2.getNUMCON().substring(9, 10));
        contractDao.setPolicyQuotaInternalId(preformalizationBody.getQuotationId());
        contractDao.setInsuranceProductId(emissionDao.getInsuranceProductId());
        contractDao.setInsuranceModalityType(preformalizationBody.getProduct().getPlan().getId());
        contractDao.setInsuranceCompanyId(new BigDecimal(preformalizationBody.getInsuranceCompany().getId()));
        contractDao.setCustomerId(preformalizationBody.getHolder().getId());
        contractDao.setDomicileContractId(preformalizationBody.getRelatedContracts().get(0).getContractId());
        contractDao.setIssuedReceiptNumber(BigDecimal.valueOf(preformalizationBody.getInstallmentPlan().getTotalNumberInstallments()));
        contractDao.setPaymentFrequencyId(paymentPeriod.getPaymentFrequencyId());
        contractDao.setPremiumAmount(BigDecimal.valueOf(preformalizationBody.getFirstInstallment().getPaymentAmount().getAmount()));
        contractDao.setSettlePendingPremiumAmount(BigDecimal.valueOf(preformalizationBody.getTotalAmount().getAmount()));
        contractDao.setCurrencyId(preformalizationBody.getInstallmentPlan().getPaymentAmount().getCurrency());
        contractDao.setInstallmentPeriodFinalDate(currentDate);
        contractDao.setInsuredAmount(BigDecimal.valueOf(preformalizationBody.getInsuredAmount().getAmount()));
        contractDao.setCtrctDisputeStatusType(preformalizationBody.getSaleChannelId());
        contractDao.setEndorsementPolicyIndType((isEndorsement) ? S_VALUE : N_VALUE);
        contractDao.setInsrncCoContractStatusType("ERR");
        contractDao.setCreationUserId(preformalizationBody.getCreationUser());
        contractDao.setUserAuditId(preformalizationBody.getUserAudit());
        contractDao.setInsurPendingDebtIndType((preformalizationBody.getFirstInstallment().getIsPaymentRequired())
                ? N_VALUE : S_VALUE);
        contractDao.setContractManagerBranchId(preformalizationBody.getBank().getBranch().getId());
        contractDao.setContractInceptionDate(currentDate);
        contractDao.setSettlementFixPremiumAmount(BigDecimal.valueOf(preformalizationBody.getTotalAmount().getAmount()));
        contractDao.setBiometryTransactionId(preformalizationBody.getIdentityVerificationCode());

        if (nonNull(preformalizationBody.getBusinessAgent())) {
            contractDao.setInsuranceManagerId(preformalizationBody.getBusinessAgent().getId());
        }

        if (nonNull(preformalizationBody.getPromoter())) {
            contractDao.setInsurancePromoterId(preformalizationBody.getPromoter().getId());
        }

        contractDao.setEndLinkageDate(ConvertUtil.generateCorrectDateFormat(
                ConvertUtil.convertDateToLocalDate(preformalizationBody.getInstallmentPlan().getMaturityDate())));

        contractDao.setInsuranceContractStartDate(ConvertUtil.generateCorrectDateFormat(
                ConvertUtil.convertDateToLocalDate(preformalizationBody.getInsuranceValidityPeriod().getStartDate())));

        contractDao.setValidityMonthsNumber(emissionDao.getContractDurationType().equals("A")
                ? emissionDao.getContractDurationNumber().multiply(BigDecimal.valueOf(12))
                : emissionDao.getContractDurationNumber());

        contractDao.setTotalDebtAmount((preformalizationBody.getFirstInstallment().getIsPaymentRequired())
                ? BigDecimal.ZERO : BigDecimal.valueOf(preformalizationBody.getFirstInstallment().getPaymentAmount().getAmount()));

        validatePrevPendBillRcptsNumber(preformalizationBody, emissionDao, contractDao);

        contractDao.setAutomaticDebitIndicatorType((preformalizationBody.getPaymentMethod().getPaymentType().equals(PAYMENT_METHOD_VALUE))
                ? S_VALUE : N_VALUE);

        return contractDao;
    }

    public void validatePrevPendBillRcptsNumber(InsuranceDTO preformalizationBody, QuotationDAO emissionDao,
                                                InsuranceContractDAO contractDao) {
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

        contractDao.setPrevPendBillRcptsNumber(prevPendBillRcptsNumber);
    }




}
