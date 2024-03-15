package com.bbva.rbvd.lib.rbvd118.util;

import com.bbva.rbvd.lib.rbvd118.impl.util.ConstantsUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConstantsUtilTest {

    @Test
    public void participantRolContractorShouldReturnEight() {
        assertEquals(Integer.valueOf(8), ConstantsUtil.ParticipantRol.CONTRACTOR.getRol());
    }

    @Test
    public void participantRolInsuredShouldReturnNine() {
        assertEquals(Integer.valueOf(9), ConstantsUtil.ParticipantRol.INSURED.getRol());
    }

    @Test
    public void participantRolPaymentManagerShouldReturnTwentyThree() {
        assertEquals(Integer.valueOf(23), ConstantsUtil.ParticipantRol.PAYMENT_MANAGER.getRol());
    }

    @Test
    public void numberDiezShouldReturnTen() {
        assertEquals(10, ConstantsUtil.Number.DIEZ);
    }

    @Test
    public void numberTresShouldReturnThree() {
        assertEquals(3, ConstantsUtil.Number.TRES);
    }

    @Test
    public void numberDosShouldReturnTwo() {
        assertEquals(2, ConstantsUtil.Number.DOS);
    }

    @Test
    public void numberUnoShouldReturnOne() {
        assertEquals(1, ConstantsUtil.Number.UNO);
    }

    @Test
    public void numberCeroShouldReturnZero() {
        assertEquals(0, ConstantsUtil.Number.CERO);
    }
}