package com.framgia.wsm.utils;

import android.support.annotation.StringDef;

import static com.framgia.wsm.utils.StatusCode.ACCEPT_CODE;
import static com.framgia.wsm.utils.StatusCode.PENDING_CODE;
import static com.framgia.wsm.utils.StatusCode.REJECT_CODE;

/**
 * Created by ASUS on 16/06/2017.
 */

@StringDef({ ACCEPT_CODE, PENDING_CODE, REJECT_CODE })
public @interface StatusCode {
    String ACCEPT_CODE = "approved";
    String PENDING_CODE = "pending";
    String REJECT_CODE = "rejected";
}
