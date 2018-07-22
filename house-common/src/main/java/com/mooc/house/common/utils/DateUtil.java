package com.mooc.house.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/7/5.
 */
public final class DateUtil {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static Date strToDateYMD(String date) {
        Date returnDate = null;
        try {
            returnDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return returnDate;
    }

    public static String dateToStrYMD(Date date) {
        return sdf.format(date);
    }

    public static Date getNowDate() {
        String format = sdf.format(new Date());
        Date date = null;
        try {
            date = sdf.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
