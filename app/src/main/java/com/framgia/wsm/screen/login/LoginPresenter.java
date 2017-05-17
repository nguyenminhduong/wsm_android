package com.framgia.wsm.screen.login;

import com.framgia.wsm.data.source.UserRepository;

/**
 * Listens to user actions from the UI ({@link LoginActivity}), retrieves the data and updates
 * the UI as required.
 */
final class LoginPresenter implements LoginContract.Presenter {
    private static final String TAG = LoginPresenter.class.getName();

    private LoginContract.ViewModel mViewModel;
    private UserRepository mUserRepository;

    public LoginPresenter(UserRepository userRepository) {
        mUserRepository = userRepository;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void setViewModel(LoginContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }
}
