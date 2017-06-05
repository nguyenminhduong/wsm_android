package com.framgia.wsm.screen.confirmrequestoff;

/**
 * Exposes the data to be used in the ConfirmRequestOff screen.
 */

public class ConfirmRequestOffViewModel implements ConfirmRequestOffContract.ViewModel {

    private ConfirmRequestOffContract.Presenter mPresenter;

    public ConfirmRequestOffViewModel(ConfirmRequestOffContract.Presenter presenter) {
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
