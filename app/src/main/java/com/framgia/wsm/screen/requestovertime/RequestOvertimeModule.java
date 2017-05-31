package com.framgia.wsm.screen.requestovertime;

import android.app.Activity;
import android.support.annotation.NonNull;
import com.framgia.wsm.utils.dagger.ActivityScope;
import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the {@link RequestOvertimePresenter}.
 */
@Module
public class RequestOvertimeModule {

    private Activity mActivity;

    public RequestOvertimeModule(@NonNull Activity activity) {
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    public RequestOvertimeContract.ViewModel provideViewModel(
            RequestOvertimeContract.Presenter presenter) {
        return new RequestOvertimeViewModel(presenter);
    }

    @ActivityScope
    @Provides
    public RequestOvertimeContract.Presenter providePresenter() {
        return new RequestOvertimePresenter();
    }
}
