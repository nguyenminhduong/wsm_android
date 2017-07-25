package com.framgia.wsm;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;
import com.crashlytics.android.Crashlytics;
import com.framgia.wsm.data.source.RepositoryModule;
import com.framgia.wsm.data.source.remote.api.NetworkModule;
import io.fabric.sdk.android.Fabric;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public class MainApplication extends Application {

    private AppComponent mAppComponent;

    public AppComponent getAppComponent() {
        if (mAppComponent == null) {
            mAppComponent = DaggerAppComponent.builder()
                    .applicationModule(new ApplicationModule(getApplicationContext()))
                    .networkModule(new NetworkModule(this))
                    .repositoryModule(new RepositoryModule())
                    .build();
        }
        return mAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
}
