package com.framgia.wsm.screen.changepassword;

import android.content.Context;
import android.util.Log;
import com.framgia.wsm.R;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.error.RequestError;
import com.framgia.wsm.data.source.remote.api.request.ChangePasswordRequest;
import com.framgia.wsm.utils.common.StringUtils;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import com.framgia.wsm.utils.validator.Validator;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Listens to user actions from the UI ({@link ChangePasswordActivity}), retrieves the data and
 * updates
 * the UI as required.
 */
final class ChangePasswordPresenter implements ChangePasswordContract.Presenter {
    private static final String TAG = ChangePasswordPresenter.class.getName();

    private Context mContext;
    private UserRepository mUserRepository;
    private ChangePasswordContract.ViewModel mViewModel;
    private Validator mValidator;
    private BaseSchedulerProvider mBaseSchedulerProvider;
    private CompositeDisposable mCompositeDisposable;

    ChangePasswordPresenter(Context context, UserRepository userRepository, Validator validator,
            BaseSchedulerProvider baseSchedulerProvider) {
        mContext = context;
        mUserRepository = userRepository;
        mValidator = validator;
        mBaseSchedulerProvider = baseSchedulerProvider;
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void setViewModel(ChangePasswordContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void changePassword(String currentPassword, String newPassword) {
        ChangePasswordRequest changePasswordRequest =
                new ChangePasswordRequest(currentPassword, newPassword);
        Disposable disposable = mUserRepository.changePassword(changePasswordRequest)
                .subscribeOn(mBaseSchedulerProvider.io())
                .observeOn(mBaseSchedulerProvider.ui())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        mViewModel.onChangePasswordSuccess();
                    }
                }, new RequestError() {
                    @Override
                    public void onRequestError(BaseException error) {
                        mViewModel.onChangePasswordError(error);
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public boolean validateDataInput(String currentPassword, String newPassword,
            String confirmPassword) {
        boolean isValid;
        validateCurrentPasswordInput(currentPassword);
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
    public void validateCurrentPasswordInput(String currentPassword) {
        String errorMessage = mValidator.validateValueNonEmpty(currentPassword);
        if (!StringUtils.isBlank(errorMessage)) {
            mViewModel.onInputCurrentPasswordError(errorMessage);
        } else {
            mViewModel.onInputCurrentPasswordError("");
        }
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
