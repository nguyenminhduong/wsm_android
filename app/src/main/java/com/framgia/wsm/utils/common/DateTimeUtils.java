package com.framgia.wsm.utils.common;

import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import com.framgia.wsm.data.model.LeaveType;
import com.framgia.wsm.screen.requestoff.RequestOffViewModel;
import com.framgia.wsm.utils.Constant;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static com.framgia.wsm.utils.Constant.TimeConst.DAY_25_OF_MONTH;
import static com.framgia.wsm.utils.Constant.TimeConst.ONE_MONTH;

/**
 * Created by framgia on 19/05/2017.
 */
public final class DateTimeUtils {

    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy/MM/dd";
    public static final String DATE_FORMAT_YYYY_MM_DD_2 = "yyyy-MM-dd";
    public static final String DATE_FORMAT_EEE_D_MMM__YYYY = "EEE, d MMM yyyy";
    public static final String DATE_FORMAT_YYYY_MM_JAPANESE = "MM/yyyy";
    public static final String DATE_FORMAT_MMMM = "MMMM";
    public static final String FORMAT_DATE = "dd/MM/yyyy";
    public static final String TIME_FORMAT_HH_MM = "HH:mm";
    public static final String DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM = "yyyy/MM/dd - HH:mm";
    public static final String DATE_TIME_FORMAT_HH_MM_DD_MM_YYYY = "HH:mm - dd/MM/yyyy";
    public static final String DATE_TIME_FORMAT_HH_MM_DD_MM_YYYY_2 = "HH:mm dd/MM/yyyy";
    public static final String DATE_FORMAT_YYYY_MM_DD_A = "yyyy/MM/dd a";
    public static final String INPUT_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String DATE_FORMAT_YYYY_MM = "MM/yyyy";
    private static final String TAG = DateTimeUtils.class.getName();
    private static final int DAY_OF_YEAR = 365;
    private static final String TIME_ZONE_GMT = "GMT";
    private static final int MINUTES_OF_HOUR = 60;

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
        DateFormat df = new SimpleDateFormat(format, Locale.US);
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

    public static Date convertStringToDate(String date, String format) {
        SimpleDateFormat parser = new SimpleDateFormat(format, Locale.getDefault());
        try {
            return parser.parse(date);
        } catch (java.text.ParseException e) {
            return new Date();
        }
    }

    public static Date convertStringToDateTime(String date) {
        SimpleDateFormat parser =
                new SimpleDateFormat(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM, Locale.getDefault());
        try {
            return parser.parse(date);
        } catch (java.text.ParseException e) {
            return new Date();
        }
    }

    public static String convertDateTimeToDate(String dateTime) {
        return convertToString(convertStringToDateTime(dateTime), DATE_FORMAT_YYYY_MM_DD);
    }

    public static String convertDateTimeToString(String dateTime, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        return dateTime + Constant.BLANK_DASH_BLANK + convertToString(calendar.getTime(),
                TIME_FORMAT_HH_MM);
    }

    public static String changeTimeOfDateString(String dateTime, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        return convertToString(convertStringToDate(dateTime), DATE_FORMAT_YYYY_MM_DD)
                + Constant.BLANK_DASH_BLANK
                + convertToString(calendar.getTime(), TIME_FORMAT_HH_MM);
    }

    public static int getMinutesBetweenTwoDate(String dateFrom, String dateTo,
            int currentLeaveTypeId, List<LeaveType> typeList) {
        int totalCompensationMinutes = 0;
        int compensationMinutes = (int) TimeUnit.MILLISECONDS.toMinutes(
                convertStringToDateTime(dateFrom).getTime() - convertStringToDateTime(
                        dateTo).getTime());
        for (int i = 0; i < typeList.size(); i++) {
            if (currentLeaveTypeId == typeList.get(i).getId()) {
                int blockMinutes = typeList.get(i).getBlockMinutes();
                if (blockMinutes > 0) {
                    int surplus = compensationMinutes % blockMinutes;
                    if (surplus != 0) {
                        totalCompensationMinutes =
                                blockMinutes * (compensationMinutes / blockMinutes) + blockMinutes;
                    }
                    if (surplus == 0) {
                        totalCompensationMinutes =
                                blockMinutes * (compensationMinutes / blockMinutes);
                    }
                } else {
                    totalCompensationMinutes = compensationMinutes;
                }
            }
        }
        return totalCompensationMinutes;
    }

