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

    public TimeSheetDate() {
    }

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

    public void setDate(String date) {
        mDate = date;
    }

    public Status getStatus() {
        return mStatus;
    }

    public int getTimeIn() {
        return mTimeIn;
    }

    public void setTimeIn(int timeIn) {
        mTimeIn = timeIn;
    }

    public int getTimeOut() {
        return mTimeOut;
    }

    public void setTimeOut(int timeOut) {
        mTimeOut = timeOut;
    }

    public enum Status {
        @Expose @SerializedName("normal")
        NORMAL(0),
        @Expose @SerializedName("in_late_leave_early")
        IN_LATE_LEAVE_EARLY(1),
        @Expose @SerializedName("holiday")
        HOLIDAY_DATE(2),
        @Expose @SerializedName("forgot_checkin_checkout")
        FORGOT_CHECK_IN_CHECK_OUT(3),
        @Expose @SerializedName("in_late_leave_early_have_compensation")
        IN_LATE_LEAVE_EARLY_HAVE_COMPENSATION(4),
        @Expose @SerializedName("day_off_ro")
        DAY_OFF_RO(5),
        @Expose @SerializedName("day_off_half_ro")
        DAY_OFF_HALF_RO(6),
        @Expose @SerializedName("day_off_p")
        DAY_OFF_P(7),
        @Expose @SerializedName("day_off_half_p")
        DAY_OFF_HALF_P(8),
        @Expose @SerializedName("forgot_checkin_checkout_more_five_time")
        FORGOT_CHECK_IN_CHECK_OUT_MORE_FIVE_TIME(9);

        private int mValue;

        Status(int value) {
            this.mValue = value;
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
                    return FORGOT_CHECK_IN_CHECK_OUT;
                case 4:
                    return IN_LATE_LEAVE_EARLY_HAVE_COMPENSATION;
                case 5:
                    return DAY_OFF_RO;
                case 6:
                    return DAY_OFF_HALF_RO;
                case 7:
                    return DAY_OFF_P;
                case 8:
                    return DAY_OFF_HALF_P;
                case 9:
                    return FORGOT_CHECK_IN_CHECK_OUT_MORE_FIVE_TIME;
                default:
                    return NORMAL;
            }
        }

        public int getValue() {
            return this.mValue;
        }
    }
}
