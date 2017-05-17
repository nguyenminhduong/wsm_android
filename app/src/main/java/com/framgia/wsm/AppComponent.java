package com.framgia.wsm;

import android.content.Context;
import com.framgia.wsm.data.source.RepositoryModule;
import com.framgia.wsm.data.source.remote.api.NetworkModule;
import com.framgia.wsm.data.source.remote.api.service.WSMApi;
import com.framgia.wsm.utils.dagger.AppScope;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import dagger.Component;

/**
 * Created by le.quang.dao on 21/03/2017.
 */

@AppScope
@Component(modules = { ApplicationModule.class, NetworkModule.class, RepositoryModule.class })
public interface AppComponent {

    //============== Region for Repository ================//

    WSMApi nameApi();

    //=============== Region for common ===============//

    Context applicationContext();

    BaseSchedulerProvider baseSchedulerProvider();
}
