package com.framgia.wsm.screen.changepassword;

import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.BasePresenter;
import com.framgia.wsm.screen.BaseViewModel;

/**
 * This specifies the contract between the view and the presenter.
 */
interface ChangePasswordContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel {
        void onChangePasswordError(BaseException e);

        void onInputCurrentPasswordError(String message);

        void onInputNewPasswordError(String message);

        void onInputConfirmPasswordError(String message);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<ViewModel> {
        void changePassword(String currentPassword, String newPassword);

        boolean validateDataInput(String currentPassword, String newPassword,
                String confirmPassword);

        void validateCurrentPasswordInput(String currentPassword);

        void validateNewPasswordInput(String newPassword);

        boolean validateConfirmPasswordInput(String newPassword, String confirmPassword);
    }
}
