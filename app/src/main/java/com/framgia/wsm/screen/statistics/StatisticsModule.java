package com.framgia.wsm.screen.statistics;

import android.content.Context;
import com.framgia.wsm.utils.dagger.FragmentScope;
import dagger.Module;
import dagger.Provides;

/**
 * Created by nguyenhuy95dn on 8/2/2017.
 */

@Module
public class StatisticsModule {

    public StatisticsModule() {
    }

    @FragmentScope
    @Provides
    public StatisticsContract.ViewModel provideViewModel(Context context,
            StatisticsContract.Presenter presenter) {
        return new StatisticsViewModel(context, presenter);
    }

    @FragmentScope
    @Provides
    public StatisticsContract.Presenter providePresenter() {
        return new StatisticsPresenter();
    }
}
