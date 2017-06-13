package com.framgia.wsm.utils;

import android.support.annotation.IntDef;

/**
 * Created by ASUS on 13/06/2017.
 */

@IntDef({
        RequestTypeAnim.REQUEST_OVERTIME, RequestTypeAnim.REQUEST_OFF,
        RequestTypeAnim.REQUEST_LATE_EARLY
})
public @interface RequestTypeAnim {
    int REQUEST_OVERTIME = 0;
    int REQUEST_OFF = 1;
    int REQUEST_LATE_EARLY = 2;
}
