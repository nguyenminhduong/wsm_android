package com.framgia.wsm.screen.managelistrequests;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.framgia.wsm.BR;
import com.framgia.wsm.R;
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

    private Context mContext;
    private final Object mObject;
    private LeaveRequest mLeaveRequest;
    private OffRequest mRequestOff;
    private RequestOverTime mRequestOverTime;
    private final ItemRecyclerViewClickListener mItemClickListener;
    private final ActionRequestListener mActionRequestListener;
    private final int mItemPosition;
    private int mStatusColor;
    private int mColorImage;
    private boolean mIsChecked;

    ItemManageListRequestViewModel(Context context, Object object,
            ItemRecyclerViewClickListener itemClickListener,
            ActionRequestListener actionRequestListener, int itemPosition) {
        mContext = context.getApplicationContext();
        mObject = object;
        mItemClickListener = itemClickListener;
        mActionRequestListener = actionRequestListener;
        mItemPosition = itemPosition;
        if (object instanceof LeaveRequest) {
            mLeaveRequest = (LeaveRequest) object;
            setChecked(mLeaveRequest.isChecked());
        } else if (object instanceof OffRequest) {
            mRequestOff = (OffRequest) object;
            setChecked(mRequestOff.isChecked());
        } else {
            mRequestOverTime = (RequestOverTime) object;
            setChecked(mRequestOverTime.isChecked());
        }
        initStatus();
        checkStatusForward();
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
                mContext.getString(R.string.format_date_mm_dd_yyyy));
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
            case StatusCode.CANCELED_CODE:
                setColorImage(ColorManagers.getColorCancel());
                setStatusColor(ColorManagers.getColorCancel());
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

    @Bindable
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

    public boolean isCancelStatus() {
        if (mLeaveRequest != null) {
            return StatusCode.CANCELED_CODE.equals(mLeaveRequest.getStatus());
        }
        if (mRequestOverTime != null) {
            return StatusCode.CANCELED_CODE.equals(mRequestOverTime.getStatus());
        }
        return StatusCode.CANCELED_CODE.equals(mRequestOff.getStatus());
    }

    private void checkStatusForward() {
        if (mLeaveRequest != null
                && StatusCode.FORWARD_CODE.equals(mLeaveRequest.getStatus())
                && mLeaveRequest.isCanApproveReject()) {
            setStatus(StatusCode.PENDING_CODE);
            mLeaveRequest.setStatus(StatusCode.PENDING_CODE);
            notifyPropertyChanged(BR.pendingStatus);
            return;
        }
        if (mRequestOverTime != null
                && StatusCode.FORWARD_CODE.equals(mRequestOverTime.getStatus())
                && mRequestOverTime.isCanApproveReject()) {
            setStatus(StatusCode.PENDING_CODE);
            mRequestOverTime.setStatus(StatusCode.PENDING_CODE);
            notifyPropertyChanged(BR.pendingStatus);
            return;
        }
        if (mRequestOff != null
                && StatusCode.FORWARD_CODE.equals(mRequestOff.getStatus())
                && mRequestOff.isCanApproveReject()) {
            setStatus(StatusCode.PENDING_CODE);
            mRequestOff.setStatus(StatusCode.PENDING_CODE);
            notifyPropertyChanged(BR.pendingStatus);
        }
    }

    public boolean isVisibleCheckbox() {
        if (mLeaveRequest != null) {
            return mLeaveRequest.isCanApproveReject() && StatusCode.REJECT_CODE.equals(
                    mLeaveRequest.getStatus()) || StatusCode.PENDING_CODE.equals(
                    mLeaveRequest.getStatus());
        }
        if (mRequestOverTime != null) {
            return mRequestOverTime.isCanApproveReject() && StatusCode.REJECT_CODE.equals(
                    mRequestOverTime.getStatus()) || StatusCode.PENDING_CODE.equals(
                    mRequestOverTime.getStatus());
        }
        return mRequestOff.isCanApproveReject() && StatusCode.REJECT_CODE.equals(
                mRequestOff.getStatus()) || StatusCode.PENDING_CODE.equals(mRequestOff.getStatus());
    }

    @Bindable
    public boolean isChecked() {
        return mIsChecked;
    }

    public void setChecked(boolean checked) {
        mIsChecked = checked;
        notifyPropertyChanged(BR.checked);
    }

    public void onCheckedItem() {
        if (mActionRequestListener == null) {
            return;
        }
        mActionRequestListener.onCheckedItem(mItemPosition, mIsChecked);
    }
}
