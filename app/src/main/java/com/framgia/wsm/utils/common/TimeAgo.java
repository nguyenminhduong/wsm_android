package com.framgia.wsm.utils.common;

import android.content.Context;
import com.framgia.wsm.R;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by minhd on 7/31/2017.
 */

public final class TimeAgo {
    private static final List<Long> TIMES =
            Arrays.asList(TimeUnit.DAYS.toMillis(365), TimeUnit.DAYS.toMillis(30),
                    TimeUnit.DAYS.toMillis(1), TimeUnit.HOURS.toMillis(1),
                    TimeUnit.MINUTES.toMillis(1), TimeUnit.SECONDS.toMillis(1));

    private TimeAgo() {
        // No-op
    }

    public static String toDuration(long duration, Context context) {
        String[] timesString = context.getResources().getStringArray(R.array.time_string);
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < TimeAgo.TIMES.size(); i++) {
            Long current = TimeAgo.TIMES.get(i);
            long temp = duration / current;
            if (temp > 0) {
                res.append(temp)
                        .append(' ')
                        .append(timesString[i])
                        .append(temp != 1 ? context.getString(R.string.s) : "")
                        .append(' ')
                        .append(context.getString(R.string.ago));
                break;
            }
        }
        if ("".equals(res.toString())) {
            return context.getString(R.string.just_now);
        } else {
            return res.toString();
        }
    }
}
