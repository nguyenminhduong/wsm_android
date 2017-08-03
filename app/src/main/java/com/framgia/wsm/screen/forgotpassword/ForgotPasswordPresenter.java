package com.framgia.wsm.screen.forgotpassword;

/**
 * Listens to user actions from the UI ({@link ForgotPasswordActivity}), retrieves the data and
 * updates
 * the UI as required.
 */
final class ForgotPasswordPresenter implements ForgotPasswordContract.Presenter {
    private static final String TAG = ForgotPasswordPresenter.class.getName();

    private ForgotPasswordContract.ViewModel mViewModel;

    ForgotPasswordPresenter() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void setViewModel(ForgotPasswordContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void sendEmail(String email) {
        //Todo edit later
    }

    @Override
    public void validateDataInput(String email) {
        //Todo edit later
    }

    @Override
    public void validateEmailInput(String emailInput) {
        //Todo edit later
    }
}
