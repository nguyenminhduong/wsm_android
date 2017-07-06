package com.framgia.wsm.screen.requestovertime.confirmovertime;

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
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.requestovertime.RequestOvertimeActivity;
import com.framgia.wsm.utils.ActionType;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.StatusCode;
import com.framgia.wsm.utils.common.DateTimeUtils;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.widget.dialog.DialogManager;
import com.fstyle.library.DialogAction;
import com.fstyle.library.MaterialDialog;

/**
 * Exposes the data to be used in the ConfirmOvertime screen.
 */

public class ConfirmOvertimeViewModel extends BaseObservable
        implements ConfirmOvertimeContract.ViewModel {

    private static final String TAG = "ConfirmOvertimeViewModel";

    private Context mContext;
    private ConfirmOvertimeContract.Presenter mPresenter;
    private RequestOverTime mRequestOverTime;
    private Navigator mNavigator;
    private DialogManager mDialogManager;
    private User mUser;
    private int mActionType;

    ConfirmOvertimeViewModel(Context context, ConfirmOvertimeContract.Presenter presenter,
            RequestOverTime requestOverTime, Navigator navigator, DialogManager dialogManager) {
        mContext = context;
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mRequestOverTime = requestOverTime;
        mNavigator = navigator;
        mDialogManager = dialogManager;
        mPresenter.getUser();
    }

    private void setTimeRequestOverTIme(RequestOverTime requestOverTime) {
        requestOverTime.setFromTime(
                DateTimeUtils.convertUiFormatToDataFormat(requestOverTime.getFromTime(),
                        DateTimeUtils.INPUT_TIME_FORMAT,
                        DateTimeUtils.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM));
        requestOverTime.setToTime(
                DateTimeUtils.convertUiFormatToDataFormat(requestOverTime.getToTime(),
                        DateTimeUtils.INPUT_TIME_FORMAT,
                        DateTimeUtils.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM));
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
    public void onGetUserSuccess(User user) {
        if (user == null) {
            return;
        }
        mUser = user;
        notifyPropertyChanged(BR.user);
    }

    @Override
    public void onGetUserError(BaseException e) {
        Log.e(TAG, "ConfirmOvertimeViewModel", e);
    }

    @Override
    public void onDismissProgressDialog() {
        mDialogManager.dismissProgressDialog();
    }

    @Override
    public void onShowIndeterminateProgressDialog() {
        mDialogManager.showIndeterminateProgressDialog();
    }

    @Override
    public void onCreateFormOverTimeSuccess() {
        mNavigator.finishActivityWithResult(Activity.RESULT_OK);
    }

    @Override
    public void onCreateFormOverTimeError(BaseException exception) {
        mDialogManager.dialogError(exception, new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog,
                    @NonNull DialogAction dialogAction) {
                mNavigator.finishActivity();
            }
        });
    }

    @Override
    public void onEditFormOverTimeSuccess(RequestOverTime requestOverTime) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.EXTRA_REQUEST_OVERTIME, requestOverTime);
        bundle.putInt(Constant.EXTRA_ACTION_TYPE, ActionType.ACTION_DETAIL);
        mNavigator.startActivityAtRoot(ConfirmOvertimeActivity.class, bundle);
    }

    @Override
    public void onEditFormOverTimeError(BaseException exception) {
        mDialogManager.dialogError(exception, new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog,
                    @NonNull DialogAction dialogAction) {
                mNavigator.finishActivity();
            }
        });
    }

    @Override
    public void onDeleteFormOverTimeSuccess() {
        mNavigator.finishActivityWithResult(Activity.RESULT_OK);
    }

    @Override
    public void onDeleteFormOverTimeError(BaseException exception) {
        mDialogManager.dialogError(exception);
    }

    @Bindable
    public User getUser() {
        return mUser;
    }

    public RequestOverTime getRequestOverTime() {
        return mRequestOverTime;
    }

    public String getTitleToolbar() {
        if (mActionType == ActionType.ACTION_CREATE) {
            return mContext.getString(R.string.confirm_request_overtime);
        }
        if (mActionType == ActionType.ACTION_DETAIL) {
            return mContext.getString(R.string.detail_request_overtime);
        }
        return mContext.getString(R.string.confirm_edit_request_overtime);
    }

    public boolean isDetail() {
        return mActionType == ActionType.ACTION_DETAIL;
    }

    public boolean isAcceptStatus() {
        return StatusCode.ACCEPT_CODE.equals(mRequestOverTime.getStatus());
    }

    public boolean isPendingStatus() {
        return StatusCode.PENDING_CODE.equals(mRequestOverTime.getStatus());
    }

    public boolean isRejectStatus() {
        return StatusCode.REJECT_CODE.equals(mRequestOverTime.getStatus());
    }

    public boolean isForwardedStatus() {
        return StatusCode.FORWARDED.equals(mRequestOverTime.getStatus());
    }

    public void setActionType(int actionType) {
        mActionType = actionType;
        if (ActionType.ACTION_DETAIL == mActionType) {
            setTimeRequestOverTIme(mRequestOverTime);
        }
    }

    public boolean isVisibleButtonSubmit() {
        return StatusCode.PENDING_CODE.equals(mRequestOverTime.getStatus())
                || mActionType == ActionType.ACTION_CREATE;
    }

    public void onClickArrowBack(View view) {
        mNavigator.finishActivity();
    }

    public void onClickSubmit(View view) {
        if (mRequestOverTime == null) {
            return;
        }
        if (mActionType == ActionType.ACTION_CREATE) {
            mPresenter.createFormRequestOverTime(mRequestOverTime);
            return;
        }
        mPresenter.editFormRequestOvertime(mRequestOverTime);
    }

    public void onClickDelete(View view) {
        if (mRequestOverTime == null) {
            return;
        }
        mPresenter.deleteFormRequestOvertime(mRequestOverTime.getId());
    }

    public void onClickEdit(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.EXTRA_ACTION_TYPE, ActionType.ACTION_EDIT);
        bundle.putParcelable(Constant.EXTRA_REQUEST_OVERTIME, mRequestOverTime);
        mNavigator.startActivity(RequestOvertimeActivity.class, bundle);
    }
}
