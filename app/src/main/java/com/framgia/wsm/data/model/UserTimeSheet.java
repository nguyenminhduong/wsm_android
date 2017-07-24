package com.framgia.wsm.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by minhd on 7/6/2017.
 */

public class UserTimeSheet {
    @Expose
    @SerializedName("month")
    private String mMonth;
    @Expose
    @SerializedName("total_day_off")
    private float mTotalDayOff;
    @Expose
    @SerializedName("number_day_off_have_salary")
    private float mNumberDayOffHaveSalary;
    @Expose
    @SerializedName("number_day_off_no_salary")
    private float mNumberDayOffNoSalary;
    @Expose
    @SerializedName("number_over_time")
    private float mNumberOverTime;
    @Expose
    @SerializedName("total_fining")
    private float mTotalFining;
    @Expose
    @SerializedName("timesheet")
    private List<TimeSheetDate> mTimeSheetDates;

    public String getMonth() {
        return mMonth;
    }

    public void setMonth(String month) {
        mMonth = month;
    }

    public double getTotalDayOff() {
        return mTotalDayOff;
    }

    public void setTotalDayOff(float totalDayOff) {
        mTotalDayOff = totalDayOff;
    }

    public float getNumberDayOffHaveSalary() {
        return mNumberDayOffHaveSalary;
    }

    public void setNumberDayOffHaveSalary(float numberDayOffHaveSalary) {
        mNumberDayOffHaveSalary = numberDayOffHaveSalary;
    }

    public float getNumberDayOffNoSalary() {
        return mNumberDayOffNoSalary;
    }

    public void setNumberDayOffNoSalary(float numberDayOffNoSalary) {
        mNumberDayOffNoSalary = numberDayOffNoSalary;
    }

    public float getNumberOverTime() {
        return mNumberOverTime;
    }

    public void setNumberOverTime(float numberOverTime) {
        mNumberOverTime = numberOverTime;
    }

    public float getTotalFining() {
        return mTotalFining;
    }

    public void setTotalFining(float totalFining) {
        mTotalFining = totalFining;
    }

    public List<TimeSheetDate> getTimeSheetDates() {
        return mTimeSheetDates;
    }

    public void setTimeSheetDates(List<TimeSheetDate> timeSheetDates) {
        mTimeSheetDates = timeSheetDates;
    }
}
