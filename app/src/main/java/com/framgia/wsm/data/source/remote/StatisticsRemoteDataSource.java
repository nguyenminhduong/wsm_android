package com.framgia.wsm.data.source.remote;

import com.framgia.wsm.data.source.StatisticsDataSource;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.response.StatisticsResponse;
import com.framgia.wsm.data.source.remote.api.service.WSMApi;
import io.reactivex.Single;
import javax.inject.Inject;

/**
 * Created by nguyenhuy95dn on 8/4/2017.
 */

public class StatisticsRemoteDataSource extends BaseRemoteDataSource
        implements StatisticsDataSource.RemoteDataSource {

    @Inject
    public StatisticsRemoteDataSource(WSMApi api) {
        super(api);
    }

    @Override
    public Single<BaseResponse<StatisticsResponse>> getStatistics() {
        return mWSMApi.getStatistics();
    }
}
