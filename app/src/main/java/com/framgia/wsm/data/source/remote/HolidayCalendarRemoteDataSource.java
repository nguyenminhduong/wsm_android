package com.framgia.wsm.data.source.remote;

import com.framgia.wsm.data.source.HolidayCalendarDataSource;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.response.HolidayCalendarResponse;
import com.framgia.wsm.data.source.remote.api.service.WSMApi;
import io.reactivex.Observable;
import javax.inject.Inject;

/**
 * Created by minhd on 6/28/2017.
 */

public class HolidayCalendarRemoteDataSource extends BaseRemoteDataSource
        implements HolidayCalendarDataSource.RemoteDataSource {
    @Inject
    public HolidayCalendarRemoteDataSource(WSMApi api) {
        super(api);
    }

    @Override
    public Observable<BaseResponse<HolidayCalendarResponse>> getHolidayCalendar(int year) {
        return mWSMApi.getHolidayCalendar(year);
    }
}
