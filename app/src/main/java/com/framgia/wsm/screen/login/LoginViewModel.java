package com.framgia.wsm.screen.login;

/**
 * Exposes the data to be used in the Login screen.
 */

public class LoginViewModel implements LoginContract.ViewModel {

    private LoginContract.Presenter mPresenter;

    public LoginViewModel(LoginContract.Presenter presenter) {
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
