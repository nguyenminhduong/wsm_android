package com.framgia.wsm.screen.confirmrequestoff;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import com.android.databinding.library.baseAdapters.BR;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.RequestOff;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.requestoff.RequestOffActivity;
import com.framgia.wsm.utils.ActionType;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.StatusCode;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.widget.dialog.DialogManager;
import com.fstyle.library.DialogAction;
import com.fstyle.library.MaterialDialog;

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
    private int mActionType;
    private Context mContext;

    ConfirmRequestOffViewModel(Context context, ConfirmRequestOffContract.Presenter presenter,
            Navigator navigator, DialogManager dialogManager, RequestOff requestOff,
            int actionType) {
        mContext = context;
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mDialogManager = dialogManager;
        mRequestOff = requestOff;
        mNavigator = navigator;
        mPresenter.getUser();
        mActionType = actionType;
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

    @Override
    public void onDeleteFormRequestOffSuccess() {
        mNavigator.finishActivityWithResult(Activity.RESULT_OK);
    }

    @Override
    public void onDeleteFormRequestOffError(BaseException exception) {
        mDialogManager.dialogError(exception);
    }

    @Override
    public void onEditFormRequestOffSuccess(RequestOff requestOff) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.EXTRA_REQUEST_OFF, mRequestOff);
        bundle.putInt(Constant.EXTRA_ACTION_TYPE, ActionType.ACTION_DETAIL);
        mNavigator.startActivityAtRoot(ConfirmRequestOffActivity.class, bundle);
    }

    @Override
    public void onEditFormRequestOffError(BaseException exception) {
        mDialogManager.dialogError(exception, new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog,
                    @NonNull DialogAction dialogAction) {
                mNavigator.finishActivity();
            }
        });
    }

    @Bindable
    public User getUser() {
        return mUser;
    }

    @Bindable
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

    public boolean isDetail() {
        return mActionType == ActionType.ACTION_DETAIL;
    }

    public boolean isAcceptStatus() {
        return mRequestOff.getStatus() == StatusCode.ACCEPT_CODE;
    }

    public boolean isPendingStatus() {
        return mRequestOff.getStatus() == StatusCode.PENDING_CODE;
    }

    public boolean isRejectStatus() {
        return mRequestOff.getStatus() == StatusCode.REJECT_CODE;
    }

    public String getTitleToolbar() {
        if (mActionType == ActionType.ACTION_CREATE) {
            return mContext.getString(R.string.confirm_request_off);
        }
        return mContext.getString(R.string.detail_request_off);
    }

    public void onCickArrowBack(View view) {
        mNavigator.finishActivity();
    }

    public void onClickSubmit(View view) {
        if (mRequestOff == null) {
            return;
        }
        if (mActionType == ActionType.ACTION_CREATE) {
            mPresenter.createFormRequestOff(mRequestOff);
            return;
        }
        mPresenter.editFormRequestOff(mRequestOff);
    }

    public void onClickDelete(View view) {
        if (mRequestOff == null) {
            return;
        }
        mPresenter.deleteFormRequestOff(mRequestOff.getId());
    }

    public void onClickEdit(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.EXTRA_ACTION_TYPE, ActionType.ACTION_EDIT);
        bundle.putParcelable(Constant.EXTRA_REQUEST_OFF, mRequestOff);
        mNavigator.startActivity(RequestOffActivity.class, bundle);
    }
}
