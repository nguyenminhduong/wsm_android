package com.framgia.wsm.screen.requestleave;

import com.framgia.wsm.data.model.LeaveRequest;
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
    public boolean validateDataInput(LeaveRequest request) {
        switch (request.getLeaveTypeId()) {
            case RequestLeaveViewModel.LeaveTypeId.IN_LATE_A:
            case RequestLeaveViewModel.LeaveTypeId.IN_LATE_M:
                if (validateCheckinTime(request.getCheckInTime()) & validateCompensationFrom(
                        request.getCompensationRequest().getFromTime()) & validateCompensationTo(
                        request.getCompensationRequest().getToTime()) & validateReason(
                        request.getReason())) {
                    return true;
                }
                break;
            case RequestLeaveViewModel.LeaveTypeId.LEAVE_EARLY_A:
            case RequestLeaveViewModel.LeaveTypeId.LEAVE_EARLY_M:
                if (validateCheckoutTime(request.getCheckOutTime()) & validateCompensationFrom(
                        request.getCompensationRequest().getFromTime()) & validateCompensationTo(
                        request.getCompensationRequest().getToTime()) & validateReason(
                        request.getReason())) {
                    return true;
                }
                break;
            case RequestLeaveViewModel.LeaveTypeId.IN_LATE_WOMAN_A:
            case RequestLeaveViewModel.LeaveTypeId.IN_LATE_WOMAN_M:
                if (validateCheckinTime(request.getCheckInTime())) {
                    return true;
                }
                break;
            case RequestLeaveViewModel.LeaveTypeId.LEAVE_EARLY_WOMAN_A:
            case RequestLeaveViewModel.LeaveTypeId.LEAVE_EARLY_WOMAN_M:
                if (validateCheckoutTime(request.getCheckOutTime())) {
                    return true;
                }
                break;
            case RequestLeaveViewModel.LeaveTypeId.FORGOT_CARD_ALL_DAY:
            case RequestLeaveViewModel.LeaveTypeId.FORGOT_CHECK_ALL_DAY:
                if (validateCheckinTime(request.getCheckInTime()) & validateCheckoutTime(
                        request.getCheckOutTime())) {
                    return true;
                }

                break;
            case RequestLeaveViewModel.LeaveTypeId.FORGOT_CARD_IN:
            case RequestLeaveViewModel.LeaveTypeId.FORGOT_TO_CHECK_IN:
                if (validateCheckinTime(request.getCheckInTime())) {
                    return true;
                }
                break;
            case RequestLeaveViewModel.LeaveTypeId.FORGOT_CARD_OUT:
            case RequestLeaveViewModel.LeaveTypeId.FORGOT_TO_CHECK_OUT:
                if (validateCheckoutTime(request.getCheckOutTime())) {
                    return true;
                }
                break;
            case RequestLeaveViewModel.LeaveTypeId.LEAVE_OUT:
                if (validateCheckinTime(request.getCheckInTime()) & validateCheckoutTime(
                        request.getCheckOutTime()) & validateCompensationFrom(
                        request.getCompensationRequest().getFromTime()) & validateCompensationTo(
                        request.getCompensationRequest().getToTime()) & validateReason(
                        request.getReason())) {
                    return true;
                }
                break;
        }
        return false;
    }

    private boolean validateReason(String reason) {
        String errorMessage = mValidator.validateValueNonEmpty(reason);
        if (StringUtils.isNotBlank(errorMessage)) {
            mViewModel.onInputReasonError(errorMessage);
            return false;
        }
        return true;
    }

    private boolean validateCheckinTime(String checkinTime) {
        String errorMessage = mValidator.validateValueNonEmpty(checkinTime);
        if (StringUtils.isNotBlank(errorMessage)) {
            mViewModel.onInputCheckinTimeError(errorMessage);
            return false;
        }
        return true;
    }

    private boolean validateCheckoutTime(String checkoutTime) {
        String errorMessage = mValidator.validateValueNonEmpty(checkoutTime);
        if (StringUtils.isNotBlank(errorMessage)) {
            mViewModel.onInputCheckoutTimeError(errorMessage);
            return false;
        }
        return true;
    }

    private boolean validateCompensationFrom(String compensationFrom) {
        String errorMessage = mValidator.validateValueNonEmpty(compensationFrom);
        if (StringUtils.isNotBlank(errorMessage)) {
            mViewModel.onInputCompensationFromError(errorMessage);
            return false;
        }
        return true;
    }

    private boolean validateCompensationTo(String compensationTo) {
        String errorMessage = mValidator.validateValueNonEmpty(compensationTo);
        if (StringUtils.isNotBlank(errorMessage)) {
            mViewModel.onInputCompensationToError(errorMessage);
            return false;
        }
        return true;
    }
}
