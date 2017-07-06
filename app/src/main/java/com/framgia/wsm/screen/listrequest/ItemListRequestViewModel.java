package com.framgia.wsm.screen.listrequest;

import android.databinding.BaseObservable;
import com.framgia.wsm.data.model.LeaveRequest;
import com.framgia.wsm.data.model.OffRequest;
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.screen.BaseRecyclerViewAdapter;
import com.framgia.wsm.utils.StatusCode;

/**
 * Created by ASUS on 13/06/2017.
 */

public class ItemListRequestViewModel extends BaseObservable {
    private Object mObject;
    private LeaveRequest mRequest;
    private OffRequest mRequestOff;
    private RequestOverTime mRequestOverTime;
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
    }

    //TODO Edit later
    public String getTimeRequest() {
        //        if (mRequest != null) {
        //            return mRequest.getTimeRequest();
        //        }
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

    //TODO Edit later
    public String getCreateAt() {
        //        if (mRequest != null) {
        //            return mRequest.getCreatedAt();
        //        }
        if (mRequestOverTime != null) {
            return mRequestOverTime.getCreatedAt();
        }
        if (mRequestOff != null) {
            return mRequestOff.getCreatedAt();
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
        if (mRequest != null) {
            return mRequest.getCompensation().getFromTime();
        }
        if (mRequestOverTime != null) {
            return mRequestOverTime.getFromTime();
        }
        if (mRequestOff != null && mRequestOff.getStartDayHaveSalary() != null) {
            return mRequestOff.getStartDayHaveSalary().getOffPaidFrom();
        }
        return "";
    }

    public String getToTime() {
        if (mRequest != null) {
            return mRequest.getCompensation().getToTime();
        }
        if (mRequestOff != null && mRequestOff.getEndDayHaveSalary() != null) {
            return mRequestOff.getEndDayHaveSalary().getOffPaidTo();
        }
        if (mRequestOverTime != null) {
            return mRequestOverTime.getToTime();
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
}
