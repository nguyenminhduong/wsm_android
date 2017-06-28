package com.framgia.wsm.data.source;

import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.response.HolidayCalendarResponse;
import io.reactivex.Observable;

/**
 * Created by minhd on 6/28/2017.
 */

public interface HolidayCalendarDataSource {

    interface RemoteDataSource {
        Observable<BaseResponse<HolidayCalendarResponse>> getHolidayCalendar(int year);
    }
}
