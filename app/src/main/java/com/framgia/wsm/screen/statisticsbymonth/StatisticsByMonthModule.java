package com.framgia.wsm.screen.statisticsbymonth;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import com.framgia.wsm.utils.dagger.FragmentScope;
import dagger.Module;
import dagger.Provides;

/**
 * Created by nguyenhuy95dn on 8/3/2017.
 */

@Module
public class StatisticsByMonthModule {
    private Fragment mFragment;

    public StatisticsByMonthModule(@NonNull Fragment fragment) {
        mFragment = fragment;
    }

    @FragmentScope
    @Provides
    public StatisticsByMonthContract.ViewModel provideViewModel(
            StatisticsByMonthContract.Presenter presenter) {
        return new StatisticsByMonthViewModel(presenter);
    }

    @FragmentScope
    @Provides
    public StatisticsByMonthContract.Presenter providePresenter() {
        return new StatisticsByMonthPresenter();
    }
}
