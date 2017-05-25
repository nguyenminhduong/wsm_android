package com.framgia.wsm.data.source;

import com.framgia.wsm.data.source.remote.TimeSheetRemoteDataSource;

/**
 * Created by Duong on 5/24/2017.
 */

public class TimeSheetRepository {
    private TimeSheetDataSource.RemoteDataSource mRemoteDataSource;

    public TimeSheetRepository(TimeSheetRemoteDataSource remoteDataSource) {
        mRemoteDataSource = remoteDataSource;
    }
}
