package com.framgia.wsm.data.source.remote.api.response;

import com.framgia.wsm.data.model.TimeSheetDate;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by Duong on 5/26/2017.
 */

public class TimeSheetResponse {
    @Expose
    @SerializedName("content")
    private List<TimeSheetDate> mTimeSheetDates;
    @Expose
    @SerializedName("status")
    private int mStatus;
    @Expose
    @SerializedName("message")
    private String mMessage;

    public List<TimeSheetDate> getTimeSheetDates() {
        return mTimeSheetDates;
    }

    public void setTimeSheetDates(List<TimeSheetDate> timeSheetDates) {
        mTimeSheetDates = timeSheetDates;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }
}
