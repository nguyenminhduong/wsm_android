package com.framgia.wsm.utils;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public final class Constant {

    // TODO: 17/05/2017 update later
    public static final String END_POINT_URL = "http://edev.framgia.vn";
    // For bundle
    public static final String ARGUMENT_LIST_USER = "ARGUMENT_LIST_USER";
    public static final String BREAK_LINE = "\n";
    public static final String DRAWER_IS_OPEN = "OPEN";
    public static final String DRAWER_IS_CLOSE = "CLOSE";
    public static final String EXTRA_REQUEST_TYPE = "EXTRA_REQUEST_TYPE";
    public static final String EXTRA_REQUEST_LEAVE = "REQUEST_LEAVE";
    public static final String EXTRA_REQUEST_OVERTIME = "REQUEST_OVERTIME";
    public static final String BLANK_DASH_BLANK = " - ";
    public static final String BLANK = " ";
    public static final String EXTRA_REQUEST_OFF = "REQUEST_OFF";
    public static final String EXTRA_ACTION_TYPE = "ACTION_TYPE";
    public static final String EXTRA_USER = "EXTRA_USER";
    public static final String STAFF_OFFICIAL = "B";
    public static final int BEGIN_INDEX = 0;
    public static final int END_INDEX = 1;
    public static final String BEGIN_MORNING_TIME = "07:45";
    public static final String END_MORNING_TIME = "11:45";
    public static final String BEGIN_AFTERNOON_TIME = "12:45";
    public static final String END_AFTERNOON_TIME = "16:45";
    public static final String DATE_TIME_SPACE = " - ";
    public static final String EXTRA_REQUEST_TYPE_CODE = "EXTRA_REQUEST_TYPE_CODE";

    private Constant() {
        // No-op
    }

    public static class TimeConst {
        public static final int SEVEN_HOUR = 7;
        public static final int EIGHT_HOUR = 8;
        public static final int NIGHT_HOUR = 9;
        public static final int TEN_HOUR = 10;
        public static final int ELEVEN_HOUR = 11;
        public static final int TWELVE_HOUR = 12;
        public static final int THIRTEEN_HOUR = 13;
        public static final int FOURTEEN_HOUR = 14;
        public static final int FIFTEEN_HOUR = 15;
        public static final int SIXTEEN_HOUR = 16;
        public static final int FIFTEEN_MINUTES = 15;
        public static final int FORTY_FIVE_MINUTES = 45;
        public static final int ONE_MONTH = 1;
        public static final int DAY_25_OF_MONTH = 25;
        public static final int DAY_26_OF_MONTH = 26;
    }

    public static class NumberConst {
        public static final double FOURTEEN_DOT_FIVE = 14.5;
        public static final double THREE = 3.0;
        public static final double TWENTY = 20.0;
        public static final double ONE = 1.0;
        public static final double FIVE = 5.0;
        public static final double SIXTY = 60.0;
        public static final double FIFTY = 50.0;
        public static final double ONE_HUNDRED_EIGHTY = 180.0;
        public static final double FOURTEEN = 14.0;
    }

    public static class RequestCode {
        public static final int REQUEST_OFF = 100;
        public static final int REQUEST_OVERTIME = 101;
        public static final int REQUEST_LEAVE = 102;
        public static final int PROFILE_USER = 103;
        public static final int REQUEST_SELECT_AVATAR = 104;
        public static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 105;
    }
}
