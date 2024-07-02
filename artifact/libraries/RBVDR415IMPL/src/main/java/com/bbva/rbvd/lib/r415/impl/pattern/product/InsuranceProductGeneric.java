package com.bbva.rbvd.lib.r415.impl.pattern.product;

import com.bbva.elara.configuration.manager.application.ApplicationConfigurationService;
import com.bbva.rbvd.dto.cicsconnection.icr3.ICMRYS3;
import com.bbva.rbvd.dto.cicsconnection.icr3.ICR3Request;
import com.bbva.rbvd.dto.cicsconnection.icr3.ICR3Response;
import com.bbva.rbvd.dto.insrncsale.commons.ValidityPeriodDTO;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyDTO;
import com.bbva.rbvd.dto.preformalization.dao.QuotationDAO;
import com.bbva.rbvd.dto.preformalization.transfer.PayloadConfig;
import com.bbva.rbvd.lib.r415.impl.business.ICR3Business;
import com.bbva.rbvd.lib.r415.impl.pattern.PostInsuranceProduct;
import com.bbva.rbvd.lib.r415.impl.pattern.PreInsuranceProduct;
import com.bbva.rbvd.lib.r415.impl.pattern.impl.PreFormalizationDecorator;
import com.bbva.rbvd.lib.r415.impl.transfer.PayloadStore;
import com.bbva.rbvd.lib.r415.impl.util.ConvertUtil;
import com.bbva.rbvd.lib.r415.impl.util.ValidationUtil;
import com.bbva.rbvd.lib.r602.RBVDR602;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class InsuranceProductGeneric extends PreFormalizationDecorator {

    private static final Logger LOGGER = LoggerFactory.getLogger(InsuranceProductGeneric.class);


    protected InsuranceProductGeneric(PreInsuranceProduct preInsuranceProduct, PostInsuranceProduct postInsuranceProduct) {
        super(preInsuranceProduct, postInsuranceProduct);
    }

    @Override
    public PolicyDTO start(PolicyDTO input, RBVDR602 rbvdr602, ApplicationConfigurationService applicationConfigurationService) {
        PayloadConfig payloadConfig = this.getPreInsuranceProduct().getConfig(input);

        ICR3Business icr3Business = new ICR3Business(applicationConfigurationService);
        ICR3Request icr3Request = icr3Business.mapRequestFromPreformalizationBody(input);
        ICR3Response icr3Response = rbvdr602.executePreFormalizationInsurance(icr3Request);
        LOGGER.info("InsuranceProductGeneric - start() - icr3Response: {}", icr3Response);
        ValidationUtil.checkHostAdviceErrors(icr3Response);

        String hostBranchId = icr3Response.getIcmrys3().getOFICON();
        input.getBank().getBranch().setId(hostBranchId);

        filltOutputTrx(input,icr3Response.getIcmrys3(),payloadConfig.getQuotation());

        PayloadStore payloadStore = new PayloadStore();
        payloadStore.setResposeBody(input);
        payloadStore.setIcr3Response(icr3Response);
        payloadStore.setQuotationDAO(payloadConfig.getQuotation());
        payloadStore.setPaymentFrequencyId(payloadConfig.getPaymentFrequencyId());
        payloadStore.setPaymentFrequencyName(payloadConfig.getPaymentFrequencyName());

        this.getPostInsuranceProduct().end(payloadStore);

        return input;
    }

    private void filltOutputTrx(PolicyDTO policyDTO, ICMRYS3 icmrys3, QuotationDAO quotationDAO){
        policyDTO.setId(getContractFrontIcr3Response(icmrys3));
        policyDTO.getProduct().setName(quotationDAO.getInsuranceProductDesc());
        policyDTO.getProduct().getPlan().setDescription(quotationDAO.getInsuranceModalityName());
        policyDTO.setOperationDate(ConvertUtil.convertStringDateWithTimeFormatToDate(icmrys3.getFECCTR()));
        fillValidityPeriod(policyDTO,icmrys3);
    }

    private void fillValidityPeriod(PolicyDTO response,ICMRYS3 icmrys3){
        if(response.getValidityPeriod() != null){
            response.getValidityPeriod().setEndDate(icmrys3.getFECFIN() != null ? ConvertUtil.convertStringDateWithDateFormatToDate(icmrys3.getFECFIN()) : null);
        }else{
            if(ValidationUtil.allValuesNotNullOrEmpty(Arrays.asList(icmrys3.getFECINI(),icmrys3.getFECFIN()))){
                ValidityPeriodDTO validityPeriodDTO = new ValidityPeriodDTO();
                validityPeriodDTO.setStartDate(ConvertUtil.convertStringDateWithDateFormatToDate(icmrys3.getFECINI()));
                validityPeriodDTO.setEndDate(ConvertUtil.convertStringDateWithDateFormatToDate(icmrys3.getFECFIN()));
                response.setValidityPeriod(validityPeriodDTO);
            }
        }
    }

    private String getContractFrontIcr3Response(ICMRYS3 icmrys3) {
        return icmrys3.getNUMCON().substring(0, 4) +
                icmrys3.getNUMCON().substring(4, 8) +
                icmrys3.getNUMCON().charAt(8) +
                icmrys3.getNUMCON().charAt(9) +
                icmrys3.getNUMCON().substring(10);
    }

    public static final class Builder {
        private PreInsuranceProduct preInsuranceProduct;
        private PostInsuranceProduct postInsuranceProduct;


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


        public InsuranceProductGeneric build() {
            return new InsuranceProductGeneric(preInsuranceProduct, postInsuranceProduct);
        }
    }

}
