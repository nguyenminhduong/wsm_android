package com.framgia.wsm.screen.managelistrequests;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Color;
import com.framgia.wsm.BR;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.LeaveRequest;
import com.framgia.wsm.data.model.OffRequest;
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.screen.BaseRecyclerViewAdapter;
import com.framgia.wsm.utils.StatusCode;
import com.framgia.wsm.utils.common.DateTimeUtils;

/**
 * Created by tri on 10/07/2017.
 */

public class ItemManageListRequestViewModel extends BaseObservable {

    private Object mObject;
    private LeaveRequest mLeaveRequest;
    private OffRequest mRequestOff;
    private RequestOverTime mRequestOverTime;
    private final BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener<Object>
            mItemClickListener;
    private ActionRequestListener mActionRequestListener;
    private int mStatusColor;
    private int mImageRequestType;

    public ItemManageListRequestViewModel(Object object,
            BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener<Object> itemClickListener,
            ActionRequestListener actionRequestListener) {
        mObject = object;
        mItemClickListener = itemClickListener;
        mActionRequestListener = actionRequestListener;
        if (object instanceof LeaveRequest) {
            mLeaveRequest = (LeaveRequest) object;
        } else if (object instanceof OffRequest) {
            mRequestOff = (OffRequest) object;
        } else {
            mRequestOverTime = (RequestOverTime) object;
        }
        initValueStatus();
        initImageRequestType();
    }

    public void onApproveRequest() {
        if (mActionRequestListener == null) {
            return;
        }
        if (mLeaveRequest != null) {
            mActionRequestListener.onApproveRequest(mLeaveRequest.getId());
        }
        if (mRequestOverTime != null) {
            mActionRequestListener.onApproveRequest(mRequestOverTime.getId());
        }
        if (mRequestOff != null) {
            mActionRequestListener.onApproveRequest(mRequestOff.getId());
        }
    }

    public void onRejectRequest() {
        if (mActionRequestListener == null) {
            return;
        }
        if (mLeaveRequest != null) {
            mActionRequestListener.onRejectRequest(mLeaveRequest.getId());
        }
        if (mRequestOverTime != null) {
            mActionRequestListener.onRejectRequest(mRequestOverTime.getId());
        }
        if (mRequestOff != null) {
            mActionRequestListener.onRejectRequest(mRequestOff.getId());
        }
    }

    public void onItemClicked() {
        if (mItemClickListener == null) {
            return;
        }
        mItemClickListener.onItemRecyclerViewClick(mObject);
    }

    @Bindable
    public int getStatusColor() {
        return mStatusColor;
    }

    private void setStatusColor(int statusColor) {
        mStatusColor = statusColor;
        notifyPropertyChanged(BR.statusColor);
    }

    private void setStatus(String status) {
        switch (status) {
            case StatusCode.ACCEPT_CODE:
                setStatusColor(Color.GREEN);
                break;
            case StatusCode.PENDING_CODE:
                setStatusColor(Color.BLUE);
                break;
            case StatusCode.REJECT_CODE:
                setStatusColor(Color.RED);
                break;
            case StatusCode.FORWARD_CODE:
                setStatusColor(Color.MAGENTA);
                break;
            default:
                break;
        }
    }

    private void initValueStatus() {
        if (mLeaveRequest != null) {
            setStatus(mLeaveRequest.getStatus());
            return;
        }
        if (mRequestOverTime != null) {
            setStatus(mRequestOverTime.getStatus());
            return;
        }
        if (mRequestOff != null) {
            setStatus(mRequestOff.getStatus());
        }
    }

    private String getFormatTime(String input) {
        return DateTimeUtils.convertUiFormatToDataFormat(input, DateTimeUtils.INPUT_TIME_FORMAT,
                DateTimeUtils.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM);
    }

    public String getDateOfCreation() {
        if (mLeaveRequest != null) {
            return getFormatTime(mLeaveRequest.getCreateAt());
        }
        if (mRequestOverTime != null) {
            return getFormatTime(mRequestOverTime.getCreatedAt());
        }
        if (mRequestOff != null) {
            return getFormatTime(mRequestOff.getCreatedAt());
        }
        return "";
    }

    public String getLeaveTypeName() {
        if (mLeaveRequest != null && mLeaveRequest.getLeaveType() != null) {
            return mLeaveRequest.getLeaveType().getName();
        }
        return "";
    }

    public boolean isVisibleLeaveTypeName() {
        return mLeaveRequest != null && mLeaveRequest.getLeaveType() != null;
    }

    @Bindable
    public int getImageRequestType() {
        return mImageRequestType;
    }

    private void setImageRequestType(int imageRequestType) {
        mImageRequestType = imageRequestType;
        notifyPropertyChanged(BR.imageRequestType);
    }

    private void initImageRequestType() {
        if (mLeaveRequest != null) {
            setImageRequestType(R.drawable.ic_clock_a);
            return;
        }
        if (mRequestOverTime != null) {
            setImageRequestType(R.drawable.ic_over_time);
            return;
        }
        if (mRequestOff != null) {
            setImageRequestType(R.drawable.ic_day_off);
        }
    }

    public String getStatus() {
        if (mLeaveRequest != null) {
            return mLeaveRequest.getStatus();
        }
        if (mRequestOverTime != null) {
            return mRequestOverTime.getStatus();
        }
        if (mRequestOff != null) {
            return mRequestOff.getStatus();
        }
        return "";
    }

    public boolean isVisibleButtonApprove() {
        if (mLeaveRequest != null) {
            return StatusCode.REJECT_CODE.equals(mLeaveRequest.getStatus())
                    || StatusCode.PENDING_CODE.equals(mLeaveRequest.getStatus());
        }
        if (mRequestOverTime != null) {
            return StatusCode.REJECT_CODE.equals(mRequestOverTime.getStatus())
                    || StatusCode.PENDING_CODE.equals(mRequestOverTime.getStatus());
        }
        return StatusCode.REJECT_CODE.equals(mRequestOff.getStatus())
                || StatusCode.PENDING_CODE.equals(mRequestOff.getStatus());
    }

    public boolean isVisibleButtonReject() {
        if (mLeaveRequest != null) {
            return StatusCode.ACCEPT_CODE.equals(mLeaveRequest.getStatus())
                    || StatusCode.PENDING_CODE.equals(mLeaveRequest.getStatus());
        }
        if (mRequestOverTime != null) {
            return StatusCode.ACCEPT_CODE.equals(mRequestOverTime.getStatus())
                    || StatusCode.PENDING_CODE.equals(mRequestOverTime.getStatus());
        }
        return StatusCode.ACCEPT_CODE.equals(mRequestOff.getStatus())
                || StatusCode.PENDING_CODE.equals(mRequestOff.getStatus());
    }
}
