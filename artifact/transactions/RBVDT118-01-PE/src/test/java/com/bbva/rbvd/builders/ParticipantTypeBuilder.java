package com.bbva.rbvd.builders;

import com.bbva.rbvd.dto.insrncsale.policy.ParticipantTypeDTO;

public class ParticipantTypeBuilder {
    private final ParticipantTypeDTO participantTypeDTO;

    public ParticipantTypeBuilder() {
        participantTypeDTO = new ParticipantTypeDTO();
    }

    public static ParticipantTypeBuilder instance() {
        return new ParticipantTypeBuilder();
    }

    public ParticipantTypeBuilder withId(String id) {
        participantTypeDTO.setId(id);
        return this;
    }

    public ParticipantTypeDTO build() {
        return participantTypeDTO;
    }
}
