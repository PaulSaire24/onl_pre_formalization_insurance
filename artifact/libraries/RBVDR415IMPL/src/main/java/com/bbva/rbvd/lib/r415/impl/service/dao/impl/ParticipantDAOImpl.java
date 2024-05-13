package com.bbva.rbvd.lib.r415.impl.service.dao.impl;

import com.bbva.pisd.dto.insurance.utils.PISDProperties;
import com.bbva.pisd.lib.r012.PISDR012;
import com.bbva.rbvd.dto.insrncsale.dao.IsrcContractParticipantDAO;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyDTO;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDErrors;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDProperties;
import com.bbva.rbvd.lib.r415.impl.service.dao.IParticipantDAO;
import com.bbva.rbvd.lib.r415.impl.transform.bean.ParticipantBean;
import com.bbva.rbvd.lib.r415.impl.transform.map.ParticipantMap;
import com.bbva.rbvd.lib.r415.impl.util.ValidationUtil;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class ParticipantDAOImpl implements IParticipantDAO {

    private final PISDR012 pisdr012;

    public ParticipantDAOImpl(PISDR012 pisdr012) {
        this.pisdr012 = pisdr012;
    }


    @Override
    public void insertInsuranceParticipants(PolicyDTO requestBody, List<Map<String, Object>> rolesFromDB, String contractId) {
        List<IsrcContractParticipantDAO> participants = ParticipantBean.buildIsrcContractParticipants(
                requestBody, rolesFromDB, contractId);
        Map<String, Object>[] participantsArguments = ParticipantMap.createSaveParticipantArguments(participants);
        int[] saveParticipants = this.pisdr012.executeMultipleInsertionOrUpdate(RBVDProperties.QUERY_INSERT_INSRNC_CTR_PARTICIPANT.getValue(),
                participantsArguments);

        ValidationUtil.validateMultipleInsertion(saveParticipants, RBVDErrors.INSERTION_ERROR_IN_PARTICIPANT_TABLE);
    }

    @Override
    public List<Map<String, Object>> getRolesByProductIdAndModality(BigDecimal productId, String modality) {
        Map<String, Object> responseQueryRoles = this.pisdr012.executeGetRolesByProductAndModality(productId, modality);
        return (List<Map<String, Object>>) responseQueryRoles.get(
                PISDProperties.KEY_OF_INSRC_LIST_RESPONSES.getValue());
    }

}
