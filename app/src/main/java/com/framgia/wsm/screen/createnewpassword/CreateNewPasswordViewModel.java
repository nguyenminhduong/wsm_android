package com.framgia.wsm.screen.createnewpassword;

/**
 * Exposes the data to be used in the CreateNewPassword screen.
 */

public class CreateNewPasswordViewModel implements CreateNewPasswordContract.ViewModel {

    private CreateNewPasswordContract.Presenter mPresenter;

    public CreateNewPasswordViewModel(CreateNewPasswordContract.Presenter presenter) {
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
