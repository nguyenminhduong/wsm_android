package com.framgia.wsm.screen.profile;

/**
 * Listens to user actions from the UI ({@link ProfileFragment}), retrieves the data and updates
 * the UI as required.
 */
final class ProfilePresenter implements ProfileContract.Presenter {
    private static final String TAG = ProfilePresenter.class.getName();

    private ProfileContract.ViewModel mViewModel;

    ProfilePresenter() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void setViewModel(ProfileContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }
}
