package com.framgia.wsm.screen.listrequest;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.IntDef;
import com.framgia.wsm.BR;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.Branch;
import com.framgia.wsm.data.model.LeaveRequest;
import com.framgia.wsm.data.model.OffRequest;
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.data.model.Shifts;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.screen.BaseRecyclerViewAdapter;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.StatusCode;
import com.framgia.wsm.utils.UserType;
import com.framgia.wsm.utils.common.DateTimeUtils;
import com.framgia.wsm.utils.common.StringUtils;
import java.util.List;

/**
 * Created by ASUS on 13/06/2017.
 */

public class ItemListRequestViewModel extends BaseObservable {

    private final Object mObject;
    private final BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener<Object>
            mItemClickListener;
    private Context mContext;
    private LeaveRequest mRequest;
    private OffRequest mRequestOff;
    private RequestOverTime mRequestOverTime;
    private int mStatusImage;
    private int mExtraTime;
    private User mUser;
    private int positionBranchLeaveRequest;

    ItemListRequestViewModel(Context context, Object object,
            BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener<Object> itemClickListener,
            User user) {
        mContext = context;
        mObject = object;
        mItemClickListener = itemClickListener;
        if (object instanceof LeaveRequest) {
            mRequest = (LeaveRequest) object;
            mExtraTime =
                    user.getSpecial() == UserType.CHILDREN ? Constant.TimeConst.FIFTEEN_MINUTES : 0;
            mUser = user;
            positionBranchLeaveRequest =
                    searchPositionBranch(user.getBranches(), mRequest.getBranch().getBranchId());
        } else if (object instanceof OffRequest) {
            mRequestOff = (OffRequest) object;
        } else {
            mRequestOverTime = (RequestOverTime) object;
        }
        initValueStatus();
    }

    private int searchPositionBranch(List<Branch> branchList, int branchId) {
        for (int i = 0; i < branchList.size(); i++) {
            if (branchList.get(i).getBranchId() == branchId) {
                return i;
            }
        }
        return 0;
    }

    private Shifts getCurrentShift() {
        //TODO edit later
        return mUser.getBranches().get(positionBranchLeaveRequest).getShifts().get(0);
    }

