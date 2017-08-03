package com.framgia.wsm.screen.statistics;

import com.framgia.wsm.screen.main.MainComponent;
import com.framgia.wsm.utils.dagger.FragmentScope;
import dagger.Component;

/**
 * Created by nguyenhuy95dn on 8/2/2017.
 */

@FragmentScope
@Component(dependencies = MainComponent.class, modules = StatisticsModule.class)
public interface StatisticsComponent {
    void inject(StatisticsFragment statisticsFragment);
}
