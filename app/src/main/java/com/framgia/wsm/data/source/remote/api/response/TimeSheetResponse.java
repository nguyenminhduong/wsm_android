package com.framgia.wsm.data.source.remote.api.response;

import com.framgia.wsm.data.model.UserTimeSheet;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Duong on 5/26/2017.
 */

public class TimeSheetResponse {
    @Expose
    @SerializedName("month")
    private String mMonth;
    @Expose
    @SerializedName("year")
    private String mYear;
    @Expose
    @SerializedName("usertimesheets")
    private UserTimeSheet mUserTimeSheet;

    public String getMonth() {
        return mMonth;
    }

    public void setMonth(String month) {
        mMonth = month;
    }

    public String getYear() {
        return mYear;
    }

    public void setYear(String year) {
        mYear = year;
    }

    public UserTimeSheet getUserTimeSheet() {
        return mUserTimeSheet;
    }

    public void setUserTimeSheet(UserTimeSheet userTimeSheet) {
        mUserTimeSheet = userTimeSheet;
    }
}