    public static String addMinutesToStringDate(String dateTime, int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(convertStringToDateTime(dateTime));
        cal.add(Calendar.MINUTE, minutes);
        return convertToString(cal.getTime(), DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM);
    }

    public static String convertDateToString(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        return convertToString(calendar.getTime(), DATE_FORMAT_YYYY_MM_DD);
    }

    public static boolean checkHourOfDateLessThan(String dateTime, int hour, int minute) {
        if (DateTimeUtils.getHourOfDay(dateTime) < hour
                || DateTimeUtils.getHourOfDay(dateTime) == hour
                && DateTimeUtils.getMinute(dateTime) < minute) {
            return true;
        }
        return false;
    }

    /**
     * kiểm tra khoảng thời gian của 2 datetime có <= hour
     */
    public static boolean checkHourOfDateLessThanOrEqualWithDuration(String firstTime,
            String secondTime, float hour) {
        Date firstDate = convertStringToDate(
                convertUiFormatToDataFormat(firstTime, DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM,
                        TIME_FORMAT_HH_MM), TIME_FORMAT_HH_MM);
        String secondTimeAfterAddDuration =
                addMinutesToStringDate(secondTime, (int) (hour * MINUTES_OF_HOUR));
        Date secondDate = convertStringToDate(
                convertUiFormatToDataFormat(secondTimeAfterAddDuration,
                        DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM, TIME_FORMAT_HH_MM), TIME_FORMAT_HH_MM);
        return firstDate.before(secondDate) || firstDate.equals(secondDate);
    }

    /**
     * kiểm tra giờ của datetime có <= hour, minute
     */
    public static boolean checkHourOfDateLessThanOrEqual(String dateTime, int hour, int minute) {
        if (DateTimeUtils.getHourOfDay(dateTime) < hour
                || DateTimeUtils.getHourOfDay(dateTime) == hour
                && DateTimeUtils.getMinute(dateTime) <= minute) {
            return true;
        }
        return false;
    }

    public static int getDayOfMonth(String dateTime) {
        return getDateOrTime(dateTime, Calendar.DAY_OF_MONTH);
    }

    public static int getMonth(String dateTime) {
        return getDateOrTime(dateTime, Calendar.MONTH);
    }

    public static int getYear(String dateTime) {
        return getDateOrTime(dateTime, Calendar.YEAR);
    }

    public static int getHourOfDay(String dateTime) {
        return getDateOrTime(dateTime, Calendar.HOUR_OF_DAY);
    }

    public static int getMinute(String dateTime) {
        return getDateOrTime(dateTime, Calendar.MINUTE);
    }

    public static int getDayOfWeek(String dateTime) {
        return getDateOrTime(dateTime, Calendar.DAY_OF_WEEK);
    }

    private static int getDateOrTime(String dateTime, int dateOrTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(convertStringToDateTime(dateTime));
        return calendar.get(dateOrTime);
    }

