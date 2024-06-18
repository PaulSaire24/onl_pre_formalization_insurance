package com.bbva.rbvd;

import com.bbva.rbvd.lib.r415.RBVDR415;
import com.bbva.elara.domain.transaction.RequestHeaderParamsName;
import com.bbva.elara.domain.transaction.Severity;
import com.bbva.elara.domain.transaction.response.HttpResponseCode;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyDTO;
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
		RBVDR415 rbvdR415 = this.getServiceLibrary(RBVDR415.class);
        LOGGER.info("RBVDT11801PETransaction - START");

        this.loggHeaders();

        PolicyDTO requestBody = this.getPolicyDTO();

        PolicyDTO responseBody = rbvdR415.executeLogicPreFormalization(requestBody);

        if (Objects.nonNull(responseBody)) {
            this.mapResponse(responseBody);
            this.setHttpResponseCode(HttpResponseCode.HTTP_CODE_200, Severity.OK);
        } else {
            this.setSeverity(Severity.ENR);
        }
    }

    public void mapResponse(PolicyDTO responseBody) {
        setId(responseBody.getId());
        setQuotationnumber(responseBody.getQuotationNumber());
        setPolicynumber(responseBody.getPolicyNumber());
        setAlias(responseBody.getAlias());
        setProducttype(responseBody.getProductType());
        setProduct(responseBody.getProduct());
        setHasacceptedcontract(responseBody.getHasAcceptedContract());
        setIsdatatreatment(responseBody.getIsDataTreatment());
        setPaymentmethod(responseBody.getPaymentMethod());
        setParticipants(responseBody.getParticipants());
        setFirstinstallment(responseBody.getFirstInstallment());
        setPromoter(responseBody.getPromoter());
        setInspection(responseBody.getInspection());
        setRelatedcontracts(responseBody.getRelatedContracts());
        setTotalamount(responseBody.getTotalAmount());
        setTotalamountwithouttax(responseBody.getTotalAmountWithoutTax());
        setInsuredamount(responseBody.getInsuredAmount());
        setInstallmentplan(responseBody.getInstallmentPlan());
        setOperationdate(responseBody.getOperationDate());
        setStatus(responseBody.getStatus());
        setInsurancecompany(responseBody.getInsuranceCompany());
        setPaymentconfiguration(responseBody.getPaymentConfiguration());
        setHolder(responseBody.getHolder());
        setCancelationdate(responseBody.getCancelationDate());
        setValidityperiod(responseBody.getValidityPeriod());
        setCurrentinstallment(responseBody.getCurrentInstallment());
        setPremiumdebt(responseBody.getPremiumDebt());
        setRenewalpolicy(responseBody.getRenewalPolicy());
        setCertificatenumber(responseBody.getCertificateNumber());
        setSubscriptiontype(responseBody.getSubscriptionType());
        setBusinessagent(responseBody.getBusinessAgent());
        setBank(responseBody.getBank());
        setLastinstallment(responseBody.getLastInstallment());
        setExternaldocumentationsenddate(responseBody.getExternalDocumentationSendDate());
        setNonrenewalpolicy(responseBody.getNonRenewalPolicy());
        setCouponcode(responseBody.getCouponCode());
    }

    public PolicyDTO getPolicyDTO() {
        PolicyDTO requestBody = new PolicyDTO();
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
        requestBody.setHolder(this.getHolder());
        requestBody.setPaymentMethod(this.getPaymentmethod());
        requestBody.setValidityPeriod(this.getValidityperiod());
        requestBody.setTotalAmount(this.getTotalamount());
        requestBody.setInsuredAmount(this.getInsuredamount());
        requestBody.setDataTreatment(this.getIsdatatreatment());
        requestBody.setRelatedContracts(this.getRelatedcontracts());
        requestBody.setInstallmentPlan(this.getInstallmentplan());
        requestBody.setHasAcceptedContract(this.getHasacceptedcontract());
        requestBody.setInspection(this.getInspection());
        requestBody.setFirstInstallment(this.getFirstinstallment());
        requestBody.setParticipants(this.getParticipants());
        requestBody.setBusinessAgent(this.getBusinessagent());
        requestBody.setPromoter(this.getPromoter());
        requestBody.setInsuranceCompany(this.getInsurancecompany());
        requestBody.setBank(this.getBank());
        requestBody.setCouponCode(this.getCouponcode());
        requestBody.setIdentityVerificationCode(this.getIdentityverificationcode());

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

}
