package com.bbva.rbvd.lib.r415.impl;

import com.bbva.apx.exception.business.BusinessException;
import com.bbva.pisd.dto.contract.search.ReceiptSearchCriteria;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyDTO;
import com.bbva.rbvd.lib.r415.impl.pattern.PreFormalizationInsurance;
import com.bbva.rbvd.lib.r415.impl.pattern.impl.InsuranceParameter;
import com.bbva.rbvd.lib.r415.impl.pattern.impl.InsuranceStore;
import com.bbva.rbvd.lib.r415.impl.pattern.product.InsuranceProductGeneric;
import com.bbva.rbvd.lib.r415.impl.pattern.product.InsuranceProductLifeLaw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The RBVDR415Impl class...
 */
public class RBVDR415Impl extends RBVDR415Abstract {

	private static final Logger LOGGER = LoggerFactory.getLogger(RBVDR415Impl.class);

	@Override
	public PolicyDTO executeLogicPreFormalization(PolicyDTO requestBody) {
		try {
			// No tocar por error de modulos
			ReceiptSearchCriteria receiptSearchCriteria = new ReceiptSearchCriteria();
			LOGGER.info("RBVDR415Impl - executeLogicPreFormalization() - receiptSearchCriteria para que no salga error xd: {}", receiptSearchCriteria);

			LOGGER.info("RBVDR415Impl - executeLogicPreFormalization() - requestBody: {}", requestBody);

			PolicyDTO response = null;
			PreFormalizationInsurance preInsuranceProduct;

			if("842".equals(requestBody.getProduct().getId())){
				preInsuranceProduct = InsuranceProductLifeLaw.Builder.an()
						.withPreInsuranceProduct(InsuranceParameter.Builder.an().withPisdr226(this.pisdR226).withPisdr601(this.pisdR601).withApplicationConfigurationService(this.applicationConfigurationService).build())
						.withPostInsuranceProduct(InsuranceStore.Builder.an().withPisdr012(this.pisdR012).withPisdr226(this.pisdR226).withApplicationConfigurationService(this.applicationConfigurationService).build())
						.withInternalApiConnectorImpersonation(this.internalApiConnectorImpersonation)
						.build();

				response = preInsuranceProduct.start(requestBody,this.rbvdR602,this.applicationConfigurationService);
			}else{
				preInsuranceProduct = InsuranceProductGeneric.Builder.an()
						.withPreInsuranceProduct(InsuranceParameter.Builder.an().withPisdr226(this.pisdR226).withPisdr601(this.pisdR601).withApplicationConfigurationService(this.applicationConfigurationService).build())
						.withPostInsuranceProduct(InsuranceStore.Builder.an().withPisdr012(this.pisdR012).withPisdr226(this.pisdR226).withApplicationConfigurationService(this.applicationConfigurationService).build())
						.build();
				response = preInsuranceProduct.start(requestBody,this.rbvdR602,this.applicationConfigurationService);
			}
			LOGGER.info("RBVDR415Impl - executeLogicPreFormalization() - output: {}", requestBody);
			LOGGER.info("RBVDR415Impl - executeLogicPreFormalization() - Fin del proceso");
			return response;
		}catch (BusinessException tuex){
			LOGGER.error("RBVDR415Impl - executeLogicPreFormalization() - BusinessException: {}", tuex.getMessage());
			this.addAdviceWithDescription(tuex.getAdviceCode(),tuex.getMessage());
			return null;
		}
	}

}
