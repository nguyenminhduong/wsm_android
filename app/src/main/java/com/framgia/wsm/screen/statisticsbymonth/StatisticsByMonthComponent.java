package com.framgia.wsm.screen.statisticsbymonth;

import com.framgia.wsm.screen.main.MainComponent;
import com.framgia.wsm.utils.dagger.FragmentScope;
import dagger.Component;

/**
 * Created by nguyenhuy95dn on 8/3/2017.
 */

@FragmentScope
@Component(dependencies = MainComponent.class, modules = StatisticsByMonthModule.class)
public interface StatisticsByMonthComponent {
    void inject(StatisticsByMonthFragment statisticsByMonthFragment);
}
