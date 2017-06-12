package com.framgia.wsm.screen.requestleave;

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
 * Listens to user actions from the UI ({@link RequestLeaveActivity}), retrieves the data and
 * updates
 * the UI as required.
 */
final class RequestLeavePresenter implements RequestLeaveContract.Presenter {
    private static final String TAG = RequestLeavePresenter.class.getName();

    private CompositeDisposable mCompositeDisposable;
    private BaseSchedulerProvider mSchedulerProvider;
    private UserRepository mUserRepository;
    private Validator mValidator;

    private RequestLeaveContract.ViewModel mViewModel;

    RequestLeavePresenter(UserRepository userRepository,
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
    }

    @Override
    public void setViewModel(RequestLeaveContract.ViewModel viewModel) {
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
        switch (request.getPositionType()) {
            case RequestLeaveViewModel.PositionType.IN_LATE_A:
            case RequestLeaveViewModel.PositionType.IN_LATE_M:
                validateCheckinTime(request.getCheckinTime());
                validateCompensationFrom(request.getCompensation().getFromTime());
                validateCompensationTo(request.getCompensation().getToTime());
                break;
            case RequestLeaveViewModel.PositionType.LEAVE_EARLY_A:
            case RequestLeaveViewModel.PositionType.LEAVE_EARLY_M:
                validateCheckoutTime(request.getCheckoutTime());
                validateCompensationFrom(request.getCompensation().getFromTime());
                validateCompensationTo(request.getCompensation().getToTime());
                break;
            case RequestLeaveViewModel.PositionType.IN_LATE_WOMAN_A:
            case RequestLeaveViewModel.PositionType.IN_LATE_WOMAN_M:
                validateCheckinTime(request.getCheckinTime());
                break;
            case RequestLeaveViewModel.PositionType.LEAVE_EARLY_WOMAN_A:
            case RequestLeaveViewModel.PositionType.LEAVE_EARLY_WOMAN_M:
                validateCheckoutTime(request.getCheckoutTime());
                break;
            case RequestLeaveViewModel.PositionType.FORGOT_CARD_ALL_DAY:
            case RequestLeaveViewModel.PositionType.FORGOT_TO_CHECK_IN_OUT_ALL_DAY:
                validateCheckinTime(request.getCheckinTime());
                validateCheckoutTime(request.getCheckoutTime());
                break;
            case RequestLeaveViewModel.PositionType.FORGOT_CARD_IN:
            case RequestLeaveViewModel.PositionType.FORGOT_TO_CHECK_IN:
                validateCheckinTime(request.getCheckinTime());
                break;
            case RequestLeaveViewModel.PositionType.FORGOT_CARD_OUT:
            case RequestLeaveViewModel.PositionType.FORGOT_TO_CHECK_OUT:
                validateCheckoutTime(request.getCheckoutTime());
                break;
            case RequestLeaveViewModel.PositionType.LEAVE_OUT:
                validateCheckinTime(request.getCheckinTime());
                validateCheckoutTime(request.getCheckoutTime());
                validateCompensationFrom(request.getCompensation().getFromTime());
                validateCompensationTo(request.getCompensation().getToTime());
                break;
        }
        try {
            return mValidator.validateAll(mViewModel);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "validateDataInput: ", e);
            return false;
        }
    }

    private void validateProjectName(String projectName) {
        String errorMessage = mValidator.validateValueNonEmpty(projectName);
        if (StringUtils.isNotBlank(errorMessage)) {
            mViewModel.onInputProjectNameError(errorMessage);
        }
    }

    private void validateReason(String reason) {
        String errorMessage = mValidator.validateValueNonEmpty(reason);
        if (StringUtils.isNotBlank(errorMessage)) {
            mViewModel.onInputReasonError(errorMessage);
        }
    }

    private void validateCheckinTime(String checkinTime) {
        String errorMessage = mValidator.validateValueNonEmpty(checkinTime);
        if (StringUtils.isNotBlank(errorMessage)) {
            mViewModel.onInputCheckinTimeError(errorMessage);
        }
    }

    private void validateCheckoutTime(String checkoutTime) {
        String errorMessage = mValidator.validateValueNonEmpty(checkoutTime);
        if (StringUtils.isNotBlank(errorMessage)) {
            mViewModel.onInputCheckoutTimeError(errorMessage);
        }
    }

    private void validateCompensationFrom(String compensationFrom) {
        String errorMessage = mValidator.validateValueNonEmpty(compensationFrom);
        if (StringUtils.isNotBlank(errorMessage)) {
            mViewModel.onInputCompensationFromError(errorMessage);
        }
    }

    private void validateCompensationTo(String compensationTo) {
        String errorMessage = mValidator.validateValueNonEmpty(compensationTo);
        if (StringUtils.isNotBlank(errorMessage)) {
            mViewModel.onInputCompensationToError(errorMessage);
        }
    }
}
