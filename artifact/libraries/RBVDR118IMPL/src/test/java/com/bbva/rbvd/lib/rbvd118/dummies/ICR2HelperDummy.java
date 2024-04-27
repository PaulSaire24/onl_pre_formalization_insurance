package com.bbva.rbvd.lib.rbvd118.dummies;

import com.bbva.rbvd.dto.insrncsale.commons.IdentityDocumentDTO;
import com.bbva.rbvd.dto.insrncsale.commons.PaymentAmountDTO;
import com.bbva.rbvd.dto.insrncsale.policy.ParticipantTypeDTO;
import com.bbva.rbvd.dto.preformalization.dto.InsuranceDTO;
import com.bbva.rbvd.lib.rbvd118.builders.BankBuilder;
import com.bbva.rbvd.lib.rbvd118.builders.ContractDetailsBuilder;
import com.bbva.rbvd.lib.rbvd118.builders.FirstInstallmentBuilder;
import com.bbva.rbvd.lib.rbvd118.builders.HolderBuilder;
import com.bbva.rbvd.lib.rbvd118.builders.IdentityDocumentBuilder;
import com.bbva.rbvd.lib.rbvd118.builders.InsuredAmountBuilder;
import com.bbva.rbvd.lib.rbvd118.builders.ParticipantBuilder;
import com.bbva.rbvd.lib.rbvd118.builders.ParticipantTypeBuilder;
import com.bbva.rbvd.lib.rbvd118.builders.PaymentAmountBuilder;
import com.bbva.rbvd.lib.rbvd118.builders.PolicyInstallmentPlanBuilder;
import com.bbva.rbvd.lib.rbvd118.builders.PolicyPaymentMethodBuilder;
import com.bbva.rbvd.lib.rbvd118.builders.PreformalizationRequestBuilder;
import com.bbva.rbvd.lib.rbvd118.builders.ProductBuilder;
import com.bbva.rbvd.lib.rbvd118.builders.RelatedContractBuilder;
import com.bbva.rbvd.lib.rbvd118.builders.TotalAmountBuilder;
import com.bbva.rbvd.lib.rbvd118.builders.ValidityPeriodBuilder;
import com.bbva.rbvd.lib.rbvd118.impl.util.ConstantsUtil;

import java.util.Calendar;
import java.util.Date;

import static java.lang.Integer.parseInt;

public class ICR2HelperDummy {
    public static String id = "123";
    public static String documentType = "DNI";
    public static String documentNumber = "72781801";
    public static Date startDate = getDateFromString("2023-01-01");
    public static Long totalNumberOfInstallments = 10L;
    public static Double paymentAmount = 100.0;
    public static String paymentCurrency = "PEN";

    public static ParticipantTypeDTO legalRepresentativeParticipantTypeDTO = ParticipantTypeBuilder.instance()
            .withId(ConstantsUtil.Participant.LEGAL_REPRESENTATIVE)
            .build();

    public static ParticipantTypeDTO paymentManagerParticipantTypeDTO = ParticipantTypeBuilder.instance()
            .withId(ConstantsUtil.Participant.PAYMENT_MANAGER)
            .build();

    public static IdentityDocumentDTO identityDocumentDTO = IdentityDocumentBuilder.instance()
            .withDocumentType(documentType)
            .withDocumentNumber(documentNumber)
            .build();

    public static PaymentAmountDTO paymentAmountDTO = PaymentAmountBuilder.instance()
            .withAmount(paymentAmount)
            .withCurrency(paymentCurrency)
            .build();

