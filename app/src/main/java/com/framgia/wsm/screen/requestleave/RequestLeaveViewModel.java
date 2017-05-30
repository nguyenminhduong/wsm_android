package com.framgia.wsm.screen.requestleave;

/**
 * Exposes the data to be used in the RequestLeave screen.
 */

public class RequestLeaveViewModel implements RequestLeaveContract.ViewModel {

    private RequestLeaveContract.Presenter mPresenter;

    public RequestLeaveViewModel(RequestLeaveContract.Presenter presenter) {
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
