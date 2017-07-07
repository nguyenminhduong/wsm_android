package com.framgia.wsm.screen.confirmrequestleave;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import com.android.databinding.library.baseAdapters.BR;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.LeaveRequest;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.request.RequestLeaveRequest;
import com.framgia.wsm.screen.confirmrequestoff.ConfirmRequestOffActivity;
import com.framgia.wsm.screen.requestleave.RequestLeaveActivity;
import com.framgia.wsm.screen.requestleave.RequestLeaveViewModel;
import com.framgia.wsm.utils.ActionType;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.StatusCode;
import com.framgia.wsm.utils.common.DateTimeUtils;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.widget.dialog.DialogManager;
import com.fstyle.library.DialogAction;
import com.fstyle.library.MaterialDialog;

/**
 * Exposes the data to be used in the ConfirmRequestLeave screen.
 */

public class ConfirmRequestLeaveViewModel extends BaseObservable
        implements ConfirmRequestLeaveContract.ViewModel {
    private static final String TAG = "ConfirmRequestLeaveViewModel";

    private ConfirmRequestLeaveContract.Presenter mPresenter;
    private LeaveRequest mRequest;
    private User mUser;
    private Navigator mNavigator;
    private int mLeaveTypeId;
    private DialogManager mDialogManager;
    private int mActionType;
    private Context mContext;
    private RequestLeaveRequest mRequestLeaveRequest;

    ConfirmRequestLeaveViewModel(Context context, ConfirmRequestLeaveContract.Presenter presenter,
            LeaveRequest request, Navigator navigator, DialogManager dialogManager,
            int actionType) {
        mContext = context;
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mNavigator = navigator;
        mDialogManager = dialogManager;
        mRequest = request;
        mPresenter.getUser();
        mActionType = actionType;
        mRequestLeaveRequest = new RequestLeaveRequest();
        if (mActionType == ActionType.ACTION_DETAIL) {
            mLeaveTypeId = mRequest.getLeaveType().getId();
        } else {
            mLeaveTypeId = mRequest.getLeaveTypeId();
        }
    }

    public boolean isVisibleLayoutCheckout() {
        return mLeaveTypeId == (RequestLeaveViewModel.LeaveType.LEAVE_EARLY_A)
                || mLeaveTypeId == (RequestLeaveViewModel.LeaveType.LEAVE_EARLY_M)
                || mLeaveTypeId == (RequestLeaveViewModel.LeaveType.LEAVE_EARLY_WOMAN_A)
                || mLeaveTypeId == (RequestLeaveViewModel.LeaveType.LEAVE_EARLY_WOMAN_M)
                || mLeaveTypeId == (RequestLeaveViewModel.LeaveType.FORGOT_CARD_OUT)
                || mLeaveTypeId == (RequestLeaveViewModel.LeaveType.FORGOT_TO_CHECK_OUT)
                || mLeaveTypeId == (RequestLeaveViewModel.LeaveType.FORGOT_CARD_ALL_DAY)
                || mLeaveTypeId == (RequestLeaveViewModel.LeaveType.FORGOT_CHECK_ALL_DAY)
                || mLeaveTypeId == (RequestLeaveViewModel.LeaveType.LEAVE_OUT);
    }

    public boolean isVisibleLayoutCheckin() {
        return mLeaveTypeId == (RequestLeaveViewModel.LeaveType.IN_LATE_A)
                || mLeaveTypeId == (RequestLeaveViewModel.LeaveType.IN_LATE_M)
                || mLeaveTypeId == (RequestLeaveViewModel.LeaveType.IN_LATE_WOMAN_A)
                || mLeaveTypeId == (RequestLeaveViewModel.LeaveType.IN_LATE_WOMAN_M)
                || mLeaveTypeId == (RequestLeaveViewModel.LeaveType.FORGOT_CARD_IN)
                || mLeaveTypeId == (RequestLeaveViewModel.LeaveType.FORGOT_TO_CHECK_IN)
                || mLeaveTypeId == (RequestLeaveViewModel.LeaveType.FORGOT_CARD_ALL_DAY)
                || mLeaveTypeId == (RequestLeaveViewModel.LeaveType.FORGOT_CHECK_ALL_DAY)
                || mLeaveTypeId == (RequestLeaveViewModel.LeaveType.LEAVE_OUT);
    }

    public boolean isVisibleLayoutCompensation() {
        return mLeaveTypeId == (RequestLeaveViewModel.LeaveType.IN_LATE_A)
                || mLeaveTypeId == (RequestLeaveViewModel.LeaveType.IN_LATE_M)
                || mLeaveTypeId == (RequestLeaveViewModel.LeaveType.LEAVE_EARLY_A)
                || mLeaveTypeId == (RequestLeaveViewModel.LeaveType.LEAVE_EARLY_M)
                || mLeaveTypeId == (RequestLeaveViewModel.LeaveType.LEAVE_OUT);
    }

    public boolean isDetail() {
        return mActionType == ActionType.ACTION_DETAIL;
    }

    public boolean isAcceptStatus() {
        return StatusCode.ACCEPT_CODE.equals(mRequest.getStatus());
    }

    public boolean isPendingStatus() {
        return StatusCode.PENDING_CODE.equals(mRequest.getStatus());
    }

    public boolean isRejectStatus() {
        return StatusCode.REJECT_CODE.equals(mRequest.getStatus());
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Override
    public void onCreateFormRequestLeaveSuccess() {
        mNavigator.finishActivityWithResult(Activity.RESULT_OK);
    }

    @Override
    public void onCreateFormFormRequestLeaveError(BaseException exception) {
        mDialogManager.dialogError(exception);
    }

    @Override
    public void onEditFormRequestLeaveSuccess() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.EXTRA_REQUEST_OFF, mRequest);
        bundle.putInt(Constant.EXTRA_ACTION_TYPE, ActionType.ACTION_DETAIL);
        mNavigator.startActivityAtRoot(ConfirmRequestOffActivity.class, bundle);
    }

    @Override
    public void onEditFormFormRequestLeaveError(BaseException exception) {
        mDialogManager.dialogError(exception);
    }

    @Override
    public void onDeleteFormRequestLeaveSuccess() {
        mNavigator.finishActivityWithResult(Activity.RESULT_OK);
    }

    @Override
    public void onDeleteFormFormRequestLeaveError(BaseException exception) {
        mDialogManager.dialogError(exception);
    }

    @Override
    public void onGetUserSuccess(User user) {
        if (user == null) {
            return;
        }
        mUser = user;
        notifyPropertyChanged(BR.user);
    }

    @Override
    public void onGetUserError(BaseException exception) {
        Log.e(TAG, "ConfirmRequestLeaveViewModel", exception);
    }

    public String getTitleToolbar() {
        if (mActionType == ActionType.ACTION_DETAIL) {
            return mContext.getString(R.string.request_leave);
        }
        return mContext.getString(R.string.confirm_request_leave);
    }

    public LeaveRequest getRequest() {
        return mRequest;
    }

    @Bindable
    public User getUser() {
        return mUser;
    }

    public boolean isVisibleButtonSubmit() {
        return StatusCode.PENDING_CODE.equals(mRequest.getStatus())
                || mActionType == ActionType.ACTION_CREATE;
    }

    public String getCheckInTime() {
        return DateTimeUtils.convertUiFormatToDataFormat(mRequest.getCheckInTime(),
                DateTimeUtils.INPUT_TIME_FORMAT, DateTimeUtils.DATE_TIME_FORMAT_HH_MM_DD_MM_YYYY);
    }

    public String getCheckOutTime() {
        return DateTimeUtils.convertUiFormatToDataFormat(mRequest.getCheckOutTime(),
                DateTimeUtils.INPUT_TIME_FORMAT, DateTimeUtils.DATE_TIME_FORMAT_HH_MM_DD_MM_YYYY);
    }

    public void onClickArrowBack(View view) {
        mNavigator.finishActivity();
    }

    public void onClickSubmit(View view) {
        if (mRequest == null) {
            return;
        }
        if (mActionType == ActionType.ACTION_CONFIRM_CREATE) {
            mRequestLeaveRequest.setLeaveRequest(mRequest);
            mPresenter.createFormRequestLeave(mRequestLeaveRequest);
            return;
        }
        mPresenter.editFormRequestLeave(mRequest);
    }

    public void onClickDelete(View view) {
        if (mRequest == null) {
            return;
        }
        mDialogManager.dialogBasic(mContext.getString(R.string.confirm_delete),
                mContext.getString(R.string.do_you_want_delete_this_request),
                new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog,
                            @NonNull DialogAction dialogAction) {
                        mPresenter.deleteFormRequestLeave(mRequest.getId());
                    }
                });
    }

    public void onClickEdit(View view) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.EXTRA_REQUEST_LEAVE, mRequest);
        bundle.putInt(Constant.EXTRA_ACTION_TYPE, ActionType.ACTION_EDIT);
        mNavigator.startActivity(RequestLeaveActivity.class, bundle);
    }
}
