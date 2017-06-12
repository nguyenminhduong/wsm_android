package com.framgia.wsm.screen.requestleave;

import android.content.Context;
import android.databinding.Bindable;
import android.os.Bundle;
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
    private int mCurrentBranchPosition;
    private int mCurrentGroupPosition;
    private int mCurrentLeaveTypePosition;
    private boolean mIsVisibleLayoutCompensation;
    private boolean mIsVisibleLayoutCheckin;
    private boolean mIsVisibleLayoutCheckout;
    private DialogManager mDialogManager;
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
        setVisibleLayoutCheckin(true);
        mRequest = new Request();
        mRequest.setCompensation(new Request.Compensation());
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
    public void onGetUserSuccess(User user) {
        if (user == null) {
            return;
        }
        mUser = user;
        notifyPropertyChanged(BR.user);
        setCurrentLeaveType();
        setCurrentBranch();
        setCurrentGroup();
        setTitleExampleLeave();
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

    private void setTitleExampleLeave() {
        switch (mCurrentLeaveType) {
            case Constant.LeaveType.IN_LATE_WOMAN_M:
            case Constant.LeaveType.IN_LATE_M:
                setTitleExampleLeaveIn(mContext.getString(R.string.title_in_late_m),
                        mContext.getString(R.string.exmple_in_late_m));
                break;
            case Constant.LeaveType.IN_LATE_WOMAN_A:
            case Constant.LeaveType.IN_LATE_A:
                setTitleExampleLeaveIn(mContext.getString(R.string.title_in_late_a),
                        mContext.getString(R.string.exmple_in_late_a));
                break;
            case Constant.LeaveType.LEAVE_EARLY_WOMAN_M:
            case Constant.LeaveType.LEAVE_EARLY_M:
                setTitleExampleLeaveOut(mContext.getString(R.string.title_leave_early_m),
                        mContext.getString(R.string.exmple_leave_early_m));
                break;
            case Constant.LeaveType.LEAVE_EARLY_WOMAN_A:
            case Constant.LeaveType.LEAVE_EARLY_A:
                setTitleExampleLeaveOut(mContext.getString(R.string.title_leave_early_a),
                        mContext.getString(R.string.exmple_leave_early_a));
                break;
            case Constant.LeaveType.LEAVE_OUT:
            case Constant.LeaveType.FORGOT_CHECK_ALL_DAY:
            case Constant.LeaveType.FORGOT_CARD_ALL_DAY:
                setTitleExampleLeaveIn(mContext.getString(R.string.title_leave_out_from),
                        mContext.getString(R.string.exmple_leave_out_from));
                setTitleExampleLeaveOut(mContext.getString(R.string.title_leave_out_to),
                        mContext.getString(R.string.exmple_leave_out_to));
                break;
            case Constant.LeaveType.FORGOT_CARD_IN:
            case Constant.LeaveType.FORGOT_TO_CHECK_IN:
                setTitleExampleLeaveIn(mContext.getString(R.string.title_forgot_in),
                        mContext.getString(R.string.exmple_forgot_in));
                break;
            case Constant.LeaveType.FORGOT_CARD_OUT:
            case Constant.LeaveType.FORGOT_TO_CHECK_OUT:
                setTitleExampleLeaveOut(mContext.getString(R.string.title_forgot_out),
                        mContext.getString(R.string.exmple_forgot_out));
                break;
            default:
                break;
        }
    }

    public void onPickBranch(View view) {
        if (mUser.getBranches() == null || mUser.getBranches().size() == 0) {
            return;
        }
        String[] branches = new String[mUser.getBranches().size()];
        for (int i = 0; i < branches.length; i++) {
            branches[i] = mUser.getBranches().get(i).getBranchName();
        }
        mDialogManager.dialogListSingleChoice(mContext.getString(R.string.branch), branches,
                mCurrentBranchPosition, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view,
                            int position, CharSequence charSequence) {
                        mCurrentBranchPosition = position;
                        setCurrentBranch();
                        return true;
                    }
                });
    }

    public void onPickGroup(View view) {
        if (mUser.getGroups() == null || mUser.getGroups().size() == 0) {
            return;
        }
        String[] groups = new String[mUser.getGroups().size()];
        for (int i = 0; i < groups.length; i++) {
            groups[i] = mUser.getGroups().get(i).getGroupName();
        }
        mDialogManager.dialogListSingleChoice(mContext.getString(R.string.group), groups,
                mCurrentBranchPosition, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view,
                            int position, CharSequence charSequence) {
                        mCurrentGroupPosition = position;
                        setCurrentGroup();
                        return true;
                    }
                });
    }

    public void onPickTypeRequest(View view) {
        if (mUser.getLeaveTypes() == null || mUser.getLeaveTypes().size() == 0) {
            return;
        }
        String[] leaveTypes = new String[mUser.getLeaveTypes().size()];
        for (int i = 0; i < leaveTypes.length; i++) {
            leaveTypes[i] = mUser.getLeaveTypes().get(i).getName();
        }
        mDialogManager.dialogListSingleChoice(mContext.getString(R.string.leave_type), leaveTypes,
                mCurrentLeaveTypePosition, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view,
                            int positionType, CharSequence charSequence) {
                        mCurrentLeaveTypePosition = positionType;
                        setCurrentLeaveType();
                        setTitleExampleLeave();
                        setLayoutLeaveType(mCurrentLeaveType);
                        return true;
                    }
                });
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
    public String getTitleToolbar() {
        return mContext.getString(R.string.request_leave);
    }

    @Bindable
    public String getCurrentBranch() {
        return mCurrentBranch;
    }

    @Bindable
    public String getCurrentGroup() {
        return mCurrentGroup;
    }

    @Bindable
    public String getCurrentLeaveType() {
        return mCurrentLeaveType;
    }

    private void setCurrentLeaveType() {
        mCurrentLeaveType = mUser.getLeaveTypes().get(mCurrentLeaveTypePosition).getName();
        mRequest.setLeaveType(mUser.getLeaveTypes().get(mCurrentLeaveTypePosition));
        notifyPropertyChanged(BR.currentLeaveType);
    }

    public void setCurrentBranch() {
        mCurrentBranch = mUser.getBranches().get(mCurrentBranchPosition).getBranchName();
        mRequest.setBranch(mUser.getBranches().get(mCurrentBranchPosition));
        notifyPropertyChanged(BR.currentBranch);
    }

    public void setCurrentGroup() {
        mCurrentGroup = mUser.getGroups().get(mCurrentGroupPosition).getGroupName();
        mRequest.setGroup(mUser.getGroups().get(mCurrentGroupPosition));
        notifyPropertyChanged(BR.currentGroup);
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
        return mRequest.getProject();
    }

    public void setProjectName(String projectName) {
        mProjectName = projectName;
        mRequest.setProject(projectName);
        notifyPropertyChanged(BR.projectName);
    }

    @Bindable
    public String getReason() {
        return mRequest.getReason();
    }

    public void setReason(String reason) {
        mReason = reason;
        mRequest.setReason(reason);
        notifyPropertyChanged(BR.reason);
    }

    @Bindable
    public String getCheckinTime() {
        mRequest.setCheckinTime("34344");
        return mRequest.getCheckinTime();
    }

    public void setCheckinTime(String checkinTime) {
        mCheckinTime = checkinTime;
        mRequest.setCheckinTime(checkinTime);
        notifyPropertyChanged(BR.checkinTime);
    }

    @Bindable
    public String getCheckoutTime() {
        return mRequest.getCheckoutTime();
    }

    public void setCheckoutTime(String checkoutTime) {
        mCheckoutTime = checkoutTime;
        mRequest.setCheckoutTime(checkoutTime);
        notifyPropertyChanged(BR.checkoutTime);
    }

    @Bindable
    public String getCompensationFromTime() {
        return mRequest.getCompensation().getFromTime();
    }

    public void setCompensationFromTime(String compensationFromTime) {
        mCompensationFromTime = compensationFromTime;
        mRequest.getCompensation().setFromTime(compensationFromTime);
        notifyPropertyChanged(BR.compensationFromTime);
    }

    @Bindable
    public String getCompensationToTime() {
        return mRequest.getCompensation().getToTime();
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
}
