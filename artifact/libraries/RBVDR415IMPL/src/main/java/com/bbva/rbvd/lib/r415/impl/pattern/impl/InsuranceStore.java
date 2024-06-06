package com.bbva.rbvd.lib.r415.impl.pattern.impl;

import com.bbva.elara.configuration.manager.application.ApplicationConfigurationService;
import com.bbva.pisd.lib.r012.PISDR012;
import com.bbva.pisd.lib.r226.PISDR226;
import com.bbva.rbvd.lib.r415.impl.pattern.PostInsuranceProduct;
import com.bbva.rbvd.lib.r415.impl.service.dao.IContractDAO;
import com.bbva.rbvd.lib.r415.impl.service.dao.IParticipantDAO;
import com.bbva.rbvd.lib.r415.impl.service.dao.impl.ContractDAOImpl;
import com.bbva.rbvd.lib.r415.impl.service.dao.impl.ParticipantDAOImpl;
import com.bbva.rbvd.lib.r415.impl.transfer.PayloadStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static org.springframework.util.CollectionUtils.isEmpty;

public class InsuranceStore implements PostInsuranceProduct {

    private static final Logger LOGGER = LoggerFactory.getLogger(InsuranceStore.class);

    private final PISDR012 pisdr012;
    private final PISDR226 pisdr226;
    private final ApplicationConfigurationService applicationConfigurationService;

    public InsuranceStore(PISDR012 pisdr012, PISDR226 pisdr226, ApplicationConfigurationService applicationConfigurationService) {
        this.pisdr012 = pisdr012;
        this.pisdr226 = pisdr226;
        this.applicationConfigurationService = applicationConfigurationService;
    }

    @Override
    public void end(PayloadStore payloadStore) {
        this.saveContract(payloadStore);
        this.saveParticipants(payloadStore);
    }

    private void saveContract(PayloadStore payloadStore){
        IContractDAO contractDAO = new ContractDAOImpl(this.pisdr226);
        contractDAO.insertInsuranceContract(payloadStore);
        LOGGER.info("InsuranceStore - saveContract() end");
    }

    private void saveParticipants(PayloadStore payloadStore){
        IParticipantDAO participantDAO = new ParticipantDAOImpl(this.pisdr012, this.applicationConfigurationService);

        //Busca roles por producto y plan
        List<Map<String, Object>> rolesFromDB = participantDAO.getRolesByProductIdAndModality(
                payloadStore.getQuotationDAO().getInsuranceProductId(), payloadStore.getResposeBody().getProduct().getPlan().getId());
        LOGGER.info("InsuranceStore - saveParticipants() - rolesFromDB: {}", rolesFromDB);

        if(!isEmpty(rolesFromDB) && !isEmpty(payloadStore.getResposeBody().getParticipants())){
            //Registra participantes
            participantDAO.insertInsuranceParticipants(payloadStore.getResposeBody(), rolesFromDB, payloadStore.getIcr2Response().getIcmrys2().getNUMCON());
        }
        LOGGER.info("InsuranceStore - saveParticipants() end");
    }

    public static class Builder{
        private PISDR012 pisdr012;
        private PISDR226 pisdr226;
        private ApplicationConfigurationService applicationConfigurationService;

        private Builder(){}
        public static Builder an() {
            return new Builder();
        }

        public Builder withPisdr012(PISDR012 pisdr012){
            this.pisdr012 = pisdr012;
            return this;
        }

        public Builder withPisdr226(PISDR226 pisdr226){
            this.pisdr226 = pisdr226;
            return this;
        }

        public Builder withApplicationConfigurationService(ApplicationConfigurationService applicationConfigurationService){
            this.applicationConfigurationService = applicationConfigurationService;
            return this;
        }


        public InsuranceStore build(){
            return new InsuranceStore(pisdr012, pisdr226, applicationConfigurationService);
        }
    }
}
