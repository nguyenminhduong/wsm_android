package com.framgia.wsm.screen.login;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.android.databinding.library.baseAdapters.BR;
import com.framgia.wsm.R;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.firebase.FireBaseInstanceIDService;
import com.framgia.wsm.screen.forgotpassword.ForgotPasswordActivity;
import com.framgia.wsm.screen.main.MainActivity;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.utils.validator.Rule;
import com.framgia.wsm.utils.validator.ValidType;
import com.framgia.wsm.utils.validator.Validation;
import com.framgia.wsm.widget.dialog.DialogManager;

/**
 * Exposes the data to be used in the Login screen.
 */

public class LoginViewModel extends BaseObservable implements LoginContract.ViewModel {
    private static final String TAG = "LoginViewModel";

    private Context mContext;
    private Navigator mNavigator;
    private DialogManager mDialogManager;
    private LoginContract.Presenter mPresenter;
    private String mUsernameError;
    private String mPasswordError;
    private String mDeviceId;
    private boolean mIsUnauthorized;

    @Validation({
            @Rule(types = ValidType.EMAIL_FORMAT, message = R.string.invalid_email_format),
            @Rule(types = ValidType.NON_EMPTY, message = R.string.is_empty)
    })
    private String mUsername;

    @Validation({
            @Rule(types = ValidType.VALUE_RANGE_MIN_6, message = R.string.least_6_characters),
            @Rule(types = ValidType.NON_EMPTY, message = R.string.is_empty)
    })
    private String mPassword;

    LoginViewModel(Context context, LoginContract.Presenter presenter, Navigator navigator,
            DialogManager dialogManager, Bundle bundle) {
        mContext = context;
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mNavigator = navigator;
        mDialogManager = dialogManager;
        onStartFireBaseServices();
        mIsUnauthorized = bundle != null && bundle.getBoolean(Constant.EXTRA_UNAUTHORIZED);
        mPresenter.checkUserLogin(mIsUnauthorized);
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
    public void onLoginError(BaseException e) {
        mDialogManager.dismissProgressDialog();
        mNavigator.showToast(e.getMessage());
    }

    @Override
    public void onLoginSuccess() {
        mDialogManager.dismissProgressDialog();
        mNavigator.startActivity(MainActivity.class);
        mNavigator.finishActivity();
    }

    @Override
    public void onUserLoggedIn() {
        mNavigator.startActivity(MainActivity.class);
        mNavigator.finishActivity();
    }

    @Override
    public void onInputUserNameError(String message) {
        mUsernameError = message;
        notifyPropertyChanged(BR.usernameError);
    }

    @Override
    public void onInputPasswordError(String message) {
        mPasswordError = message;
        notifyPropertyChanged(BR.passwordError);
    }

    @Override
    public void onForgotPasswordClick() {
        mNavigator.startActivity(ForgotPasswordActivity.class);
    }

    @Bindable
    public String getUsernameError() {
        return mUsernameError;
    }

    public void setUsernameError(String usernameError) {
        mUsernameError = usernameError;
        notifyPropertyChanged(BR.usernameError);
    }

    @Bindable
    public String getPasswordError() {
        return mPasswordError;
    }

    public void setPasswordError(String passwordError) {
        mPasswordError = passwordError;
        notifyPropertyChanged(BR.passwordError);
    }

    @Bindable
    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    @Bindable
    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    private void onStartFireBaseServices() {
        FireBaseInstanceIDService fireBaseInstanceIDService = new FireBaseInstanceIDService();
        mDeviceId = fireBaseInstanceIDService.getToken();
        Log.d(TAG, "Token FireBase: " + mDeviceId);
    }

    public void onLoginClick(View view) {
        if (!mPresenter.validateDataInput(mUsername, mPassword)) {
            return;
        }
        mDialogManager.showIndeterminateProgressDialog();
        mPresenter.login(mUsername, mPassword, mDeviceId);
    }
}
