package com.framgia.wsm.screen.statisticsbymonth;

import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.local.UserLocalDataSource;
import com.framgia.wsm.data.source.remote.UserRemoteDataSource;
import com.framgia.wsm.utils.dagger.FragmentScope;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import dagger.Module;
import dagger.Provides;

/**
 * Created by nguyenhuy95dn on 8/3/2017.
 */

@Module
class StatisticsByMonthModule {

    @FragmentScope
    @Provides
    public StatisticsByMonthContract.ViewModel provideViewModel(
            StatisticsByMonthContract.Presenter presenter) {
        return new StatisticsByMonthViewModel(presenter);
    }

    @FragmentScope
    @Provides
    public StatisticsByMonthContract.Presenter providePresenter(UserRepository userRepository,
            BaseSchedulerProvider baseSchedulerProvider) {
        return new StatisticsByMonthPresenter(userRepository, baseSchedulerProvider);
    }

    @FragmentScope
    @Provides
    public UserRepository provideUserRepository(UserLocalDataSource userLocalDataSource,
            UserRemoteDataSource userRemoteDataSource) {
        return new UserRepository(userLocalDataSource, userRemoteDataSource);
    }
}
