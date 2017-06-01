package com.framgia.wsm.screen.requestovertime.confirmovertime;

/**
 * Exposes the data to be used in the ConfirmOvertime screen.
 */

public class ConfirmOvertimeViewModel implements ConfirmOvertimeContract.ViewModel {

    private ConfirmOvertimeContract.Presenter mPresenter;

    public ConfirmOvertimeViewModel(ConfirmOvertimeContract.Presenter presenter) {
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
