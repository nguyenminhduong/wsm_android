package com.framgia.wsm.screen.requestovertime;

import android.app.Activity;
import android.support.annotation.NonNull;
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
            RequestOvertimeContract.Presenter presenter, Navigator navigator) {
        return new RequestOvertimeViewModel(presenter, navigator);
    }

    @ActivityScope
    @Provides
    public RequestOvertimeContract.Presenter providePresenter(UserRepository userRepository,
            BaseSchedulerProvider baseSchedulerProvider) {
        return new RequestOvertimePresenter(userRepository, baseSchedulerProvider);
    }

    @ActivityScope
    @Provides
    Navigator provideNavigator() {
        return new Navigator(mActivity);
    }

    @ActivityScope
    @Provides
    public UserRepository provideUserRepository(UserLocalDataSource userLocalDataSource,
            UserRemoteDataSource remoteDataSource) {
        return new UserRepository(userLocalDataSource, remoteDataSource);
    }
}
