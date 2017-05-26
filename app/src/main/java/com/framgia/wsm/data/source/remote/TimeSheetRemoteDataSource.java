package com.framgia.wsm.data.source.remote;

import com.framgia.wsm.data.source.TimeSheetDataSource;
import com.framgia.wsm.data.source.remote.api.service.WSMApi;
import javax.inject.Inject;

/**
 * Created by Duong on 5/24/2017.
 */

public class TimeSheetRemoteDataSource extends BaseRemoteDataSource
        implements TimeSheetDataSource.RemoteDataSource {

    @Inject
    public TimeSheetRemoteDataSource(WSMApi api) {
        super(api);
    }
}
