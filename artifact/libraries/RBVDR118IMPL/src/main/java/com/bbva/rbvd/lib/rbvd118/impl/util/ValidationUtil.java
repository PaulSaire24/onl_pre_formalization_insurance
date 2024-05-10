package com.bbva.rbvd.lib.rbvd118.impl.util;


import com.bbva.rbvd.dto.insrncsale.policy.ParticipantDTO;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyDTO;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDErrors;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDValidation;
import com.bbva.rbvd.dto.preformalization.util.ConstantsUtil;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;


public class ValidationUtil {

    private ValidationUtil(){}

    public static ParticipantDTO filterOneParticipantByType(List<ParticipantDTO> participants,String participantType){
        if(!CollectionUtils.isEmpty(participants)){
            Optional<ParticipantDTO> filter = participants.stream()
                    .filter(participantDTO -> participantType.equals(participantDTO.getParticipantType().getId()))
                    .findFirst();
            return filter.orElse(null);
        }else{
            return null;
        }
    }

    public static List<ParticipantDTO> filterListParticipantsByType(List<ParticipantDTO> participants, String participantType) {
        List<ParticipantDTO> participantDTOS = participants.stream()
                    .filter(participantDTO -> participantType.equals(participantDTO.getParticipantType().getId()))
                    .collect(Collectors.toList());
        return participantDTOS.isEmpty() ? null : participantDTOS;
    }

    public static void validateInsertion(int insertedRows, RBVDErrors error) {
        if (insertedRows != 1) {
            throw RBVDValidation.build(error);
        }
    }

    public static void validateMultipleInsertion(int[] insertedRows, RBVDErrors error) {
        if(isNull(insertedRows) || insertedRows.length == 0) {
            throw RBVDValidation.build(error);
        }
    }

    public static boolean validateEndorsement(PolicyDTO requestBody) {

        ParticipantDTO participantDTO = ValidationUtil.filterOneParticipantByType(
                requestBody.getParticipants(), ConstantsUtil.Participant.ENDORSEE);

        return participantDTO != null && participantDTO.getIdentityDocument() != null
                && participantDTO.getIdentityDocument().getDocumentType().getId() != null
                && ConstantsUtil.DocumentType.RUC.equals(participantDTO.getIdentityDocument().getDocumentType().getId())
                && participantDTO.getBenefitPercentage() != null;
    }



}