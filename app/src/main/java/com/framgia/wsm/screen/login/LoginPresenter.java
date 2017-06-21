package com.framgia.wsm.screen.login;

import android.util.Log;
import com.framgia.wsm.data.source.TokenRepository;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.error.RequestError;
import com.framgia.wsm.data.source.remote.api.response.SignInDataResponse;
import com.framgia.wsm.data.source.remote.api.response.UserProfileResponse;
import com.framgia.wsm.utils.common.StringUtils;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import com.framgia.wsm.utils.validator.Validator;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Listens to user actions from the UI ({@link LoginActivity}), retrieves the data and updates
 * the UI as required.
 */
final class LoginPresenter implements LoginContract.Presenter {
    private static final String TAG = LoginPresenter.class.getName();

    private LoginContract.ViewModel mViewModel;
    private UserRepository mUserRepository;
    private TokenRepository mTokenRepository;
    private Validator mValidator;
    private CompositeDisposable mCompositeDisposable;
    private BaseSchedulerProvider mSchedulerProvider;

    LoginPresenter(UserRepository userRepository, TokenRepository tokenRepository,
            Validator validator, BaseSchedulerProvider schedulerProvider) {
        mUserRepository = userRepository;
        mTokenRepository = tokenRepository;
        mValidator = validator;
        mCompositeDisposable = new CompositeDisposable();
        mSchedulerProvider = schedulerProvider;
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
    public void login(final String userName, String passWord) {
        validateUserNameInput(userName);
        mUserRepository.login(userName, passWord)
                .subscribeOn(mSchedulerProvider.io())
                .flatMap(new Function<SignInDataResponse, ObservableSource<UserProfileResponse>>() {
                    @Override
                    public ObservableSource<UserProfileResponse> apply(
                            @NonNull SignInDataResponse signInDataResponse) throws Exception {
                        mTokenRepository.saveToken(signInDataResponse.getAuthenToken());
                        return mUserRepository.getUserProfile(signInDataResponse.getUser().getId());
                    }
                })
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Consumer<UserProfileResponse>() {
                    @Override
                    public void accept(@NonNull UserProfileResponse userProfileResponse)
                            throws Exception {
                        mUserRepository.saveUser(userProfileResponse.getUser());
                        mViewModel.onLoginSuccess();
                    }
                }, new RequestError() {
                    @Override
                    public void onRequestError(BaseException e) {
                        mViewModel.onLoginError(e);
                    }
                });
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

    @Override
    public void checkUserLogin() {
        if (!mTokenRepository.getToken().equals("")) {
            mViewModel.onUserLoggedIn();
        }
    }

    @Override
    public void validateUserNameInput(String userName) {
        String messageUsername = mValidator.validateValueNonEmpty(userName);
        if (StringUtils.isBlank(messageUsername)) {
            messageUsername = mValidator.validateEmailFormat(userName);
            if (!StringUtils.isBlank(messageUsername)) {
                mViewModel.onInputUserNameError(messageUsername);
            } else {
                mViewModel.onInputUserNameError("");
            }
        } else {
            mViewModel.onInputUserNameError(messageUsername);
        }
    }

    private void validatePasswordInput(String password) {
        String messagePassword = mValidator.validateValueNonEmpty(password);
        if (!StringUtils.isBlank(messagePassword)) {
            mViewModel.onInputPasswordError(messagePassword);
        }
    }
}
