package com.dawei.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private DateUtil() {

    }

    public static Date  getBeforeDay() throws ParseException{
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
        return getDate(parseDate(calendar.getTime()));
//        //设置时间格式
    }

    public static Date getDate(String dateStr) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(dateStr);
    }

    public static String parseDate(Date date) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

}
