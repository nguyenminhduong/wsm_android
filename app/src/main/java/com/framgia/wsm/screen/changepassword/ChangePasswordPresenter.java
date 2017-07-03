package com.framgia.wsm.screen.changepassword;

/**
 * Listens to user actions from the UI ({@link ChangePasswordActivity}), retrieves the data and
 * updates
 * the UI as required.
 */
final class ChangePasswordPresenter implements ChangePasswordContract.Presenter {
    private static final String TAG = ChangePasswordPresenter.class.getName();

    private ChangePasswordContract.ViewModel mViewModel;

    ChangePasswordPresenter() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void setViewModel(ChangePasswordContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }
}
