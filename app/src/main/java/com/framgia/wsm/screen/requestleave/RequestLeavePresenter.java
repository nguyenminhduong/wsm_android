package com.framgia.wsm.screen.requestleave;

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
        switch (request.getLeaveType().getName()) {
            case RequestLeaveViewModel.LeaveType.IN_LATE_A:
            case RequestLeaveViewModel.LeaveType.IN_LATE_M:
                if (validateCheckinTime(request.getCheckinTime())
                        & validateCompensationFrom(request.getCompensation().getFromTime())
                        & validateCompensationTo(request.getCompensation().getToTime())
                        & validateReason(request.getReason())
                        & validateProjectName(request.getProject())) {
                    return true;
                }
                break;
            case RequestLeaveViewModel.LeaveType.LEAVE_EARLY_A:
            case RequestLeaveViewModel.LeaveType.LEAVE_EARLY_M:
                if (validateCheckoutTime(request.getCheckoutTime())
                        & validateCompensationFrom(request.getCompensation().getFromTime())
                        & validateCompensationTo(request.getCompensation().getToTime())
                        & validateReason(request.getReason())
                        & validateProjectName(request.getProject())) {
                    return true;
                }
                break;
            case RequestLeaveViewModel.LeaveType.IN_LATE_WOMAN_A:
            case RequestLeaveViewModel.LeaveType.IN_LATE_WOMAN_M:
                if (validateCheckinTime(request.getCheckinTime()) & validateProjectName(
                        request.getProject())) {
                    return true;
                }
                break;
            case RequestLeaveViewModel.LeaveType.LEAVE_EARLY_WOMAN_A:
            case RequestLeaveViewModel.LeaveType.LEAVE_EARLY_WOMAN_M:
                if (validateCheckoutTime(request.getCheckoutTime()) & validateProjectName(
                        request.getProject())) {
                    return true;
                }
                break;
            case RequestLeaveViewModel.LeaveType.FORGOT_CARD_ALL_DAY:
            case RequestLeaveViewModel.LeaveType.FORGOT_CHECK_ALL_DAY:
                if (validateCheckinTime(request.getCheckinTime()) & validateCheckoutTime(
                        request.getCheckoutTime()) & validateProjectName(request.getProject())) {
                    return true;
                }

                break;
            case RequestLeaveViewModel.LeaveType.FORGOT_CARD_IN:
            case RequestLeaveViewModel.LeaveType.FORGOT_TO_CHECK_IN:
                if (validateCheckinTime(request.getCheckinTime()) & validateProjectName(
                        request.getProject())) {
                    return true;
                }
                break;
            case RequestLeaveViewModel.LeaveType.FORGOT_CARD_OUT:
            case RequestLeaveViewModel.LeaveType.FORGOT_TO_CHECK_OUT:
                if (validateCheckoutTime(request.getCheckoutTime()) & validateProjectName(
                        request.getProject())) {
                    return true;
                }
                break;
            case RequestLeaveViewModel.LeaveType.LEAVE_OUT:
                if (validateCheckinTime(request.getCheckinTime())
                        & validateCheckoutTime(request.getCheckoutTime())
                        & validateCompensationFrom(request.getCompensation().getFromTime())
                        & validateCompensationTo(request.getCompensation().getToTime())
                        & validateReason(request.getReason())
                        & validateProjectName(request.getProject())) {
                    return true;
                }
                break;
        }
        return false;
    }

    private boolean validateProjectName(String projectName) {
        String errorMessage = mValidator.validateValueNonEmpty(projectName);
        if (StringUtils.isNotBlank(errorMessage)) {
            mViewModel.onInputProjectNameError(errorMessage);
            return false;
        }
        return true;
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
