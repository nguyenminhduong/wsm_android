package com.framgia.wsm.screen.login;

import android.util.Log;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.utils.common.StringUtils;
import com.framgia.wsm.utils.validator.Validator;

/**
 * Listens to user actions from the UI ({@link LoginActivity}), retrieves the data and updates
 * the UI as required.
 */
final class LoginPresenter implements LoginContract.Presenter {
    private static final String TAG = LoginPresenter.class.getName();

    private LoginContract.ViewModel mViewModel;
    private UserRepository mUserRepository;
    private Validator mValidator;

    LoginPresenter(UserRepository userRepository, Validator validator) {
        mUserRepository = userRepository;
        mValidator = validator;
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

    @Override
    public void login(String userName, String passWord) {
        // TODO: Login
    }

    @Override
    public void validateUserNameInput(String userName) {
        String messageUsername = mValidator.validateValueNonEmpty(userName);
        if (StringUtils.isBlank(messageUsername)) {
            messageUsername = mValidator.validateEmailFormat(userName);
            if (!StringUtils.isBlank(messageUsername)) {
                mViewModel.onInputUserNameError(messageUsername);
            }
        } else {
            mViewModel.onInputUserNameError(messageUsername);
        }
    }

    @Override
    public void validatePasswordInput(String password) {
        String messagePassword = mValidator.validateValueNonEmpty(password);
        if (!StringUtils.isBlank(messagePassword)) {
            mViewModel.onInputPasswordError(messagePassword);
        }
    }

    @Override
    public boolean validateDataInput(String userName, String password) {
        validateUserNameInput(userName);
        validatePasswordInput(password);
        try {
            return mValidator.validateAll(mViewModel);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "validateDataInput: ", e);
            return false;
        }
    }
}
