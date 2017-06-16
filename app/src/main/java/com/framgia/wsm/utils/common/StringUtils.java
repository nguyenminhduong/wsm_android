package com.framgia.wsm.utils.common;

/**
 * Created by framgia on 05/05/2017.
 */

public final class StringUtils {

    private StringUtils() {
        // No-op
    }

    public static boolean isBlank(String input) {
        return input != null && input.isEmpty();
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
            result = Double.parseDouble(input);
        }
        return result;
    }
}
