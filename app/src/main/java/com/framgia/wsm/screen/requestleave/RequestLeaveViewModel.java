package com.framgia.wsm.screen.requestleave;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.IntDef;
import android.util.Log;
import android.view.View;
import com.framgia.wsm.BR;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.widget.dialog.DialogManagerImpl;
import com.fstyle.library.MaterialDialog;

/**
 * Exposes the data to be used in the RequestLeave screen.
 */

public class RequestLeaveViewModel extends BaseObservable
        implements RequestLeaveContract.ViewModel {

    private static final String TAG = "RequestLeaveActivity";
    private static final String IN_LATE_M = "In late (M)";

    private RequestLeaveContract.Presenter mPresenter;
    private Context mContext;
    private Navigator mNavigator;
    private User mUser;
    private String mCurrentBranch;
    private String mCurrentGroup;
    private String mCurrentLeaveType;
    private boolean mIsVisibleLayoutCompensation;
    private boolean mIsVisibleLayoutTime;
    private DialogManagerImpl mDialogManager;

    RequestLeaveViewModel(Context context, Navigator navigator,
            RequestLeaveContract.Presenter presenter, DialogManagerImpl dialogManager) {
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mContext = context;
        mNavigator = navigator;
        mDialogManager = dialogManager;
        mCurrentLeaveType = IN_LATE_M;
        setVisibleLayoutCompensation(true);
        mCurrentBranch = "Da nang Office";
        mCurrentGroup = "Android members";
        //TODO Change later
    }

    @Bindable
    public String getTitleToolbar() {
        return mContext.getResources().getString(R.string.request_leave);
    }

    @Bindable
    public String getEmployeeName() {
        return mUser != null ? mUser.getName() : "";
    }

    @Bindable
    public String getEmployeeCode() {
        return mUser != null ? mUser.getCode() : "";
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
    }

    @Bindable
    public boolean isVisibleLayoutCompensation() {
        return mIsVisibleLayoutCompensation;
    }

    private void setVisibleLayoutCompensation(boolean visibleLayoutCompensation) {
        mIsVisibleLayoutCompensation = visibleLayoutCompensation;
        notifyPropertyChanged(BR.visibleLayoutCompensation);
    }

    @Bindable
    public boolean isVisibleLayoutTime() {
        return mIsVisibleLayoutTime;
    }

    private void setVisibleLayoutTime(boolean visibleLayoutTime) {
        mIsVisibleLayoutTime = visibleLayoutTime;
        notifyPropertyChanged(BR.visibleLayoutTime);
    }

    private void setLayoutLeaveType(int positionType) {
        if (positionType == PositionType.LEAVE_OUT
                || positionType == PositionType.FORGOT_TO_CHECK_IN_OUT_ALL_DAY) {
            setVisibleLayoutTime(true);
        } else {
            setVisibleLayoutTime(false);
        }
        if (positionType == PositionType.IN_LATE_A
                || positionType == PositionType.IN_LATE_M
                || positionType == PositionType.LEAVE_EARLY_A
                || positionType == PositionType.LEAVE_EARLY_M
                || positionType == PositionType.LEAVE_OUT) {
            setVisibleLayoutCompensation(true);
        } else {
            setVisibleLayoutCompensation(false);
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
        notifyPropertyChanged(BR.employeeName);
        notifyPropertyChanged(BR.employeeCode);
    }

    @Override
    public void onGetUserError(BaseException exception) {
        Log.e(TAG, "onGetUserError", exception);
    }

    public void onPickBranch(View view) {
        //TODO pick branch
    }

    public void onPickroupt(View view) {
        //TODO pick group
    }

    public void onClickCreate(View view) {
        //TODO start activity confirm request leave
    }

    public void onPickTypeRequest(View view) {
        mDialogManager.dialogListSingleChoice(
                mContext.getResources().getString(R.string.leave_type), R.array.leave_type,
                PositionType.IN_LATE_M, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view,
                            int positionType, CharSequence charSequence) {
                        setCurrentLeaveType(String.valueOf(charSequence));
                        setLayoutLeaveType(positionType);
                        return true;
                    }
                });
    }

    @IntDef({
            PositionType.IN_LATE_M, PositionType.IN_LATE_A, PositionType.LEAVE_EARLY_A,
            PositionType.LEAVE_EARLY_M, PositionType.LEAVE_OUT,
            PositionType.FORGOT_TO_CHECK_IN_OUT_ALL_DAY, PositionType.FORGOT_TO_CHECK_IN,
            PositionType.FORGOT_TO_CHECK_OUT, PositionType.FORGOT_CARD_ALL_DAY,
            PositionType.FORGOT_CARD_IN, PositionType.FORGOT_CARD_OUT
    })
    @interface PositionType {
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
    }
}
