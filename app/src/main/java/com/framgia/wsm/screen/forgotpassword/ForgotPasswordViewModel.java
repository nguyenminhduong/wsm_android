package com.framgia.wsm.screen.forgotpassword;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;
import com.framgia.wsm.BR;
import com.framgia.wsm.R;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.utils.validator.Rule;
import com.framgia.wsm.utils.validator.ValidType;
import com.framgia.wsm.utils.validator.Validation;
import com.framgia.wsm.widget.dialog.DialogManager;

/**
 * Exposes the data to be used in the forgotpassword screen.
 */

public class ForgotPasswordViewModel extends BaseObservable
        implements ForgotPasswordContract.ViewModel {

    private final ForgotPasswordContract.Presenter mPresenter;
    private final Context mContext;
    private final Navigator mNavigator;
    private final DialogManager mDialogManager;
    private String mEmailError;

    @Validation({
            @Rule(types = ValidType.EMAIL_FORMAT, message = R.string.invalid_email_format),
            @Rule(types = ValidType.NON_EMPTY, message = R.string.is_empty)
    })
    private String mEmail;

    public ForgotPasswordViewModel(Context context, ForgotPasswordContract.Presenter presenter,
            Navigator navigator, DialogManager dialogManager) {
        mContext = context;
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mNavigator = navigator;
        mDialogManager = dialogManager;
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    public void onClickArrowBack(View view) {
        mNavigator.finishActivity();
    }

    public void onSendEmailClick(View view) {
        if (!mPresenter.validateDataInput(mEmail)) {
            return;
        }
        mDialogManager.showIndeterminateProgressDialog();
        mPresenter.sendEmail(mEmail);
    }

    @Override

    public void onSendEmailSuccess() {
        mDialogManager.dismissProgressDialog();
        setEmail("");
        mNavigator.finishActivity();
        mNavigator.showToast(mContext.getString(R.string.message_send_email_success));
    }

    @Override
    public void onSendEmailError(BaseException exception) {
        mDialogManager.dismissProgressDialog();
        mNavigator.showToast(exception.getMessage());
    }

    @Override
    public void onInputEmailError(String message) {
        mEmailError = message;
        notifyPropertyChanged(BR.emailError);
    }

    @Bindable
    public String getEmailError() {
        return mEmailError;
    }

    @Bindable
    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
        notifyPropertyChanged(BR.email);
    }

    public void validateEmail(String email) {
        mPresenter.validateEmailInput(email);
    }
}