    public String getTimeRequestLeave() {
        if (mRequest != null) {
            final String beginMorningTime =
                    DateTimeUtils.convertUiFormatToDataFormatAndPlusExtraTime(
                            getCurrentShift().getTimeIn(), DateTimeUtils.INPUT_TIME_FORMAT,
                            DateTimeUtils.TIME_FORMAT_HH_MM, mExtraTime);
            final String beginAfterTime =
                    DateTimeUtils.convertUiFormatToDataFormat(getCurrentShift().getTimeAfternoon(),
                            DateTimeUtils.INPUT_TIME_FORMAT, DateTimeUtils.TIME_FORMAT_HH_MM);
            final String timeLunch =
                    DateTimeUtils.convertUiFormatToDataFormat(getCurrentShift().getTimeLunch(),
                            DateTimeUtils.INPUT_TIME_FORMAT, DateTimeUtils.TIME_FORMAT_HH_MM);
            final String timeOut = DateTimeUtils.convertUiFormatToDataFormatAndPlusExtraTime(
                    getCurrentShift().getTimeOut(), DateTimeUtils.INPUT_TIME_FORMAT,
                    DateTimeUtils.TIME_FORMAT_HH_MM, mExtraTime);
            final String requestCheckInByTimeAndDate = DateTimeUtils.convertUiFormatToDataFormat(
                    mRequest.getCheckInTime() + Constant.DATE_TIME_SPACE,
                    DateTimeUtils.INPUT_TIME_FORMAT,
                    DateTimeUtils.DATE_TIME_FORMAT_HH_MM_DD_MM_YYYY_2);
            final String requestCheckOutByTimeAndDate =
                    DateTimeUtils.convertUiFormatToDataFormat(mRequest.getCheckOutTime(),
                            DateTimeUtils.INPUT_TIME_FORMAT,
                            DateTimeUtils.DATE_TIME_FORMAT_HH_MM_DD_MM_YYYY_2);
            final String requestCheckOutByTime =
                    DateTimeUtils.convertUiFormatToDataFormat(mRequest.getCheckOutTime(),
                            DateTimeUtils.INPUT_TIME_FORMAT, DateTimeUtils.TIME_FORMAT_HH_MM);
            final String requestCheckInByTime =
                    DateTimeUtils.convertUiFormatToDataFormat(mRequest.getCheckInTime(),
                            DateTimeUtils.INPUT_TIME_FORMAT, DateTimeUtils.TIME_FORMAT_HH_MM);
            final String requestCheckOutByDate =
                    DateTimeUtils.convertUiFormatToDataFormat(mRequest.getCheckOutTime(),
                            DateTimeUtils.INPUT_TIME_FORMAT,
                            mContext.getString(R.string.format_date_mm_dd_yyyy));
            switch (mRequest.getLeaveType().getId()) {
                case LeaveType.IN_LATE_A:
                case LeaveType.IN_LATE_WOMAN_A:
                    return beginAfterTime + Constant.BLANK_DASH_BLANK + requestCheckInByTimeAndDate;
                case LeaveType.IN_LATE_M:
                case LeaveType.IN_LATE_WOMAN_M:
                    return beginMorningTime
                            + Constant.BLANK_DASH_BLANK
                            + requestCheckInByTimeAndDate;
                case LeaveType.LEAVE_EARLY_WOMAN_M:
                case LeaveType.LEAVE_EARLY_M:
                    return requestCheckOutByTime
                            + Constant.BLANK_DASH_BLANK
                            + timeLunch
                            + Constant.BLANK
                            + requestCheckOutByDate;
                case LeaveType.LEAVE_OUT:
                case LeaveType.FORGOT_CARD_ALL_DAY:
                case LeaveType.FORGOT_CHECK_ALL_DAY:
                    return requestCheckInByTime
                            + Constant.BLANK_DASH_BLANK
                            + requestCheckOutByTimeAndDate;
                case LeaveType.FORGOT_CARD_IN:
                case LeaveType.FORGOT_TO_CHECK_IN:
                    return requestCheckInByTimeAndDate;
                case LeaveType.FORGOT_CARD_OUT:
                case LeaveType.FORGOT_TO_CHECK_OUT:
                    return requestCheckOutByTimeAndDate;
                case LeaveType.LEAVE_EARLY_WOMAN_A:
                case LeaveType.LEAVE_EARLY_A:
                    return requestCheckOutByTime
                            + Constant.BLANK_DASH_BLANK
                            + timeOut
                            + Constant.BLANK
                            + requestCheckOutByDate;
                default:
                    return "";
            }
        }

        return "";
    }

    public String getCreateAt() {
        if (mRequest != null) {
            return DateTimeUtils.convertUiFormatToDataFormat(mRequest.getCreateAt(),
                    DateTimeUtils.INPUT_TIME_FORMAT,
                    DateTimeUtils.DATE_TIME_FORMAT_HH_MM_DD_MM_YYYY);
        }
        if (mRequestOverTime != null) {
            return DateTimeUtils.convertUiFormatToDataFormat(mRequestOverTime.getCreatedAt(),
                    DateTimeUtils.INPUT_TIME_FORMAT,
                    DateTimeUtils.DATE_TIME_FORMAT_HH_MM_DD_MM_YYYY);
        }
        if (mRequestOff != null) {
            return DateTimeUtils.convertUiFormatToDataFormat(mRequestOff.getCreatedAt(),
                    DateTimeUtils.INPUT_TIME_FORMAT, DateTimeUtils.FORMAT_DATE);
        }
        return "";
    }

    public int getLeaveTypeId() {
        if (mRequest != null && mRequest.getLeaveType() != null) {
            return mRequest.getLeaveType().getId();
        }
        return 0;
    }

    public String getLeaveTypeName() {
        if (mRequest != null && mRequest.getLeaveType() != null) {
            return mRequest.getLeaveType().getName();
        }
        return "";
    }

