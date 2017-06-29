package com.framgia.wsm.screen.updateprofile;

import android.util.Log;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.error.RequestError;
import com.framgia.wsm.data.source.remote.api.request.UpdateProfileRequest;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.response.UserProfileResponse;
import com.framgia.wsm.utils.common.StringUtils;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import com.framgia.wsm.utils.validator.Validator;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Listens to user actions from the UI ({@link UpdateProfileActivity}), retrieves the data and
 * updates
 * the UI as required.
 */
final class UpdateProfilePresenter implements UpdateProfileContract.Presenter {
    private static final String TAG = UpdateProfilePresenter.class.getName();

    private UpdateProfileContract.ViewModel mViewModel;
    private UserRepository mUserRepository;
    private BaseSchedulerProvider mSchedulerProvider;
    private CompositeDisposable mCompositeDisposable;
    private Validator mValidator;

    UpdateProfilePresenter(UserRepository userRepository,
            BaseSchedulerProvider baseSchedulerProvider, Validator validator) {
        mUserRepository = userRepository;
        mSchedulerProvider = baseSchedulerProvider;
        mCompositeDisposable = new CompositeDisposable();
        mValidator = validator;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
        mCompositeDisposable.clear();
    }

    @Override
    public void setViewModel(UpdateProfileContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public boolean validateData(User user) {
        validateEmployeeName(user.getName());
        validateBirthday(user.getBirthday());
        try {
            return mValidator.validateAll(user);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "validateDataInput: ", e);
            return false;
        }
    }

    @Override
    public void updateProfile(UpdateProfileRequest updateProfileRequest) {
        Disposable disposable = mUserRepository.updateProfile(updateProfileRequest)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Consumer<BaseResponse<UserProfileResponse>>() {
                    @Override
                    public void accept(@NonNull BaseResponse<UserProfileResponse> userBaseResponse)
                            throws Exception {
                        mViewModel.onUpdateProfileSuccess(userBaseResponse.getData().getUser());
                    }
                }, new RequestError() {
                    @Override
                    public void onRequestError(BaseException error) {
                        mViewModel.onUpdateProfileError(error);
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    private void validateEmployeeName(String employeeName) {
        String errorMessage = mValidator.validateValueNonEmpty(employeeName);
        if (StringUtils.isNotBlank(errorMessage)) {
            mViewModel.onInputEmployeeNameError(errorMessage);
        }
    }

    private void validateBirthday(String birthday) {
        String errorMessage = mValidator.validateValueNonEmpty(birthday);
        if (StringUtils.isNotBlank(errorMessage)) {
            mViewModel.onInputBirthdayError(errorMessage);
        }
    }
}
