package com.framgia.wsm.screen.requestoff;

import android.content.Context;
import com.framgia.wsm.R;

/**
 * Exposes the data to be used in the RequestOff screen.
 */

public class RequestOffViewModel implements RequestOffContract.ViewModel {

    private RequestOffContract.Presenter mPresenter;
    private Context mContext;

    public RequestOffViewModel(Context context, RequestOffContract.Presenter presenter) {
        mContext = context;
        mPresenter = presenter;
        mPresenter.setViewModel(this);
    }

    public String getTitleToolbar() {
        return mContext.getResources().getString(R.string.create_request_off);
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
