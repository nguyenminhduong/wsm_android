package com.framgia.wsm.data.source.remote.api.response;

import com.framgia.wsm.data.model.Chart;
import com.framgia.wsm.data.model.StatisticOfPersonal;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by nguyenhuy95dn on 8/4/2017.
 */

public class StatisticsResponse {
    @Expose
    @SerializedName("statistics_of_month")
    private StatisticOfPersonal mStatisticOfPersonal;
    @Expose
    @SerializedName("statistics_of_year")
    private List<Chart> mStatisticOfChart;

    public StatisticOfPersonal getStatisticOfPersonal() {
        return mStatisticOfPersonal;
    }

    public void setStatisticOfPersonal(StatisticOfPersonal statisticOfPersonal) {
        mStatisticOfPersonal = statisticOfPersonal;
    }

    public List<Chart> getStatisticOfChart() {
        return mStatisticOfChart;
    }

    public void setStatisticOfChart(List<Chart> statisticOfChart) {
        mStatisticOfChart = statisticOfChart;
    }
}
