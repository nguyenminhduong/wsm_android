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

    public void setLayoutLeaveType(int leaveType) {
        if (leaveType == (RequestLeaveViewModel.LeaveType.LEAVE_OUT)
                || leaveType == (RequestLeaveViewModel.LeaveType.FORGOT_CHECK_ALL_DAY)
                || leaveType == (RequestLeaveViewModel.LeaveType.FORGOT_CARD_ALL_DAY)
                || leaveType == (RequestLeaveViewModel.LeaveType.LEAVE_EARLY_A)
                || leaveType == (RequestLeaveViewModel.LeaveType.LEAVE_EARLY_M)
                || leaveType == (RequestLeaveViewModel.LeaveType.LEAVE_EARLY_WOMAN_A)
                || leaveType == (RequestLeaveViewModel.LeaveType.LEAVE_EARLY_WOMAN_M)
                || leaveType == (RequestLeaveViewModel.LeaveType.FORGOT_CARD_OUT)
                || leaveType == (RequestLeaveViewModel.LeaveType.FORGOT_TO_CHECK_OUT)) {
            setVisibleLayoutCheckout(true);
        } else {
            setVisibleLayoutCheckout(false);
        }
        if (leaveType == (RequestLeaveViewModel.LeaveType.LEAVE_OUT)
                || leaveType == (RequestLeaveViewModel.LeaveType.FORGOT_CHECK_ALL_DAY)
                || leaveType == (RequestLeaveViewModel.LeaveType.FORGOT_CARD_ALL_DAY)
                || leaveType == (RequestLeaveViewModel.LeaveType.IN_LATE_A)
                || leaveType == (RequestLeaveViewModel.LeaveType.IN_LATE_M)
                || leaveType == (RequestLeaveViewModel.LeaveType.IN_LATE_WOMAN_A)
                || leaveType == (RequestLeaveViewModel.LeaveType.IN_LATE_WOMAN_M)
                || leaveType == (RequestLeaveViewModel.LeaveType.FORGOT_CARD_IN)
                || leaveType == (RequestLeaveViewModel.LeaveType.FORGOT_TO_CHECK_IN)) {
            setVisibleLayoutCheckin(true);
        } else {
            setVisibleLayoutCheckin(false);
        }
        if (leaveType == (RequestLeaveViewModel.LeaveType.IN_LATE_A)
                || leaveType == (RequestLeaveViewModel.LeaveType.IN_LATE_M)
                || leaveType == (RequestLeaveViewModel.LeaveType.LEAVE_EARLY_A)
                || leaveType == (RequestLeaveViewModel.LeaveType.LEAVE_EARLY_M)
                || leaveType == (RequestLeaveViewModel.LeaveType.LEAVE_OUT)) {
            setVisibleLayoutCompensation(true);
        } else {
            setVisibleLayoutCompensation(false);
        }
    }
}
