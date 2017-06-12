package com.framgia.wsm.screen.requestovertime.confirmovertime;

import android.view.View;
import com.framgia.wsm.data.model.Request;
import com.framgia.wsm.utils.navigator.Navigator;

/**
 * Exposes the data to be used in the ConfirmOvertime screen.
 */

public class ConfirmOvertimeViewModel implements ConfirmOvertimeContract.ViewModel {

    private ConfirmOvertimeContract.Presenter mPresenter;
    private Request mRequest;
    private Navigator mNavigator;

    public ConfirmOvertimeViewModel(ConfirmOvertimeContract.Presenter presenter, Request request,
            Navigator navigator) {
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mRequest = request;
        mNavigator = navigator;
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    public Request getRequest() {
        return mRequest;
    }

    public void onCickArrowBack(View view) {
        mNavigator.finishActivity();
    }
}
