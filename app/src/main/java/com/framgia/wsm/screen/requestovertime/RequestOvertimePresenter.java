package com.framgia.wsm.screen.requestovertime;

import android.util.Log;
import com.framgia.wsm.data.model.Request;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.error.RequestError;
import com.framgia.wsm.utils.common.StringUtils;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import com.framgia.wsm.utils.validator.Validator;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Listens to user actions from the UI ({@link RequestOvertimeActivity}), retrieves the data and
 * updates
 * the UI as required.
 */
final class RequestOvertimePresenter implements RequestOvertimeContract.Presenter {
    private static final String TAG = RequestOvertimePresenter.class.getName();

    private RequestOvertimeContract.ViewModel mViewModel;
    private UserRepository mUserRepository;
    private BaseSchedulerProvider mSchedulerProvider;
    private CompositeDisposable mCompositeDisposable;
    private Validator mValidator;

    RequestOvertimePresenter(UserRepository userRepository,
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
    public void setViewModel(RequestOvertimeContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void getUser() {
        Disposable disposable = mUserRepository.getUser()
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(@NonNull User user) throws Exception {
                        mViewModel.onGetUserSuccess(user);
                    }
                }, new RequestError() {
                    @Override
                    public void onRequestError(BaseException error) {
                        mViewModel.onGetUserError(error);
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public boolean validateDataInput(Request request) {
        validateProjectName(request.getProject());
        validateReason(request.getReason());
        validateFromTime(request.getFromTime());
        validateToTime(request.getToTime());
        try {
            return mValidator.validateAll(mViewModel);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "validateDataInput: ", e);
            return false;
        }
    }

    private void validateProjectName(String projectName) {
        String errorMessage = mValidator.validateValueNonEmpty(projectName);
        if (!StringUtils.isBlank(errorMessage)) {
            mViewModel.onInputProjectNameError(errorMessage);
        }
    }

    private void validateReason(String reason) {
        String errorMessage = mValidator.validateValueNonEmpty(reason);
        if (!StringUtils.isBlank(errorMessage)) {
            mViewModel.onInputReasonError(errorMessage);
        }
    }

    private void validateFromTime(String fromTime) {
        String errorMessage = mValidator.validateValueNonEmpty(fromTime);
        if (!StringUtils.isBlank(errorMessage)) {
            mViewModel.onInputFromTimeError(errorMessage);
        }
    }

    private void validateToTime(String toTime) {
        String errorMessage = mValidator.validateValueNonEmpty(toTime);
        if (!StringUtils.isBlank(errorMessage)) {
            mViewModel.onInputToTimeError(errorMessage);
        }
    }
}
