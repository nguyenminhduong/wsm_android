package com.framgia.wsm.screen.requestoff;

import android.util.Log;
import com.framgia.wsm.data.model.RequestOff;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.error.RequestError;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.common.StringUtils;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import com.framgia.wsm.utils.validator.Validator;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Listens to user actions from the UI ({@link RequestOffActivity}), retrieves the data and updates
 * the UI as required.
 */
final class RequestOffPresenter implements RequestOffContract.Presenter {
    private static final String TAG = RequestOffPresenter.class.getName();

    private RequestOffContract.ViewModel mViewModel;
    private UserRepository mUserRepository;
    private BaseSchedulerProvider mSchedulerProvider;
    private CompositeDisposable mCompositeDisposable;
    private Validator mValidator;

    RequestOffPresenter(UserRepository userRepository, BaseSchedulerProvider baseSchedulerProvider,
            Validator validator) {
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
    public void setViewModel(RequestOffContract.ViewModel viewModel) {
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
    public boolean validateData(RequestOff requestOff) {
        String errorMessage = mValidator.validateValueNonEmpty(requestOff.getReason());
        if (StringUtils.isNotBlank(errorMessage)) {
            mViewModel.onInputReasonError(errorMessage);
        }
        try {
            return mValidator.validateAll(requestOff);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "validateDataInput: ", e);
            return false;
        }
    }

    @Override
    public boolean validateAllNumberDayHaveSalary(RequestOff requestOff) {
        return validateAnnualLeave(requestOff.getCompanyPay().getAnnualLeave())
                && validateLeaveForMarriage(requestOff.getCompanyPay().getLeaveForMarriage())
                && validateLeaveForChildMarriage(
                requestOff.getCompanyPay().getLeaveForChildMarriage())
                && validateFuneralLeave(requestOff.getCompanyPay().getFuneralLeave())
                && validateLeaveForCareOfSickChild(
                requestOff.getInsuranceCoverage().getLeaveForCareOfSickChild())
                && validatePregnancyExaminationLeave(
                requestOff.getInsuranceCoverage().getPregnancyExaminationLeave())
                && validateSickLeave(requestOff.getInsuranceCoverage().getSickLeave())
                && validateMiscarriageLeave(requestOff.getInsuranceCoverage().getMiscarriageLeave())
                && validateMaternityLeave(requestOff.getInsuranceCoverage().getMaternityLeave())
                && validateWifeLaborLeave(requestOff.getInsuranceCoverage().getWifeLaborLeave());
    }

    @Override
    public void validateNumberDayHaveSalary(RequestOff requestOff) {
        if (!validateAnnualLeave(requestOff.getCompanyPay().getAnnualLeave())) {
            mViewModel.onInputNumberDayHaveSalaryError(RequestOffViewModel.TypeOfDays.ANNUAL_LEAVE);
        }
        if (!validateLeaveForMarriage(requestOff.getCompanyPay().getLeaveForMarriage())) {
            mViewModel.onInputNumberDayHaveSalaryError(
                    RequestOffViewModel.TypeOfDays.LEAVE_FOR_MARRIAGE);
        }
        if (!validateLeaveForChildMarriage(requestOff.getCompanyPay().getLeaveForChildMarriage())) {
            mViewModel.onInputNumberDayHaveSalaryError(
                    RequestOffViewModel.TypeOfDays.LEAVE_FOR_CHILD_MARRIAGE);
        }
        if (!validateFuneralLeave(requestOff.getCompanyPay().getFuneralLeave())) {
            mViewModel.onInputNumberDayHaveSalaryError(
                    RequestOffViewModel.TypeOfDays.FUNERAL_LEAVE);
        }
        if (!validateLeaveForCareOfSickChild(
                requestOff.getInsuranceCoverage().getLeaveForCareOfSickChild())) {
            mViewModel.onInputNumberDayHaveSalaryError(
                    RequestOffViewModel.TypeOfDays.LEAVE_FOR_CARE_OF_SICK_CHILD);
        }
        if (!validatePregnancyExaminationLeave(
                requestOff.getInsuranceCoverage().getPregnancyExaminationLeave())) {
            mViewModel.onInputNumberDayHaveSalaryError(
                    RequestOffViewModel.TypeOfDays.PREGNANCY_EXAMINATON);
        }
        if (!validateSickLeave(requestOff.getInsuranceCoverage().getSickLeave())) {
            mViewModel.onInputNumberDayHaveSalaryError(RequestOffViewModel.TypeOfDays.SICK_LEAVE);
        }
        if (!validateMiscarriageLeave(requestOff.getInsuranceCoverage().getMiscarriageLeave())) {
            mViewModel.onInputNumberDayHaveSalaryError(
                    RequestOffViewModel.TypeOfDays.MISCARRIAGE_LEAVE);
        }
        if (!validateMaternityLeave(requestOff.getInsuranceCoverage().getMaternityLeave())) {
            mViewModel.onInputNumberDayHaveSalaryError(
                    RequestOffViewModel.TypeOfDays.MATERNTY_LEAVE);
        }
        if (!validateWifeLaborLeave(requestOff.getInsuranceCoverage().getWifeLaborLeave())) {
            mViewModel.onInputNumberDayHaveSalaryError(
                    RequestOffViewModel.TypeOfDays.WIFE_LABOR_LEAVE);
        }
    }

    private boolean validateAnnualLeave(String input) {
        return StringUtils.convertStringToDouble(input) <= Constant.NumberConst.FOURTEEN_DOT_FIVE;
    }

    private boolean validateLeaveForMarriage(String input) {
        return StringUtils.convertStringToDouble(input) <= Constant.NumberConst.THREE;
    }

    private boolean validateLeaveForChildMarriage(String input) {
        return StringUtils.convertStringToDouble(input) <= Constant.NumberConst.ONE;
    }

    private boolean validateFuneralLeave(String input) {
        return StringUtils.convertStringToDouble(input) <= Constant.NumberConst.THREE;
    }

    private boolean validateLeaveForCareOfSickChild(String input) {
        return StringUtils.convertStringToDouble(input) <= Constant.NumberConst.TWENTY;
    }

    private boolean validatePregnancyExaminationLeave(String input) {
        return StringUtils.convertStringToDouble(input) <= Constant.NumberConst.FIVE;
    }

    private boolean validateSickLeave(String input) {
        return StringUtils.convertStringToDouble(input) <= Constant.NumberConst.SIXTY;
    }

    private boolean validateMiscarriageLeave(String input) {
        return StringUtils.convertStringToDouble(input) <= Constant.NumberConst.FIFTY;
    }

    private boolean validateMaternityLeave(String input) {
        return StringUtils.convertStringToDouble(input) <= Constant.NumberConst.ONE_HUNDRED_EIGHTY;
    }

    private boolean validateWifeLaborLeave(String input) {
        return StringUtils.convertStringToDouble(input) <= Constant.NumberConst.FOURTEEN;
    }
}
