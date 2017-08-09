package com.framgia.wsm.data.source;

import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.response.StatisticsResponse;
import io.reactivex.Single;

/**
 * Created by nguyenhuy95dn on 8/4/2017.
 */

public class StatisticsDataSource {

    /**
     * RemoteDataSource
     */
    public interface RemoteDataSource {
        Single<BaseResponse<StatisticsResponse>> getStatistics();
    }
}
