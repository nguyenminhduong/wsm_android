package com.framgia.wsm.screen.requestleave;

import android.content.Context;
import android.databinding.Bindable;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.util.Log;
import android.view.View;
import com.framgia.wsm.BR;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.Request;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.BaseRequestLeave;
import com.framgia.wsm.screen.confirmrequestleave.ConfirmRequestLeaveActivity;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.utils.validator.Rule;
import com.framgia.wsm.utils.validator.ValidType;
import com.framgia.wsm.utils.validator.Validation;
import com.framgia.wsm.widget.dialog.DialogManager;
import com.fstyle.library.MaterialDialog;

/**
 * Exposes the data to be used in the Request screen.
 */

public class RequestLeaveViewModel extends BaseRequestLeave
        implements RequestLeaveContract.ViewModel {

    private static final String TAG = "RequestLeaveActivity";

    private RequestLeaveContract.Presenter mPresenter;
    private Context mContext;
    private Navigator mNavigator;
    private User mUser;
    private String mCurrentBranch;
    private String mCurrentGroup;
    private String mCurrentLeaveType;
    private boolean mIsVisibleLayoutCompensation;
    private boolean mIsVisibleLayoutCheckin;
    private boolean mIsVisibleLayoutCheckout;
    private DialogManager mDialogManager;
    private int mCurrentPositionLeaveType;
    private String mTitleLeaveInType;
    private String mExampleLeaveInType;
    private String mTitleLeaveOutType;
    private String mExampleLeaveOutType;
    private Request mRequest;
    private String mProjectNameError;
    private String mReasonError;
    private String mCheckinTimeError;
    private String mCheckoutTimeError;
    private String mCompensationFromTimeError;
    private String mCompensationToTimeError;

    @Validation({
            @Rule(types = ValidType.NON_EMPTY, message = R.string.is_empty)
    })
    private String mProjectName;

    @Validation({
            @Rule(types = ValidType.NON_EMPTY, message = R.string.is_empty)
    })
    private String mReason;

    @Validation({
            @Rule(types = ValidType.NON_EMPTY, message = R.string.is_empty)
    })
    private String mCheckinTime;

    @Validation({
            @Rule(types = ValidType.NON_EMPTY, message = R.string.is_empty)
    })
    private String mCheckoutTime;

    @Validation({
            @Rule(types = ValidType.NON_EMPTY, message = R.string.is_empty)
    })
    private String mCompensationFromTime;
    @Validation({
            @Rule(types = ValidType.NON_EMPTY, message = R.string.is_empty)
    })
    private String mCompensationToTime;

    RequestLeaveViewModel(Context context, Navigator navigator,
            RequestLeaveContract.Presenter presenter, DialogManager dialogManager) {
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mContext = context;
        mNavigator = navigator;
        mDialogManager = dialogManager;
        setVisibleLayoutCompensation(true);
        mCurrentPositionLeaveType = PositionType.IN_LATE_M;
        setVisibleLayoutCheckin(true);
        mCurrentLeaveType = mContext.getString(R.string.leave_type_in_late_m);
        mTitleLeaveInType = mContext.getString(R.string.title_in_late_m);
        mExampleLeaveInType = mContext.getString(R.string.exmple_in_late_m);
        mRequest = new Request();
        mRequest.setCompensation(new Request.Compensation());
        mPresenter.getUser();
    }

    @Bindable
    public String getTitleToolbar() {
        return mContext.getString(R.string.request_leave);
    }

    @Bindable
    public String getCurrentBranch() {
        return mCurrentBranch;
    }

    private void setCurrentBranch(String currentBranch) {
        mCurrentBranch = currentBranch;
        notifyPropertyChanged(BR.currentBranch);
    }

    @Bindable
    public String getCurrentGroup() {
        return mCurrentGroup;
    }

    private void setCurrentGroup(String currentGroup) {
        mCurrentGroup = currentGroup;
        notifyPropertyChanged(BR.currentGroup);
    }

    @Bindable
    public String getCurrentLeaveType() {
        return mCurrentLeaveType;
    }

    private void setCurrentLeaveType(String currentLeaveType) {
        mCurrentLeaveType = currentLeaveType;
        notifyPropertyChanged(BR.currentLeaveType);
        mRequest.setRequestName(currentLeaveType);
    }

    @Bindable
    public boolean isVisibleLayoutCompensation() {
        return mIsVisibleLayoutCompensation;
    }

    @Override
    public void setVisibleLayoutCompensation(boolean visibleLayoutCompensation) {
        mIsVisibleLayoutCompensation = visibleLayoutCompensation;
        notifyPropertyChanged(BR.visibleLayoutCompensation);
    }

    @Bindable
    public boolean isVisibleLayoutCheckin() {
        return mIsVisibleLayoutCheckin;
    }

    public void setVisibleLayoutCheckin(boolean visibleLayoutCheckin) {
        mIsVisibleLayoutCheckin = visibleLayoutCheckin;
        notifyPropertyChanged(BR.visibleLayoutCheckin);
    }

    @Bindable
    public boolean isVisibleLayoutCheckout() {
        return mIsVisibleLayoutCheckout;
    }

    public void setVisibleLayoutCheckout(boolean visibleLayoutCheckout) {
        mIsVisibleLayoutCheckout = visibleLayoutCheckout;
        notifyPropertyChanged(BR.visibleLayoutCheckout);
    }

    private void setCurrentPositionLeaveType(int currentPositionLeaveType) {
        mCurrentPositionLeaveType = currentPositionLeaveType;
        mRequest.setPositionType(currentPositionLeaveType);
    }

    @Bindable
    public String getTitleLeaveInType() {
        return mTitleLeaveInType;
    }

    private void setTitleExampleLeaveIn(String titleLeaveType, String exampleLeaveType) {
        mTitleLeaveInType = titleLeaveType;
        notifyPropertyChanged(BR.titleLeaveInType);
        mExampleLeaveInType = exampleLeaveType;
        notifyPropertyChanged(BR.exampleLeaveInType);
    }

    private void setTitleExampleLeaveOut(String titleLeaveType, String exampleLeaveType) {
        mTitleLeaveOutType = titleLeaveType;
        notifyPropertyChanged(BR.titleLeaveOutType);
        mExampleLeaveOutType = exampleLeaveType;
        notifyPropertyChanged(BR.exampleLeaveOutType);
    }

    @Bindable
    public String getExampleLeaveInType() {
        return mExampleLeaveInType;
    }

    @Bindable
    public String getTitleLeaveOutType() {
        return mTitleLeaveOutType;
    }

    @Bindable
    public String getExampleLeaveOutType() {
        return mExampleLeaveOutType;
    }

    private void setTitleExampleLeaveIn(int positionType) {
        switch (positionType) {
            case PositionType.IN_LATE_WOMAN_M:
            case PositionType.IN_LATE_M:
                setTitleExampleLeaveIn(mContext.getString(R.string.title_in_late_m),
                        mContext.getString(R.string.exmple_in_late_m));
                break;
            case PositionType.IN_LATE_WOMAN_A:
            case PositionType.IN_LATE_A:
                setTitleExampleLeaveIn(mContext.getString(R.string.title_in_late_a),
                        mContext.getString(R.string.exmple_in_late_a));
                break;
            case PositionType.LEAVE_EARLY_WOMAN_M:
            case PositionType.LEAVE_EARLY_M:
                setTitleExampleLeaveOut(mContext.getString(R.string.title_leave_early_m),
                        mContext.getString(R.string.exmple_leave_early_m));
                break;
            case PositionType.LEAVE_EARLY_WOMAN_A:
            case PositionType.LEAVE_EARLY_A:
                setTitleExampleLeaveOut(mContext.getString(R.string.title_leave_early_a),
                        mContext.getString(R.string.exmple_leave_early_a));
                break;
            case PositionType.LEAVE_OUT:
            case PositionType.FORGOT_TO_CHECK_IN_OUT_ALL_DAY:
            case PositionType.FORGOT_CARD_ALL_DAY:
                setTitleExampleLeaveIn(mContext.getString(R.string.title_leave_out_from),
                        mContext.getString(R.string.exmple_leave_out_from));
                setTitleExampleLeaveOut(mContext.getString(R.string.title_leave_out_to),
                        mContext.getString(R.string.exmple_leave_out_to));
                break;
            case PositionType.FORGOT_CARD_IN:
            case PositionType.FORGOT_TO_CHECK_IN:
                setTitleExampleLeaveIn(mContext.getString(R.string.title_forgot_in),
                        mContext.getString(R.string.exmple_forgot_in));
                break;
            case PositionType.FORGOT_CARD_OUT:
            case PositionType.FORGOT_TO_CHECK_OUT:
                setTitleExampleLeaveOut(mContext.getString(R.string.title_forgot_out),
                        mContext.getString(R.string.exmple_forgot_out));
                break;
            default:
                break;
        }
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
    public void onGetUserSuccess(User user) {
        if (user == null) {
            return;
        }
        mUser = user;
        notifyPropertyChanged(BR.user);
    }

    @Override
    public void onGetUserError(BaseException exception) {
        Log.e(TAG, "onGetUserError", exception);
    }

    @Override
    public void onInputReasonError(String errorMessage) {
        mReasonError = errorMessage;
        notifyPropertyChanged(BR.reasonError);
    }

    @Override
    public void onInputProjectNameError(String errorMessage) {
        mProjectNameError = errorMessage;
        notifyPropertyChanged(BR.projectNameError);
    }

    @Override
    public void onInputCheckinTimeError(String errorMessage) {
        mCheckinTimeError = errorMessage;
        notifyPropertyChanged(BR.checkinTimeError);
    }

    @Override
    public void onInputCheckoutTimeError(String errorMessage) {
        mCheckoutTimeError = errorMessage;
        notifyPropertyChanged(BR.checkoutTimeError);
    }

    @Override
    public void onInputCompensationFromError(String errorMessage) {
        mCompensationFromTimeError = errorMessage;
        notifyPropertyChanged(BR.compensationFromTimeError);
    }

    @Override
    public void onInputCompensationToError(String errorMessage) {
        mCompensationToTimeError = errorMessage;
        notifyPropertyChanged(BR.compensationToTimeError);
    }

    public void onPickBranch(View view) {
        //TODO pick branch
    }

    public void onPickroupt(View view) {
        //TODO pick group
    }

    public void onClickCreate(View view) {
        if (!mPresenter.validateDataInput(mRequest)) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.EXTRA_REQUEST_LEAVE, mRequest);
        mNavigator.startActivity(ConfirmRequestLeaveActivity.class, bundle);
    }

    @Bindable
    public String getProjectNameError() {
        return mProjectNameError;
    }

    @Bindable
    public String getReasonError() {
        return mReasonError;
    }

    @Bindable
    public String getCheckinTimeError() {
        return mCheckinTimeError;
    }

    @Bindable
    public String getCheckoutTimeError() {
        return mCheckoutTimeError;
    }

    @Bindable
    public String getCompensationFromTimeError() {
        return mCompensationFromTimeError;
    }

    @Bindable
    public String getCompensationToTimeError() {
        return mCompensationToTimeError;
    }

    @Bindable
    public String getProjectName() {
        return mProjectName;
    }

    public void setProjectName(String projectName) {
        mProjectName = projectName;
        mRequest.setProject(projectName);
        notifyPropertyChanged(BR.projectName);
    }

    @Bindable
    public String getReason() {
        return mReason;
    }

    public void setReason(String reason) {
        mReason = reason;
        mRequest.setReason(reason);
        notifyPropertyChanged(BR.reason);
    }

    @Bindable
    public String getCheckinTime() {
        return mCheckinTime;
    }

    public void setCheckinTime(String checkinTime) {
        mCheckinTime = checkinTime;
        mRequest.setCheckinTime(checkinTime);
        notifyPropertyChanged(BR.checkinTime);
    }

    @Bindable
    public String getCheckoutTime() {
        return mCheckoutTime;
    }

    public void setCheckoutTime(String checkoutTime) {
        mCheckoutTime = checkoutTime;
        mRequest.setCheckoutTime(checkoutTime);
        notifyPropertyChanged(BR.checkoutTime);
    }

    @Bindable
    public String getCompensationFromTime() {
        return mCompensationFromTime;
    }

    public void setCompensationFromTime(String compensationFromTime) {
        mCompensationFromTime = compensationFromTime;
        mRequest.getCompensation().setFromTime(compensationFromTime);
        notifyPropertyChanged(BR.compensationFromTime);
    }

    @Bindable
    public String getCompensationToTime() {
        return mCompensationToTime;
    }

    public void setCompensationToTime(String compensationToTime) {
        mCompensationToTime = compensationToTime;
        mRequest.getCompensation().setToTime(compensationToTime);
        notifyPropertyChanged(BR.compensationToTime);
    }

    @Bindable
    public User getUser() {
        return mUser;
    }

    public void onPickTypeRequest(View view) {
        mDialogManager.dialogListSingleChoice(mContext.getString(R.string.leave_type),
                R.array.leave_type, mCurrentPositionLeaveType,
                new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view,
                            int positionType, CharSequence charSequence) {
                        setCurrentLeaveType(String.valueOf(charSequence));
                        setCurrentPositionLeaveType(positionType);
                        setLayoutLeaveType(positionType);
                        setTitleExampleLeaveIn(positionType);
                        return true;
                    }
                });
    }

    @IntDef({
            PositionType.IN_LATE_M, PositionType.IN_LATE_A, PositionType.LEAVE_EARLY_A,
            PositionType.LEAVE_EARLY_M, PositionType.LEAVE_OUT,
            PositionType.FORGOT_TO_CHECK_IN_OUT_ALL_DAY, PositionType.FORGOT_TO_CHECK_IN,
            PositionType.FORGOT_TO_CHECK_OUT, PositionType.FORGOT_CARD_ALL_DAY,
            PositionType.FORGOT_CARD_IN, PositionType.FORGOT_CARD_OUT, PositionType.IN_LATE_WOMAN_M,
            PositionType.IN_LATE_WOMAN_A, PositionType.LEAVE_EARLY_WOMAN_M,
            PositionType.LEAVE_EARLY_WOMAN_A
    })
    public @interface PositionType {
        int IN_LATE_M = 0;
        int IN_LATE_A = 1;
        int LEAVE_EARLY_M = 2;
        int LEAVE_EARLY_A = 3;
        int LEAVE_OUT = 4;
        int FORGOT_TO_CHECK_IN_OUT_ALL_DAY = 5;
        int FORGOT_TO_CHECK_IN = 6;
        int FORGOT_TO_CHECK_OUT = 7;
        int FORGOT_CARD_ALL_DAY = 8;
        int FORGOT_CARD_IN = 9;
        int FORGOT_CARD_OUT = 10;
        int IN_LATE_WOMAN_M = 11;
        int IN_LATE_WOMAN_A = 12;
        int LEAVE_EARLY_WOMAN_M = 13;
        int LEAVE_EARLY_WOMAN_A = 14;
    }
}
