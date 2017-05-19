package com.framgia.wsm.utils.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by framgia on 19/05/2017.
 */

public final class DateTimeUtils {

    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy/MM/dd";
    public static final String DATE_FORMAT_YYYY_MM_JAPANESE = "MM/yyyy";

    private DateTimeUtils() {
        // No-op
    }

    public static int getDaysInMonth(int month, int year) {
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.MARCH:
            case Calendar.MAY:
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.OCTOBER:
            case Calendar.DECEMBER:
                return 31;
            case Calendar.APRIL:
            case Calendar.JUNE:
            case Calendar.SEPTEMBER:
            case Calendar.NOVEMBER:
                return 30;
            case Calendar.FEBRUARY:
                return ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) ? 29 : 28;
            default:
                throw new IllegalArgumentException("Invalid Month");
        }
    }

    public static String convertToString(Date source, String format) {
        if (source == null) {
            return null;
        }
        DateFormat df = new SimpleDateFormat(format);
        return df.format(source);
    }

    public static Date convertStringToDate(String date) {
        SimpleDateFormat parser = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD, Locale.getDefault());
        try {
            return parser.parse(date);
        } catch (java.text.ParseException e) {
            return new Date();
        }
    }
}
