package com.framgia.wsm.screen.main;

import android.content.Context;
import com.framgia.wsm.AppComponent;
import com.framgia.wsm.data.source.local.sharedprf.SharedPrefsApi;
import com.framgia.wsm.data.source.remote.api.service.WSMApi;
import com.framgia.wsm.utils.dagger.ActivityScope;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import com.framgia.wsm.widget.dialog.DialogManager;
import dagger.Component;

/**
 * This is a Dagger component. Refer to {@link com.framgia.wsm.screen.main.MainApplication} for the
 * list of Dagger components
 * used in this application.
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = MainModule.class)
public interface MainComponent {

    //============== Region for Repository ================//

    WSMApi nameApi();

    SharedPrefsApi sharedPrefsApi();

    //=============== Region for common ===============//

    Context applicationContext();

    BaseSchedulerProvider baseSchedulerProvider();

    DialogManager dialogManager();

    void inject(MainActivity mainActivity);
}
