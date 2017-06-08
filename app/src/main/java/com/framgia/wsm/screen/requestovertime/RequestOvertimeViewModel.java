package com.framgia.wsm.screen.requestovertime;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.android.databinding.library.baseAdapters.BR;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.Request;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.requestovertime.confirmovertime.ConfirmOvertimeActivity;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.utils.validator.Rule;
import com.framgia.wsm.utils.validator.ValidType;
import com.framgia.wsm.utils.validator.Validation;

import static com.framgia.wsm.utils.Constant.EXTRA_REQUEST_OVERTIME;

/**
 * Exposes the data to be used in the RequestOvertime screen.
 */

public class RequestOvertimeViewModel extends BaseObservable
        implements RequestOvertimeContract.ViewModel {

    private static final String TAG = "RequestOvertimeViewModel";

    private RequestOvertimeContract.Presenter mPresenter;
    private Request mRequest;
    private User mUser;
    private Navigator mNavigator;
    private String mBranch;
    private String mGroup;
    private String mProjectNameError;
    private String mReasonError;
    private String mFromTimeError;
    private String mToTimeError;

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
    private String mFromTime;

    @Validation({
            @Rule(types = ValidType.NON_EMPTY, message = R.string.is_empty)
    })
    private String mToTime;

    RequestOvertimeViewModel(RequestOvertimeContract.Presenter presenter, Navigator navigator) {
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mNavigator = navigator;
        mRequest = new Request();
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
    }

    @Override
    public void onGetUserError(BaseException e) {
        Log.e(TAG, "RequestOvertimeViewModel", e);
    }

    @Override
    public void onInputProjectNameError(String projectNameError) {
        mProjectNameError = projectNameError;
        notifyPropertyChanged(BR.projectNameError);
    }

    @Override
    public void onInputReasonError(String reasonError) {
        mReasonError = reasonError;
        notifyPropertyChanged(BR.reasonError);
    }

    @Override
    public void onInputFromTimeError(String fromTImeError) {
        mFromTimeError = fromTImeError;
        notifyPropertyChanged(BR.fromTimeError);
    }

    @Override
    public void onInputToTimeError(String toTimeError) {
        mToTimeError = toTimeError;
        notifyPropertyChanged(BR.toTimeError);
    }

    @Bindable
    public User getUser() {
        return mUser;
    }

    @Bindable
    public String getBranch() {
        return mBranch;
    }

    public void setBranch(String branch) {
        mBranch = branch;
        notifyPropertyChanged(BR.branch);
    }

    @Bindable
    public String getGroup() {
        return mGroup;
    }

    public void setGroup(String group) {
        mGroup = group;
        notifyPropertyChanged(BR.group);
    }

    @Bindable
    public String getProjectName() {
        return mProjectName;
    }

    public void setProjectName(String projectName) {
        mProjectName = projectName;
    }

    @Bindable
    public String getReason() {
        return mReason;
    }

    public void setReason(String reason) {
        mReason = reason;
    }

    @Bindable
    public String getFromTime() {
        return mFromTime;
    }

    public void setFromTime(String fromTime) {
        mFromTime = fromTime;
        notifyPropertyChanged(BR.fromTime);
    }

    @Bindable
    public String getToTime() {
        return mToTime;
    }

    public void setToTime(String toTime) {
        mToTime = toTime;
        notifyPropertyChanged(BR.toTime);
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
    public String getFromTimeError() {
        return mFromTimeError;
    }

    @Bindable
    public String getToTimeError() {
        return mToTimeError;
    }

    public void onClickNext(View view) {
        if (!mPresenter.validateDataInput(mProjectName, mReason, mFromTime, mToTime)) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_REQUEST_OVERTIME, mRequest);
        mNavigator.startActivity(ConfirmOvertimeActivity.class, bundle);
    }

    public void onCickFromTime(View view) {
        //TODO pick date time for From time
    }

    public void onCickToTime(View view) {
        //TODO pick date time for To time
    }
}
