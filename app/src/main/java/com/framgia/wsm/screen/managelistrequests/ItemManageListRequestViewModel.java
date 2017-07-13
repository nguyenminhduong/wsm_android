package com.framgia.wsm.screen.managelistrequests;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
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
    private int mImageRequestType;
    private int mItemPosition;
    private String mStatusRequest;

    ItemManageListRequestViewModel(Object object,
            BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener<Object> itemClickListener,
            ActionRequestListener actionRequestListener, int itemPosition) {
        mObject = object;
        mItemClickListener = itemClickListener;
        mActionRequestListener = actionRequestListener;
        mItemPosition = itemPosition;
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
            mActionRequestListener.onApproveRequest(mItemPosition, mLeaveRequest.getId());
        }
        if (mRequestOverTime != null) {
            mActionRequestListener.onApproveRequest(mItemPosition, mRequestOverTime.getId());
        }
        if (mRequestOff != null) {
            mActionRequestListener.onApproveRequest(mItemPosition, mRequestOff.getId());
        }
    }

    public void onRejectRequest() {
        if (mActionRequestListener == null) {
            return;
        }
        if (mLeaveRequest != null) {
            mActionRequestListener.onRejectRequest(mItemPosition, mLeaveRequest.getId());
        }
        if (mRequestOverTime != null) {
            mActionRequestListener.onRejectRequest(mItemPosition, mRequestOverTime.getId());
        }
        if (mRequestOff != null) {
            mActionRequestListener.onRejectRequest(mItemPosition, mRequestOff.getId());
        }
    }

    public void onItemClicked() {
        if (mItemClickListener == null) {
            return;
        }
        mItemClickListener.onItemRecyclerViewClick(mObject);
    }

    public boolean isVisibleStatusAccept() {
        return StatusCode.ACCEPT_CODE.endsWith(mStatusRequest);
    }

    public boolean isVisibleStatusReject() {
        return StatusCode.REJECT_CODE.endsWith(mStatusRequest);
    }

    public boolean isVisibleStatusPending() {
        return StatusCode.PENDING_CODE.endsWith(mStatusRequest);
    }

    public boolean isVisibleStatusFoward() {
        return StatusCode.FORWARD_CODE.endsWith(mStatusRequest);
    }

    private void initValueStatus() {
        if (mLeaveRequest != null) {
            mStatusRequest = mLeaveRequest.getStatus();
            return;
        }
        if (mRequestOverTime != null) {
            mStatusRequest = mRequestOverTime.getStatus();
            return;
        }
        if (mRequestOff != null) {
            mStatusRequest = mRequestOff.getStatus();
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
