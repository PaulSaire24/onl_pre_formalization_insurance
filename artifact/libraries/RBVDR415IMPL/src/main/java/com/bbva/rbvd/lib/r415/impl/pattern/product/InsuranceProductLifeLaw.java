package com.bbva.rbvd.lib.r415.impl.pattern.product;

import com.bbva.elara.configuration.manager.application.ApplicationConfigurationService;
import com.bbva.elara.utility.api.connector.APIConnector;
import com.bbva.rbvd.dto.cicsconnection.icr2.ICMRYS2;
import com.bbva.rbvd.dto.cicsconnection.icr2.ICR2Request;
import com.bbva.rbvd.dto.cicsconnection.icr2.ICR2Response;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyDTO;
import com.bbva.rbvd.dto.preformalization.dao.QuotationDAO;
import com.bbva.rbvd.dto.preformalization.transfer.PayloadConfig;
import com.bbva.rbvd.dto.preformalization.util.ConstantsUtil;
import com.bbva.rbvd.lib.r047.RBVDR047;
import com.bbva.rbvd.lib.r415.impl.business.CreatedInsuranceEventBusiness;
import com.bbva.rbvd.lib.r415.impl.business.ICR2Business;
import com.bbva.rbvd.lib.r415.impl.pattern.PostInsuranceProduct;
import com.bbva.rbvd.lib.r415.impl.pattern.PreInsuranceProduct;
import com.bbva.rbvd.lib.r415.impl.pattern.impl.PreFormalizationDecorator;
import com.bbva.rbvd.lib.r415.impl.service.api.ConsumeInternalService;
import com.bbva.rbvd.lib.r415.impl.transfer.PayloadStore;
import com.bbva.rbvd.lib.r415.impl.util.ConvertUtil;
import com.bbva.rbvd.lib.r415.impl.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InsuranceProductLifeLaw extends PreFormalizationDecorator {

    private static final Logger LOGGER = LoggerFactory.getLogger(InsuranceProductLifeLaw.class);

    private final APIConnector internalApiConnectorImpersonation;

    public InsuranceProductLifeLaw(PreInsuranceProduct preInsuranceProduct, PostInsuranceProduct postInsuranceProduct, APIConnector internalApiConnectorImpersonation) {
        super(preInsuranceProduct, postInsuranceProduct);
        this.internalApiConnectorImpersonation = internalApiConnectorImpersonation;
    }

    @Override
    public PolicyDTO start(PolicyDTO input, RBVDR047 rbvdr047,ApplicationConfigurationService applicationConfigurationService) {
        PayloadConfig payloadConfig = this.getPreInsuranceProduct().getConfig(input);

        ICR2Request icr2Request = ICR2Business.mapRequestFromPreformalizationBody(input);
        ICR2Response icr2Response = rbvdr047.executePreFormalizationContract(icr2Request);
        LOGGER.info("InsuranceProductLifeLaw - start() - icr2Response: {}", icr2Response);
        ValidationUtil.checkHostAdviceErrors(icr2Response);

        String hostBranchId = icr2Response.getIcmrys2().getOFICON();
        input.getBank().getBranch().setId(hostBranchId);

        boolean isEndorsement = ValidationUtil.validateEndorsement(input);

        filltOutputTrx(input,icr2Response.getIcmrys2(),payloadConfig.getQuotation());

        PayloadStore payloadStore = new PayloadStore();
        payloadStore.setResposeBody(input);
        payloadStore.setEndorsement(isEndorsement);
        payloadStore.setIcr2Response(icr2Response);
        payloadStore.setQuotationDAO(payloadConfig.getQuotation());
        payloadStore.setPaymentFrequencyId(payloadConfig.getPaymentFrequencyId());
        payloadStore.setPaymentFrequencyName(payloadConfig.getPaymentFrequencyName());

        this.getPostInsuranceProduct().end(payloadStore);

        //llamar al evento para que avise a DWP que ya se contrató
        String flagCallEvent = applicationConfigurationService.getDefaultProperty(
                "flag.callevent.createinsured.for.preemision",ConstantsUtil.N_VALUE);
        if(flagCallEvent.equalsIgnoreCase(ConstantsUtil.S_VALUE)){
            ConsumeInternalService consumeInternalService = new ConsumeInternalService(this.internalApiConnectorImpersonation);
            Integer httpStatusCode = consumeInternalService.callEventUpsilonToUpdateStatusInDWP(
                    CreatedInsuranceEventBusiness.createRequestCreatedInsuranceEvent(input));
            LOGGER.info("InsuranceProductLifeLaw - start() - callEventUpsilonToUpdateStatusInDWP - httpStatusCode: {}",httpStatusCode);
        }

        return input;
    }


    private void filltOutputTrx(PolicyDTO policyDTO, ICMRYS2 icmrys2, QuotationDAO quotationDAO){
        policyDTO.setId(getContractFrontIcr2Response(icmrys2));
        policyDTO.getProduct().setName(quotationDAO.getInsuranceProductDesc());
        policyDTO.setOperationDate(ConvertUtil.convertStringDateWithTimeFormatToDate(icmrys2.getFECCTR()));
        policyDTO.getValidityPeriod().setEndDate(ConvertUtil.convertStringDateWithDateFormatToDate(icmrys2.getFECFIN()));
    }

    private String getContractFrontIcr2Response(ICMRYS2 icmrys2) {
        return icmrys2.getNUMCON().substring(0, 4) +
                icmrys2.getNUMCON().substring(4, 8) +
                icmrys2.getNUMCON().charAt(8) +
                icmrys2.getNUMCON().charAt(9) +
                icmrys2.getNUMCON().substring(10);
    }


    public static final class Builder {
        private PreInsuranceProduct preInsuranceProduct;
        private PostInsuranceProduct postInsuranceProduct;
        private APIConnector internalApiConnectorImpersonation;


        private Builder() {
        }

        public static Builder an() {
            return new Builder();
        }

        public Builder withPreInsuranceProduct(PreInsuranceProduct preInsuranceProduct) {
            this.preInsuranceProduct = preInsuranceProduct;
            return this;
        }

        public Builder withPostInsuranceProduct(PostInsuranceProduct postInsuranceProduct) {
            this.postInsuranceProduct = postInsuranceProduct;
            return this;
        }

        public Builder withInternalApiConnectorImpersonation(APIConnector internalApiConnectorImpersonation) {
            this.internalApiConnectorImpersonation = internalApiConnectorImpersonation;
            return this;
        }


        public InsuranceProductLifeLaw build() {
            return new InsuranceProductLifeLaw(preInsuranceProduct, postInsuranceProduct,internalApiConnectorImpersonation);
        }
    }

}
