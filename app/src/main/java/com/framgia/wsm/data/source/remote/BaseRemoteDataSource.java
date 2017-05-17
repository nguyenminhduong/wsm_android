package com.framgia.wsm.data.source.remote;

import com.framgia.wsm.data.source.remote.api.service.WSMApi;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public abstract class BaseRemoteDataSource {

    WSMApi mWSMApi;

    public BaseRemoteDataSource(WSMApi api) {
        mWSMApi = api;
    }
}
