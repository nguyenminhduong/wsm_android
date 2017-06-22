package com.framgia.wsm.screen.confirmrequestoff;

import android.app.Activity;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;
import android.view.View;
import com.android.databinding.library.baseAdapters.BR;
import com.framgia.wsm.data.model.RequestOff;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.widget.dialog.DialogManager;

/**
 * Exposes the data to be used in the ConfirmRequestOff screen.
 */

public class ConfirmRequestOffViewModel extends BaseObservable
        implements ConfirmRequestOffContract.ViewModel {

    private static final String TAG = "ConfirmRequestOffViewModel";

    private ConfirmRequestOffContract.Presenter mPresenter;
    private Navigator mNavigator;
    private DialogManager mDialogManager;
    private User mUser;
    private RequestOff mRequestOff;

    ConfirmRequestOffViewModel(ConfirmRequestOffContract.Presenter presenter, Navigator navigator,
            DialogManager dialogManager, RequestOff requestOff) {
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mDialogManager = dialogManager;
        mRequestOff = requestOff;
        mNavigator = navigator;
        mPresenter.getUser();
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Override
    public void onCreateFormRequestOffSuccess() {
        mNavigator.finishActivityWithResult(Activity.RESULT_OK);
    }

    @Override
    public void onCreateFormFormRequestOffError(BaseException exception) {
        mDialogManager.dialogError(exception);
    }

    @Override
    public void onGetUserSuccess(User user) {
        if (user == null) {
            return;
        }
        mUser = user;
        notifyPropertyChanged(BR.user);
    }

    @Override
    public void onGetUserError(BaseException e) {
        Log.e(TAG, "ConfirmRequestOffViewModel", e);
    }

    public void onCickArrowBack(View view) {
        mNavigator.finishActivity();
    }

    public void onCickSubmit(View view) {
        if (mRequestOff == null) {
            return;
        }
        mPresenter.createFormRequestOff(mRequestOff);
    }

    @Bindable
    public User getUser() {
        return mUser;
    }

    public RequestOff getRequestOff() {
        return mRequestOff;
    }

    @Bindable
    public boolean isVisiableProjectName() {
        return mRequestOff.getProject() != null;
    }

    @Bindable
    public boolean isVisiablePosition() {
        return mRequestOff.getPosition() != null;
    }

    @Bindable
    public boolean isVisiableLayoutCompanyPay() {
        return mRequestOff.getCompanyPay().getAnnualLeave() != null
                || mRequestOff.getCompanyPay().getLeaveForChildMarriage() != null
                || mRequestOff.getCompanyPay().getLeaveForMarriage() != null
                || mRequestOff.getCompanyPay().getFuneralLeave() != null;
    }

    @Bindable
    public boolean isVisiableLayoutInsurance() {
        return mRequestOff.getInsuranceCoverage().getLeaveForCareOfSickChild() != null
                || mRequestOff.getInsuranceCoverage().getSickLeave() != null
                || mRequestOff.getInsuranceCoverage().getMaternityLeave() != null
                || mRequestOff.getInsuranceCoverage().getPregnancyExaminationLeave() != null
                || mRequestOff.getInsuranceCoverage().getMiscarriageLeave() != null
                || mRequestOff.getInsuranceCoverage().getWifeLaborLeave() != null;
    }

    @Bindable
    public boolean isVisiableLayoutHaveSalary() {
        return mRequestOff.getStartDayHaveSalary() != null;
    }

    @Bindable
    public boolean isVisiableLayoutOffNoSalary() {
        return mRequestOff.getStartDayNoSalary() != null;
    }

    @Bindable
    public boolean isVisiableAnnualLeave() {
        return mRequestOff.getCompanyPay().getAnnualLeave() != null;
    }

    @Bindable
    public boolean isVisiableLeaveForChildMarriage() {
        return mRequestOff.getCompanyPay().getLeaveForChildMarriage() != null;
    }

    @Bindable
    public boolean isVisiableLeaveForMarriage() {
        return mRequestOff.getCompanyPay().getLeaveForMarriage() != null;
    }

    @Bindable
    public boolean isVisiableFuneralLeave() {
        return mRequestOff.getCompanyPay().getFuneralLeave() != null;
    }

    @Bindable
    public boolean isVisiableLeaveForCareOfSickChild() {
        return mRequestOff.getInsuranceCoverage().getLeaveForCareOfSickChild() != null;
    }

    @Bindable
    public boolean isVisiableSickLeave() {
        return mRequestOff.getInsuranceCoverage().getSickLeave() != null;
    }

    @Bindable
    public boolean isVisiableMaternityLeave() {
        return mRequestOff.getInsuranceCoverage().getMaternityLeave() != null;
    }

    @Bindable
    public boolean isVisiablePregnancyExaminationLeave() {
        return mRequestOff.getInsuranceCoverage().getPregnancyExaminationLeave() != null;
    }

    @Bindable
    public boolean isVisiableMiscarriageLeave() {
        return mRequestOff.getInsuranceCoverage().getMiscarriageLeave() != null;
    }

    @Bindable
    public boolean isVisiableWifeLaborLeave() {
        return mRequestOff.getInsuranceCoverage().getWifeLaborLeave() != null;
    }
}
