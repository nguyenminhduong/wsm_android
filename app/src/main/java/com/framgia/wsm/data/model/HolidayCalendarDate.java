package com.framgia.wsm.data.model;

import com.framgia.wsm.utils.common.DateTimeUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Date;

/**
 * Created by minhd on 6/28/2017.
 */

public class HolidayCalendarDate extends BaseModel {
    @Expose
    @SerializedName("date")
    String mDate;
    @Expose
    @SerializedName("name")
    String mHolidayName;
    @Expose
    @SerializedName("holiday_type")
    String mHolidayType;

    public Date getDate() {
        return DateTimeUtils.convertStringToDate(mDate, DateTimeUtils.DATE_FORMAT_YYYY_MM_DD_2);
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getHolidayName() {
        return mHolidayName;
    }

    public void setHolidayName(String holidayName) {
        mHolidayName = holidayName;
    }

    public String getHolidayType() {
        return mHolidayType;
    }

    public void setHolidayType(String holidayType) {
        mHolidayType = holidayType;
    }
}
