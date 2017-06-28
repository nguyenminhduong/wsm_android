package com.framgia.wsm.data.source;

import com.framgia.wsm.data.model.HolidayCalendar;
import com.framgia.wsm.data.model.HolidayCalendarDate;
import com.framgia.wsm.data.source.remote.HolidayCalendarRemoteDataSource;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.response.HolidayCalendarResponse;
import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by minhd on 6/28/2017.
 */

public class HolidayCalendarRepository {
    private HolidayCalendarDataSource.RemoteDataSource mRemoteDataSource;

    public HolidayCalendarRepository(HolidayCalendarRemoteDataSource remoteDataSource) {
        mRemoteDataSource = remoteDataSource;
    }

    public Observable<BaseResponse<HolidayCalendarResponse>> getHolidayCalendar(int year) {
        // todo edit later
        List<HolidayCalendar> holidayCalendars = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            HolidayCalendar holidayCalendar = new HolidayCalendar();
            holidayCalendar.setYear(year);
            holidayCalendar.setMonth(i);
            holidayCalendar.setHolidayCalendarDates(new ArrayList<HolidayCalendarDate>());
            holidayCalendars.add(holidayCalendar);
        }
        HolidayCalendarResponse holidayCalendarResponse = new HolidayCalendarResponse();
        holidayCalendarResponse.setHolidayCalendars(holidayCalendars);
        BaseResponse<HolidayCalendarResponse> baseResponse =
                new BaseResponse<HolidayCalendarResponse>(holidayCalendarResponse) {
                };
        return Observable.just(baseResponse);
    }
}
