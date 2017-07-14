package com.framgia.wsm.utils.binding;

import android.graphics.Color;

/**
 * Created by tri on 27/06/2017.
 */

public class ColorManagers {

    private static final String COLOR_GREEN1 = "#C9E4D6";
    private static final String COLOR_GREEN2 = "#67BF7F";
    private static final String COLOR_GREEN3 = "#00AE72";
    private static final String COLOR_GREEN4 = "#00A06B";
    private static final String COLOR_GREEN5 = "#008C5E";
    private static final String COLOR_GREEN6 = "#007F54";
    private static final String COLOR_GREEN7 = "#006241";
    private static final String COLOR_GREEN8 = "#00676B";
    private static final String COLOR_GREEN_DARK = "#009688";
    private static final String COLOR_STATUS_GREEN = "#4CAF50";
    private static final String COLOR_STATUS_BLUE = "#2196F3";
    private static final String COLOR_STATUS_BLUE_DARK = "#1565C0";
    private static final String COLOR_STATUS_RED = "#B22222";

    public ColorManagers() {
    }

    public static int getColorGreen1() {
        return Color.parseColor(COLOR_GREEN1);
    }

    public static int getColorGreen2() {
        return Color.parseColor(COLOR_GREEN2);
    }

    public static int getColorGreen3() {
        return Color.parseColor(COLOR_GREEN3);
    }

    public static int getColorGreen4() {
        return Color.parseColor(COLOR_GREEN4);
    }

    public static int getColorGreen5() {
        return Color.parseColor(COLOR_GREEN5);
    }

    public static int getColorGreen6() {
        return Color.parseColor(COLOR_GREEN6);
    }

    public static int getColorGreen7() {
        return Color.parseColor(COLOR_GREEN7);
    }

    public static int getColorGreen8() {
        return Color.parseColor(COLOR_GREEN8);
    }

    public static int getColorPrimary() {
        return Color.parseColor(COLOR_GREEN_DARK);
    }

    public static int getColorStatusAccept() {
        return Color.parseColor(COLOR_STATUS_GREEN);
    }

    public static int getColorStatusPending() {
        return Color.parseColor(COLOR_STATUS_BLUE);
    }

    public static int getColorStatusForward() {
        return Color.parseColor(COLOR_STATUS_BLUE_DARK);
    }

    public static int getColorStatusReject() {
        return Color.parseColor(COLOR_STATUS_RED);
    }
}
