package com.framgia.wsm.utils;

import android.support.annotation.IntDef;

/**
 * Created by framgia on 29/08/2017.
 */

@IntDef({ TypeStatus.PENDING, TypeStatus.APPROVE, TypeStatus.REJECTED, TypeStatus.CANCELED })
public @interface TypeStatus {
    int PENDING = 0;
    int APPROVE = 1;
    int REJECTED = 2;
    int CANCELED = 4;
}
