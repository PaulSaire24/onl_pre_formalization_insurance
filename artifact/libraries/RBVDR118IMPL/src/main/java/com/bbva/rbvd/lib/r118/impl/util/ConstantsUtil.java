package com.bbva.rbvd.lib.r118.impl.util;

public class ConstantsUtil {

    public static final String FIELD_PRODUCT_SHORT_DESC = "PRODUCT_SHORT_DESC";

    private ConstantsUtil() {
    }

    public enum ParticipantRol {
        CONTRACTOR(8),
        INSURED(9),
        PAYMENT_MANAGER(23);
        private final Integer rol;
        ParticipantRol(Integer rol) {
            this.rol = rol;
        }
        public Integer getRol() {
            return rol;
        }
    }

    public static class Participant {
        public static final String ENDORSEE = "ENDORSEE";
        public static final String PAYMENT_MANAGER = "PAYMENT_MANAGER";
        public static final String INSURED = "INSURED";
        public static final String LEGAL_REPRESENTATIVE = "LEGAL_REPRESENTATIVE";
        public static final String BENEFICIARY = "BENEFICIARY";
    }

    public static final class DocumentType {
        public static final String RUC = "RUC";
    }

    public static final class Queries {
        public static final String QUERY_SELECT_PRODUCT_BY_PRODUCT_TYPE = "PISD.SELECT_PRODUCT_BY_PRODUCT_TYPE";
        public static final String QUERY_GET_INSURED_DATA_LIFE = "PISD.GET_INSURED_DATA_LIFE";
    }

    public static final class Delimeter {
        public static final String VERTICAL_BAR = "|";
    }

    public static final class Number {
        public static final int DIEZ = 10;
        public static final int TRES = 3;
        public static final int DOS = 2;
        public static final int UNO = 1;
        public static final int CERO = 0;
    }

    public static final class RBVDR118 {
        public static final String FIELD_PREMIUM_AMOUNT = "PREMIUM_AMOUNT";
        public static final String FIELD_PREMIUM_CURRENCY_ID = "PREMIUM_CURRENCY_ID";
        public static final String FIELD_POLICY_PAYMENT_FREQUENCY_TYPE = "POLICY_PAYMENT_FREQUENCY_TYPE";
        public static final String PROPERTY_RANGE_PAYMENT_AMOUNT = "property.range.payment.amount.insurance";
        public static final String PROPERTY_VALIDATION_RANGE = "property.validation.range.";
        public static final String LIMA_TIME_ZONE = "America/Lima";
        public static final String GMT_TIME_ZONE = "GMT";
    }

    public static final class RelatedContractType {
        public static final String InternalContract = "INTERNAL_CONTRACT";
        public static final String ExternalContract = "EXTERNAL_CONTRACT";
    }
}