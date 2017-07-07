package com.framgia.wsm.data.source;

import com.framgia.wsm.data.model.UserTimeSheet;
import com.framgia.wsm.data.source.remote.TimeSheetRemoteDataSource;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import io.reactivex.Observable;

/**
 * Created by Duong on 6/24/2017.
 */

public class TimeSheetRepository {
    private TimeSheetDataSource.RemoteDataSource mRemoteDataSource;

    public TimeSheetRepository(TimeSheetRemoteDataSource remoteDataSource) {
        mRemoteDataSource = remoteDataSource;
    }

    public Observable<BaseResponse<UserTimeSheet>> getTimeSheet(int month, int year) {
        return mRemoteDataSource.getTimeSheet(month, year);
    }
}
