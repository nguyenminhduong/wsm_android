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
    private int mCurrentPostionLeaveType;
    private String mTitleLeaveType;
    private String mExampleLeaveType;
    private String mTitleLeaveTypeLayoutTime;
    private String mExampleLeaveTypeLayoutTime;

    RequestLeaveViewModel(Context context, Navigator navigator,
            RequestLeaveContract.Presenter presenter, DialogManagerImpl dialogManager) {
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mContext = context;
        mNavigator = navigator;
        mDialogManager = dialogManager;
        setVisibleLayoutCompensation(true);
        mCurrentPostionLeaveType = PositionType.IN_LATE_M;
        mCurrentLeaveType = mContext.getString(R.string.leave_type_in_late_m);
        mTitleLeaveType = mContext.getString(R.string.title_in_late_m);
        mExampleLeaveType = mContext.getString(R.string.exmple_in_late_m);
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

    private void setCurrentPostionLeaveType(int currentPostionLeaveType) {
        mCurrentPostionLeaveType = currentPostionLeaveType;
    }

    @Bindable
    public String getTitleLeaveType() {
        return mTitleLeaveType;
    }

    private void setTitleExampleLeaveType(String titleLeaveType, String exampleLeaveType) {
        mTitleLeaveType = titleLeaveType;
        notifyPropertyChanged(BR.titleLeaveType);
        mExampleLeaveType = exampleLeaveType;
        notifyPropertyChanged(BR.exampleLeaveType);
    }

    @Bindable
    public String getExampleLeaveType() {
        return mExampleLeaveType;
    }

    @Bindable
    public String getTitleLeaveTypeLayoutTime() {
        return mTitleLeaveTypeLayoutTime;
    }

    @Bindable
    public String getExampleLeaveTypeLayoutTime() {
        return mExampleLeaveTypeLayoutTime;
    }

    private void setLayoutLeaveType(int positionType) {
        if (positionType == PositionType.LEAVE_OUT
                || positionType == PositionType.FORGOT_TO_CHECK_IN_OUT_ALL_DAY
                || positionType == PositionType.FORGOT_CARD_ALL_DAY) {
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

    private void setTitleExampleLeaveType(int positionType) {
        switch (positionType) {
            case PositionType.IN_LATE_M:
                setTitleExampleLeaveType(mContext.getString(R.string.title_in_late_m),
                        mContext.getString(R.string.exmple_in_late_m));
                break;
            case PositionType.IN_LATE_A:
                setTitleExampleLeaveType(mContext.getString(R.string.title_in_late_a),
                        mContext.getString(R.string.exmple_in_late_a));
                break;

            case PositionType.LEAVE_EARLY_M:
                setTitleExampleLeaveType(mContext.getString(R.string.title_leave_early_m),
                        mContext.getString(R.string.exmple_leave_early_m));
                break;
            case PositionType.LEAVE_EARLY_A:
                setTitleExampleLeaveType(mContext.getString(R.string.title_leave_early_a),
                        mContext.getString(R.string.exmple_leave_early_a));
                break;
            case PositionType.LEAVE_OUT:
            case PositionType.FORGOT_TO_CHECK_IN_OUT_ALL_DAY:
            case PositionType.FORGOT_CARD_ALL_DAY:
                setTitleExampleLeaveType(mContext.getString(R.string.title_leave_out_from),
                        mContext.getString(R.string.exmple_leave_out_from));
                mTitleLeaveTypeLayoutTime = mContext.getString(R.string.title_leave_out_to);
                notifyPropertyChanged(BR.titleLeaveTypeLayoutTime);
                mExampleLeaveTypeLayoutTime = mContext.getString(R.string.exmple_leave_out_to);
                notifyPropertyChanged(BR.exampleLeaveTypeLayoutTime);
                break;
            case PositionType.FORGOT_CARD_IN:
            case PositionType.FORGOT_TO_CHECK_IN:
                setTitleExampleLeaveType(mContext.getString(R.string.title_forgot_in),
                        mContext.getString(R.string.exmple_forgot_in));
                break;
            case PositionType.FORGOT_CARD_OUT:
            case PositionType.FORGOT_TO_CHECK_OUT:
                setTitleExampleLeaveType(mContext.getString(R.string.title_forgot_out),
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
        mDialogManager.dialogListSingleChoice(mContext.getString(R.string.leave_type),
                R.array.leave_type, mCurrentPostionLeaveType,
                new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view,
                            int positionType, CharSequence charSequence) {
                        setCurrentLeaveType(String.valueOf(charSequence));
                        setCurrentPostionLeaveType(positionType);
                        setLayoutLeaveType(positionType);
                        setTitleExampleLeaveType(positionType);
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
