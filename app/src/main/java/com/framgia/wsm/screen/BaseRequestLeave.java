package com.framgia.wsm.screen;

import android.databinding.BaseObservable;
import com.framgia.wsm.utils.Constant;

/**
 * Created by tri on 02/06/2017.
 */

public abstract class BaseRequestLeave extends BaseObservable {

    public abstract void setVisibleLayoutCheckout(boolean isVisible);

    public abstract void setVisibleLayoutCompensation(boolean isVisible);

    public abstract void setVisibleLayoutCheckin(boolean isVisible);

    public void setLayoutLeaveType(String leaveType) {
        if (leaveType.equals(Constant.LeaveType.LEAVE_OUT) || leaveType.equals(
                Constant.LeaveType.FORGOT_CHECK_ALL_DAY) || leaveType.equals(
                Constant.LeaveType.FORGOT_CARD_ALL_DAY) || leaveType.equals(
                Constant.LeaveType.LEAVE_EARLY_A) || leaveType.equals(
                Constant.LeaveType.LEAVE_EARLY_M) || leaveType.equals(
                Constant.LeaveType.LEAVE_EARLY_WOMAN_A) || leaveType.equals(
                Constant.LeaveType.LEAVE_EARLY_WOMAN_M) || leaveType.equals(
                Constant.LeaveType.FORGOT_CARD_OUT) || leaveType.equals(
                Constant.LeaveType.FORGOT_TO_CHECK_OUT)) {
            setVisibleLayoutCheckout(true);
        } else {
            setVisibleLayoutCheckout(false);
        }
        if (leaveType.equals(Constant.LeaveType.LEAVE_OUT)
                || leaveType.equals(Constant.LeaveType.FORGOT_CHECK_ALL_DAY)
                || leaveType.equals(Constant.LeaveType.FORGOT_CARD_ALL_DAY)
                || leaveType.equals(Constant.LeaveType.IN_LATE_A)
                || leaveType.equals(Constant.LeaveType.IN_LATE_M)
                || leaveType.equals(Constant.LeaveType.IN_LATE_WOMAN_A)
                || leaveType.equals(Constant.LeaveType.IN_LATE_WOMAN_M)
                || leaveType.equals(Constant.LeaveType.FORGOT_CARD_IN)
                || leaveType.equals(Constant.LeaveType.FORGOT_TO_CHECK_IN)) {
            setVisibleLayoutCheckin(true);
        } else {
            setVisibleLayoutCheckin(false);
        }
        if (leaveType.equals(Constant.LeaveType.IN_LATE_A)
                || leaveType.equals(Constant.LeaveType.IN_LATE_M)
                || leaveType.equals(Constant.LeaveType.LEAVE_EARLY_A)
                || leaveType.equals(Constant.LeaveType.LEAVE_EARLY_M)
                || leaveType.equals(Constant.LeaveType.LEAVE_OUT)) {
            setVisibleLayoutCompensation(true);
        } else {
            setVisibleLayoutCompensation(false);
        }
    }
}
