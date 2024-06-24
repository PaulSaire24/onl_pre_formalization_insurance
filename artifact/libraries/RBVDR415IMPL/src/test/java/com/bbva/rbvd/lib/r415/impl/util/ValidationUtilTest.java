package com.bbva.rbvd.lib.r415.impl.util;


import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

public class ValidationUtilTest {

    @Test
    public void testfilterOneParticipantByType_InputEmpty(){
        Assert.assertNull(ValidationUtil.filterOneParticipantByType(Collections.emptyList(), "PAYMENT_MANAGER"));
    }

    @Test
    public void testAllValuesNotNullOrEmpty_AllValuesNotNull(){
        Assert.assertTrue(ValidationUtil.allValuesNotNullOrEmpty(Collections.singletonList("value")));
    }

    @Test
    public void testAllValuesNotNullOrEmpty_OneValueNull(){
        Assert.assertFalse(ValidationUtil.allValuesNotNullOrEmpty(Arrays.asList("02","plan","null",null,new BigDecimal("13"))));
    }

}