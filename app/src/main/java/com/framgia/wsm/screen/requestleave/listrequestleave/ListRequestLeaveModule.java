package com.framgia.wsm.screen.requestleave.listrequestleave;

import android.app.Activity;
import android.support.annotation.NonNull;
import com.framgia.wsm.utils.dagger.ActivityScope;
import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the {@link ListRequestLeavePresenter}.
 */
@Module
public class ListRequestLeaveModule {

    private Activity mActivity;

    public ListRequestLeaveModule(@NonNull Activity activity) {
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    public ListRequestLeaveContract.ViewModel provideViewModel(
            ListRequestLeaveContract.Presenter presenter) {
        return new ListRequestLeaveViewModel(presenter);
    }

    @ActivityScope
    @Provides
    public ListRequestLeaveContract.Presenter providePresenter() {
        return new ListRequestLeavePresenter();
    }
}
