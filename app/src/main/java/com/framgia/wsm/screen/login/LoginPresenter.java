package com.framgia.wsm.screen.login;

import android.os.Bundle;
import android.util.Log;
import com.framgia.wsm.data.model.LeaveType;
import com.framgia.wsm.data.model.OffType;
import com.framgia.wsm.data.model.OffTypeDay;
import com.framgia.wsm.data.model.Setting;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.TokenRepository;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.error.RequestError;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.response.SignInDataResponse;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.common.StringUtils;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import com.framgia.wsm.utils.validator.Validator;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Listens to user actions from the UI ({@link LoginActivity}), retrieves the data and updates
 * the UI as required.
 */
final class LoginPresenter implements LoginContract.Presenter {

    private static final String TAG = LoginPresenter.class.getName();

    private static final int ID_1 = 1;
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
    private boolean mIsUnauthorized;
    private List<OffType> offTypesCompany;

    LoginPresenter(Bundle bundle, UserRepository userRepository, TokenRepository tokenRepository,
            Validator validator, BaseSchedulerProvider schedulerProvider) {
        mIsUnauthorized = bundle != null && bundle.getBoolean(Constant.EXTRA_UNAUTHORIZED);
        mUserRepository = userRepository;
        mTokenRepository = tokenRepository;
        mValidator = validator;
        mCompositeDisposable = new CompositeDisposable();
        mSchedulerProvider = schedulerProvider;
        mUser = new User();
        offTypesCompany = new ArrayList<>();
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
                        offTypesCompany = offTypes;
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
                .flatMap(new Function<List<OffType>, Single<BaseResponse<Setting>>>() {

                    @Override
                    public Single<BaseResponse<Setting>> apply(@NonNull List<OffType> offTypes)
                            throws Exception {
                        mUser.setTypesInsurance(offTypes);
                        return mUserRepository.getSetting();
                    }
                })
                .toObservable()
                .flatMap(new Function<BaseResponse<Setting>, ObservableSource<List<LeaveType>>>() {
                    @Override
                    public ObservableSource<List<LeaveType>> apply(
                            @NonNull BaseResponse<Setting> settingBaseResponse) throws Exception {
                        if (settingBaseResponse.getData() != null) {
                            mUser.setSetting(settingBaseResponse.getData());
                        }
                        return mUserRepository.getListLeaveType();
                    }
                })
                .flatMap(
                        new Function<List<LeaveType>, ObservableSource<BaseResponse<OffTypeDay>>>
                                () {
                            @Override
                            public ObservableSource<BaseResponse<OffTypeDay>> apply(
                                    @NonNull List<LeaveType> leaveTypes) throws Exception {
                                mUser.setLeaveTypes(leaveTypes);
                                return mUserRepository.getRemainingDayOff();
                            }
                        })
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Consumer<BaseResponse<OffTypeDay>>() {
                    @Override
                    public void accept(@NonNull BaseResponse<OffTypeDay> offTypeDayBaseResponse)
                            throws Exception {
                        if (offTypeDayBaseResponse.getData().getRemainingDayOff() != null) {
                            offTypesCompany.add(new OffType(ID_1, ANNUAL, COMPANY,
                                    offTypeDayBaseResponse.getData().getRemainingDayOff()));
                        } else {
                            offTypesCompany.add(new OffType(ID_1, ANNUAL, COMPANY,
                                    Float.parseFloat(Constant.DEFAULT_DOUBLE_VALUE)));
                        }
                        mUser.setTypesCompany(offTypesCompany);
                        mUser.setManage(mIsManage);
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
        if (mIsUnauthorized) {
            mUserRepository.clearData();
            return;
        }
        User user = mUserRepository.getUserCheckLogin();
        if (user != null && user.getLeaveTypes() != null) {
            mViewModel.onUserLoggedIn();
            return;
        }
        mUserRepository.clearData();
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
        if (StringUtils.isBlank(messagePassword)) {
            messagePassword = mValidator.validateValueRangeMin6(password);
            if (!StringUtils.isBlank(messagePassword)) {
                mViewModel.onInputPasswordError(messagePassword);
            } else {
                mViewModel.onInputPasswordError("");
            }
        } else {
            mViewModel.onInputPasswordError(messagePassword);
        }
    }
}
