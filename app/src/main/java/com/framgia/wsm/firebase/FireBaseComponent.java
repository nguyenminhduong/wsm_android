package com.framgia.wsm.firebase;

import com.framgia.wsm.AppComponent;
import com.framgia.wsm.utils.dagger.ServiceScope;
import dagger.Component;

/**
 * Created by ASUS on 03/08/2017.
 */
@ServiceScope
@Component(dependencies = AppComponent.class, modules = FireBaseModule.class)
public interface FireBaseComponent {
    void inject(FireBaseMessageService fireBaseMessageService);
}
