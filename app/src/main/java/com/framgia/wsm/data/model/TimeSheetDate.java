package com.framgia.wsm.data.model;

import com.framgia.wsm.utils.common.DateTimeUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Date;

/**
 * Created by framgia on 19/05/2017.
 */

public class TimeSheetDate extends BaseModel {
    String mDate;
    int mTimeIn;
    int mTimeOut;
    @Expose
    @SerializedName("status")
    Status mStatus;

    public TimeSheetDate(String date, int timeIn, int timeOut, Status status) {
        this.mDate = date;
        this.mTimeIn = timeIn;
        this.mTimeOut = timeOut;
        this.mStatus = status;
    }

    public String getDateString() {
        return mDate;
    }

    public Date getDate() {
        return DateTimeUtils.convertStringToDate(mDate);
    }

    public Status getStatus() {
        return mStatus;
    }

    public enum Status {
        @Expose @SerializedName("normal")
        NORMAL(0),
        @Expose @SerializedName("in_late_leave_early")
        IN_LATE_LEAVE_EARLY(1),
        @Expose @SerializedName("holiday")
        HOLIDAY_DATE(2),
        @Expose @SerializedName("date_off_have_salary")
        DATE_OFF_HAVE_SALARY(3),
        @Expose @SerializedName("date_off_no_salary")
        DATE_OFF_NO_SALARY(4),
        @Expose @SerializedName("forgot_checkin_checkout")
        FORGOT_CHECK_IN_CHECK_OUT(5),
        @Expose @SerializedName("in_late_leave_early_have_compensation")
        IN_LATE_LEAVE_EARLY_HAVE_COMPENSATION(6);

        private int mValue;

        Status(int value) {
            this.mValue = value;
        }

        public int getValue() {
            return this.mValue;
        }

        public static Status getValue(int value) {
            switch (value) {
                case 0:
                    return NORMAL;
                case 1:
                    return IN_LATE_LEAVE_EARLY;
                case 2:
                    return HOLIDAY_DATE;
                case 3:
                    return DATE_OFF_HAVE_SALARY;
                case 4:
                    return DATE_OFF_NO_SALARY;
                case 5:
                    return FORGOT_CHECK_IN_CHECK_OUT;
                case 6:
                    return IN_LATE_LEAVE_EARLY_HAVE_COMPENSATION;
                default:
                    return NORMAL;
            }
        }
    }
}
