package com.framgia.wsm.screen.createnewpassword;

import android.content.Context;
import android.util.Log;
import com.framgia.wsm.R;
import com.framgia.wsm.utils.common.StringUtils;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import com.framgia.wsm.utils.validator.Validator;

/**
 * Listens to user actions from the UI ({@link CreateNewPasswordActivity}), retrieves the data and
 * updates
 * the UI as required.
 */
final class CreateNewPasswordPresenter implements CreateNewPasswordContract.Presenter {
    private static final String TAG = CreateNewPasswordPresenter.class.getName();

    private final Context mContext;
    private CreateNewPasswordContract.ViewModel mViewModel;
    private final Validator mValidator;
    private final BaseSchedulerProvider mBaseSchedulerProvider;

    CreateNewPasswordPresenter(Context context, Validator validator,
            BaseSchedulerProvider baseSchedulerProvider) {
        mContext = context;
        mValidator = validator;
        mBaseSchedulerProvider = baseSchedulerProvider;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void setViewModel(CreateNewPasswordContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void resetPassword(String newPassword, String confirmPassword) {
        //Todo edit later
    }

    @Override
    public boolean validateDataInput(String newPassword, String confirmPassword) {
        boolean isValid;
        validateNewPasswordInput(newPassword);
        try {
            isValid =
                    mValidator.validateAll(mViewModel) && validateConfirmPasswordInput(newPassword,
                            confirmPassword);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "IllegalAccessException: ", e);
            isValid = false;
        }
        return isValid;
    }

    @Override
    public void validateNewPasswordInput(String newPassword) {
        String errorMessage = mValidator.validateValueNonEmpty(newPassword);
        if (StringUtils.isBlank(errorMessage)) {
            errorMessage = mValidator.validateValueRangeMin6(newPassword);
            if (!StringUtils.isBlank(errorMessage)) {
                mViewModel.onInputNewPasswordError(errorMessage);
            } else {
                mViewModel.onInputNewPasswordError("");
            }
        } else {
            mViewModel.onInputNewPasswordError(errorMessage);
        }
    }

    @Override
    public boolean validateConfirmPasswordInput(String newPassword, String confirmPassword) {
        if (StringUtils.isBlank(newPassword)) {
            newPassword = "";
        }
        if (StringUtils.isBlank(confirmPassword)) {
            confirmPassword = "";
        }
        boolean isValid;
        if (!newPassword.equals(confirmPassword)) {
            mViewModel.onInputConfirmPasswordError(
                    mContext.getString(R.string.confirm_password_does_not_match));
            isValid = false;
        } else {
            mViewModel.onInputConfirmPasswordError(mContext.getString(R.string.none));
            isValid = true;
        }
        return isValid;
    }
}
