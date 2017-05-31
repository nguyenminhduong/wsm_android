package com.framgia.wsm.screen.requestleave;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.utils.navigator.Navigator;

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

    public RequestLeaveViewModel(Context context, Navigator navigator,
            RequestLeaveContract.Presenter presenter) {
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mContext = context;
        mNavigator = navigator;
    }

    @Bindable
    public String getTitleToolbar() {
        return mContext.getResources().getString(R.string.request_leave);
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
