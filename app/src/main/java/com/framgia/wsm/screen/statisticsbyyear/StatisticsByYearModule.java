package com.framgia.wsm.screen.statisticsbyyear;

import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.local.UserLocalDataSource;
import com.framgia.wsm.data.source.remote.UserRemoteDataSource;
import com.framgia.wsm.utils.dagger.FragmentScope;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import dagger.Module;
import dagger.Provides;

/**
 * Created by nguyenhuy95dn on 8/4/2017.
 */

@Module
class StatisticsByYearModule {

    @FragmentScope
    @Provides
    public StatisticsByYearContract.ViewModel provideViewModel(
            StatisticsByYearContract.Presenter presenter) {
        return new StatisticsByYearViewModel(presenter);
    }

    @FragmentScope
    @Provides
    public StatisticsByYearContract.Presenter providePresenter(UserRepository userRepository,
            BaseSchedulerProvider baseSchedulerProvider) {
        return new StatisticsByYearPresenter(userRepository, baseSchedulerProvider);
    }

    @FragmentScope
    @Provides
    public UserRepository provideUserRepository(UserLocalDataSource userLocalDataSource,
            UserRemoteDataSource userRemoteDataSource) {
        return new UserRepository(userLocalDataSource, userRemoteDataSource);
    }
}
