package com.framgia.wsm.screen;

import android.databinding.BaseObservable;
import com.framgia.wsm.screen.requestleave.RequestLeaveViewModel;

/**
 * Created by tri on 02/06/2017.
 */

public abstract class BaseRequestLeave extends BaseObservable {

    public abstract void setVisibleLayoutCheckout(boolean isVisible);

    public abstract void setVisibleLayoutCompensation(boolean isVisible);

    public abstract void setVisibleLayoutCheckin(boolean isVisible);

    public void setLayoutLeaveType(int positionType) {
        if (positionType == RequestLeaveViewModel.PositionType.LEAVE_OUT
                || positionType == RequestLeaveViewModel.PositionType.FORGOT_TO_CHECK_IN_OUT_ALL_DAY
                || positionType == RequestLeaveViewModel.PositionType.FORGOT_CARD_ALL_DAY
                || positionType == RequestLeaveViewModel.PositionType.LEAVE_EARLY_A
                || positionType == RequestLeaveViewModel.PositionType.LEAVE_EARLY_M
                || positionType == RequestLeaveViewModel.PositionType.LEAVE_EARLY_WOMAN_A
                || positionType == RequestLeaveViewModel.PositionType.LEAVE_EARLY_WOMAN_M
                || positionType == RequestLeaveViewModel.PositionType.FORGOT_CARD_OUT
                || positionType == RequestLeaveViewModel.PositionType.FORGOT_TO_CHECK_OUT) {
            setVisibleLayoutCheckout(true);
        } else {
            setVisibleLayoutCheckout(false);
        }
        if (positionType == RequestLeaveViewModel.PositionType.LEAVE_OUT
                || positionType == RequestLeaveViewModel.PositionType.FORGOT_TO_CHECK_IN_OUT_ALL_DAY
                || positionType == RequestLeaveViewModel.PositionType.FORGOT_CARD_ALL_DAY
                || positionType == RequestLeaveViewModel.PositionType.IN_LATE_A
                || positionType == RequestLeaveViewModel.PositionType.IN_LATE_M
                || positionType == RequestLeaveViewModel.PositionType.IN_LATE_WOMAN_A
                || positionType == RequestLeaveViewModel.PositionType.IN_LATE_WOMAN_M
                || positionType == RequestLeaveViewModel.PositionType.FORGOT_CARD_IN
                || positionType == RequestLeaveViewModel.PositionType.FORGOT_TO_CHECK_IN) {
            setVisibleLayoutCheckin(true);
        } else {
            setVisibleLayoutCheckin(false);
        }
        if (positionType == RequestLeaveViewModel.PositionType.IN_LATE_A
                || positionType == RequestLeaveViewModel.PositionType.IN_LATE_M
                || positionType == RequestLeaveViewModel.PositionType.LEAVE_EARLY_A
                || positionType == RequestLeaveViewModel.PositionType.LEAVE_EARLY_M
                || positionType == RequestLeaveViewModel.PositionType.LEAVE_OUT) {
            setVisibleLayoutCompensation(true);
        } else {
            setVisibleLayoutCompensation(false);
        }
    }
}
