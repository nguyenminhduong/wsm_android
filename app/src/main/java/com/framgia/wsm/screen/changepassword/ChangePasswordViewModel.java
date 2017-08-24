package com.framgia.wsm.screen.changepassword;

import android.app.Activity;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;
import android.view.View;
import com.android.databinding.library.baseAdapters.BR;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.utils.validator.Rule;
import com.framgia.wsm.utils.validator.ValidType;
import com.framgia.wsm.utils.validator.Validation;

/**
 * Exposes the data to be used in the changePassword screen.
 */

public class ChangePasswordViewModel extends BaseObservable
        implements ChangePasswordContract.ViewModel {
    private static final String TAG = "ChangePasswordViewModel";

    private ChangePasswordContract.Presenter mPresenter;
    @Validation({
            @Rule(types = ValidType.VALUE_RANGE_MIN_6, message = R.string.least_6_characters),
            @Rule(types = ValidType.NON_EMPTY, message = R.string.is_empty)
    })
    private String mNewPassword;
    private Navigator mNavigator;
    private String mCurrentPasswordError;
    private String mNewPasswordError;
    private String mConfirmPasswordError;
    @Validation({
            @Rule(types = ValidType.NON_EMPTY, message = R.string.is_empty)
    })
    private String mCurrentPassword;
    private String mConfirmPassword;
    private User mUser;

    public ChangePasswordViewModel(ChangePasswordContract.Presenter presenter,
            Navigator navigator) {
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mNavigator = navigator;
        mPresenter.getUser();
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
    public void onGetUserSuccess(User user) {
        if (user == null) {
            return;
        }
        mUser = user;
        notifyPropertyChanged(BR.user);
        notifyPropertyChanged(BR.avatar);
    }

    @Override
    public void onChangePasswordSuccess() {
        mNavigator.showToast(R.string.change_password_success);
        mNavigator.finishActivityWithResult(Activity.RESULT_OK);
    }

    @Override
    public void onChangePasswordError(BaseException e) {
        mNavigator.showToast(R.string.invalid_current_password);
    }

    @Override
    public void onGetUserError(BaseException exception) {
        Log.e(TAG, "onGetUserError", exception);
    }

    @Override
    public void onInputCurrentPasswordError(String message) {
        mCurrentPasswordError = message;
        notifyPropertyChanged(BR.currentPasswordError);
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
    public User getUser() {
        return mUser;
    }

    @Bindable
    public String getAvatar() {
        return mUser != null ? Constant.END_POINT_URL + mUser.getAvatar() : "";
    }

    @Bindable
    public String getCurrentPasswordError() {
        return mCurrentPasswordError;
    }

    public void setCurrentPasswordError(String currentPasswordError) {
        mCurrentPasswordError = currentPasswordError;
        notifyPropertyChanged(BR.currentPasswordError);
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
    public String getCurrentPassword() {
        return mCurrentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        mCurrentPassword = currentPassword;
        notifyPropertyChanged(BR.currentPassword);
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
    }

    public void validateConfirmPassword(String confirmPassWord) {
        mPresenter.validateConfirmPasswordInput(mNewPassword, confirmPassWord);
        mConfirmPassword = confirmPassWord;
        notifyPropertyChanged(BR.confirmPassword);
    }

    public void onChangePasswordClick(View view) {
        if (!mPresenter.validateDataInput(mCurrentPassword, mNewPassword, mConfirmPassword)) {
            return;
        }
        mPresenter.changePassword(mCurrentPassword, mNewPassword, mConfirmPassword);
    }

    public void onClickArrowBack(View view) {
        mNavigator.finishActivity();
    }
}
