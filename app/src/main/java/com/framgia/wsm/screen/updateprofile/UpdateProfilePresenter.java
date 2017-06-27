package com.framgia.wsm.screen.updateprofile;

/**
 * Listens to user actions from the UI ({@link UpdateProfileActivity}), retrieves the data and
 * updates
 * the UI as required.
 */
final class UpdateProfilePresenter implements UpdateProfileContract.Presenter {
    private static final String TAG = UpdateProfilePresenter.class.getName();

    private UpdateProfileContract.ViewModel mViewModel;

    UpdateProfilePresenter() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void setViewModel(UpdateProfileContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }
}
