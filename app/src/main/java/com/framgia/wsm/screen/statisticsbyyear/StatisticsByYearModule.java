package com.framgia.wsm.screen.statisticsbyyear;

import com.framgia.wsm.utils.dagger.FragmentScope;
import dagger.Module;
import dagger.Provides;

/**
 * Created by nguyenhuy95dn on 8/4/2017.
 */

@Module
public class StatisticsByYearModule {

    StatisticsByYearModule() {
    }

    @FragmentScope
    @Provides
    public StatisticsByYearContract.ViewModel provideViewModel(
            StatisticsByYearContract.Presenter presenter) {
        return new StatisticsByYearViewModel(presenter);
    }

    @FragmentScope
    @Provides
    public StatisticsByYearContract.Presenter providePresenter() {
        return new StatisticsByYearPresenter();
    }
}
