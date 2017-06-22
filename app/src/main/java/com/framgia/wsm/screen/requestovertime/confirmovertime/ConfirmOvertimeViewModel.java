package com.framgia.wsm.screen.requestovertime.confirmovertime;

import android.app.Activity;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;
import android.view.View;
import com.android.databinding.library.baseAdapters.BR;
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.widget.dialog.DialogManager;

/**
 * Exposes the data to be used in the ConfirmOvertime screen.
 */

public class ConfirmOvertimeViewModel extends BaseObservable
        implements ConfirmOvertimeContract.ViewModel {

    private static final String TAG = "ConfirmOvertimeViewModel";

    private ConfirmOvertimeContract.Presenter mPresenter;
    private RequestOverTime mRequestOverTime;
    private Navigator mNavigator;
    private DialogManager mDialogManager;
    private User mUser;

    ConfirmOvertimeViewModel(ConfirmOvertimeContract.Presenter presenter,
            RequestOverTime requestOverTime, Navigator navigator, DialogManager dialogManager) {
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

    public void onCickArrowBack(View view) {
        mNavigator.finishActivity();
    }

    public void onCickSubmit(View view) {
        if (mRequestOverTime == null) {
            return;
        }
        mPresenter.createFormRequestOverTime(mRequestOverTime);
    }
}
