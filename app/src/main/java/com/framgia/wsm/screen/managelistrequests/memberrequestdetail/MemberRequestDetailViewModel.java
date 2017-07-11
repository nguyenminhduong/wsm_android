package com.framgia.wsm.screen.managelistrequests.memberrequestdetail;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v4.app.Fragment;
import com.framgia.wsm.data.model.OffRequest;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.utils.StatusCode;
import com.framgia.wsm.utils.navigator.Navigator;

/**
 * Created by ths on 11/07/2017.
 */

public class MemberRequestDetailViewModel extends BaseObservable
        implements MemberRequestDetailContract.ViewModel {

    private static final String TAG = "MemberRequestDetailViewModel";

    private Context mContext;
    private MemberRequestDetailContract.Presenter mPresenter;
    private Navigator mNavigator;
    //TODO edit later
    private User mUser;
    private OffRequest mRequestOff;
    private boolean mIsVisiableProjectName;
    private DismissDialogListener mDismissDialogListener;

    MemberRequestDetailViewModel(Context context, Fragment fragment,
            MemberRequestDetailContract.Presenter presenter, Navigator navigator) {
        mContext = context;
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mNavigator = navigator;
        mRequestOff = new OffRequest();
        mDismissDialogListener = (DismissDialogListener) fragment;
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Bindable
    public User getUser() {
        return mUser;
    }

    @Bindable
    public boolean isVisiableProjectName() {
        return mIsVisiableProjectName;
    }

    @Bindable
    public OffRequest getRequestOff() {
        return mRequestOff;
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

    public boolean isAcceptStatus() {
        return StatusCode.ACCEPT_CODE.equals(mRequestOff.getStatus());
    }

    public boolean isPendingStatus() {
        return StatusCode.PENDING_CODE.equals(mRequestOff.getStatus());
    }

    public boolean isRejectStatus() {
        return StatusCode.REJECT_CODE.equals(mRequestOff.getStatus());
    }

    public void onClickClose() {
        mDismissDialogListener.onDismissDialog();
    }

    public void onClickArrowBack() {
        mDismissDialogListener.onDismissDialog();
    }
}
