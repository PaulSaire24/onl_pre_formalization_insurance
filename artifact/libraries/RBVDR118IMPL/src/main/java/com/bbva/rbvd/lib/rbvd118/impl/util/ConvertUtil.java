
package com.bbva.rbvd.lib.rbvd118.impl.util;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import java.math.BigDecimal;
import java.util.Date;

public class ConvertUtil {
    private ConvertUtil(){}

    private static final String GMT_TIME_ZONE = "GMT";

    public static String generateCorrectDateFormat(LocalDate localDate) {
        String day = (localDate.getDayOfMonth() < 10)
                ? "0" + localDate.getDayOfMonth()
                : String.valueOf(localDate.getDayOfMonth());
        String month = (localDate.getMonthOfYear() < 10)
                ? "0" + localDate.getMonthOfYear()
                : String.valueOf(localDate.getMonthOfYear());
        return day + "/" + month + "/" + localDate.getYear();
    }

    public static LocalDate convertDateToLocalDate(Date date) {
        return new LocalDate(date, DateTimeZone.forID(GMT_TIME_ZONE));
    }

    public static BigDecimal getBigDecimalValue(Object value){
        BigDecimal ret = null;
        if(value != null){
            if(value instanceof BigDecimal){
                ret = (BigDecimal) value;
            }else if(value instanceof String){
                ret = new BigDecimal((String) value);
            }else if(value instanceof Double){
                ret = BigDecimal.valueOf(((Double) value).doubleValue());
            }else if(value instanceof Integer){
                ret = BigDecimal.valueOf((Integer) value);
            }else if(value instanceof Long){
                ret = BigDecimal.valueOf((Long) value);
            }
        }

        return ret;
    }


}
