package com.bbva.rbvd;

import com.bbva.elara.domain.transaction.RequestHeaderParamsName;
import com.bbva.elara.domain.transaction.Severity;
import com.bbva.elara.domain.transaction.response.HttpResponseCode;
import com.bbva.rbvd.dto.preformalization.PreformalizationDTO;
import com.bbva.rbvd.lib.rbvd118.RBVDR118;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Trx to pre-formalizate a contract
 */
public class RBVDT11801PETransaction extends AbstractRBVDT11801PETransaction {

    private static final Logger LOGGER = LoggerFactory.getLogger(RBVDT11801PETransaction.class);

    @Override
    public void execute() {
        LOGGER.info("RBVDT11801PETransaction - START");

        RBVDR118 rbvdR118 = this.getServiceLibrary(RBVDR118.class);

        this.loggHeaders();

        PreformalizationDTO requestBody = this.getPolicyDTO();

        PreformalizationDTO responseBody;

        responseBody = rbvdR118.executePreFormalization(requestBody);

        if (Objects.nonNull(responseBody)) {
            this.mapResponse(responseBody);
            this.setHttpResponseCode(HttpResponseCode.HTTP_CODE_200, Severity.OK);
        } else {
            this.setSeverity(Severity.ENR);
        }
    }

    public void mapResponse(PreformalizationDTO responseBody) {
        setId(responseBody.getId());
        setQuotationNumber(responseBody.getQuotationNumber());
        setPolicyNumber(responseBody.getPolicyNumber());
        setProductType(responseBody.getProductType());
        setProduct(responseBody.getProduct());
        setHasAcceptedContract(responseBody.isHasAcceptedContract());
        setIsDataTreatment(responseBody.isDataTreatment());
        setPaymentMethod(responseBody.getPaymentMethod());
        setParticipants(responseBody.getParticipants());
        setFirstInstallment(responseBody.getFirstInstallment());
        setPromoter(responseBody.getPromoter());
        setInspection(responseBody.getInspection());
        setRelatedContracts(responseBody.getRelatedContracts());
        setTotalAmount(responseBody.getTotalAmount());
        setTotalAmountWithoutTax(responseBody.getTotalAmountWithoutTax());
        setInsuredAmount(responseBody.getInsuredAmount());
        setInstallmentPlan(responseBody.getInstallmentPlan());
        setOperationDate(responseBody.getOperationDate().toString());
        setStatus(responseBody.getInsuranceStatus());
        setInsuranceCompany(responseBody.getInsuranceCompany());
        setPaymentConfiguration(responseBody.getPaymentConfiguration());
        setHolder(responseBody.getHolder());
        setCancelationDate(responseBody.getCancelationDate());
        setValidityPeriod(responseBody.getInsuranceValidityPeriod());
        setCurrentInstallment(responseBody.getCurrentInstallment());
        setPremiumDebt(responseBody.getPremiumDebt());
        setRenewalPolicy(responseBody.getRenewalPolicy());
        setCertificateNumber(responseBody.getCertificateNumber());
        setSubscriptionType(responseBody.getSubscriptionType());
        setBusinessAgent(responseBody.getBusinessAgent());
        setBank(responseBody.getBank());
        setLastInstallment(responseBody.getLastInstallment());
        //setExternalDocumentationSendDate();
        setNonRenewalPolicy(responseBody.getNonRenewalPolicy());
        setCouponCode(responseBody.getCouponCode());
    }

