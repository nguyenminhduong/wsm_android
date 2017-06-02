package com.framgia.wsm.screen.confirmrequestleave;

import android.app.Activity;
import android.support.annotation.NonNull;
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
    public ConfirmRequestLeaveContract.ViewModel provideViewModel(
            ConfirmRequestLeaveContract.Presenter presenter) {
        return new ConfirmRequestLeaveViewModel(presenter);
    }

    @ActivityScope
    @Provides
    public ConfirmRequestLeaveContract.Presenter providePresenter() {
        return new ConfirmRequestLeavePresenter();
    }
}
