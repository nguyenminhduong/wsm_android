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
import com.framgia.wsm.data.model.Request;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.confirmrequestoff.ConfirmRequestOffActivity;
import com.framgia.wsm.screen.requestleave.RequestLeaveActivity;
import com.framgia.wsm.screen.requestleave.RequestLeaveViewModel;
import com.framgia.wsm.utils.ActionType;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.StatusCode;
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
    private Request mRequest;
    private User mUser;
    private Navigator mNavigator;
    private String mLeaveType;
    private DialogManager mDialogManager;
    private int mActionType;
    private Context mContext;

    ConfirmRequestLeaveViewModel(Context context, ConfirmRequestLeaveContract.Presenter presenter,
            Request request, Navigator navigator, DialogManager dialogManager, int actionType) {
        mContext = context;
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mNavigator = navigator;
        mDialogManager = dialogManager;
        mRequest = request;
        mLeaveType = mRequest.getLeaveType().getName();
        mPresenter.getUser();
        mActionType = actionType;
    }

    public boolean isVisibleLayoutCheckout() {
        return mLeaveType.equals(RequestLeaveViewModel.LeaveType.LEAVE_EARLY_A)
                || mLeaveType.equals(RequestLeaveViewModel.LeaveType.LEAVE_EARLY_M)
                || mLeaveType.equals(RequestLeaveViewModel.LeaveType.LEAVE_EARLY_WOMAN_A)
                || mLeaveType.equals(RequestLeaveViewModel.LeaveType.LEAVE_EARLY_WOMAN_M)
                || mLeaveType.equals(RequestLeaveViewModel.LeaveType.FORGOT_CARD_OUT)
                || mLeaveType.equals(RequestLeaveViewModel.LeaveType.FORGOT_TO_CHECK_OUT)
                || mLeaveType.equals(RequestLeaveViewModel.LeaveType.FORGOT_CARD_ALL_DAY)
                || mLeaveType.equals(RequestLeaveViewModel.LeaveType.FORGOT_CHECK_ALL_DAY)
                || mLeaveType.equals(RequestLeaveViewModel.LeaveType.LEAVE_OUT);
    }

    public boolean isVisibleLayoutCheckin() {
        return mLeaveType.equals(RequestLeaveViewModel.LeaveType.IN_LATE_A) || mLeaveType.equals(
                RequestLeaveViewModel.LeaveType.IN_LATE_M) || mLeaveType.equals(
                RequestLeaveViewModel.LeaveType.IN_LATE_WOMAN_A) || mLeaveType.equals(
                RequestLeaveViewModel.LeaveType.IN_LATE_WOMAN_M) || mLeaveType.equals(
                RequestLeaveViewModel.LeaveType.FORGOT_CARD_IN) || mLeaveType.equals(
                RequestLeaveViewModel.LeaveType.FORGOT_TO_CHECK_IN) || mLeaveType.equals(
                RequestLeaveViewModel.LeaveType.FORGOT_CARD_ALL_DAY) || mLeaveType.equals(
                RequestLeaveViewModel.LeaveType.FORGOT_CHECK_ALL_DAY) || mLeaveType.equals(
                RequestLeaveViewModel.LeaveType.LEAVE_OUT);
    }

    public boolean isVisibleLayoutCompensation() {
        return mLeaveType.equals(RequestLeaveViewModel.LeaveType.IN_LATE_A) || mLeaveType.equals(
                RequestLeaveViewModel.LeaveType.IN_LATE_M) || mLeaveType.equals(
                RequestLeaveViewModel.LeaveType.LEAVE_EARLY_A) || mLeaveType.equals(
                RequestLeaveViewModel.LeaveType.LEAVE_EARLY_M) || mLeaveType.equals(
                RequestLeaveViewModel.LeaveType.LEAVE_OUT);
    }

    public boolean isDetail() {
        return mActionType == ActionType.ACTION_DETAIL;
    }

    public boolean isAcceptStatus() {
        return mRequest.getStatus() == StatusCode.ACCEPT_CODE;
    }

    public boolean isPendingStatus() {
        return mRequest.getStatus() == StatusCode.PENDING_CODE;
    }

    public boolean isRejectStatus() {
        return mRequest.getStatus() == StatusCode.REJECT_CODE;
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

    public Request getRequest() {
        return mRequest;
    }

    @Bindable
    public User getUser() {
        return mUser;
    }

    public void onClickArrowBack(View view) {
        mNavigator.finishActivity();
    }

    public void onClickSubmit(View view) {
        if (mRequest == null) {
            return;
        }
        if (mActionType == ActionType.ACTION_CONFIRM_CREATE) {
            mPresenter.createFormRequestLeave(mRequest);
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
