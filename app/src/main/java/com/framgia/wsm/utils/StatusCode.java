package com.framgia.wsm.utils;

import android.support.annotation.IntDef;

import static com.framgia.wsm.utils.StatusCode.ACCEPT_CODE;
import static com.framgia.wsm.utils.StatusCode.PENDING_CODE;
import static com.framgia.wsm.utils.StatusCode.REJECT_CODE;

/**
 * Created by ASUS on 16/06/2017.
 */

@IntDef({ ACCEPT_CODE, PENDING_CODE, REJECT_CODE })
public @interface StatusCode {
    int ACCEPT_CODE = 0;
    int PENDING_CODE = 1;
    int REJECT_CODE = 2;
}
