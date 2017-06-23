package com.framgia.wsm.screen.requestovertime.confirmovertime;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;
import android.view.View;
import com.android.databinding.library.baseAdapters.BR;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.utils.ActionType;
import com.framgia.wsm.utils.StatusCode;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.widget.dialog.DialogManager;

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
    public void onCreateFormOverTimeSuccess() {
        mNavigator.finishActivityWithResult(Activity.RESULT_OK);
    }

    @Override
    public void onCreateFormOverTimeError(BaseException exception) {
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
        if (mActionType == ActionType.ACTION_DETAIL) {
            return mContext.getString(R.string.request_overtime);
        }
        return mContext.getString(R.string.confirm_request_overtime);
    }

    public boolean isDetail() {
        return mActionType == ActionType.ACTION_DETAIL;
    }

    public boolean isAcceptStatus() {
        return mRequestOverTime.getStatus() == StatusCode.ACCEPT_CODE;
    }

    public boolean isPendingStatus() {
        return mRequestOverTime.getStatus() == StatusCode.PENDING_CODE;
    }

    public boolean isRejectStatus() {
        return mRequestOverTime.getStatus() == StatusCode.REJECT_CODE;
    }

    public void setActionType(int actionType) {
        mActionType = actionType;
    }

    public void onClickArrowBack(View view) {
        mNavigator.finishActivity();
    }

    public void onClickSubmit(View view) {
        if (mRequestOverTime == null) {
            return;
        }
        mPresenter.createFormRequestOverTime(mRequestOverTime);
    }

    public void onClickDelete(View view) {
        // todo delete request
    }

    public void onClickEdit(View view) {
        // todo open edit screen
    }
}
