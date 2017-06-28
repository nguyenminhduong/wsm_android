package com.framgia.wsm.data.source.remote.api.response;

import com.framgia.wsm.data.model.HolidayCalendar;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by minhd on 6/28/2017.
 */

public class HolidayCalendarResponse {
    @Expose
    @SerializedName("holiday_calendar")
    List<HolidayCalendar> mHolidayCalendars;

    public List<HolidayCalendar> getHolidayCalendars() {
        return mHolidayCalendars;
    }

    public void setHolidayCalendars(List<HolidayCalendar> holidayCalendars) {
        mHolidayCalendars = holidayCalendars;
    }
}
