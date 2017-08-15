package com.framgia.wsm.utils;

import android.support.annotation.IntDef;

/**
 * Created by minhd on 8/11/2017.
 */

@IntDef({
        UserType.NORMAL, UserType.WHITE_LIST, UserType.CHILDREN, UserType.BABY
})
public @interface UserType {
    int NORMAL = 0;
    int WHITE_LIST = 1;
    int CHILDREN = 2;
    int BABY = 3;
}
