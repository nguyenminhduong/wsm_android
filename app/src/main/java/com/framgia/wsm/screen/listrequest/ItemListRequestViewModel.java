package com.framgia.wsm.screen.listrequest;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.IntDef;
import com.framgia.wsm.BR;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.LeaveRequest;
import com.framgia.wsm.data.model.OffRequest;
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.screen.BaseRecyclerViewAdapter;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.StatusCode;
import com.framgia.wsm.utils.common.DateTimeUtils;

/**
 * Created by ASUS on 13/06/2017.
 */

public class ItemListRequestViewModel extends BaseObservable {

    private final Object mObject;
    private LeaveRequest mRequest;
    private OffRequest mRequestOff;
    private RequestOverTime mRequestOverTime;
    private int mStatusImage;
    private final BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener<Object>
            mItemClickListener;

    public ItemListRequestViewModel(Object object,
            BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener<Object> itemClickListener) {
        mObject = object;
        mItemClickListener = itemClickListener;
        if (object instanceof LeaveRequest) {
            mRequest = (LeaveRequest) object;
        } else if (object instanceof OffRequest) {
            mRequestOff = (OffRequest) object;
        } else {
            mRequestOverTime = (RequestOverTime) object;
        }
        initValueStatus();
    }

    public String getTimeRequestLeave() {
        if (mRequest != null) {
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
                            DateTimeUtils.INPUT_TIME_FORMAT, DateTimeUtils.FORMAT_DATE);
            switch (mRequest.getLeaveType().getId()) {
                case LeaveType.IN_LATE_M:
                case LeaveType.IN_LATE_A:
                    return Constant.BEGIN_MORNING_TIME
                            + Constant.BLANK_DASH_BLANK
                            + requestCheckInByTimeAndDate;
                case LeaveType.LEAVE_EARLY_M:
                    return requestCheckOutByTime
                            + Constant.DATE_TIME_SPACE
                            + Constant.END_MORNING_TIME
                            + Constant.BLANK
                            + requestCheckOutByDate;
                case LeaveType.LEAVE_OUT:
                case LeaveType.FORGOT_CARD_ALL_DAY:
                case LeaveType.FORGOT_CHECK_ALL_DAY:
                    return requestCheckInByTime
                            + Constant.DATE_TIME_SPACE
                            + Constant.BLANK
                            + requestCheckOutByTimeAndDate;
                case LeaveType.FORGOT_CARD_IN:
                case LeaveType.IN_LATE_WOMAN_M:
                case LeaveType.IN_LATE_WOMAN_A:
                case LeaveType.FORGOT_TO_CHECK_IN:
                    return requestCheckInByTimeAndDate;
                case LeaveType.FORGOT_CARD_OUT:
                case LeaveType.LEAVE_EARLY_A:
                case LeaveType.LEAVE_EARLY_WOMAN_M:
                case LeaveType.LEAVE_EARLY_WOMAN_A:
                case LeaveType.FORGOT_TO_CHECK_OUT:
                    return requestCheckOutByTimeAndDate;
                default:
                    return "";
            }
        }
        return "";
    }

    //TODO Edit later
    public String getBeingHandledBy() {
        //        if (mRequest != null) {
        //            return mRequest.getBeingHandledBy();
        //        }
        if (mRequestOverTime != null) {
            return mRequestOverTime.getBeingHandledBy();
        }
        if (mRequestOff != null) {
            return mRequestOff.getBeingHandledBy();
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

    public String getLeaveTypeCode() {
        if (mRequest != null && mRequest.getLeaveType() != null) {
            return mRequest.getLeaveType().getCode();
        }
        return "";
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
            if (mRequestOff.getStartDayHaveSalary() != null) {
                if (!"".equals(mRequestOff.getStartDayHaveSalary().getOffPaidFrom())) {
                    return mRequestOff.getStartDayHaveSalary().getOffPaidFrom()
                            + Constant.BLANK
                            + mRequestOff.getStartDayHaveSalary().getPaidFromPeriod();
                }
            }
            if (mRequestOff.getStartDayNoSalary() != null) {
                if (!"".equals(mRequestOff.getStartDayNoSalary().getOffFrom())) {
                    return mRequestOff.getStartDayNoSalary().getOffFrom()
                            + Constant.BLANK
                            + mRequestOff.getStartDayNoSalary().getOffFromPeriod();
                }
            }
        }
        return "";
    }

    public String getToTime() {
        if (mRequest != null) {
            mRequest.getCompensation().getToTime();
        }
        if (mRequestOff != null) {
            if (mRequestOff.getEndDayHaveSalary() != null) {
                if (!"".equals(mRequestOff.getEndDayHaveSalary().getOffPaidTo())) {
                    return mRequestOff.getEndDayHaveSalary().getOffPaidTo()
                            + Constant.BLANK
                            + mRequestOff.getEndDayHaveSalary().getPaidToPeriod();
                }
            }
            if (mRequestOff.getEndDayNoSalary() != null) {
                if (!"".equals(mRequestOff.getEndDayNoSalary().getOffTo())) {
                    return mRequestOff.getEndDayNoSalary().getOffTo() + Constant.BLANK + mRequestOff
                            .getEndDayNoSalary()
                            .getOffToPeriod();
                }
            }
        }
        if (mRequestOverTime != null) {
            return DateTimeUtils.convertUiFormatToDataFormat(mRequestOverTime.getToTime(),
                    DateTimeUtils.INPUT_TIME_FORMAT,
                    DateTimeUtils.DATE_TIME_FORMAT_HH_MM_DD_MM_YYYY);
        }
        return "";
    }

    public String getCheckinTime() {
        if (mRequest != null) {
            return mRequest.getCheckInTime();
        }
        return "";
    }

    public String getCheckoutTime() {
        if (mRequest != null) {
            return mRequest.getCheckOutTime();
        }
        return "";
    }

    public String getHandler() {
        //TODO Edit Later
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
}
