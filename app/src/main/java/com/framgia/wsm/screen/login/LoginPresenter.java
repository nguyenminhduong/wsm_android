package com.framgia.wsm.screen.login;

import android.util.Log;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.error.RequestError;
import com.framgia.wsm.data.source.remote.api.response.UserResponse;
import com.framgia.wsm.utils.common.StringUtils;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import com.framgia.wsm.utils.validator.Validator;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Listens to user actions from the UI ({@link LoginActivity}), retrieves the data and updates
 * the UI as required.
 */
final class LoginPresenter implements LoginContract.Presenter {
    private static final String TAG = LoginPresenter.class.getName();

    private LoginContract.ViewModel mViewModel;
    private UserRepository mUserRepository;
    private Validator mValidator;
    private CompositeDisposable mCompositeDisposable;
    private BaseSchedulerProvider mSchedulerProvider;

    LoginPresenter(UserRepository userRepository, Validator validator,
            BaseSchedulerProvider baseSchedulerProvider) {
        mUserRepository = userRepository;
        mValidator = validator;
        mCompositeDisposable = new CompositeDisposable();
        mSchedulerProvider = baseSchedulerProvider;
        Disposable disposable = mValidator.initNGWordPattern();
        if (disposable != null) {
            mCompositeDisposable.add(disposable);
        }
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
        mCompositeDisposable.clear();
    }

    @Override
    public void setViewModel(LoginContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void login(String userName, String passWord) {
        Disposable subscription = mUserRepository.login(userName, passWord)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Consumer<UserResponse>() {
                    @Override
                    public void accept(@NonNull UserResponse userResponse) throws Exception {
                        mUserRepository.saveUser(userResponse.getUser());
                        mViewModel.onLoginSuccess();
                    }
                }, new RequestError() {
                    @Override
                    public void onRequestError(BaseException error) {
                        mViewModel.onLoginError(error);
                    }
                });
        mCompositeDisposable.add(subscription);
    }

    @Override
    public void validateUserNameInput(String userName) {
        String messageUsername = mValidator.validateValueNonEmpty(userName);
        if (StringUtils.isBlank(messageUsername)) {
            messageUsername = mValidator.validateEmailFormat(userName);
            if (!StringUtils.isBlank(messageUsername)) {
                mViewModel.onInputUserNameError(messageUsername);
            }
        } else {
            mViewModel.onInputUserNameError(messageUsername);
        }
    }

    @Override
    public void validatePasswordInput(String password) {
        String messagePassword = mValidator.validateValueNonEmpty(password);
        if (!StringUtils.isBlank(messagePassword)) {
            mViewModel.onInputPasswordError(messagePassword);
        }
    }

    @Override
    public boolean validateDataInput(String userName, String password) {
        validateUserNameInput(userName);
        validatePasswordInput(password);
        try {
            return mValidator.validateAll(mViewModel);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "validateDataInput: ", e);
            return false;
        }
    }
}
