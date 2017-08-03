package com.framgia.wsm.screen.forgotpassword;

import android.content.Context;
import android.view.View;
import com.framgia.wsm.R;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.utils.TypeToast;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.widget.dialog.DialogManager;

/**
 * Exposes the data to be used in the forgotpassword screen.
 */

public class ForgotPasswordViewModel implements ForgotPasswordContract.ViewModel {

    private final ForgotPasswordContract.Presenter mPresenter;
    private final Context mContext;
    private final Navigator mNavigator;
    private final DialogManager mDialogManager;

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
        //Todo edit later
    }

    @Override
    public void onSendEmailSuccess() {
        mDialogManager.dialogError(mContext.getString(R.string.message_send_email_success));
    }

    @Override
    public void onSendEmailError(BaseException exception) {
        mNavigator.showToastCustom(TypeToast.ERROR, exception.getMessage());
    }

    @Override
    public void onInputEmailError(String message) {
        //Todo edit later
    }
}
