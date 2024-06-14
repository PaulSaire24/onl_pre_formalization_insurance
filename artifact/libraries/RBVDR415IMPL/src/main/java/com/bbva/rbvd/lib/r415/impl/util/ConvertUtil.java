
package com.bbva.rbvd.lib.r415.impl.util;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ConvertUtil {
    private ConvertUtil(){}


    public static String generateCorrectDateFormat(LocalDate localDate) {
        String day = (localDate.getDayOfMonth() < 10)
                ? "0" + localDate.getDayOfMonth()
                : String.valueOf(localDate.getDayOfMonth());
        String month = (localDate.getMonthOfYear() < 10)
                ? "0" + localDate.getMonthOfYear()
                : String.valueOf(localDate.getMonthOfYear());
        return day + "/" + month + "/" + localDate.getYear();
    }

    public static Date convertStringDateWithTimeFormatToDate(String strDate){
        if(StringUtils.isEmpty(strDate)){
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(strDate, formatter);
        return Date.from(dateTime.atZone(TimeZone.getTimeZone("GMT").toZoneId()).toInstant());
    }

    public static Date convertStringDateWithDateFormatToDate(String strDate){
        if(StringUtils.isEmpty(strDate)){
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        java.time.LocalDate localDate = java.time.LocalDate.parse(strDate, formatter);
        return Date.from(localDate.atStartOfDay(TimeZone.getTimeZone("GMT").toZoneId()).toInstant());
    }

    public static Calendar convertDateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static LocalDate convertDateToLocalDate(Date date) {
        return new LocalDate(date, DateTimeZone.forID("GMT"));
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

    public static String getRequestJsonFormat(final Object requestBody) {
        return JsonUtil.getInstance().serialization(requestBody);
    }


}
