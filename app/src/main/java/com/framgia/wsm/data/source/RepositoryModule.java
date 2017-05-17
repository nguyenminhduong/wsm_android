package com.framgia.wsm.data.source;

import com.framgia.wsm.utils.dagger.AppScope;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import com.framgia.wsm.utils.rx.SchedulerProvider;
import dagger.Module;
import dagger.Provides;

/**
 * Created by le.quang.dao on 21/03/2017.
 */

@Module
public class RepositoryModule {

    @Provides
    @AppScope
    public BaseSchedulerProvider provideBaseSchedulerProvider() {
        return SchedulerProvider.getInstance();
    }
}
