package com.framgia.wsm.screen.main;

import com.framgia.wsm.AppComponent;
import com.framgia.wsm.utils.dagger.ActivityScope;
import dagger.Component;

/**
 * This is a Dagger component. Refer to {@link com.framgia.wsm.screen.main.MainApplication} for the
 * list of Dagger components
 * used in this application.
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = MainModule.class)
public interface MainComponent {
    void inject(MainActivity mainActivity);
}
