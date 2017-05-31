package com.framgia.wsm.screen.requestovertime;

/**
 * Exposes the data to be used in the RequestOvertime screen.
 */

public class RequestOvertimeViewModel implements RequestOvertimeContract.ViewModel {

    private RequestOvertimeContract.Presenter mPresenter;

    public RequestOvertimeViewModel(RequestOvertimeContract.Presenter presenter) {
        mPresenter = presenter;
        mPresenter.setViewModel(this);
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }
}
