package com.framgia.wsm.screen.confirmrequestleave;

import com.framgia.wsm.AppComponent;
import com.framgia.wsm.utils.dagger.ActivityScope;
import dagger.Component;

/**
 * This is a Dagger component. Refer to {@link com.framgia.wsm.MainApplication} for the list of
 * Dagger components
 * used in this application.
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = ConfirmRequestLeaveModule.class)
public interface ConfirmRequestLeaveComponent {
    void inject(ConfirmRequestLeaveActivity confirmrequestleaveActivity);
}
