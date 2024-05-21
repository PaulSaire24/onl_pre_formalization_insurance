package com.bbva.rbvd.dto.preformalization.dao;

import java.math.BigDecimal;

public class QuotationDAO {

    private String insuranceBusinessName;
    private BigDecimal insuranceProductId;
    private String insuranceProductDesc;
    private String contractDurationType;
    private BigDecimal contractDurationNumber;
    private String insuranceModalityName;
    private String insuranceCompanyQuotaId;


    public String getInsuranceBusinessName() {
        return insuranceBusinessName;
    }

    public void setInsuranceBusinessName(String insuranceBusinessName) {
        this.insuranceBusinessName = insuranceBusinessName;
    }

    public BigDecimal getInsuranceProductId() {
        return insuranceProductId;
    }

    public void setInsuranceProductId(BigDecimal insuranceProductId) {
        this.insuranceProductId = insuranceProductId;
    }

    public String getInsuranceProductDesc() {
        return insuranceProductDesc;
    }

    public void setInsuranceProductDesc(String insuranceProductDesc) {
        this.insuranceProductDesc = insuranceProductDesc;
    }

    public String getContractDurationType() {
        return contractDurationType;
    }

    public void setContractDurationType(String contractDurationType) {
        this.contractDurationType = contractDurationType;
    }

    public BigDecimal getContractDurationNumber() {
        return contractDurationNumber;
    }

    public void setContractDurationNumber(BigDecimal contractDurationNumber) {
        this.contractDurationNumber = contractDurationNumber;
    }

    public String getInsuranceModalityName() {
        return insuranceModalityName;
    }

    public void setInsuranceModalityName(String insuranceModalityName) {
        this.insuranceModalityName = insuranceModalityName;
    }

    public String getInsuranceCompanyQuotaId() {
        return insuranceCompanyQuotaId;
    }

    public void setInsuranceCompanyQuotaId(String insuranceCompanyQuotaId) {
        this.insuranceCompanyQuotaId = insuranceCompanyQuotaId;
    }

    @Override
    public String toString() {
        return "QuotationDAO{" +
                "insuranceBusinessName='" + insuranceBusinessName + '\'' +
                ", insuranceProductId=" + insuranceProductId +
                ", insuranceProductDesc='" + insuranceProductDesc + '\'' +
                ", contractDurationType='" + contractDurationType + '\'' +
                ", contractDurationNumber=" + contractDurationNumber +
                ", insuranceModalityName='" + insuranceModalityName + '\'' +
                ", insuranceCompanyQuotaId='" + insuranceCompanyQuotaId + '\'' +
                '}';
    }
}
