package com.framgia.wsm.screen.confirmrequestleave;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import com.framgia.wsm.data.model.Request;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.dagger.ActivityScope;
import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the {@link ConfirmRequestLeavePresenter}.
 */
@Module
public class ConfirmRequestLeaveModule {

    private Activity mActivity;

    public ConfirmRequestLeaveModule(@NonNull Activity activity) {
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    public ConfirmRequestLeaveContract.ViewModel provideViewModel(Context context,
            ConfirmRequestLeaveContract.Presenter presenter) {
        Request requestLeave =
                mActivity.getIntent().getParcelableExtra(Constant.EXTRA_REQUEST_LEAVE);
        return new ConfirmRequestLeaveViewModel(context, presenter, requestLeave);
    }

    @ActivityScope
    @Provides
    public ConfirmRequestLeaveContract.Presenter providePresenter() {
        return new ConfirmRequestLeavePresenter();
    }
}
