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
    @SerializedName("holiday_name")
    String mHolidayName;
    @Expose
    @SerializedName("status")
    Status mStatus;

    public HolidayCalendarDate(String date, Status status) {
        mDate = date;
        mStatus = status;
    }

    public Date getDate() {
        return DateTimeUtils.convertStringToDate(mDate);
    }

    public void setDate(String date) {
        mDate = date;
    }

    public Status getStatus() {
        return mStatus;
    }

    public void setStatus(Status status) {
        mStatus = status;
    }

    public String getHolidayName() {
        return mHolidayName;
    }

    public void setHolidayName(String holidayName) {
        mHolidayName = holidayName;
    }

    public enum Status {
        @Expose @SerializedName("normal")
        NORMAL(0), @Expose @SerializedName("normal_holiday")
        NORMAL_HOLIDAY(1), @Expose @SerializedName("company_holiday")
        COMPANY_HOLIDAY(2);

        private int mValue;

        Status(int value) {
            this.mValue = value;
        }

        public static Status getValue(int value) {
            switch (value) {
                case 0:
                    return NORMAL;
                case 1:
                    return NORMAL_HOLIDAY;
                case 2:
                    return COMPANY_HOLIDAY;
                default:
                    return NORMAL;
            }
        }

        public int getValue() {
            return this.mValue;
        }
    }
}
