package com.framgia.wsm.screen.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import com.framgia.wsm.data.source.NotificationRepository;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.local.UserLocalDataSource;
import com.framgia.wsm.data.source.remote.NotificationRemoteDataSource;
import com.framgia.wsm.data.source.remote.UserRemoteDataSource;
import com.framgia.wsm.utils.dagger.ActivityScope;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import com.framgia.wsm.widget.dialog.DialogManager;
import com.framgia.wsm.widget.dialog.DialogManagerImpl;
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
            MainViewPagerAdapter viewPagerAdapter, Navigator navigator,
            DialogManager dialogManager) {
        return new MainViewModel(context, presenter, viewPagerAdapter, navigator, dialogManager);
    }

    @ActivityScope
    @Provides
    MainContract.Presenter providePresenter(UserRepository userRepository,
            NotificationRepository notificationRepository,
            BaseSchedulerProvider baseSchedulerProvider) {
        return new MainPresenter(userRepository, notificationRepository, baseSchedulerProvider);
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

    @ActivityScope
    @Provides
    NotificationRepository provideNotificationRepository(
            NotificationRemoteDataSource remoteDataSource) {
        return new NotificationRepository(remoteDataSource);
    }

    @ActivityScope
    @Provides
    DialogManager provideDialogManager() {
        return new DialogManagerImpl(mActivity);
    }
}
