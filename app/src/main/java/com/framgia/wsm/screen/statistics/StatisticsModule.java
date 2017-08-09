package com.framgia.wsm.screen.statistics;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.framgia.wsm.data.source.StatisticsRepository;
import com.framgia.wsm.data.source.remote.StatisticsRemoteDataSource;
import com.framgia.wsm.utils.dagger.FragmentScope;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import com.framgia.wsm.widget.dialog.DialogManager;
import dagger.Module;
import dagger.Provides;

/**
 * Created by nguyenhuy95dn on 8/2/2017.
 */

@Module
public class StatisticsModule {

    private Fragment mFragment;

    public StatisticsModule(@NonNull Fragment fragment) {
        mFragment = fragment;
    }

    @FragmentScope
    @Provides
    public StatisticsContract.ViewModel provideViewModel(Context context,
            StatisticsContract.Presenter presenter, StatisticContainerAdapter adapter,
            DialogManager dialogManager) {
        return new StatisticsViewModel(context, presenter, adapter, dialogManager);
    }

    @FragmentScope
    @Provides
    public StatisticsContract.Presenter providePresenter(StatisticsRepository statisticsRepository,
            BaseSchedulerProvider baseSchedulerProvider) {
        return new StatisticsPresenter(statisticsRepository, baseSchedulerProvider);
    }

    @FragmentScope
    @Provides
    StatisticsRepository provideStatisticsRepository(
            StatisticsRemoteDataSource statisticsRemoteDataSource) {
        return new StatisticsRepository(statisticsRemoteDataSource);
    }

    @FragmentScope
    @Provides
    public StatisticContainerAdapter provideManageListRequestAdapter() {
        FragmentManager fragmentManager = mFragment.getFragmentManager();
        return new StatisticContainerAdapter(mFragment.getContext(), fragmentManager);
    }
}
