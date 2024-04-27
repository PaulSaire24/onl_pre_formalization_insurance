package com.bbva.rbvd.lib.rbvd118.impl.util;


import com.bbva.rbvd.dto.insrncsale.policy.ParticipantDTO;

import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

public class ValidationUtil {

    private ValidationUtil(){}

    public static ParticipantDTO filterParticipantByType(List<ParticipantDTO> participants,String participantType){
        if(!isEmpty(participants)){
            return participants.stream()
                    .filter(participantDTO -> participantType.equals(participantDTO.getParticipantType().getId()))
                    .findFirst().orElse(null);
        }else{
            return null;
        }
    }

}