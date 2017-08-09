package com.framgia.wsm.screen.createnewpassword;

/**
 * Listens to user actions from the UI ({@link CreateNewPasswordActivity}), retrieves the data and
 * updates
 * the UI as required.
 */
final class CreateNewPasswordPresenter implements CreateNewPasswordContract.Presenter {
    private static final String TAG = CreateNewPasswordPresenter.class.getName();

    private CreateNewPasswordContract.ViewModel mViewModel;

    CreateNewPasswordPresenter() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void setViewModel(CreateNewPasswordContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }
}
