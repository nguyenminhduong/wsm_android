package com.framgia.wsm.screen.managelistrequests;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.framgia.wsm.BR;
import com.framgia.wsm.data.model.LeaveRequest;
import com.framgia.wsm.data.model.OffRequest;
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.utils.StatusCode;
import com.framgia.wsm.utils.binding.ColorManagers;
import com.framgia.wsm.utils.common.DateTimeUtils;

/**
 * Created by tri on 10/07/2017.
 */

public class ItemManageListRequestViewModel extends BaseObservable {

    private final Object mObject;
    private LeaveRequest mLeaveRequest;
    private OffRequest mRequestOff;
    private RequestOverTime mRequestOverTime;
    private final ItemRecyclerViewClickListener mItemClickListener;
    private final ActionRequestListener mActionRequestListener;
    private final int mItemPosition;
    private int mStatusColor;
    private int mColorImage;

    ItemManageListRequestViewModel(Object object, ItemRecyclerViewClickListener itemClickListener,
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
        initStatus();
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
        mItemClickListener.onItemRecyclerViewClick(mObject, mItemPosition);
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

    private void setStatus(String status) {
        switch (status) {
            case StatusCode.ACCEPT_CODE:
                setColorImage(ColorManagers.getColorStatusAccept());
                setStatusColor(ColorManagers.getColorStatusAccept());
                break;
            case StatusCode.PENDING_CODE:
                setColorImage(ColorManagers.getColorStatusPending());
                setStatusColor(ColorManagers.getColorStatusPending());
                break;
            case StatusCode.FORWARD_CODE:
                setColorImage(ColorManagers.getColorStatusForward());
                setStatusColor(ColorManagers.getColorStatusForward());
                break;
            case StatusCode.REJECT_CODE:
                setColorImage(ColorManagers.getColorStatusReject());
                setStatusColor(ColorManagers.getColorStatusReject());
                break;
            default:
                break;
        }
    }

    private void initStatus() {
        if (mLeaveRequest != null) {
            setStatus(mLeaveRequest.getStatus());
        }
        if (mRequestOff != null) {
            setStatus(mRequestOff.getStatus());
        }
        if (mRequestOverTime != null) {
            setStatus(mRequestOverTime.getStatus());
        }
    }

    public boolean isVisibleButtonApprove() {
        if (mLeaveRequest != null) {
            return isCheckVisibleButtonApprove(mLeaveRequest.getStatus(),
                    mLeaveRequest.isCanApproveReject());
        }
        if (mRequestOverTime != null) {
            return isCheckVisibleButtonApprove(mRequestOverTime.getStatus(),
                    mRequestOverTime.isCanApproveReject());
        }
        return isCheckVisibleButtonApprove(mRequestOff.getStatus(),
                mRequestOff.isCanApproveReject());
    }

    public boolean isVisibleButtonReject() {
        if (mLeaveRequest != null) {
            return isCheckVisibleButtonReject(mLeaveRequest.getStatus(),
                    mLeaveRequest.isCanApproveReject());
        }
        if (mRequestOverTime != null) {
            return isCheckVisibleButtonReject(mRequestOverTime.getStatus(),
                    mRequestOverTime.isCanApproveReject());
        }
        return isCheckVisibleButtonReject(mRequestOff.getStatus(),
                mRequestOff.isCanApproveReject());
    }

    private boolean isCheckVisibleButtonApprove(String status, boolean isCanApproveReject) {
        return isCanApproveReject && (StatusCode.REJECT_CODE.equals(status)
                || StatusCode.PENDING_CODE.equals(status));
    }

    private boolean isCheckVisibleButtonReject(String status, boolean isCanApproveReject) {
        return isCanApproveReject && (StatusCode.ACCEPT_CODE.equals(status)
                || StatusCode.PENDING_CODE.equals(status));
    }

    @Bindable
    public int getColorImage() {
        return mColorImage;
    }

    private void setColorImage(int colorImage) {
        mColorImage = colorImage;
        notifyPropertyChanged(BR.colorImage);
    }

    @Bindable
    public int getStatusColor() {
        return mStatusColor;
    }

    private void setStatusColor(int statusColor) {
        mStatusColor = statusColor;
        notifyPropertyChanged(BR.statusColor);
    }

    public String getEmployeeName() {
        if (mLeaveRequest != null) {
            return mLeaveRequest.getUser().getName();
        }
        if (mRequestOverTime != null) {
            return mRequestOverTime.getUser().getName();
        }
        if (mRequestOff != null) {
            return mRequestOff.getUser().getName();
        }
        return "";
    }

    public boolean isAcceptStatus() {
        if (mLeaveRequest != null) {
            return StatusCode.ACCEPT_CODE.equals(mLeaveRequest.getStatus());
        }
        if (mRequestOverTime != null) {
            return StatusCode.ACCEPT_CODE.equals(mRequestOverTime.getStatus());
        }
        return StatusCode.ACCEPT_CODE.equals(mRequestOff.getStatus());
    }

    public boolean isPendingStatus() {
        if (mLeaveRequest != null) {
            return StatusCode.PENDING_CODE.equals(mLeaveRequest.getStatus());
        }
        if (mRequestOverTime != null) {
            return StatusCode.PENDING_CODE.equals(mRequestOverTime.getStatus());
        }
        return StatusCode.PENDING_CODE.equals(mRequestOff.getStatus());
    }

    public boolean isRejectStatus() {
        if (mLeaveRequest != null) {
            return StatusCode.REJECT_CODE.equals(mLeaveRequest.getStatus());
        }
        if (mRequestOverTime != null) {
            return StatusCode.REJECT_CODE.equals(mRequestOverTime.getStatus());
        }
        return StatusCode.REJECT_CODE.equals(mRequestOff.getStatus());
    }

    public boolean isForwardStatus() {
        if (mLeaveRequest != null) {
            return StatusCode.FORWARD_CODE.equals(mLeaveRequest.getStatus());
        }
        if (mRequestOverTime != null) {
            return StatusCode.FORWARD_CODE.equals(mRequestOverTime.getStatus());
        }
        return StatusCode.FORWARD_CODE.equals(mRequestOff.getStatus());
    }
}
