package com.framgia.wsm.data.source;

import com.framgia.wsm.data.source.remote.StatisticsRemoteDataSource;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.response.StatisticsResponse;
import io.reactivex.Single;

/**
 * Created by nguyenhuy95dn on 8/4/2017.
 */

public class StatisticsRepository {
    private final StatisticsDataSource.RemoteDataSource mRemoteDataSource;

    public StatisticsRepository(StatisticsRemoteDataSource remoteDataSource) {
        mRemoteDataSource = remoteDataSource;
    }

    public Single<BaseResponse<StatisticsResponse>> getStatistic(int year) {
        return mRemoteDataSource.getStatistics(year);
    }
}
