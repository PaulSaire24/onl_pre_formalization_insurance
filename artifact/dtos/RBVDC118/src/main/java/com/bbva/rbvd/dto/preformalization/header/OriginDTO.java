package com.bbva.rbvd.dto.preformalization.header;

public class OriginDTO extends CommonDTO{
    private String aap;
    private BankEventDTO bank;
    private String channelCode;
    private String country;
    private String environCode;
    private String ipv4;
    private String language;
    private String operation;
    private String productCode;
    private String session;
    private String user;
    private String userType;

    public String getAap() {
        return aap;
    }

    public void setAap(String aap) {
        this.aap = aap;
    }

    public BankEventDTO getBank() {
        return bank;
    }

    public void setBank(BankEventDTO bank) {
        this.bank = bank;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEnvironCode() {
        return environCode;
    }

    public void setEnvironCode(String environCode) {
        this.environCode = environCode;
    }

    public String getIpv4() {
        return ipv4;
    }

    public void setIpv4(String ipv4) {
        this.ipv4 = ipv4;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "OriginDTO{" +
                "aap='" + aap + '\'' +
                ", bank=" + bank +
                ", channelCode='" + channelCode + '\'' +
                ", country='" + country + '\'' +
                ", environCode='" + environCode + '\'' +
                ", ipv4='" + ipv4 + '\'' +
                ", language='" + language + '\'' +
                ", operation='" + operation + '\'' +
                ", productCode='" + productCode + '\'' +
                ", session='" + session + '\'' +
                ", user='" + user + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }
}
