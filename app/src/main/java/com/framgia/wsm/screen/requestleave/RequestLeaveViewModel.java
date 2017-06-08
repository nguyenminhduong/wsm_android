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
        mRequest.setPositionLeaveType(currentPositionLeaveType);
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
            case PositionType.IN_LATE_M:
                setTitleExampleLeaveIn(mContext.getString(R.string.title_in_late_m),
                        mContext.getString(R.string.exmple_in_late_m));
                break;
            case PositionType.IN_LATE_A:
                setTitleExampleLeaveIn(mContext.getString(R.string.title_in_late_a),
                        mContext.getString(R.string.exmple_in_late_a));
                break;

            case PositionType.LEAVE_EARLY_M:
                setTitleExampleLeaveOut(mContext.getString(R.string.title_leave_early_m),
                        mContext.getString(R.string.exmple_leave_early_m));
                break;
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

    public void onPickBranch(View view) {
        //TODO pick branch
    }

    public void onPickroupt(View view) {
        //TODO pick group
    }

    public void onClickCreate(View view) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.EXTRA_REQUEST_LEAVE, mRequest);
        mNavigator.startActivity(ConfirmRequestLeaveActivity.class, bundle);
    }

    @Bindable
    public User getUser() {
        return mUser;
    }

    public Request getRequest() {
        return mRequest;
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
            PositionType.FORGOT_CARD_IN, PositionType.FORGOT_CARD_OUT
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
    }
}
