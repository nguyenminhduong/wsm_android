package com.framgia.wsm.screen.requestleave;

import android.app.Activity;
import android.support.annotation.NonNull;
import com.framgia.wsm.utils.dagger.ActivityScope;
import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the {@link RequestLeavePresenter}.
 */
@Module
public class RequestLeaveModule {

    private Activity mActivity;

    public RequestLeaveModule(@NonNull Activity activity) {
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    public RequestLeaveContract.ViewModel provideViewModel(
            RequestLeaveContract.Presenter presenter) {
        return new RequestLeaveViewModel(presenter);
    }

    @ActivityScope
    @Provides
    public RequestLeaveContract.Presenter providePresenter() {
        return new RequestLeavePresenter();
    }
}
