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
        if (leaveType == (RequestLeaveViewModel.LeaveTypeId.LEAVE_OUT)
                || leaveType == (RequestLeaveViewModel.LeaveTypeId.FORGOT_CHECK_ALL_DAY)
                || leaveType == (RequestLeaveViewModel.LeaveTypeId.FORGOT_CARD_ALL_DAY)
                || leaveType == (RequestLeaveViewModel.LeaveTypeId.LEAVE_EARLY_A)
                || leaveType == (RequestLeaveViewModel.LeaveTypeId.LEAVE_EARLY_M)
                || leaveType == (RequestLeaveViewModel.LeaveTypeId.LEAVE_EARLY_WOMAN_A)
                || leaveType == (RequestLeaveViewModel.LeaveTypeId.LEAVE_EARLY_WOMAN_M)
                || leaveType == (RequestLeaveViewModel.LeaveTypeId.FORGOT_CARD_OUT)
                || leaveType == (RequestLeaveViewModel.LeaveTypeId.FORGOT_TO_CHECK_OUT)) {
            setVisibleLayoutCheckout(true);
        } else {
            setVisibleLayoutCheckout(false);
        }
        if (leaveType == (RequestLeaveViewModel.LeaveTypeId.LEAVE_OUT)
                || leaveType == (RequestLeaveViewModel.LeaveTypeId.FORGOT_CHECK_ALL_DAY)
                || leaveType == (RequestLeaveViewModel.LeaveTypeId.FORGOT_CARD_ALL_DAY)
                || leaveType == (RequestLeaveViewModel.LeaveTypeId.IN_LATE_A)
                || leaveType == (RequestLeaveViewModel.LeaveTypeId.IN_LATE_M)
                || leaveType == (RequestLeaveViewModel.LeaveTypeId.IN_LATE_WOMAN_A)
                || leaveType == (RequestLeaveViewModel.LeaveTypeId.IN_LATE_WOMAN_M)
                || leaveType == (RequestLeaveViewModel.LeaveTypeId.FORGOT_CARD_IN)
                || leaveType == (RequestLeaveViewModel.LeaveTypeId.FORGOT_TO_CHECK_IN)) {
            setVisibleLayoutCheckin(true);
        } else {
            setVisibleLayoutCheckin(false);
        }
        if (leaveType == (RequestLeaveViewModel.LeaveTypeId.IN_LATE_A)
                || leaveType == (RequestLeaveViewModel.LeaveTypeId.IN_LATE_M)
                || leaveType == (RequestLeaveViewModel.LeaveTypeId.LEAVE_EARLY_A)
                || leaveType == (RequestLeaveViewModel.LeaveTypeId.LEAVE_EARLY_M)
                || leaveType == (RequestLeaveViewModel.LeaveTypeId.LEAVE_OUT)) {
            setVisibleLayoutCompensation(true);
        } else {
            setVisibleLayoutCompensation(false);
        }
    }
}
