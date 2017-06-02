package com.framgia.wsm.screen.requestovertime.confirmovertime;

import com.framgia.wsm.data.model.Request;

/**
 * Exposes the data to be used in the ConfirmOvertime screen.
 */

public class ConfirmOvertimeViewModel implements ConfirmOvertimeContract.ViewModel {

    private ConfirmOvertimeContract.Presenter mPresenter;
    private Request mRequest;

    public ConfirmOvertimeViewModel(ConfirmOvertimeContract.Presenter presenter,
            Request request) {
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mRequest = request;
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
}
