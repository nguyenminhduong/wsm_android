package com.framgia.wsm.firebase;

import android.app.Service;
import android.support.annotation.NonNull;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.local.UserLocalDataSource;
import com.framgia.wsm.data.source.remote.UserRemoteDataSource;
import com.framgia.wsm.utils.dagger.ServiceScope;
import dagger.Module;
import dagger.Provides;

/**
 * Created by ASUS on 03/08/2017.
 */

@Module
public class FireBaseModule {

    private Service mService;

    public FireBaseModule(@NonNull Service service) {
        this.mService = service;
    }

    @ServiceScope
    @Provides
    UserRepository provideUserRepository(UserLocalDataSource localDataSource,
            UserRemoteDataSource remoteDataSource) {
        return new UserRepository(localDataSource, remoteDataSource);
    }
}
