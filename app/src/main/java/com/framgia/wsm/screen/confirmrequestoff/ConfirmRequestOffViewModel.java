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
import com.framgia.wsm.data.model.OffRequest;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.request.RequestOffRequest;
import com.framgia.wsm.screen.requestoff.RequestOffActivity;
import com.framgia.wsm.utils.ActionType;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.StatusCode;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.widget.dialog.DialogManager;
import com.fstyle.library.DialogAction;
import com.fstyle.library.MaterialDialog;
import java.util.Objects;

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
    private OffRequest mOffRequest;
    private RequestOffRequest mRequestOffRequest;
    private int mActionType;
    private Context mContext;

    ConfirmRequestOffViewModel(Context context, ConfirmRequestOffContract.Presenter presenter,
            Navigator navigator, DialogManager dialogManager, OffRequest requestOff,
            int actionType) {
        mContext = context;
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mDialogManager = dialogManager;
        mOffRequest = requestOff;
        mNavigator = navigator;
        mPresenter.getUser();
        mActionType = actionType;
        mRequestOffRequest = new RequestOffRequest();
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
    public void onEditFormRequestOffSuccess(OffRequest requestOff) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.EXTRA_REQUEST_OFF, mOffRequest);
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
    public OffRequest getRequestOff() {
        return mOffRequest;
    }

    @Bindable
    public boolean isVisiableProjectName() {
        return mOffRequest.getProject() != null;
    }

    @Bindable
    public boolean isVisiablePosition() {
        return mOffRequest.getPosition() != null;
    }

    @Bindable
    public boolean isVisiableLayoutCompanyPay() {
        return mOffRequest.getCompanyPay().getAnnualLeave() != null
                || mOffRequest.getCompanyPay().getLeaveForChildMarriage() != null
                || mOffRequest.getCompanyPay().getLeaveForMarriage() != null
                || mOffRequest.getCompanyPay().getFuneralLeave() != null;
    }

    @Bindable
    public boolean isVisiableLayoutInsurance() {
        return mOffRequest.getInsuranceCoverage().getLeaveForCareOfSickChild() != null
                || mOffRequest.getInsuranceCoverage().getSickLeave() != null
                || mOffRequest.getInsuranceCoverage().getMaternityLeave() != null
                || mOffRequest.getInsuranceCoverage().getPregnancyExaminationLeave() != null
                || mOffRequest.getInsuranceCoverage().getMiscarriageLeave() != null
                || mOffRequest.getInsuranceCoverage().getWifeLaborLeave() != null;
    }

    @Bindable
    public boolean isVisiableLayoutHaveSalary() {
        return mOffRequest.getStartDayHaveSalary() != null;
    }

    @Bindable
    public boolean isVisiableLayoutOffNoSalary() {
        return mOffRequest.getStartDayNoSalary() != null;
    }

    @Bindable
    public boolean isVisiableAnnualLeave() {
        return mOffRequest.getCompanyPay().getAnnualLeave() != null;
    }

    @Bindable
    public boolean isVisiableLeaveForChildMarriage() {
        return mOffRequest.getCompanyPay().getLeaveForChildMarriage() != null;
    }

    @Bindable
    public boolean isVisiableLeaveForMarriage() {
        return mOffRequest.getCompanyPay().getLeaveForMarriage() != null;
    }

    @Bindable
    public boolean isVisiableFuneralLeave() {
        return mOffRequest.getCompanyPay().getFuneralLeave() != null;
    }

    @Bindable
    public boolean isVisiableLeaveForCareOfSickChild() {
        return mOffRequest.getInsuranceCoverage().getLeaveForCareOfSickChild() != null;
    }

    @Bindable
    public boolean isVisiableSickLeave() {
        return mOffRequest.getInsuranceCoverage().getSickLeave() != null;
    }

    @Bindable
    public boolean isVisiableMaternityLeave() {
        return mOffRequest.getInsuranceCoverage().getMaternityLeave() != null;
    }

    @Bindable
    public boolean isVisiablePregnancyExaminationLeave() {
        return mOffRequest.getInsuranceCoverage().getPregnancyExaminationLeave() != null;
    }

    @Bindable
    public boolean isVisiableMiscarriageLeave() {
        return mOffRequest.getInsuranceCoverage().getMiscarriageLeave() != null;
    }

    @Bindable
    public boolean isVisiableWifeLaborLeave() {
        return mOffRequest.getInsuranceCoverage().getWifeLaborLeave() != null;
    }

    public boolean isDetail() {
        return mActionType == ActionType.ACTION_DETAIL;
    }

    public boolean isAcceptStatus() {
        return Objects.equals(mOffRequest.getStatus(), StatusCode.ACCEPT_CODE);
    }

    public boolean isPendingStatus() {
        return Objects.equals(mOffRequest.getStatus(), StatusCode.PENDING_CODE);
    }

    public boolean isRejectStatus() {
        return Objects.equals(mOffRequest.getStatus(), StatusCode.REJECT_CODE);
    }

    public String getTitleToolbar() {
        if (mActionType == ActionType.ACTION_CREATE) {
            return mContext.getString(R.string.confirm_request_off);
        }
        if (mActionType == ActionType.ACTION_DETAIL) {
            return mContext.getString(R.string.detail_request_off);
        }
        return mContext.getString(R.string.confirm_edit_request_off);
    }

    public boolean isVisibleButtonSubmit() {
        return mOffRequest.getStatus() == StatusCode.PENDING_CODE
                || mActionType == ActionType.ACTION_CREATE;
    }

    public void onCickArrowBack(View view) {
        mNavigator.finishActivity();
    }

    public void onClickSubmit(View view) {
        if (mOffRequest == null) {
            return;
        }
        mRequestOffRequest.setRequestOff(mOffRequest);
        if (mActionType == ActionType.ACTION_CREATE) {
            mPresenter.createFormRequestOff(mRequestOffRequest);
            return;
        }
        mPresenter.editFormRequestOff(mRequestOffRequest);
    }

    public void onClickDelete(View view) {
        if (mOffRequest == null) {
            return;
        }
        mPresenter.deleteFormRequestOff(mOffRequest.getId());
    }

    public void onClickEdit(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.EXTRA_ACTION_TYPE, ActionType.ACTION_EDIT);
        bundle.putParcelable(Constant.EXTRA_REQUEST_OFF, mOffRequest);
        mNavigator.startActivity(RequestOffActivity.class, bundle);
    }
}
