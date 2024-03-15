package com.bbva.rbvd.dummies;

import com.bbva.rbvd.builders.BankBuilder;
import com.bbva.rbvd.builders.ContractDetailsBuilder;
import com.bbva.rbvd.builders.FirstInstallmentBuilder;
import com.bbva.rbvd.builders.HolderBuilder;
import com.bbva.rbvd.builders.IdentityDocumentBuilder;
import com.bbva.rbvd.builders.InsuredAmountBuilder;
import com.bbva.rbvd.builders.ParticipantBuilder;
import com.bbva.rbvd.builders.ParticipantTypeBuilder;
import com.bbva.rbvd.builders.PaymentAmountBuilder;
import com.bbva.rbvd.builders.PolicyInstallmentPlanBuilder;
import com.bbva.rbvd.builders.PolicyPaymentMethodBuilder;
import com.bbva.rbvd.builders.PreformalizationRequestBuilder;
import com.bbva.rbvd.builders.ProductBuilder;
import com.bbva.rbvd.builders.RelatedContractBuilder;
import com.bbva.rbvd.builders.TotalAmountBuilder;
import com.bbva.rbvd.builders.ValidityPeriodBuilder;
import com.bbva.rbvd.dto.preformalization.PreformalizationDTO;

import java.util.Calendar;
import java.util.Date;

import static java.lang.Integer.parseInt;

public class PreformalizationRequestDummy {
    public static String id = "123";
    public static String documentType = "DNI";
    public static String documentNumber = "72781801";
    public static Long totalNumberOfInstallments = 10L;
    public static Double paymentAmount = 100.0;
    public static String paymentCurrency = "PEN";

    public static PreformalizationDTO preformalizationRequest = PreformalizationRequestBuilder.instance()
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
                            .withId("PAYMENT_MANAGER")
                            .build())
                    .withId(id)
                    .withIdentityDocument(IdentityDocumentBuilder.instance()
                            .withDocumentType(documentType)
                            .withDocumentNumber(documentNumber)
                            .build())
                    .build())
            .addParticipant(ParticipantBuilder.instance()
                    .withType(ParticipantTypeBuilder.instance()
                            .withId("LEGAL_REPRESENTATIVE")
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
                            .withContractType("INTERNAL_CONTRACT")
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
