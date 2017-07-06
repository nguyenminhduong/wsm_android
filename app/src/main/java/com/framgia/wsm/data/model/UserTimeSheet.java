package com.framgia.wsm.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by minhd on 7/6/2017.
 */

public class UserTimeSheet {
    @Expose
    @SerializedName("total_day_off")
    private int mTotalDayOff;
    @Expose
    @SerializedName("number_day_off_have_salary")
    private int mNumberDayOffHaveSalary;
    @Expose
    @SerializedName("number_day_off_no_salary")
    private int mNumberDayOffNoSalary;
    @Expose
    @SerializedName("number_over_time")
    private Double mNumberOverTime;
    @Expose
    @SerializedName("total_fining")
    private int mTotalFining;
    @Expose
    @SerializedName("timesheets")
    private List<TimeSheetDate> mTimeSheetDates;

    public int getTotalDayOff() {
        return mTotalDayOff;
    }

    public void setTotalDayOff(int totalDayOff) {
        mTotalDayOff = totalDayOff;
    }

    public int getNumberDayOffHaveSalary() {
        return mNumberDayOffHaveSalary;
    }

    public void setNumberDayOffHaveSalary(int numberDayOffHaveSalary) {
        mNumberDayOffHaveSalary = numberDayOffHaveSalary;
    }

    public int getNumberDayOffNoSalary() {
        return mNumberDayOffNoSalary;
    }

    public void setNumberDayOffNoSalary(int numberDayOffNoSalary) {
        mNumberDayOffNoSalary = numberDayOffNoSalary;
    }

    public Double getNumberOverTime() {
        return mNumberOverTime;
    }

    public void setNumberOverTime(Double numberOverTime) {
        mNumberOverTime = numberOverTime;
    }

    public int getTotalFining() {
        return mTotalFining;
    }

    public void setTotalFining(int totalFining) {
        mTotalFining = totalFining;
    }

    public List<TimeSheetDate> getTimeSheetDates() {
        return mTimeSheetDates;
    }

    public void setTimeSheetDates(List<TimeSheetDate> timeSheetDates) {
        mTimeSheetDates = timeSheetDates;
    }
}
