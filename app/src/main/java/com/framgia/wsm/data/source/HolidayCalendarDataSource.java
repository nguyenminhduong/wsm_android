package com.framgia.wsm.data.source;

import com.framgia.wsm.data.model.HolidayCalendar;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import io.reactivex.Observable;
import java.util.List;

/**
 * Created by minhd on 6/28/2017.
 */

public interface HolidayCalendarDataSource {

    interface RemoteDataSource {
        Observable<BaseResponse<List<HolidayCalendar>>> getHolidayCalendar(int year);
    }
}
