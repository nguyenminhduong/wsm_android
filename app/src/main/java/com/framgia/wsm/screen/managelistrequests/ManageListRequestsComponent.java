package com.framgia.wsm.screen.managelistrequests;

import com.framgia.wsm.screen.main.MainComponent;
import com.framgia.wsm.utils.dagger.FragmentScope;
import dagger.Component;

/**
 * This is a Dagger component. Refer to {@link com.framgia.wsm.MainApplication} for the list of
 * Dagger components
 * used in this application.
 */
@FragmentScope
@Component(dependencies = MainComponent.class, modules = ManageListRequestsModule.class)
public interface ManageListRequestsComponent {
    void inject(ManageListRequestsFragment managelistrequestsFragment);
}
