package com.example.demo.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_PATTERN = "HH:mm:ss";

    public DateUtil() {
    }

    public static String format(long millis, String pattern) {
        return format(new Date(millis), pattern);
    }

    public static String format(Date date, String pattern) {
        DateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    public static String formartDateTime(Date date) {
        return format(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatUTC(Date date, String pattern) {
        DateFormat formatter = new SimpleDateFormat(pattern);
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(date);
    }

    public static String formartDate(Date date) {
        return format(date, "yyyy-MM-dd");
    }

    public static String formatDate(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String formatTime(Date date) {
        return format(date, TIME_PATTERN);
    }

    public static String formatDateTime(Date date) {
        return format(date, DATE_TIME_PATTERN);
    }

}
