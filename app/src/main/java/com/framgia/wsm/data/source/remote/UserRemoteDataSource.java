package com.framgia.wsm.data.source.remote;

import com.framgia.wsm.data.source.UserDataSource;
import com.framgia.wsm.data.source.remote.api.service.WSMApi;
import javax.inject.Inject;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public class UserRemoteDataSource extends BaseRemoteDataSource
        implements UserDataSource.RemoteDataSource {

    @Inject
    public UserRemoteDataSource(WSMApi api) {
        super(api);
    }
}
