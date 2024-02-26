package com.bbva.rbvd.lib.r118.impl.util;

import com.bbva.elara.configuration.manager.application.ApplicationConfigurationService;
import com.bbva.pisd.dto.insurance.utils.PISDProperties;
import com.bbva.rbvd.dto.insrncsale.dao.RequiredFieldsEmissionDAO;
import com.bbva.rbvd.dto.insrncsale.policy.ParticipantDTO;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyDTO;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDErrors;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDProperties;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDValidation;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.temporal.ValueRange;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.util.CollectionUtils.isEmpty;

public class ValidationUtil {

    public static ParticipantDTO filterParticipantByType(List<ParticipantDTO> participants, String participantType) {
        if (!CollectionUtils.isEmpty(participants)) {
            Optional<ParticipantDTO> participant = participants.stream()
                    .filter(participantDTO -> participantType.equals(participantDTO.getParticipantType().getId())).findFirst();
            return participant.orElse(null);
        } else {
            return null;
        }
    }

    public static List<ParticipantDTO> filterBenficiaryType(List<ParticipantDTO> participants) {
        if (!CollectionUtils.isEmpty(participants)) {
            List<ParticipantDTO> participant = participants.stream()
                    .filter(participantDTO -> ConstantsUtil.Participant.BENEFICIARY.equals(participantDTO.getParticipantType().getId())).collect(Collectors.toList());
            return participant.isEmpty() ? null : participant;
        } else {
            return null;
        }
    }

    public static boolean validateOtherParticipants(ParticipantDTO participantDTO, String participantType) {
        if (participantType.equals(participantDTO.getParticipantType().getId())) {
            return participantDTO.getIdentityDocument() != null
                    && participantDTO.getIdentityDocument().getDocumentType().getId() != null
                    && participantDTO.getIdentityDocument().getNumber() != null;
        }
        return false;
    }

    public static boolean validateEndorsementInParticipantsRequest(PolicyDTO requestBody) {
        if (Objects.nonNull(filterParticipantByType(requestBody.getParticipants(), ConstantsUtil.Participant.ENDORSEE))) {
            ParticipantDTO endorseParticipant = filterParticipantByType(requestBody.getParticipants(), ConstantsUtil.Participant.ENDORSEE);
            return endorseParticipant != null
                    && endorseParticipant.getIdentityDocument() != null
                    && Objects.nonNull(endorseParticipant.getIdentityDocument().getDocumentType().getId())
                    && ConstantsUtil.DocumentType.RUC.equals(endorseParticipant.getIdentityDocument().getDocumentType().getId())
                    && Objects.nonNull(endorseParticipant.getIdentityDocument().getNumber())
                    && Objects.nonNull(endorseParticipant.getBenefitPercentage());
        }
        return false;
    }

    public static boolean validateisNotEmptyOrNull(String parameter) {
        return (parameter != null && !parameter.isEmpty());
    }

    public static boolean validateIsNotNull(Object parameter) {
        return nonNull(parameter);
    }

    public static void validateIfPolicyExists(Map<String, Object> responseValidateIfPolicyExists) {
        BigDecimal resultNumber = (BigDecimal) responseValidateIfPolicyExists.get(RBVDProperties.FIELD_RESULT_NUMBER.getValue());
        if (nonNull(resultNumber) && resultNumber.compareTo(BigDecimal.ONE) == 0) {
            throw RBVDValidation.build(RBVDErrors.POLICY_ALREADY_EXISTS);
        }
    }

    public static RequiredFieldsEmissionDAO validateResponseQueryGetRequiredFields(Map<String, Object> responseQueryGetRequiredFields, Map<String, Object> responseQueryGetPaymentPeriod) {
        if (isEmpty(responseQueryGetRequiredFields)) {
            throw RBVDValidation.build(RBVDErrors.NON_EXISTENT_QUOTATION);
        }
        RequiredFieldsEmissionDAO emissionDao = new RequiredFieldsEmissionDAO();
        emissionDao.setInsuranceProductId((BigDecimal) responseQueryGetRequiredFields.get(RBVDProperties.FIELD_INSURANCE_PRODUCT_ID.getValue()));
        emissionDao.setContractDurationNumber((BigDecimal) responseQueryGetRequiredFields.get(RBVDProperties.FIELD_CONTRACT_DURATION_NUMBER.getValue()));
        emissionDao.setContractDurationType((String) responseQueryGetRequiredFields.get(RBVDProperties.FIELD_CONTRACT_DURATION_TYPE.getValue()));
        emissionDao.setPaymentFrequencyId((BigDecimal) responseQueryGetPaymentPeriod.get(RBVDProperties.FIELD_PAYMENT_FREQUENCY_ID.getValue()));
        emissionDao.setInsuranceCompanyQuotaId((String) responseQueryGetRequiredFields.get(RBVDProperties.FIELD_INSURANCE_COMPANY_QUOTA_ID.getValue()));
        emissionDao.setInsuranceProductDesc((String) responseQueryGetRequiredFields.get(PISDProperties.FIELD_INSURANCE_PRODUCT_DESC.getValue()));
        emissionDao.setInsuranceModalityName((String) responseQueryGetRequiredFields.get(PISDProperties.FIELD_INSURANCE_MODALITY_NAME.getValue()));
        emissionDao.setPaymentFrequencyName((String) responseQueryGetPaymentPeriod.get(PISDProperties.FIELD_PAYMENT_FREQUENCY_NAME.getValue()));
        emissionDao.setVehicleBrandName((String) responseQueryGetRequiredFields.get(PISDProperties.FIELD_VEHICLE_BRAND_NAME.getValue()));
        emissionDao.setVehicleModelName((String) responseQueryGetRequiredFields.get(PISDProperties.FIELD_VEHICLE_MODEL_NAME.getValue()));
        emissionDao.setVehicleYearId((String) responseQueryGetRequiredFields.get(PISDProperties.FIELD_VEHICLE_YEAR_ID.getValue()));
        emissionDao.setVehicleLicenseId((String) responseQueryGetRequiredFields.get(PISDProperties.FIELD_VEHICLE_LICENSE_ID.getValue()));
        emissionDao.setGasConversionType((String) responseQueryGetRequiredFields.get(PISDProperties.FIELD_VEHICLE_GAS_CONVERSION_TYPE.getValue()));
        emissionDao.setVehicleCirculationType((String) responseQueryGetRequiredFields.get(PISDProperties.FIELD_VEHICLE_CIRCULATION_SCOPE_TYPE.getValue()));
        emissionDao.setCommercialVehicleAmount((BigDecimal) responseQueryGetRequiredFields.get(PISDProperties.FIELD_COMMERCIAL_VEHICLE_AMOUNT.getValue()));

        return emissionDao;
    }