    private boolean timeHaveSalaryLessThanTimeNoSalary(String timeHaveSalary, String timeNoSalary) {
        return DateTimeUtils.convertStringToDate(timeHaveSalary, DateTimeUtils.FORMAT_DATE)
                .before(DateTimeUtils.convertStringToDate(timeNoSalary, DateTimeUtils.FORMAT_DATE));
    }

    public String getFromTime() {
        if (mRequest != null && mRequest.getCompensation() != null) {
            return mRequest.getCompensation().getFromTime();
        }
        if (mRequestOverTime != null) {
            return DateTimeUtils.convertUiFormatToDataFormat(mRequestOverTime.getFromTime(),
                    DateTimeUtils.INPUT_TIME_FORMAT,
                    DateTimeUtils.DATE_TIME_FORMAT_HH_MM_DD_MM_YYYY);
        }
        if (mRequestOff != null) {

            String timeStartDateHaveSalary = mRequestOff.getStartDayHaveSalary().getOffPaidFrom();
            String sessionStartDateHaveSalary =
                    Constant.BLANK + mRequestOff.getStartDayHaveSalary().getPaidFromPeriod();

            String timeStartDateNoSalary = mRequestOff.getStartDayNoSalary().getOffFrom();
            String sessionStartDateNoSalary =
                    Constant.BLANK + mRequestOff.getStartDayNoSalary().getOffFromPeriod();

            if (StringUtils.isNotBlank(timeStartDateHaveSalary) && StringUtils.isNotBlank(
                    timeStartDateNoSalary)) {
                if (timeHaveSalaryLessThanTimeNoSalary(timeStartDateHaveSalary,
                        timeStartDateNoSalary)) {
                    return timeStartDateHaveSalary + sessionStartDateHaveSalary;
                }
                return timeStartDateNoSalary + sessionStartDateNoSalary;
            }

            if (StringUtils.isNotBlank(timeStartDateHaveSalary)) {
                return timeStartDateHaveSalary + sessionStartDateHaveSalary;
            }
            if (StringUtils.isNotBlank(timeStartDateNoSalary)) {
                return timeStartDateNoSalary + sessionStartDateNoSalary;
            }
        }
        return "";
    }

    public String getToTime() {
        if (mRequest != null) {
            mRequest.getCompensation().getToTime();
        }
        if (mRequestOverTime != null) {
            return DateTimeUtils.convertUiFormatToDataFormat(mRequestOverTime.getToTime(),
                    DateTimeUtils.INPUT_TIME_FORMAT,
                    DateTimeUtils.DATE_TIME_FORMAT_HH_MM_DD_MM_YYYY);
        }
        if (mRequestOff != null) {

            String timeEndDateHaveSalary = mRequestOff.getEndDayHaveSalary().getOffPaidTo();
            String sessionEndDateHaveSalary =
                    Constant.BLANK + mRequestOff.getEndDayHaveSalary().getPaidToPeriod();
            String timeEndDateNoSalary = mRequestOff.getEndDayNoSalary().getOffTo();

            String sessionEndDateNoSalary =
                    Constant.BLANK + mRequestOff.getEndDayNoSalary().getOffToPeriod();

            if (StringUtils.isNotBlank(timeEndDateHaveSalary) && StringUtils.isNotBlank(
                    timeEndDateNoSalary)) {
                if (timeHaveSalaryLessThanTimeNoSalary(timeEndDateHaveSalary,
                        timeEndDateNoSalary)) {
                    return timeEndDateNoSalary + sessionEndDateHaveSalary;
                }
                return timeEndDateHaveSalary + sessionEndDateNoSalary;
            }

            if (StringUtils.isNotBlank(timeEndDateHaveSalary)) {
                return timeEndDateHaveSalary + sessionEndDateHaveSalary;
            }
            if (StringUtils.isNotBlank(timeEndDateNoSalary)) {
                return timeEndDateNoSalary + sessionEndDateNoSalary;
            }
        }
        return "";
    }

