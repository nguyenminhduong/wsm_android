package com.framgia.wsm.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nguyenhuy95dn on 8/4/2017.
 */

public class Chart {
    @Expose
    @SerializedName("name")
    private String mName;
    @Expose
    @SerializedName("data")
    private float[] mDataChart;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public float[] getDataChart() {
        return mDataChart;
    }

    public void setDataChart(float[] dataChart) {
        mDataChart = dataChart;
    }
}
