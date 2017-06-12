package com.framgia.wsm.screen.requestovertime.confirmovertime;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;
import android.view.View;
import com.android.databinding.library.baseAdapters.BR;
import com.framgia.wsm.data.model.Request;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.main.MainActivity;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.widget.dialog.DialogManager;

/**
 * Exposes the data to be used in the ConfirmOvertime screen.
 */

public class ConfirmOvertimeViewModel extends BaseObservable
        implements ConfirmOvertimeContract.ViewModel {

    private static final String TAG = "ConfirmOvertimeViewModel";

    private ConfirmOvertimeContract.Presenter mPresenter;
    private Request mRequest;
    private Navigator mNavigator;
    private DialogManager mDialogManager;
    private User mUser;

    ConfirmOvertimeViewModel(ConfirmOvertimeContract.Presenter presenter, Request request,
            Navigator navigator, DialogManager dialogManager) {
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mRequest = request;
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
    public void onCreateFormSuccess() {
        mNavigator.startActivityAtRoot(MainActivity.class);
    }

    @Override
    public void onCreateFormError(BaseException exception) {
        mDialogManager.dialogError(exception);
    }

    @Bindable
    public User getUser() {
        return mUser;
    }

    public Request getRequest() {
        return mRequest;
    }

    public void onCickArrowBack(View view) {
        mNavigator.finishActivity();
    }

    public void onCickSubmit(View view) {
        if (mRequest == null) {
            return;
        }
        mPresenter.onCreateForm(mRequest);
    }
}