    public static InsuranceDTO preformalizationRequest = PreformalizationRequestBuilder.instance()
            .withPolicyNumber(PreformalizationRequest.policyNumber)
            .withProduct(ProductBuilder.instance()
                    .withId(PreformalizationRequest.productId)
                    .withPlan(PreformalizationRequest.planId)
                    .build())
            .withValidityPeriod(ValidityPeriodBuilder.instance()
                    .withStartDate(getDateFromString(PreformalizationRequest.validityPeriod))
                    .build())
            .withFirstInstallment(FirstInstallmentBuilder.instance()
                    .withIsPaymentRequired(PreformalizationRequest.isPaymentRequired)
                    .withPaymentAmount(PaymentAmountBuilder.instance()
                            .withAmount(paymentAmount)
                            .withCurrency(paymentCurrency)
                            .build())
                    .build())
            .withTotalAmount(TotalAmountBuilder.instance()
                    .withAmount(paymentAmount)
                    .build())
            .withInstallmentPlan(PolicyInstallmentPlanBuilder.instance()
                    .withTotalNumberOfInstallments(totalNumberOfInstallments)
                    .withPaymentAmount(PaymentAmountBuilder.instance()
                            .withAmount(paymentAmount)
                            .withCurrency(paymentCurrency)
                            .build())
                    .withPeriod(id)
                    .build())
            .withInsuredAmount(InsuredAmountBuilder.instance()
                    .withAmount(paymentAmount)
                    .withCurrency(paymentCurrency)
                    .build())
            .withHolder(HolderBuilder.instance()
                    .withId(id)
                    .build())
            .withBusinessAgent(PreformalizationRequest.businessAgent)
            .withPromoter(PreformalizationRequest.promoter)
            .withBank(BankBuilder.instance()
                    .withId(PreformalizationRequest.bank)
                    .withBranch(PreformalizationRequest.branch)
                    .build())
            .withInsuranceCompany(PreformalizationRequest.insuranceCompany)
            .withQuotationId(PreformalizationRequest.quotationId)
            .withPaymentMethod(PolicyPaymentMethodBuilder.instance()
                    .withPaymentType("123")
                    .withInstallmentFrequency("12")
                    .build())
            .addParticipant(ParticipantBuilder.instance()
                    .withType(ParticipantTypeBuilder.instance()
                            .withId(ConstantsUtil.Participant.PAYMENT_MANAGER)
                            .build())
                    .withId(id)
                    .withIdentityDocument(IdentityDocumentBuilder.instance()
                            .withDocumentType(documentType)
                            .withDocumentNumber(documentNumber)
                            .build())
                    .build())
            .addParticipant(ParticipantBuilder.instance()
                    .withType(ParticipantTypeBuilder.instance()
                            .withId(ConstantsUtil.Participant.LEGAL_REPRESENTATIVE)
                            .build())
                    .withId(id)
                    .withIdentityDocument(IdentityDocumentBuilder.instance()
                            .withDocumentType(documentType)
                            .withDocumentNumber(documentNumber)
                            .build())
                    .build())
            .addRelatedContract(RelatedContractBuilder.instance()
                    .withRelationType(id)
                    .withContractDetails(ContractDetailsBuilder.instance()
                            .withContractType(ConstantsUtil.RelatedContractType.INTERNAL_CONTRACT)
                            .withContractId(id)
                            .withNumber(id)
                            .withNumberType(id)
                            .withProductType(id)
                            .build())
                    .build())
            .build();

    public static class PreformalizationRequest {
        public static String policyNumber = "123";
        public static String productId = "123";
        public static String validityPeriod = "2023-01-01";
        public static String planId = "123";
        public static Boolean isPaymentRequired = true;
        public static String businessAgent = "123";
        public static String promoter = "123";
        public static String bank = "123";
        public static String branch = "123";
        public static String insuranceCompany = "123";
        public static String quotationId = "123";
    }

    public static Date getDateFromString(String date) {
        Calendar calendar = Calendar.getInstance();
        String[] dateParts = date.split("-");
        calendar.set(Calendar.YEAR, parseInt(dateParts[0]));
        calendar.set(Calendar.MONTH, parseInt(dateParts[1]) - 1);
        calendar.set(Calendar.DATE, parseInt(dateParts[2]));
        return calendar.getTime();
    }
}
