package com.framgia.wsm.screen.changepassword;

/**
 * Exposes the data to be used in the changepassword screen.
 */

public class ChangePasswordViewModel implements ChangePasswordContract.ViewModel {

    private ChangePasswordContract.Presenter mPresenter;

    public ChangePasswordViewModel(ChangePasswordContract.Presenter presenter) {
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
