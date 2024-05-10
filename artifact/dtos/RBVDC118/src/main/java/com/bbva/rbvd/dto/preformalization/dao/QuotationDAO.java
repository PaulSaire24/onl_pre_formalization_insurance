package com.bbva.rbvd.dto.preformalization.dao;

import java.math.BigDecimal;

public class QuotationDAO {

    private String contractDurationType;
    private BigDecimal contractDurationNumber;
    private String insuranceCompanyQuotaId;
    private String insuranceProductDesc;
    private String insuranceModalityName;
    private String paymentFrequencyName;
    private String vehicleBrandName;
    private String vehicleModelName;
    private String vehicleYearId;
    private String vehicleLicenseId;
    private String gasConversionType;
    private String vehicleCirculationType;
    private BigDecimal commercialVehicleAmount;
    private BigDecimal insuranceProductId;
    private BigDecimal paymentFrequencyId;


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

    public String getInsuranceCompanyQuotaId() {
        return insuranceCompanyQuotaId;
    }

    public void setInsuranceCompanyQuotaId(String insuranceCompanyQuotaId) {
        this.insuranceCompanyQuotaId = insuranceCompanyQuotaId;
    }

    public String getInsuranceProductDesc() {
        return insuranceProductDesc;
    }

    public void setInsuranceProductDesc(String insuranceProductDesc) {
        this.insuranceProductDesc = insuranceProductDesc;
    }

    public String getInsuranceModalityName() {
        return insuranceModalityName;
    }

    public void setInsuranceModalityName(String insuranceModalityName) {
        this.insuranceModalityName = insuranceModalityName;
    }

    public String getPaymentFrequencyName() {
        return paymentFrequencyName;
    }

    public void setPaymentFrequencyName(String paymentFrequencyName) {
        this.paymentFrequencyName = paymentFrequencyName;
    }

    public String getVehicleBrandName() {
        return vehicleBrandName;
    }

    public void setVehicleBrandName(String vehicleBrandName) {
        this.vehicleBrandName = vehicleBrandName;
    }

    public String getVehicleModelName() {
        return vehicleModelName;
    }

    public void setVehicleModelName(String vehicleModelName) {
        this.vehicleModelName = vehicleModelName;
    }

    public String getVehicleYearId() {
        return vehicleYearId;
    }

    public void setVehicleYearId(String vehicleYearId) {
        this.vehicleYearId = vehicleYearId;
    }

    public String getVehicleLicenseId() {
        return vehicleLicenseId;
    }

    public void setVehicleLicenseId(String vehicleLicenseId) {
        this.vehicleLicenseId = vehicleLicenseId;
    }

    public String getGasConversionType() {
        return gasConversionType;
    }

    public void setGasConversionType(String gasConversionType) {
        this.gasConversionType = gasConversionType;
    }

    public String getVehicleCirculationType() {
        return vehicleCirculationType;
    }

    public void setVehicleCirculationType(String vehicleCirculationType) {
        this.vehicleCirculationType = vehicleCirculationType;
    }

    public BigDecimal getCommercialVehicleAmount() {
        return commercialVehicleAmount;
    }

    public void setCommercialVehicleAmount(BigDecimal commercialVehicleAmount) {
        this.commercialVehicleAmount = commercialVehicleAmount;
    }

    public BigDecimal getInsuranceProductId() {
        return insuranceProductId;
    }

    public void setInsuranceProductId(BigDecimal insuranceProductId) {
        this.insuranceProductId = insuranceProductId;
    }

    public BigDecimal getPaymentFrequencyId() {
        return paymentFrequencyId;
    }

    public void setPaymentFrequencyId(BigDecimal paymentFrequencyId) {
        this.paymentFrequencyId = paymentFrequencyId;
    }

    @Override
    public String toString() {
        return "QuotationDAO{" +
                "contractDurationType='" + contractDurationType + '\'' +
                ", contractDurationNumber=" + contractDurationNumber +
                ", insuranceCompanyQuotaId='" + insuranceCompanyQuotaId + '\'' +
                ", insuranceProductDesc='" + insuranceProductDesc + '\'' +
                ", insuranceModalityName='" + insuranceModalityName + '\'' +
                ", paymentFrequencyName='" + paymentFrequencyName + '\'' +
                ", vehicleBrandName='" + vehicleBrandName + '\'' +
                ", vehicleModelName='" + vehicleModelName + '\'' +
                ", vehicleYearId='" + vehicleYearId + '\'' +
                ", vehicleLicenseId='" + vehicleLicenseId + '\'' +
                ", gasConversionType='" + gasConversionType + '\'' +
                ", vehicleCirculationType='" + vehicleCirculationType + '\'' +
                ", commercialVehicleAmount=" + commercialVehicleAmount +
                ", insuranceProductId=" + insuranceProductId +
                ", paymentFrequencyId=" + paymentFrequencyId +
                '}';
    }
}
