package com.framgia.wsm.screen.requestovertime.confirmovertime;

import android.app.Activity;
import android.support.annotation.NonNull;
import com.framgia.wsm.utils.dagger.ActivityScope;
import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the {@link ConfirmOvertimePresenter}.
 */
@Module
public class ConfirmOvertimeModule {

    private Activity mActivity;

    public ConfirmOvertimeModule(@NonNull Activity activity) {
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    public ConfirmOvertimeContract.ViewModel provideViewModel(
            ConfirmOvertimeContract.Presenter presenter) {
        return new ConfirmOvertimeViewModel(presenter);
    }

    @ActivityScope
    @Provides
    public ConfirmOvertimeContract.Presenter providePresenter() {
        return new ConfirmOvertimePresenter();
    }
}
