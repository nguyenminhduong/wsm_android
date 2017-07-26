package com.framgia.wsm.utils;

import android.support.annotation.IntDef;

/**
 * Created by tri on 26/07/2017.
 */

@IntDef({
        TypeToast.SUCCESS, TypeToast.ERROR, TypeToast.INFOR, TypeToast.WARNING
})
public @interface TypeToast {
    int SUCCESS = 0;
    int ERROR = 1;
    int INFOR = 2;
    int WARNING = 4;
}
