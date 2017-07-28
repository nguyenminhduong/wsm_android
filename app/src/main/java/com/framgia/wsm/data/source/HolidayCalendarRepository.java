package com.framgia.wsm.data.source;

import com.framgia.wsm.data.model.HolidayCalendar;
import com.framgia.wsm.data.source.remote.HolidayCalendarRemoteDataSource;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import io.reactivex.Observable;
import io.reactivex.Single;
import java.util.List;

/**
 * Created by minhd on 6/28/2017.
 */

public class HolidayCalendarRepository {
    private HolidayCalendarDataSource.RemoteDataSource mRemoteDataSource;

    public HolidayCalendarRepository(HolidayCalendarRemoteDataSource remoteDataSource) {
        mRemoteDataSource = remoteDataSource;
    }

    public Single<BaseResponse<List<HolidayCalendar>>> getHolidayCalendar(int year) {
        return mRemoteDataSource.getHolidayCalendar(year);
    }
}
