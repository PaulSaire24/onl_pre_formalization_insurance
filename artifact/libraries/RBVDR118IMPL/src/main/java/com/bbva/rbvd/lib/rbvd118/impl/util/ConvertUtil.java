package com.bbva.rbvd.lib.rbvd118.impl.util;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import java.util.Date;

public class ConvertUtil {

    private ConvertUtil(){}


    public static String generateCorrectDateFormat(LocalDate localDate) {
        return String.format("%02d/%02d/%d", localDate.getDayOfMonth(), localDate.getMonthOfYear(), localDate.getYear());
    }

    public static LocalDate convertDateToLocalDate(Date date) {
        return new LocalDate(date, DateTimeZone.forID(GMT_TIME_ZONE));
    }

}
