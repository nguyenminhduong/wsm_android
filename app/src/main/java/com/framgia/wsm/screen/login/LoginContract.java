package com.framgia.wsm.screen.login;

import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.BasePresenter;
import com.framgia.wsm.screen.BaseViewModel;

/**
 * This specifies the contract between the view and the presenter.
 */
interface LoginContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel {
        void onLoginError(BaseException throwable);

        void onLoginSuccess();

        void onUserLoggedIn();

        void onInputUserNameError(String message);

        void onInputPasswordError(String message);

        void onForgotPasswordClick();
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<ViewModel> {
        void login(String userName, String passWord);

        boolean validateDataInput(String userName, String passWord);

        void validateUserNameInput(String userName);

        void checkUserLogin();
    }
}
