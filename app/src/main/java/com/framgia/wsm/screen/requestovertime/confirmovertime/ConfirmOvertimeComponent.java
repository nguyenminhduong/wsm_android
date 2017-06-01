package com.framgia.wsm.screen.requestovertime.confirmovertime;

import com.framgia.wsm.AppComponent;
import com.framgia.wsm.utils.dagger.ActivityScope;
import dagger.Component;

/**
 * This is a Dagger component. Refer to
 * {@link com.framgia.wsm.screen.requestovertime.confirmovertime.MainApplication}
 * for the list of Dagger components
 * used in this application.
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = ConfirmOvertimeModule.class)
public interface ConfirmOvertimeComponent {
    void inject(ConfirmOvertimeActivity confirmovertimeActivity);
}
