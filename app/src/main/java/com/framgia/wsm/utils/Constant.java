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
    }
}
