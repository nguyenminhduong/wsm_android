package com.framgia.wsm.utils.string;

import android.content.Context;
import android.text.TextUtils;
import com.framgia.wsm.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by le.quang.dao on 28/03/2017.
 */

public final class StringUtils {
    private StringUtils() {
        // No-op
    }

    public static boolean isBlank(String str) {
        return str == null || TextUtils.isEmpty(str.trim()) || str.equalsIgnoreCase("null");
    }

    public static List<String> getListMonths(Context context) {
        final List<String> months = new ArrayList<>();
        months.add(context.getString(R.string.jan));
        months.add(context.getString(R.string.feb));
        months.add(context.getString(R.string.mar));
        months.add(context.getString(R.string.apr));
        months.add(context.getString(R.string.may));
        months.add(context.getString(R.string.jun));
        months.add(context.getString(R.string.jul));
        months.add(context.getString(R.string.aug));
        months.add(context.getString(R.string.sep));
        months.add(context.getString(R.string.oct));
        months.add(context.getString(R.string.nov));
        months.add(context.getString(R.string.dec));
        return months;
    }
}
