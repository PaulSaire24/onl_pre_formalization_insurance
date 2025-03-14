package com.bbva.rbvd.lib.r415.impl.service.dao.impl;

import com.bbva.elara.configuration.manager.application.ApplicationConfigurationService;
import com.bbva.pisd.dto.insurance.utils.PISDProperties;
import com.bbva.pisd.lib.r012.PISDR012;
import com.bbva.rbvd.dto.insrncsale.dao.IsrcContractParticipantDAO;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyDTO;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDProperties;
import com.bbva.rbvd.dto.preformalization.util.RBVDMessageError;
import com.bbva.rbvd.lib.r415.impl.service.dao.IParticipantDAO;
import com.bbva.rbvd.lib.r415.impl.transform.bean.ParticipantBean;
import com.bbva.rbvd.lib.r415.impl.transform.map.ParticipantMap;
import com.bbva.rbvd.lib.r415.impl.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class ParticipantDAOImpl implements IParticipantDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParticipantDAOImpl.class);

    private final PISDR012 pisdr012;
    private final ApplicationConfigurationService applicationConfigurationService;

    public ParticipantDAOImpl(PISDR012 pisdr012,ApplicationConfigurationService applicationConfigurationService) {
        this.pisdr012 = pisdr012;
        this.applicationConfigurationService = applicationConfigurationService;
    }


    @Override
    public void insertInsuranceParticipants(PolicyDTO requestBody, List<Map<String, Object>> rolesFromDB, String contractId) {
        ParticipantBean participantBean = new ParticipantBean(this.applicationConfigurationService);
        List<IsrcContractParticipantDAO> participants = participantBean.buildIsrcContractParticipants(
                requestBody, rolesFromDB, contractId);
        LOGGER.info("ParticipantDAOImpl - insertInsuranceParticipants() | participants: {}", participants.toString());

        Map<String, Object>[] participantsArguments = ParticipantMap.createSaveParticipantArguments(participants);
        LOGGER.info("ParticipantDAOImpl - insertInsuranceParticipants() | participantsArguments: {}", participantsArguments);

        int[] saveParticipants = this.pisdr012.executeMultipleInsertionOrUpdate(RBVDProperties.QUERY_INSERT_INSRNC_CTR_PARTICIPANT.getValue(),
                participantsArguments);

        ValidationUtil.validateMultipleInsertion(saveParticipants,
                RBVDMessageError.ERROR_INSERT_PARTICIPANTS.getAdviceCode(),
                RBVDMessageError.ERROR_INSERT_PARTICIPANTS.getMessage());
    }

    @Override
    public List<Map<String, Object>> getRolesByProductIdAndModality(BigDecimal productId, String modality) {
        Map<String, Object> responseQueryRoles = this.pisdr012.executeGetRolesByProductAndModality(productId, modality);
        return (List<Map<String, Object>>) responseQueryRoles.get(
                PISDProperties.KEY_OF_INSRC_LIST_RESPONSES.getValue());
    }

}
