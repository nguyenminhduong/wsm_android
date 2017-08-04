package com.framgia.wsm.screen.statisticsbyyear;

import com.framgia.wsm.screen.main.MainComponent;
import com.framgia.wsm.utils.dagger.FragmentScope;
import dagger.Component;

/**
 * Created by nguyenhuy95dn on 8/4/2017.
 */

@FragmentScope
@Component(dependencies = MainComponent.class, modules = StatisticsByYearModule.class)
public interface StatisticsByYearComponent {
    void inject(StatisticsByYearFragment statisticsByYearFragment);
}
