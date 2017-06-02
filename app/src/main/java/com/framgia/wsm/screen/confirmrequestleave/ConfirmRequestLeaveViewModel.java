package com.framgia.wsm.screen.confirmrequestleave;

import android.content.Context;
import android.databinding.Bindable;
import com.android.databinding.library.baseAdapters.BR;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.Request;
import com.framgia.wsm.screen.BaseRequestLeave;

/**
 * Exposes the data to be used in the ConfirmRequestLeave screen.
 */

public class ConfirmRequestLeaveViewModel extends BaseRequestLeave
        implements ConfirmRequestLeaveContract.ViewModel {

    private ConfirmRequestLeaveContract.Presenter mPresenter;
    private Request mRequest;
    private Context mContext;
    private boolean mIsVisibleLayoutTime;
    private boolean mIsVisibleLayoutCompensation;
    private String mLeaveTypeName;

    ConfirmRequestLeaveViewModel(Context context, ConfirmRequestLeaveContract.Presenter presenter,
            Request request) {
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mContext = context;
        mRequest = request;
        setLayoutLeaveType(mRequest.getPositionLeaveType());
        setLeaveType(mRequest.getPositionLeaveType());
    }

    @Bindable
    public String getTitleToolbar() {
        return mContext.getString(R.string.confirm_request_leave);
    }

    @Bindable
    public boolean isVisibleLayoutTime() {
        return mIsVisibleLayoutTime;
    }

    @Override
    public void setVisibleLayoutTime(boolean visibleLayoutTime) {
        mIsVisibleLayoutTime = visibleLayoutTime;
        notifyPropertyChanged(BR.visibleLayoutTime);
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
    public String getLeaveTypeName() {
        return mLeaveTypeName;
    }

    private void setLeaveTypeName(String leaveTypeName) {
        mLeaveTypeName = leaveTypeName;
        notifyPropertyChanged(BR.leaveTypeName);
    }

    private void setLeaveType(int positionType) {
        String[] leaveType = mContext.getResources().getStringArray(R.array.leave_type);
        for (int i = 0; i < leaveType.length; i++) {
            if (positionType == i) {
                setLeaveTypeName(leaveType[i]);
            }
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
}
