package com.framgia.wsm.screen.main;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import com.framgia.wsm.utils.dagger.ActivityScope;
import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the {@link MainPresenter}.
 */
@Module
class MainModule {

    private AppCompatActivity mActivity;

    MainModule(@NonNull AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    MainContract.ViewModel provideViewModel(MainContract.Presenter presenter,
            MainViewPagerAdapter viewPagerAdapter) {
        return new MainViewModel(presenter, viewPagerAdapter);
    }

    @ActivityScope
    @Provides
    MainContract.Presenter providePresenter() {
        return new MainPresenter();
    }

    @ActivityScope
    @Provides
    MainViewPagerAdapter provideViewPagerAdapter() {
        return new MainViewPagerAdapter(mActivity.getSupportFragmentManager());
    }
}
