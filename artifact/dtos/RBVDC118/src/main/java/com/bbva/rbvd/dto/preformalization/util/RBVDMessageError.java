package com.bbva.rbvd.dto.preformalization.util;

public enum RBVDMessageError {
    QUOTATION_EXIST_IN_CONTRACT("RBVD00120011",false,"Ya existe un contrato vigente para la cotización ingresada"),
    QUOTATION_NOT_EXIST("RBVD00000129",false,"No existen datos de la cotización ingresada"),
    PAYMENT_PERIOD_NOT_EXIST("RBVD00000111",false,"No se encontró datos del periodo de pago ingresado"),
    ERROR_INSERT_INSURANCE_CONTRACT("RBVD00000121",true,"Error al guardar los datos del contrato de seguro"),
    ERROR_INSERT_PARTICIPANTS("RBVD00000124",true,"Error al guardar los participantes del seguro"),;


    private final String adviceCode;
    private final boolean rollback;
    private final String message;

    public String getAdviceCode() {
        return adviceCode;
    }

    public boolean isRollback() {
        return rollback;
    }

    public String getMessage() {
        return message;
    }

    private RBVDMessageError(String adviceCode, boolean rollback, String message) {
        this.adviceCode = adviceCode;
        this.rollback = rollback;
        this.message = message;
    }

}
