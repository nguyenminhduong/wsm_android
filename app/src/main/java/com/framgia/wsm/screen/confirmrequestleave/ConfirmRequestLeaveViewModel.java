package com.framgia.wsm.screen.confirmrequestleave;

/**
 * Exposes the data to be used in the ConfirmRequestLeave screen.
 */

public class ConfirmRequestLeaveViewModel implements ConfirmRequestLeaveContract.ViewModel {

    private ConfirmRequestLeaveContract.Presenter mPresenter;

    public ConfirmRequestLeaveViewModel(ConfirmRequestLeaveContract.Presenter presenter) {
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
