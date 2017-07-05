package com.framgia.wsm.utils.common;

import com.framgia.wsm.utils.Constant;

/**
 * Created by framgia on 05/05/2017.
 */

public final class StringUtils {

    private StringUtils() {
        // No-op
    }

    public static boolean isBlank(String input) {
        return input == null || input.isEmpty();
    }

    public static boolean isNotBlank(String input) {
        return !isBlank(input);
    }

    public static int convertStringToNumber(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return Integer.MIN_VALUE;
        }
    }

    public static double convertStringToDouble(String input) {
        double result = 0;
        if (input == null) {
            return result;
        } else {
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                return result;
            }
        }
    }

    public static String getStaffType(String input) {
        if (input == null) {
            return "";
        }
        return input.substring(Constant.BEGIN_INDEX, Constant.END_INDEX);
    }
}
