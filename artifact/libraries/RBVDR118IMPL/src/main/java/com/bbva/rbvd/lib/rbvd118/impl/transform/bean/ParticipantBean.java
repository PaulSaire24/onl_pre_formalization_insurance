package com.bbva.rbvd.lib.rbvd118.impl.transform.bean;

import com.bbva.rbvd.dto.insrncsale.commons.ContactDetailDTO;
import com.bbva.rbvd.dto.insrncsale.dao.IsrcContractParticipantDAO;
import com.bbva.rbvd.dto.insrncsale.policy.ParticipantDTO;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyDTO;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDProperties;
import com.bbva.rbvd.dto.preformalization.util.ConstantsUtil;
import com.bbva.rbvd.lib.rbvd118.impl.util.ConvertUtil;
import com.bbva.rbvd.lib.rbvd118.impl.util.ValidationUtil;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Objects;
import java.util.stream.Collectors;

public class ParticipantBean {

    private ParticipantBean(){}

    public static List<IsrcContractParticipantDAO> buildIsrcContractParticipants(PolicyDTO requestBody,
                                                                                 List<Map<String, Object>> rolesFromDB, String contractId) {
        ParticipantDTO paymentManager = requestBody.getParticipants().get(0);
        List<ParticipantDTO> legalRepre = ValidationUtil.filterListParticipantsByType(requestBody.getParticipants(),
                ConstantsUtil.Participant.LEGAL_REPRESENTATIVE);
        List<ParticipantDTO> beneficiary = ValidationUtil.filterListParticipantsByType(requestBody.getParticipants(),
                ConstantsUtil.Participant.BENEFICIARY);
        List<ParticipantDTO> insured = ValidationUtil.filterListParticipantsByType(requestBody.getParticipants(),
                ConstantsUtil.Participant.INSURED);

        List<BigDecimal> participantRoles = rolesFromDB.stream()
                        .map(rol -> ConvertUtil.getBigDecimalValue(rol.get(RBVDProperties.FIELD_PARTICIPANT_ROLE_ID.getValue())))
                                .collect(Collectors.toList());
        BigDecimal partyOrderNumber = BigDecimal.valueOf(1L);

        removeRolesIfPresent(participantRoles, legalRepre, ConstantsUtil.Number.TRES);
        removeRolesIfPresent(participantRoles, insured, ConstantsUtil.Number.DOS);

        BigDecimal finalPartyOrderNumber = partyOrderNumber;
        List<IsrcContractParticipantDAO> listParticipants = participantRoles.stream()
                .map(rol -> ParticipantBean.createBasicParticipantDao(contractId,rol, finalPartyOrderNumber,paymentManager,requestBody))
                .collect(Collectors.toList());

        if(!CollectionUtils.isEmpty(legalRepre)){
            for(ParticipantDTO legal : legalRepre){
                listParticipants.add(
                        ParticipantBean.createBasicParticipantDao(contractId,
                                ConvertUtil.getBigDecimalValue(ConstantsUtil.Number.TRES),
                                partyOrderNumber, legal,requestBody));
                partyOrderNumber = partyOrderNumber.add(BigDecimal.valueOf(1L));
            }
        }

        if(insured != null){
            for (ParticipantDTO ins : insured) {
                listParticipants.add(
                        ParticipantBean.createBasicParticipantDao(contractId,
                                ConvertUtil.getBigDecimalValue(ConstantsUtil.Number.DOS),partyOrderNumber,
                                ins,requestBody));
                partyOrderNumber = partyOrderNumber.add(BigDecimal.valueOf(1L));
            }
        }

        if(!CollectionUtils.isEmpty(beneficiary)){
            for(ParticipantDTO benef : beneficiary){
                listParticipants.add(ParticipantBean.createParticipantBeneficiaryDao(contractId,
                        ConvertUtil.getBigDecimalValue(ConstantsUtil.Number.DOS),
                        benef,requestBody,partyOrderNumber));
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

    private static IsrcContractParticipantDAO createParticipantBeneficiaryDao(String id, BigDecimal rol,
                                                                             ParticipantDTO participant,
                                                                              PolicyDTO requestBody,
                                                                             BigDecimal partyOrderNumber) {

        IsrcContractParticipantDAO participantDao = createBasicParticipantDao(id,rol,partyOrderNumber,participant,requestBody);

        participantDao.setCustomerRelationshipType(participant.getRelationship().getId());
        participantDao.setRefundPer(participant.getBenefitPercentage());
        participantDao.setInsuredCustomerName(participant.getFullName());
        participantDao.setFirstLastName(participant.getLastName());
        participantDao.setSecondLastName(participant.getSecondLastName());

        Optional<ContactDetailDTO> phoneContact = participant.getContactDetails().stream()
                .filter(phone -> "PHONE".equals(phone.getContact().getContactDetailType())).findFirst();
        Optional<ContactDetailDTO> emailContact = participant.getContactDetails().stream()
                .filter(email -> "EMAIL".equals(email.getContact().getContactDetailType())).findFirst();
        participantDao.setContactEmailDesc(
                emailContact.map(contactDetailDTO -> contactDetailDTO.getContact().getAddress()).orElse(null));
        participantDao.setPhoneId(
                phoneContact.map(contactDetailDTO -> contactDetailDTO.getContact().getPhoneNumber()).orElse(null));

        return participantDao;
    }

    private static IsrcContractParticipantDAO createBasicParticipantDao(String contractId, BigDecimal rol,
                                                                   BigDecimal partyOrderNumber, ParticipantDTO participant,
                                                                        PolicyDTO requestBody) {
        IsrcContractParticipantDAO participantDao = new IsrcContractParticipantDAO();
        participantDao.setEntityId(contractId.substring(0,4));
        participantDao.setBranchId(contractId.substring(4, 8));
        participantDao.setIntAccountId(contractId.substring(10));
        participantDao.setParticipantRoleId(rol);
        participantDao.setPartyOrderNumber(partyOrderNumber);
        participantDao.setPersonalDocType(participant.getIdentityDocument().getDocumentType().getId());
        participantDao.setParticipantPersonalId(participant.getIdentityDocument().getNumber());
        participantDao.setCustomerId(Objects.nonNull(participant.getCustomerId()) ? participant.getCustomerId() : null);
        participantDao.setCreationUserId(requestBody.getCreationUser());
        participantDao.setUserAuditId(requestBody.getUserAudit());
        return participantDao;
    }

}
