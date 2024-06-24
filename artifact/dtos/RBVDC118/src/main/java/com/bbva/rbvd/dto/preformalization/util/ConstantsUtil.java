package com.bbva.rbvd.dto.preformalization.util;

public class ConstantsUtil {

    private ConstantsUtil() {
    }


    public static class Participant {
        public static final String PAYMENT_MANAGER = "PAYMENT_MANAGER";

        public static final String LEGAL_REPRESENTATIVE = "LEGAL_REPRESENTATIVE";

    }

    public static final class DocumentType {
        public static final String RUC = "RUC";
    }


    public enum StatusContract{

        PENDIENTE("PEN"),
        CONTRATADA("CONTRATADA"),
        PREFORMALIZADA("PREFORMALIZADA");

        private final String value;

        StatusContract(String value){
            this.value = value;
        }

        public String getValue(){
            return value;
        }

    }

    public static final class Number {
        public static final int TRES = 3;

        public static final int DOS = 2;
        public static final int UNO = 1;
    }

    public static final class TimeZone {
        public static final String LIMA_TIME_ZONE = "America/Lima";
        public static final String GMT_TIME_ZONE = "GMT";
    }

    public static final class RelatedContractType {
        public static final String INTERNAL_CONTRACT = "INTERNAL_CONTRACT";
        public static final String EXTERNAL_CONTRACT = "EXTERNAL_CONTRACT";
        public static final String PRODUCT_TYPE_ID_ACCOUNT = "ACCOUNT";
        public static final String PRODUCT_TYPE_ID_CARD = "CARD";
    }

    public static final class ApxConsole{
        public static final String KEY_TLMKT_CODE = "telemarketing.code";
        public static final String KEY_PIC_CODE = "pic.code";
        public static final String KEY_CONTACT_CENTER_CODE = "cc.code";
        public static final String KEY_AGENT_PROMOTER_CODE = "agent.and.promoter.code";
        public static final String CHANNEL_GLOMO = "pisd.channel.glomo.aap";
        public static final String CHANNEL_CONTACT_DETAIL = "pisd.channel.contact.detail.aap";
        public static final String EVENT_CHANNEL = "event.channel.key";
        public static final String FLAG_CALL_EVENT = "flag.callevent.createinsured.for.preemision";
        public static final String FLAG_FILTER_CHANNEL = "flag.filter.channel.quotation.for.preemision";
        public static final String LIST_PAYMENT_TYPE_ACCOUNT = "list.paymenttype.account";
        public static final String LIST_PAYMENT_TYPE_CARD = "list.paymenttype.card";
    }

    public static final class ContactDetailsType{
        public static final String EMAIL = "EMAIL";
        public static final String MOBILE = "MOBILE";
    }

    public static final String S_VALUE = "S";
    public static final String N_VALUE = "N";
    public static final String PAYMENT_METHOD_VALUE = "DIRECT_DEBIT";
    public static final String ID_PUT_EVENT_UPSILON_SERVICE = "createdInsurancePutEvent";


}
