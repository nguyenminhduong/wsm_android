package com.framgia.wsm.screen.requestovertime;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Bundle;
import android.view.View;
import com.android.databinding.library.baseAdapters.BR;
import com.framgia.wsm.data.model.Request;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.requestovertime.confirmovertime.ConfirmOvertimeActivity;
import com.framgia.wsm.utils.navigator.Navigator;

import static com.framgia.wsm.utils.Constant.EXTRA_REQUEST_OVERTIME;

/**
 * Exposes the data to be used in the RequestOvertime screen.
 */

public class RequestOvertimeViewModel extends BaseObservable
        implements RequestOvertimeContract.ViewModel {

    private RequestOvertimeContract.Presenter mPresenter;
    private Request mRequest;
    private User mUser;
    private Navigator mNavigator;

    public RequestOvertimeViewModel(RequestOvertimeContract.Presenter presenter,
            Navigator navigator) {
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mNavigator = navigator;
        mRequest = new Request();
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

    public void onClickCreate(View v) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_REQUEST_OVERTIME, mRequest);
        mNavigator.startActivity(ConfirmOvertimeActivity.class, bundle);
    }

    @Override
    public void onGetUserSuccess(User user) {
        mUser = user;
        notifyPropertyChanged(BR.user);
    }

    @Override
    public void onGetUserError(BaseException exception) {
        //todo show message error
    }

    @Bindable
    public Request getRequest() {
        return mRequest;
    }

    @Bindable
    public User getUser() {
        return mUser;
    }
}
