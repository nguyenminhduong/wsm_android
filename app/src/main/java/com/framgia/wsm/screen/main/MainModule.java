package com.framgia.wsm.screen.main;

import android.app.Activity;
import android.support.annotation.NonNull;
import com.framgia.wsm.utils.dagger.ActivityScope;
import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the {@link MainPresenter}.
 */
@Module
public class MainModule {

    private Activity mActivity;

    public MainModule(@NonNull Activity activity) {
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    public MainContract.ViewModel provideViewModel(MainContract.Presenter presenter) {
        return new MainViewModel(presenter);
    }

    @ActivityScope
    @Provides
    public MainContract.Presenter providePresenter() {
        return new MainPresenter();
    }
}
