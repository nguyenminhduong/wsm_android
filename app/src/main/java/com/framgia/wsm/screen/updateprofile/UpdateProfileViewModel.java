package com.framgia.wsm.screen.updateprofile;

/**
 * Exposes the data to be used in the Updateprofile screen.
 */

public class UpdateProfileViewModel implements UpdateProfileContract.ViewModel {

    private UpdateProfileContract.Presenter mPresenter;

    public UpdateProfileViewModel(UpdateProfileContract.Presenter presenter) {
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
