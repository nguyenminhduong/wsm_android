package com.framgia.wsm.screen.updateprofile;

import android.util.Log;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.utils.common.StringUtils;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import com.framgia.wsm.utils.validator.Validator;
import io.reactivex.disposables.CompositeDisposable;

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
    public void updateProfile(User user) {
        //TODO update profile
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
