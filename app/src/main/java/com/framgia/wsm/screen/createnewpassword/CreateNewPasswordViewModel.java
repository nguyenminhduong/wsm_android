package com.framgia.wsm.screen.createnewpassword;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.android.databinding.library.baseAdapters.BR;
import com.framgia.wsm.R;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.login.LoginActivity;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.utils.validator.Rule;
import com.framgia.wsm.utils.validator.ValidType;
import com.framgia.wsm.utils.validator.Validation;
import com.framgia.wsm.widget.dialog.DialogManager;

/**
 * Exposes the data to be used in the CreateNewPassword screen.
 */

public class CreateNewPasswordViewModel extends BaseObservable
        implements CreateNewPasswordContract.ViewModel {

    private final CreateNewPasswordContract.Presenter mPresenter;
    private final Navigator mNavigator;
    private final DialogManager mDialogManager;
    private final String mTokenResetPassword;
    private final String mEmailResetPassword;
    private String mNewPasswordError;
    private String mConfirmPasswordError;

    @Validation({
            @Rule(types = ValidType.VALUE_RANGE_MIN_6, message = R.string.least_6_characters),
            @Rule(types = ValidType.NON_EMPTY, message = R.string.is_empty)
    })
    private String mNewPassword;
    @Validation({
            @Rule(types = ValidType.NON_EMPTY, message = R.string.is_empty)
    })
    private String mConfirmPassword;

    public CreateNewPasswordViewModel(CreateNewPasswordContract.Presenter presenter,
            Navigator navigator, DialogManager dialogManager, String tokenResetPassword,
            String emailResetPassword) {
        mPresenter = presenter;
        mNavigator = navigator;
        mDialogManager = dialogManager;
        mTokenResetPassword = tokenResetPassword;
        mEmailResetPassword = emailResetPassword;
        mPresenter.setViewModel(this);
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Override
    public void onResetPasswordSuccess() {
        mDialogManager.dismissProgressDialog();
        mNavigator.showToast(R.string.reset_password_success);
        mNavigator.startActivity(LoginActivity.class);
    }

    @Override
    public void onResetPasswordError(BaseException exception) {
        mDialogManager.dismissProgressDialog();
        mNavigator.showToast(exception.getMessage());
    }

    @Override
    public void onInputNewPasswordError(String message) {
        mNewPasswordError = message;
        notifyPropertyChanged(BR.newPasswordError);
    }

    @Override
    public void onInputConfirmPasswordError(String message) {
        mConfirmPasswordError = message;

        notifyPropertyChanged(BR.confirmPasswordError);
    }

    @Bindable
    public String getNewPassword() {
        return mNewPassword;
    }

    public void setNewPassword(String newPassword) {
        mNewPassword = newPassword;
        notifyPropertyChanged(BR.newPassword);
    }

    @Bindable
    public String getNewPasswordError() {
        return mNewPasswordError;
    }

    public void setNewPasswordError(String newPasswordError) {
        mNewPasswordError = newPasswordError;
        notifyPropertyChanged(BR.newPasswordError);
    }

    @Bindable
    public String getConfirmPasswordError() {
        return mConfirmPasswordError;
    }

    public void setConfirmPasswordError(String confirmPasswordError) {
        mConfirmPasswordError = confirmPasswordError;
        notifyPropertyChanged(BR.confirmPasswordError);
    }

    @Bindable
    public String getConfirmPassword() {
        return mConfirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        mConfirmPassword = confirmPassword;
        notifyPropertyChanged(BR.confirmPassword);
    }

    public void validateNewPassword(String newPassWord) {
        mPresenter.validateNewPasswordInput(newPassWord);
        mNewPassword = newPassWord;
        notifyPropertyChanged(BR.newPassword);
        mPresenter.validateConfirmPasswordInput(mNewPassword, mConfirmPassword);
    }

    public void validateConfirmPassword(String confirmPassWord) {
        mPresenter.validateConfirmPasswordInput(mNewPassword, confirmPassWord);
        mConfirmPassword = confirmPassWord;
        notifyPropertyChanged(BR.confirmPassword);
    }

    public void onResetPassword() {
        if (!mPresenter.validateDataInput(mNewPassword, mConfirmPassword)) {
            return;
        }
    }
}
