package com.framgia.wsm.screen.createnewpassword;

import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.BasePresenter;
import com.framgia.wsm.screen.BaseViewModel;

/**
 * This specifies the contract between the view and the presenter.
 */
interface CreateNewPasswordContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel {
        void onResetPasswordSuccess();

        void onResetPasswordError(BaseException exception);

        void onInputNewPasswordError(String message);

        void onInputConfirmPasswordError(String message);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<ViewModel> {
        void resetPassword(String tokenResetPassword, String email, String newPassword,
                String confirmPassword);

        boolean validateDataInput(String newPassword, String confirmPassword);

        void validateNewPasswordInput(String newPassword);

        boolean validateConfirmPasswordInput(String newPassword, String confirmPassword);
    }
}
