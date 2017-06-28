package com.framgia.wsm.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by minhd on 6/28/2017.
 */

public class HolidayCalendar extends BaseModel {
    @Expose
    @SerializedName("month")
    private int mMonth;
    @Expose
    @SerializedName("year")
    private int mYear;
    @Expose
    @SerializedName("data")
    private List<HolidayCalendarDate> mHolidayCalendarDates;

    public int getMonth() {
        return mMonth;
    }

    public void setMonth(int month) {
        mMonth = month;
    }

    public int getYear() {
        return mYear;
    }

    public void setYear(int year) {
        mYear = year;
    }

    public List<HolidayCalendarDate> getHolidayCalendarDates() {
        return mHolidayCalendarDates;
    }

    public void setHolidayCalendarDates(List<HolidayCalendarDate> holidayCalendarDates) {
        mHolidayCalendarDates = holidayCalendarDates;
    }
}
