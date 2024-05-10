package com.bbva.rbvd.lib.rbvd118.impl.service.dao;

import com.bbva.rbvd.dto.insrncsale.policy.PolicyDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IParticipantDAO {

    void insertInsuranceParticipants(PolicyDTO requestBody, List<Map<String, Object>> rolesFromDB, String contractId);
    List<Map<String, Object>> getRolesByProductIdAndModality(BigDecimal productId, String modality);

}
