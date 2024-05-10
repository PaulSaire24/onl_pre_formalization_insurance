package com.bbva.rbvd.lib.rbvd118.impl.util;


import com.bbva.rbvd.dto.insrncsale.policy.ParticipantDTO;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDErrors;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

public class ValidationUtilTest {

    @Test
    public void filterOneParticipantByType_TestReturnNull(){
        ParticipantDTO participant = ValidationUtil.filterOneParticipantByType(Collections.emptyList(),"INSURED");

        Assert.assertNull(participant);
    }

    @Test
    public void validateInsertion_TestReturnException(){
        try {
            ValidationUtil.validateInsertion(0, RBVDErrors.INSERTION_ERROR_IN_PARTICIPANT_TABLE);
        } catch (Exception e) {
            Assert.assertEquals(RBVDErrors.INSERTION_ERROR_IN_PARTICIPANT_TABLE.getMessage(), e.getMessage());
        }
    }

    @Test
    public void validateMultipleInsertion_TestReturnException(){
        try {
            ValidationUtil.validateMultipleInsertion(null, RBVDErrors.INSERTION_ERROR_IN_PARTICIPANT_TABLE);
        } catch (Exception e) {
            Assert.assertEquals(RBVDErrors.INSERTION_ERROR_IN_PARTICIPANT_TABLE.getMessage(), e.getMessage());
        }
    }

}