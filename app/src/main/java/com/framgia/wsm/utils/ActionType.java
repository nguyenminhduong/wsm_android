package com.framgia.wsm.utils;

import android.support.annotation.IntDef;

/**
 * Created by Duong on 6/22/2017.
 */
@IntDef({
        ActionType.ACTION_CREATE, ActionType.ACTION_EDIT, ActionType.ACTION_DETAIL,
        ActionType.ACTION_CONFIRM
})
public @interface ActionType {
    int ACTION_CREATE = 0;
    int ACTION_EDIT = 1;
    int ACTION_DETAIL = 2;
    int ACTION_CONFIRM = 3;
}
