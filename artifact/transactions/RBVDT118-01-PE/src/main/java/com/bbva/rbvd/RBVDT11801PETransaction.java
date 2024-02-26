package com.bbva.rbvd;

import com.bbva.elara.domain.transaction.RequestHeaderParamsName;
import com.bbva.elara.domain.transaction.Severity;
import com.bbva.elara.domain.transaction.response.HttpResponseCode;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyDTO;
import com.bbva.rbvd.lib.r118.RBVDR118;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Objects;
import java.util.TimeZone;

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

        PolicyDTO requestBody = this.getPolicyDTO();

        PolicyDTO responseBody = null;

        responseBody = rbvdR118.executePreFormalization(requestBody);

        if (Objects.nonNull(responseBody)) {
            this.mapResponse(responseBody);
            this.setHttpResponseCode(HttpResponseCode.HTTP_CODE_200, Severity.OK);
        } else {
            this.setSeverity(Severity.ENR);
        }

    }

    private void mapResponse(PolicyDTO responseBody) {
        Calendar operationDate = Calendar.getInstance();
        operationDate.setTimeZone(TimeZone.getTimeZone("America/Lima"));
        operationDate.setTime(responseBody.getOperationDate());

        this.setId(responseBody.getId());
        this.setPolicynumber(responseBody.getPolicyNumber());
        this.setQuotationid(responseBody.getQuotationId());
        this.setProductid(responseBody.getProductId());
        this.setProductdescription(responseBody.getProductDescription());
        this.setProductplan(responseBody.getProductPlan());
        this.setPaymentmethod(responseBody.getPaymentMethod());

        this.setOperationdate(operationDate);
        this.setValidityperiod(responseBody.getValidityPeriod());
        this.setLinks(responseBody.getLinks());
        this.setTotalamount(responseBody.getTotalAmount());
        this.setTotalamountwithouttax(responseBody.getTotalAmountWithoutTax());
        this.setInsuredamount(responseBody.getInsuredAmount());
        this.setIsdatatreatment(responseBody.getIsDataTreatment());
        this.setDeliveries(responseBody.getDeliveries());
        this.setHolder(responseBody.getHolder());
        this.setRelatedcontracts(responseBody.getRelatedContracts());
        this.setInstallmentplan(responseBody.getInstallmentPlan());
        this.setHasacceptedcontract(responseBody.getHasAcceptedContract());
        this.setInspection(responseBody.getInspection());
        this.setFirstinstallment(responseBody.getFirstInstallment());
        this.setParticipants(responseBody.getParticipants());
        this.setBusinessagent(responseBody.getBusinessAgent());
        this.setPromoter(responseBody.getPromoter());
        this.setInsurancecompany(responseBody.getInsuranceCompany());
        this.setExternalquotationid(responseBody.getExternalQuotationId());
        this.setExternalpolicynumber(responseBody.getExternalPolicyNumber());
        this.setStatus(responseBody.getStatus());
        this.setBank(responseBody.getBank());
        this.setIdentityverificationcode(responseBody.getIdentityVerificationCode());
        this.setCouponcode(responseBody.getCouponCode());
        this.setPolicyduration(responseBody.getPolicyDuration());
        this.setSalesupplier(responseBody.getSaleSupplier());
    }

    private PolicyDTO getPolicyDTO() {
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

        requestBody.setQuotationId(this.getQuotationid());
        requestBody.setProductId(this.getProductid());
        requestBody.setProductPlan(this.getProductplan());
        requestBody.setPaymentMethod(this.getPaymentmethod());
        requestBody.setValidityPeriod(this.getValidityperiod());
        requestBody.setTotalAmount(this.getTotalamount());
        requestBody.setInsuredAmount(this.getInsuredamount());
        requestBody.setIsDataTreatment(this.getIsdatatreatment());
        requestBody.setDeliveries(this.getDeliveries());
        requestBody.setHolder(this.getHolder());
        requestBody.setRelatedContracts(this.getRelatedcontracts());
        requestBody.setInstallmentPlan(this.getInstallmentplan());
        requestBody.setHasAcceptedContract(this.getHasacceptedcontract());
        requestBody.setInspection(this.getInspection());
        requestBody.setFirstInstallment(this.getFirstinstallment());
        requestBody.setParticipants(this.getParticipants());
        requestBody.setBusinessAgent(this.getBusinessagent());
        requestBody.setPromoter(this.getPromoter());
        requestBody.setBank(this.getBank());
        requestBody.setIdentityVerificationCode(this.getIdentityverificationcode());
        requestBody.setInsuranceCompany(this.getInsurancecompany());
        requestBody.setCouponCode(this.getCouponcode());
        requestBody.setSaleSupplier(this.getSalesupplier());

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

    private void loggHeaders() {
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
