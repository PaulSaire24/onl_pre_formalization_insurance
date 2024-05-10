package com.bbva.rbvd.dto.preformalization.util;

public class ConstantsUtil {

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

    public static final class Number {
        public static final int DIEZ = 10;
        public static final int TRES = 3;
        public static final int DOS = 2;
        public static final int UNO = 1;
        public static final int CERO = 0;
    }

    public static final class RBVDR118 {
        public static final String LIMA_TIME_ZONE = "America/Lima";
        public static final String GMT_TIME_ZONE = "GMT";
    }

    public static final class RelatedContractType {
        public static final String INTERNAL_CONTRACT = "INTERNAL_CONTRACT";
        public static final String EXTERNAL_CONTRACT = "EXTERNAL_CONTRACT";
    }

    public static final class ApxConsole{
        public static final String KEY_TLMKT_CODE = "telemarketing.code";
        public static final String KEY_PIC_CODE = "pic.code";
        public static final String KEY_CONTACT_CENTER_CODE = "cc.code";
        public static final String KEY_AGENT_PROMOTER_CODE = "agent.and.promoter.code";
        public static final String CHANNEL_GLOMO = "pisd.channel.glomo.aap";
        public static final String CHANNEL_CONTACT_DETAIL = "pisd.channel.contact.detail.aap";
    }

    public static final String S_VALUE = "S";
    public static final String N_VALUE = "N";
    public static final String PAYMENT_METHOD_VALUE = "DIRECT_DEBIT";

}
