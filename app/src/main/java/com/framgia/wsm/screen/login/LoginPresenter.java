package com.framgia.wsm.screen.login;

import android.util.Log;
import com.framgia.wsm.data.model.LeaveType;
import com.framgia.wsm.data.model.OffType;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.TokenRepository;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.error.RequestError;
import com.framgia.wsm.data.source.remote.api.response.SignInDataResponse;
import com.framgia.wsm.utils.common.DateTimeUtils;
import com.framgia.wsm.utils.common.StringUtils;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import com.framgia.wsm.utils.validator.Validator;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import java.util.List;

/**
 * Listens to user actions from the UI ({@link LoginActivity}), retrieves the data and updates
 * the UI as required.
 */
final class LoginPresenter implements LoginContract.Presenter {

    private static final String TAG = LoginPresenter.class.getName();

    private static final int OVER_YEAR_4 = 4;
    private static final int OVER_YEAR_8 = 8;
    private static final int ID_1 = 1;
    private static final int ID_2 = 2;
    private static final int ID_3 = 3;
    private static final int COUNT_DAY_12 = 12;
    private static final int COUNT_DAY_13 = 13;
    private static final int COUNT_DAY_14 = 14;
    private static final String COMPANY = "company";
    private static final String ANNUAL = "Annual";

    private LoginContract.ViewModel mViewModel;
    private UserRepository mUserRepository;
    private TokenRepository mTokenRepository;
    private Validator mValidator;
    private CompositeDisposable mCompositeDisposable;
    private BaseSchedulerProvider mSchedulerProvider;
    private User mUser;
    private boolean mIsManage;

    LoginPresenter(UserRepository userRepository, TokenRepository tokenRepository,
            Validator validator, BaseSchedulerProvider schedulerProvider) {
        mUserRepository = userRepository;
        mTokenRepository = tokenRepository;
        mValidator = validator;
        mCompositeDisposable = new CompositeDisposable();
        mSchedulerProvider = schedulerProvider;
        mUser = new User();
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
    public void login(final String userName, String passWord, String deviceId) {
        validateUserNameInput(userName);
        mUserRepository.login(userName, passWord, deviceId)
                .subscribeOn(mSchedulerProvider.io())
                .flatMap(new Function<SignInDataResponse, ObservableSource<User>>() {
                    @Override
                    public ObservableSource<User> apply(
                            @NonNull SignInDataResponse signInDataResponse) throws Exception {
                        mTokenRepository.saveToken(signInDataResponse.getAuthenToken());
                        mIsManage = signInDataResponse.isManager();
                        return mUserRepository.getUserProfile(signInDataResponse.getUser().getId());
                    }
                })
                .flatMap(new Function<User, ObservableSource<List<OffType>>>() {
                    @Override
                    public ObservableSource<List<OffType>> apply(@NonNull User user)
                            throws Exception {
                        mUser = user;
                        return mUserRepository.getListOffType();
                    }
                })
                .flatMap(new Function<List<OffType>, ObservableSource<OffType>>() {
                    @Override
                    public ObservableSource<OffType> apply(List<OffType> offTypes)
                            throws Exception {
                        return Observable.fromIterable(offTypes);
                    }
                })
                .filter(new Predicate<OffType>() {
                    @Override
                    public boolean test(OffType offType) throws Exception {
                        return offType.getPayType().equals(COMPANY);
                    }
                })
                .toList()
                .toObservable()
                .flatMap(new Function<List<OffType>, ObservableSource<List<OffType>>>() {
                    @Override
                    public ObservableSource<List<OffType>> apply(List<OffType> offTypes)
                            throws Exception {
                        if (DateTimeUtils.getDayOfYear(mUser.getContractDate()) >= OVER_YEAR_8) {
                            offTypes.add(new OffType(ID_3, ANNUAL, COMPANY, COUNT_DAY_14));
                        } else if (DateTimeUtils.getDayOfYear(mUser.getContractDate())
                                >= OVER_YEAR_4) {
                            offTypes.add(new OffType(ID_2, ANNUAL, COMPANY, COUNT_DAY_13));
                        } else if (DateTimeUtils.getDayOfYear(mUser.getContractDate())
                                < OVER_YEAR_4) {
                            offTypes.add(new OffType(ID_1, ANNUAL, COMPANY, COUNT_DAY_12));
                        }
                        mUser.setTypesCompany(offTypes);
                        return mUserRepository.getListOffType();
                    }
                })
                .flatMap(new Function<List<OffType>, ObservableSource<OffType>>() {
                    @Override
                    public ObservableSource<OffType> apply(List<OffType> offTypes)
                            throws Exception {
                        return Observable.fromIterable(offTypes);
                    }
                })
                .filter(new Predicate<OffType>() {
                    @Override
                    public boolean test(OffType offType) throws Exception {
                        return offType.getPayType().equals("insurance");
                    }
                })
                .toList()
                .toObservable()
                .flatMap(new Function<List<OffType>, ObservableSource<List<LeaveType>>>() {
                    @Override
                    public ObservableSource<List<LeaveType>> apply(List<OffType> offTypes)
                            throws Exception {
                        mUser.setTypesInsurance(offTypes);
                        return mUserRepository.getListLeaveType();
                    }
                })
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Consumer<List<LeaveType>>() {
                    @Override
                    public void accept(List<LeaveType> leaveTypes) throws Exception {
                        mUser.setManage(mIsManage);
                        mUser.setLeaveTypes(leaveTypes);
                        mUserRepository.saveUser(mUser);
                        mViewModel.onLoginSuccess();
                    }
                }, new RequestError() {
                    @Override
                    public void onRequestError(BaseException error) {
                        Log.e(TAG, "onRequestError: ", error);
                        mViewModel.onLoginError(error);
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
