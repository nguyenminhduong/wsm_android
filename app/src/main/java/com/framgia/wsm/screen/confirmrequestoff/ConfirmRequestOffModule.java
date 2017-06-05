package com.framgia.wsm.screen.confirmrequestoff;

import android.app.Activity;
import android.support.annotation.NonNull;
import com.framgia.wsm.utils.dagger.ActivityScope;
import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the {@link ConfirmRequestOffPresenter}.
 */
@Module
public class ConfirmRequestOffModule {

    private Activity mActivity;

    public ConfirmRequestOffModule(@NonNull Activity activity) {
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    public ConfirmRequestOffContract.ViewModel provideViewModel(
            ConfirmRequestOffContract.Presenter presenter) {
        return new ConfirmRequestOffViewModel(presenter);
    }

    @ActivityScope
    @Provides
    public ConfirmRequestOffContract.Presenter providePresenter() {
        return new ConfirmRequestOffPresenter();
    }
}
