package com.bbva.rbvd.lib.r415.impl.transform.bean;

import com.bbva.elara.configuration.manager.application.ApplicationConfigurationService;
import com.bbva.rbvd.dto.insrncsale.dao.IsrcContractParticipantDAO;
import com.bbva.rbvd.dto.insrncsale.policy.ParticipantDTO;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyDTO;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDProperties;
import com.bbva.rbvd.dto.preformalization.util.ConstantsUtil;
import com.bbva.rbvd.lib.r415.impl.util.ConvertUtil;
import com.bbva.rbvd.lib.r415.impl.util.ValidationUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ParticipantBean {
    private final ApplicationConfigurationService applicationConfigurationService;

    public ParticipantBean(ApplicationConfigurationService applicationConfigurationService){
        this.applicationConfigurationService = applicationConfigurationService;
    }

    public List<IsrcContractParticipantDAO> buildIsrcContractParticipants(PolicyDTO requestBody,
                                                                                 List<Map<String, Object>> rolesFromDB, String contractId) {
        ParticipantDTO paymentManager = requestBody.getParticipants().get(0);
        List<ParticipantDTO> legalRepre = ValidationUtil.filterListParticipantsByType(requestBody.getParticipants(),
                ConstantsUtil.Participant.LEGAL_REPRESENTATIVE);

        List<BigDecimal> participantRoles = rolesFromDB.stream()
                        .map(rol -> ConvertUtil.getBigDecimalValue(rol.get(RBVDProperties.FIELD_PARTICIPANT_ROLE_ID.getValue())))
                                .collect(Collectors.toList());
        BigDecimal partyOrderNumber = BigDecimal.valueOf(1L);

        removeRolesIfPresent(participantRoles, legalRepre, ConstantsUtil.Number.TRES);

        BigDecimal finalPartyOrderNumber = partyOrderNumber;
        List<IsrcContractParticipantDAO> listParticipants = participantRoles.stream()
                .map(rol -> createBasicParticipantDao(contractId,rol, finalPartyOrderNumber,paymentManager,requestBody))
                .collect(Collectors.toList());

        if(!CollectionUtils.isEmpty(legalRepre)){
            for(ParticipantDTO legal : legalRepre){
                listParticipants.add(
                        createBasicParticipantDao(contractId,
                                ConvertUtil.getBigDecimalValue(ConstantsUtil.Number.TRES),
                                partyOrderNumber, legal,requestBody));
                partyOrderNumber = partyOrderNumber.add(BigDecimal.valueOf(1L));
            }
        }

        return listParticipants;
    }

    private static void removeRolesIfPresent(List<BigDecimal> participantRoles, Object participant, int role) {
        if (participant != null) {
            participantRoles.removeIf(rol -> rol.compareTo(new BigDecimal(role)) == 0);
        }
    }


    private IsrcContractParticipantDAO createBasicParticipantDao(String contractId, BigDecimal rol,
                                                                   BigDecimal partyOrderNumber, ParticipantDTO participant,
                                                                        PolicyDTO requestBody) {
        IsrcContractParticipantDAO participantDao = new IsrcContractParticipantDAO();
        participantDao.setEntityId(contractId.substring(0,4));
        participantDao.setBranchId(contractId.substring(4, 8));
        participantDao.setIntAccountId(contractId.substring(10));
        participantDao.setParticipantRoleId(rol);
        participantDao.setPartyOrderNumber(partyOrderNumber);
        participantDao.setPersonalDocType(this.applicationConfigurationService.getProperty(
                participant.getIdentityDocument().getDocumentType().getId()));
        participantDao.setParticipantPersonalId(participant.getIdentityDocument().getNumber());
        participantDao.setCustomerId(Objects.nonNull(participant.getCustomerId()) ? participant.getCustomerId() : null);
        participantDao.setCreationUserId(requestBody.getCreationUser());
        participantDao.setUserAuditId(requestBody.getUserAudit());

        //si envian nombres completos (campo fullName) deben enviar en el formato: NOMBRES|APELLIDO PATERNO|APELLIDO MATERNO
        if(!StringUtils.isEmpty(participant.getFullName())){
            List<String> listNames = Arrays.asList(participant.getFullName().split("\\|"));
            if(listNames.size() == 3){
                participantDao.setInsuredCustomerName(listNames.get(0));
                participantDao.setFirstLastName(listNames.get(1));
                participantDao.setSecondLastName(listNames.get(2));
            }
        }

        return participantDao;
    }

}
