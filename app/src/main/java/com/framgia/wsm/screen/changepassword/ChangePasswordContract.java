package com.framgia.wsm.screen.changepassword;

import com.framgia.wsm.data.model.User;
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
        void onGetUserSuccess(User user);

        void onChangePasswordSuccess();

        void onChangePasswordError(BaseException e);

        void onGetUserError(BaseException exception);

        void onInputCurrentPasswordError(String message);

        void onInputNewPasswordError(String message);

        void onInputConfirmPasswordError(String message);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<ViewModel> {
        void getUser();

        void changePassword(String currentPassword, String newPassword, String confirmPassword);

        boolean validateDataInput(String currentPassword, String newPassword,
                String confirmPassword);

        void validateCurrentPasswordInput(String currentPassword);

        void validateNewPasswordInput(String newPassword);

        boolean validateConfirmPasswordInput(String newPassword, String confirmPassword);
    }
}