    public static void validateAmountQuotation(Map<String, Object> quotation, PolicyDTO request, ApplicationConfigurationService applicationConfigurationService) {
        if (Objects.isNull(quotation.get(ConstantsUtil.RBVDR118.FIELD_POLICY_PAYMENT_FREQUENCY_TYPE)) ||
                Objects.isNull(quotation.get(ConstantsUtil.RBVDR118.FIELD_PREMIUM_CURRENCY_ID)) ||
                Objects.isNull(quotation.get(ConstantsUtil.RBVDR118.FIELD_PREMIUM_AMOUNT)))
            throw RBVDValidation.build(RBVDErrors.QUERY_EMPTY_RESULT);

        String frequency = quotation.get(ConstantsUtil.RBVDR118.FIELD_POLICY_PAYMENT_FREQUENCY_TYPE).toString();

        int paymentAmount = ((BigDecimal) quotation.get(ConstantsUtil.RBVDR118.FIELD_PREMIUM_AMOUNT)).intValue();
        String paymentCurrency = quotation.get(ConstantsUtil.RBVDR118.FIELD_PREMIUM_CURRENCY_ID).toString();

        int rangePaymentAmount = Integer.parseInt(applicationConfigurationService.getDefaultProperty(ConstantsUtil.RBVDR118.PROPERTY_RANGE_PAYMENT_AMOUNT, "5"));

        Integer amountQuotationMin = ((100 - rangePaymentAmount) * paymentAmount) / 100;
        Integer amountQuotationMax = ((100 + rangePaymentAmount) * paymentAmount) / 100;
        Integer amountTotalAmountMin = ((100 - rangePaymentAmount) * paymentAmount * 12) / 100;
        Integer amountTotalAmountMax = ((100 + rangePaymentAmount) * paymentAmount * 12) / 100;

        if (frequency.equals("A") && !(paymentCurrency.equals(request.getTotalAmount().getCurrency()) &&
                isValidateRange(request.getTotalAmount().getAmount().intValue(), amountQuotationMin, amountQuotationMax))) {
            throw RBVDValidation.build(RBVDErrors.BAD_REQUEST_CREATEINSURANCE);
        }

        if (frequency.equals("M") && !(paymentCurrency.equals(request.getTotalAmount().getCurrency()) &&
                isValidateRange(request.getTotalAmount().getAmount().intValue(), amountTotalAmountMin, amountTotalAmountMax))) {
            throw RBVDValidation.build(RBVDErrors.BAD_REQUEST_CREATEINSURANCE);
        }

        if (!(isValidateRange(request.getFirstInstallment().getPaymentAmount().getAmount().intValue(), amountQuotationMin, amountQuotationMax) &&
                paymentCurrency.equals(request.getFirstInstallment().getPaymentAmount().getCurrency()))) {
            throw RBVDValidation.build(RBVDErrors.BAD_REQUEST_CREATEINSURANCE);
        }

        if (!(isValidateRange(request.getInstallmentPlan().getPaymentAmount().getAmount().intValue(), amountQuotationMin, amountQuotationMax) &&
                paymentCurrency.equals(request.getInstallmentPlan().getPaymentAmount().getCurrency()))) {
            throw RBVDValidation.build(RBVDErrors.BAD_REQUEST_CREATEINSURANCE);
        }
    }

    public static boolean isValidateRange(Integer value, Integer min, Integer max) {
        final ValueRange range = ValueRange.of(min, max);
        return range.isValidIntValue(value);
    }

    public static void validateInsertion(int insertedRows, RBVDErrors error) {
        if (insertedRows != 1) {
            throw RBVDValidation.build(error);
        }
    }

    public static void validateMultipleInsertion(int[] insertedRows, RBVDErrors error) {
        if (isNull(insertedRows) || insertedRows.length == 0) {
            throw RBVDValidation.build(error);
        }
    }

    public static void updatePaymentRequirementStatus(PolicyDTO requestBody) {
        DateTimeZone dateTimeZone = DateTimeZone.forID(ConstantsUtil.RBVDR118.LIMA_TIME_ZONE);

        DateTime currentLocalDate = new DateTime(new Date(), dateTimeZone);
        Date currentDate = currentLocalDate.toDate();

        dateTimeZone = DateTimeZone.forID(ConstantsUtil.RBVDR118.GMT_TIME_ZONE);
        LocalDate startLocalDate = new LocalDate(requestBody.getValidityPeriod().getStartDate(), dateTimeZone);
        Date startDate = startLocalDate.toDateTimeAtStartOfDay().toDate();

        requestBody.getFirstInstallment().setIsPaymentRequired(!startDate.after(currentDate));

        requestBody.getBusinessAgent();
    }
}