    public boolean isAcceptStatus() {
        if (mRequest != null) {
            return StatusCode.ACCEPT_CODE.equals(mRequest.getStatus());
        } else if (mRequestOff != null) {
            return StatusCode.ACCEPT_CODE.equals(mRequestOff.getStatus());
        } else {
            return StatusCode.ACCEPT_CODE.equals(mRequestOverTime.getStatus());
        }
    }

    public boolean isPendingStatus() {
        if (mRequest != null) {
            return StatusCode.PENDING_CODE.equals(mRequest.getStatus());
        } else if (mRequestOff != null) {
            return StatusCode.PENDING_CODE.equals(mRequestOff.getStatus());
        } else {
            return StatusCode.PENDING_CODE.equals(mRequestOverTime.getStatus());
        }
    }

    public boolean isRejectStatus() {
        if (mRequest != null) {
            return StatusCode.REJECT_CODE.equals(mRequest.getStatus());
        } else if (mRequestOff != null) {
            return StatusCode.REJECT_CODE.equals(mRequestOff.getStatus());
        } else {
            return StatusCode.REJECT_CODE.equals(mRequestOverTime.getStatus());
        }
    }

    public void onItemClicked() {
        if (mItemClickListener == null) {
            return;
        }
        mItemClickListener.onItemRecyclerViewClick(mObject);
    }

    private void setStatus(String status) {
        switch (status) {
            case StatusCode.ACCEPT_CODE:
                setStatusImage(R.drawable.ic_status_accpect);
                break;
            case StatusCode.PENDING_CODE:
                setStatusImage(R.drawable.ic_status_pending);
                break;
            case StatusCode.REJECT_CODE:
                setStatusImage(R.drawable.ic_status_reject);
                break;
            case StatusCode.FORWARD_CODE:
                setStatusImage(R.drawable.ic_status_forward);
                break;
            case StatusCode.CANCELED_CODE:
                setStatusImage(R.drawable.ic_status_cancel);
                break;
            default:
                break;
        }
    }

    private void initValueStatus() {
        if (mRequest != null) {
            setStatus(mRequest.getStatus());
        }
        if (mRequestOff != null) {
            setStatus(mRequestOff.getStatus());
        }
        if (mRequestOverTime != null) {
            setStatus(mRequestOverTime.getStatus());
        }
    }

    @Bindable
    public int getStatusImage() {
        return mStatusImage;
    }

    private void setStatusImage(int statusImage) {
        mStatusImage = statusImage;
        notifyPropertyChanged(BR.statusImage);
    }

    @IntDef({
            LeaveType.IN_LATE_M, LeaveType.IN_LATE_A, LeaveType.LEAVE_EARLY_M,
            LeaveType.LEAVE_EARLY_A, LeaveType.LEAVE_OUT, LeaveType.FORGOT_CHECK_ALL_DAY,
            LeaveType.FORGOT_TO_CHECK_IN, LeaveType.FORGOT_TO_CHECK_OUT,
            LeaveType.FORGOT_CARD_ALL_DAY, LeaveType.FORGOT_CARD_IN, LeaveType.FORGOT_CARD_OUT,
            LeaveType.IN_LATE_WOMAN_M, LeaveType.IN_LATE_WOMAN_A, LeaveType.LEAVE_EARLY_WOMAN_M,
            LeaveType.LEAVE_EARLY_WOMAN_A
    })
    public @interface LeaveType {
        int IN_LATE_M = 1;
        int LEAVE_EARLY_M = 2;
        int LEAVE_OUT = 3;
        int IN_LATE_A = 4;
        int IN_LATE_WOMAN_M = 5;
        int FORGOT_CHECK_ALL_DAY = 7;
        int FORGOT_TO_CHECK_IN = 12;
        int FORGOT_TO_CHECK_OUT = 13;
        int LEAVE_EARLY_A = 14;
        int IN_LATE_WOMAN_A = 15;
        int LEAVE_EARLY_WOMAN_M = 16;
        int LEAVE_EARLY_WOMAN_A = 17;
        int FORGOT_CARD_IN = 19;
        int FORGOT_CARD_OUT = 20;
        int FORGOT_CARD_ALL_DAY = 21;
    }
}
