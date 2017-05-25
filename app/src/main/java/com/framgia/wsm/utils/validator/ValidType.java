package com.framgia.wsm.utils.validator;

import android.support.annotation.IntDef;

/**
 * Created by le.quang.dao on 17/03/2017.
 */

@IntDef({
        ValidType.NON_EMPTY, ValidType.NG_WORD, ValidType.VALUE_RANGE_0_100, ValidType.EMAIL_FORMAT
})
public @interface ValidType {
    int NON_EMPTY = 0x00;
    int NG_WORD = 0x01;
    int VALUE_RANGE_0_100 = 0x02;
    int EMAIL_FORMAT = 0x03;
}