    public PreformalizationDTO getPolicyDTO() {
        PreformalizationDTO requestBody = new PreformalizationDTO();
        String traceId = (String) this.getRequestHeader().getHeaderParameter(RequestHeaderParamsName.REQUESTID);
        String saleChannelId = (String) this.getRequestHeader().getHeaderParameter(RequestHeaderParamsName.CHANNELCODE);
        String user = (String) this.getRequestHeader().getHeaderParameter(RequestHeaderParamsName.USERCODE);
        String aap = (String) this.getRequestHeader().getHeaderParameter(RequestHeaderParamsName.AAP);
        String ipv4 = (String) this.getRequestHeader().getHeaderParameter(RequestHeaderParamsName.IPADDRESS);
        String environmentCode = (String) this.getRequestHeader().getHeaderParameter(RequestHeaderParamsName.ENVIRONCODE);
        String productCode = (String) this.getRequestHeader().getHeaderParameter(RequestHeaderParamsName.PRODUCTCODE);
        String headerOperationDate = (String) this.getRequestHeader().getHeaderParameter(RequestHeaderParamsName.OPERATIONDATE);
        String operationTime = (String) this.getRequestHeader().getHeaderParameter(RequestHeaderParamsName.OPERATIONTIME);

        requestBody.setQuotationNumber(this.getQuotationnumber());
        requestBody.setProduct(this.getProduct());
        requestBody.setPaymentMethod(this.getPaymentmethod());
        requestBody.setInsuranceValidityPeriod(this.getValidityperiod());
        requestBody.setTotalAmount(this.getTotalAmount());
        requestBody.setInsuredAmount(this.getInsuredAmount());
        requestBody.setDataTreatment(this.getIsDataTreatment());
        requestBody.setHolder(this.getHolder());
        requestBody.setRelatedContracts(this.getRelatedContracts());
        requestBody.setInstallmentPlan(this.getInstallmentPlan());
        requestBody.setHasAcceptedContract(this.getHasAcceptedContract());
        requestBody.setInspection(this.getInspection());
        requestBody.setFirstInstallment(this.getFirstInstallment());
        requestBody.setParticipants(this.getParticipants());
        requestBody.setBusinessAgent(this.getBusinessAgent());
        requestBody.setPromoter(this.getPromoter());
        requestBody.setBank(this.getBank());
        requestBody.setInsuranceCompany(this.getInsuranceCompany());
        requestBody.setCouponCode(this.getCouponCode());
        requestBody.setIdentityVerificationCode(this.getIdentityVerificationCode());

        requestBody.setTraceId(traceId);
        requestBody.setSaleChannelId(saleChannelId);
        requestBody.setCreationUser(user);
        requestBody.setUserAudit(user);
        requestBody.setAap(aap);
        requestBody.setIpv4(ipv4);
        requestBody.setEnvironmentCode(environmentCode);
        requestBody.setProductCode(productCode);
        requestBody.setHeaderOperationDate(headerOperationDate);
        requestBody.setHeaderOperationTime(operationTime);
        return requestBody;
    }

    public void loggHeaders() {
        String traceId = (String) this.getRequestHeader().getHeaderParameter(RequestHeaderParamsName.REQUESTID);
        LOGGER.info("Cabecera traceId: {}", traceId);
        String saleChannelId = (String) this.getRequestHeader().getHeaderParameter(RequestHeaderParamsName.CHANNELCODE);
        LOGGER.info("Cabecera channel-code: {}", saleChannelId);
        String user = (String) this.getRequestHeader().getHeaderParameter(RequestHeaderParamsName.USERCODE);
        LOGGER.info("Cabecera user-code: {}", user);
        String aap = (String) this.getRequestHeader().getHeaderParameter(RequestHeaderParamsName.AAP);
        LOGGER.info("Cabecera aap: {}", aap);
        String ipv4 = (String) this.getRequestHeader().getHeaderParameter(RequestHeaderParamsName.IPADDRESS);
        LOGGER.info("Cabecera ipv4: {}", ipv4);
        String environmentCode = (String) this.getRequestHeader().getHeaderParameter(RequestHeaderParamsName.ENVIRONCODE);
        LOGGER.info("Cabecera environmentCode: {}", environmentCode);
        String productCode = (String) this.getRequestHeader().getHeaderParameter(RequestHeaderParamsName.PRODUCTCODE);
        LOGGER.info("Cabecera productCode: {}", productCode);
        String headerOperationDate = (String) this.getRequestHeader().getHeaderParameter(RequestHeaderParamsName.OPERATIONDATE);
        LOGGER.info("Cabecera operationDate: {}", headerOperationDate);
        String operationTime = (String) this.getRequestHeader().getHeaderParameter(RequestHeaderParamsName.OPERATIONTIME);
        LOGGER.info("Cabecera operationTime: {}", operationTime);
    }

    public Object publicGetParameter(String parameter) {
        return this.getParameter(parameter);
    }
}