    public static Date currentMonthWorking() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - ONE_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, DAY_25_OF_MONTH);
        String currentMonth = DateTimeUtils.convertToString(calendar.getTime(),
                DateTimeUtils.DATE_FORMAT_YYYY_MM_DD);
        return DateTimeUtils.convertStringToDate(currentMonth);
    }

    public static int getDayOfWeek(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    private static String getDateNow() {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD_2, Locale.getDefault());
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    public static double getDayOfYear(String day) {
        Date dateNow;
        Date dateContract;
        double dayDifference = 0;
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD_2, Locale.getDefault());
        try {
            dateNow = simpleDateFormat.parse(DateTimeUtils.getDateNow());
            dateContract = simpleDateFormat.parse(day);
            long dayWork = Math.abs(dateNow.getTime() - dateContract.getTime());
            long dayWorkOfYear = dayWork / DateUtils.DAY_IN_MILLIS;
            dayDifference = Double.parseDouble(Long.toString(dayWorkOfYear)) / DAY_OF_YEAR;
        } catch (ParseException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
        return dayDifference;
    }

    public static String convertDateToString(String input) {
        if (input == null) {
            return null;
        }
        Date date = convertStringToDate(input, DATE_FORMAT_YYYY_MM_DD_A);
        return convertToString(date, DATE_FORMAT_YYYY_MM_DD);
    }

    public static int getSessionDay(String input) {
        if (input == null) {
            return RequestOffViewModel.DaySession.AM;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(convertStringToDate(input, DATE_FORMAT_YYYY_MM_DD_A));
        return calendar.get(Calendar.AM_PM);
    }

    public static String convertUiFormatToDataFormat(String time, String inputFormat,
            String outputFormat) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        TimeZone gmtTime = TimeZone.getTimeZone(TIME_ZONE_GMT);
        SimpleDateFormat sdf = new SimpleDateFormat(inputFormat, Locale.getDefault());
        sdf.setTimeZone(gmtTime);
        SimpleDateFormat newSdf = new SimpleDateFormat(outputFormat, Locale.getDefault());
        newSdf.setTimeZone(gmtTime);
        try {
            return newSdf.format(sdf.parse(time));
        } catch (ParseException e) {
            return null;
        }
    }

    public static String dayFirstMonthWorking(int cutOffDate) {
        return getFirstOrLastDateMonthWorking(cutOffDate, getFirstDateOfMonth(cutOffDate));
    }

    public static String dayLastMonthWorking(int cutOffDate) {
        return getFirstOrLastDateMonthWorking(cutOffDate, getLastDateOfMonth(cutOffDate));
    }

    private static String getFirstOrLastDateMonthWorking(int cutOffDate, Date inputDate) {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        calendar.setTime(inputDate);
        if (currentDate.after(getLastDateOfMonth(cutOffDate))) {
            int currentMonth = calendar.get(Calendar.MONTH);
            calendar.set(Calendar.MONTH, currentMonth + ONE_MONTH);
        }
        return DateTimeUtils.convertToString(calendar.getTime(),
                DateTimeUtils.DATE_FORMAT_YYYY_MM_DD);
    }

    public static String getMonthWorking(int cutOffDate) {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        int currentMonth = calendar.get(Calendar.MONTH);
        if (currentDate.before(getLastDateOfMonth(cutOffDate)) && currentDate.after(
                getFirstDateOfMonth(cutOffDate))) {
            calendar.set(Calendar.MONTH, currentMonth);
        }
        if (currentDate.after(getLastDateOfMonth(cutOffDate))) {
            calendar.set(Calendar.MONTH, currentMonth + ONE_MONTH);
        }
        return DateTimeUtils.convertToString(calendar.getTime(), DateTimeUtils.DATE_FORMAT_YYYY_MM);
    }

    private static Date getLastDateOfMonth(int cutOffDate) {
        Calendar calendar = Calendar.getInstance();
        int totalDateOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (totalDateOfMonth > cutOffDate) {
            calendar.set(Calendar.DAY_OF_MONTH, cutOffDate);
            return calendar.getTime();
        }
        calendar.set(Calendar.DAY_OF_MONTH, totalDateOfMonth);
        return calendar.getTime();
    }

    private static Date getFirstDateOfMonth(int cutOffDate) {
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH);
        if (currentMonth == Calendar.JANUARY) {
            calendar.set(Calendar.MONTH, Calendar.DECEMBER);
            calendar.set(Calendar.YEAR, Calendar.YEAR - 1);
        } else {
            calendar.set(Calendar.MONTH, currentMonth - 1);
        }

        int totalDateOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        if (totalDateOfMonth <= getCutOffDate(cutOffDate)) {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.MONTH, currentMonth);
            return calendar.getTime();
        }
        calendar.set(Calendar.DAY_OF_MONTH, cutOffDate + 1);
        calendar.set(Calendar.MONTH, currentMonth - 1);
        return calendar.getTime();
    }

    private static int getCutOffDate(int cutOffDate) {
        Calendar calendar = Calendar.getInstance();
        int totalDateOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (totalDateOfMonth > cutOffDate) {
            return cutOffDate;
        }
        return totalDateOfMonth;
    }

    public static long getTimeAgo(String date) {
        Timestamp timestamp = new Timestamp(convertStringToDate(date, INPUT_TIME_FORMAT).getTime());
        Timestamp timestampNow = new Timestamp(new Date().getTime());
        return timestampNow.getTime() - timestamp.getTime();
    }
}
