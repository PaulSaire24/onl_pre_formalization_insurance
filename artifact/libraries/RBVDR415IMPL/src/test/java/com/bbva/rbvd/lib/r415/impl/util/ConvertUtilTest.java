package com.bbva.rbvd.lib.r415.impl.util;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class ConvertUtilTest {

    @Test
    public void testGenerateCorrectDateFormat1(){
        String date = ConvertUtil.generateCorrectDateFormat(LocalDate.parse("2024-03-09"));

        Assert.assertEquals("09/03/2024", date);
    }

    @Test
    public void testGenerateCorrectDateFormat2(){
        String date = ConvertUtil.generateCorrectDateFormat(LocalDate.parse("2024-11-09"));

        Assert.assertEquals("09/11/2024", date);
    }

    @Test
    public void testGenerateCorrectDateFormat3(){
        String date = ConvertUtil.generateCorrectDateFormat(LocalDate.parse("2024-05-18"));

        Assert.assertEquals("18/05/2024", date);
    }

    @Test
    public void testConvertStringDateWithTimeFormatToDate_InputNull(){
        Assert.assertNull(ConvertUtil.convertStringDateWithTimeFormatToDate(null));
    }

    @Test
    public void testConvertStringDateWithDateFormatToDate_InputNull(){
        Assert.assertNull(ConvertUtil.convertStringDateWithDateFormatToDate(null));
    }

    @Test
    public void testConvertLongToBigDecimal(){
        Assert.assertEquals(ConvertUtil.getBigDecimalValue(134L), new BigDecimal(134));
    }

}