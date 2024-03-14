package com.bbva.rbvd.lib.rbvd118.builders;

import com.bbva.rbvd.dto.insrncsale.commons.IdentityDocumentDTO;
import com.bbva.rbvd.dto.insrncsale.policy.ParticipantDTO;
import com.bbva.rbvd.dto.insrncsale.policy.ParticipantTypeDTO;

public class ParticipantBuilder {
    private final ParticipantDTO participantDTO;

    private ParticipantBuilder() {
        participantDTO = new ParticipantDTO();
    }

    public static ParticipantBuilder instance() {
        return new ParticipantBuilder();
    }

    public ParticipantBuilder withId(String id) {
        participantDTO.setId(id);
        return this;
    }

    public ParticipantBuilder withType(ParticipantTypeDTO type) {
        participantDTO.setParticipantType(type);
        return this;
    }

    public ParticipantBuilder withIdentityDocument(IdentityDocumentDTO identityDocument) {
        participantDTO.setIdentityDocument(identityDocument);
        return this;
    }

    public ParticipantDTO build() {
        return participantDTO;
    }
}
