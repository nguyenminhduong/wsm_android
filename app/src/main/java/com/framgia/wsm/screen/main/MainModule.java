package com.framgia.wsm.screen.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.local.UserLocalDataSource;
import com.framgia.wsm.data.source.remote.UserRemoteDataSource;
import com.framgia.wsm.utils.dagger.ActivityScope;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
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
    MainContract.ViewModel provideViewModel(Context context, MainContract.Presenter presenter,
            MainViewPagerAdapter viewPagerAdapter, Navigator navigator) {
        return new MainViewModel(context, presenter, viewPagerAdapter, navigator);
    }

    @ActivityScope
    @Provides
    MainContract.Presenter providePresenter(UserRepository userRepository,
            BaseSchedulerProvider baseSchedulerProvider) {
        return new MainPresenter(userRepository, baseSchedulerProvider);
    }

    @ActivityScope
    @Provides
    UserRepository provideUserRepository(UserLocalDataSource localDataSource,
            UserRemoteDataSource remoteDataSource) {
        return new UserRepository(localDataSource, remoteDataSource);
    }

    @ActivityScope
    @Provides
    Navigator provideNavigator() {
        return new Navigator(mActivity);
    }

    @ActivityScope
    @Provides
    MainViewPagerAdapter provideViewPagerAdapter() {
        return new MainViewPagerAdapter(mActivity.getSupportFragmentManager());
    }
}
