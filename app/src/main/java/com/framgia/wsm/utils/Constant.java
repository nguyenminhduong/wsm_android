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
    public static final String EXTRA_REQUEST_LEAVE = "REQUEST_LEAVE";
    public static final String EXTRA_REQUEST_OVERTIME = "REQUEST_OVERTIME";
    public static final String BLANK_DASH_BLANK = " - ";

    private Constant() {
        // No-op
    }

    public static class LeaveType {
        public static final String IN_LATE_M = "In late (M)";
        public static final String IN_LATE_A = "In late (A)";
        public static final String LEAVE_EARLY_M = "Leave early (M)";
        public static final String LEAVE_EARLY_A = "Leave early (A)";
        public static final String LEAVE_OUT = "Leave out";
        public static final String FORGOT_CHECK_ALL_DAY = "Forgot to check in/check out (all day)";
        public static final String FORGOT_TO_CHECK_IN = "Forgot to check in";
        public static final String FORGOT_TO_CHECK_OUT = "Forgot to check out";
        public static final String FORGOT_CARD_ALL_DAY = "Forgot card (all day)";
        public static final String FORGOT_CARD_IN = "Forgot card (in)";
        public static final String FORGOT_CARD_OUT = "Forgot card (out)";
        public static final String IN_LATE_WOMAN_M = "In late (woman only) (M)";
        public static final String IN_LATE_WOMAN_A = "In late (woman only) (A)";
        public static final String LEAVE_EARLY_WOMAN_M = "Leave early (woman only) (M)";
        public static final String LEAVE_EARLY_WOMAN_A = "Leave early (woman only) (A)";
    }
}
