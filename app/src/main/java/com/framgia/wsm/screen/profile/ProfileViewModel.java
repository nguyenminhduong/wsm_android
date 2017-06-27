package com.framgia.wsm.screen.profile;

/**
 * Exposes the data to be used in the Profile screen.
 */

public class ProfileViewModel implements ProfileContract.ViewModel {

    private ProfileContract.Presenter mPresenter;

    public ProfileViewModel(ProfileContract.Presenter presenter) {
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
