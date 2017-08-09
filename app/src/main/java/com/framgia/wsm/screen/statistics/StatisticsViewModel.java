package com.framgia.wsm.screen.statistics;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.util.Log;
import com.framgia.wsm.BR;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.Chart;
import com.framgia.wsm.data.model.StatisticOfPersonal;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.statisticsbymonth.StatisticsByMonthFragment;
import com.framgia.wsm.screen.statisticsbyyear.StatisticsByYearFragment;
import com.framgia.wsm.utils.string.StringUtils;
import com.framgia.wsm.widget.dialog.DialogManager;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Exposes the data to be used in the Statistics screen.
 */

public class StatisticsViewModel extends BaseObservable implements StatisticsContract.ViewModel {

    private static final String TAG = "StatisticsViewModel";
    private static final int NUMBER_DAY_OFF = 0;
    private static final int NUMBER_LEAVE_TIME = 1;
    private static final int NUMBER_OVERTIME = 2;
    private static final int MONTH_IN_YEAR = 12;

    private Context mContext;
    private StatisticsContract.Presenter mPresenter;
    private final StatisticContainerAdapter mAdapter;
    private float[] mDayOffData;
    private float[] mLeaveTimeData;
    private float[] mOverTimeData;
    private boolean isLoading;
    private DialogManager mDialogManager;

    StatisticsViewModel(Context context, StatisticsContract.Presenter presenter,
            StatisticContainerAdapter adapter, DialogManager dialogManager) {
        mContext = context;
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mAdapter = adapter;
        mDialogManager = dialogManager;
        isLoading = false;
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Bindable
    public LineData getCombinedChar() {
        return getLineDatas();
    }

    public List<String> getMonths() {
        return StringUtils.getListMonths(mContext);
    }

    public StatisticContainerAdapter getAdapter() {
        return mAdapter;
    }

    private LineDataSet setLineDataSet(ArrayList<Entry> entries, String legendName, int color) {
        LineDataSet dataSet = new LineDataSet(entries, legendName);
        dataSet.setColor(color);
        dataSet.setLineWidth(2.5f);
        dataSet.setCircleColor(color);
        dataSet.setCircleRadius(4f);
        dataSet.setValueFormatter(new LargeValueFormatter());
        dataSet.setFillColor(color);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setDrawValues(false);
        dataSet.setValueTextSize(mContext.getResources().getDimension(R.dimen.sp_4));
        dataSet.setValueTextColor(color);
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        return dataSet;
    }

    private DataSet requestOff() {
        ArrayList<Entry> entries = new ArrayList<>();
        for (int index = 0; index < MONTH_IN_YEAR; index++) {
            entries.add(new Entry(index, mDayOffData[index]));
        }
        return setLineDataSet(entries, mContext.getString(R.string.request_off_approved),
                Color.BLUE);
    }

    private DataSet requestLeave() {
        ArrayList<Entry> entries = new ArrayList<>();
        for (int index = 0; index < MONTH_IN_YEAR; index++) {
            entries.add(new Entry(index, mLeaveTimeData[index]));
        }
        return setLineDataSet(entries, mContext.getString(R.string.request_leave_legend),
                Color.GRAY);
    }

    private DataSet requestOT() {
        ArrayList<Entry> entries = new ArrayList<>();
        for (int index = 0; index < MONTH_IN_YEAR; index++) {
            entries.add(new Entry(index, mOverTimeData[index]));
        }
        return setLineDataSet(entries, mContext.getString(R.string.request_ot_approved),
                Color.GREEN);
    }

    private LineData getLineDatas() {
        LineData lineDatas = new LineData();
        if (isLoading) {
            lineDatas.addDataSet((ILineDataSet) requestOff());
            lineDatas.addDataSet((ILineDataSet) requestLeave());
            lineDatas.addDataSet((ILineDataSet) requestOT());
        }
        return lineDatas;
    }

    @Override
    public void onGetStatisticsSuccess(List<Chart> statisticOfCharts) {
        isLoading = true;
        mDayOffData = statisticOfCharts.get(NUMBER_DAY_OFF).getDataChart();
        mLeaveTimeData = statisticOfCharts.get(NUMBER_LEAVE_TIME).getDataChart();
        mOverTimeData = statisticOfCharts.get(NUMBER_OVERTIME).getDataChart();
        notifyPropertyChanged(BR.combinedChar);
    }

    @Override
    public void onGetStatisticsError(BaseException error) {
        Log.e(TAG, "", error);
    }

    @Override
    public void onGetStatisticsByMonthSuccess(StatisticOfPersonal statisticOfPersonal) {
        Fragment fragment =
                mAdapter.getFragment(StatisticContainerAdapter.StatisticResultTab.TAB_MONTH);
        ((StatisticsByMonthFragment) fragment).setStatistic(statisticOfPersonal);
    }

    @Override
    public void onGetStatisticsByYearSuccess(StatisticOfPersonal statisticOfPersonal) {
        Fragment fragment =
                mAdapter.getFragment(StatisticContainerAdapter.StatisticResultTab.TAB_YEAR);
        ((StatisticsByYearFragment) fragment).setStatistic(statisticOfPersonal);
    }

    @Override
    public void onShowIndeterminateProgressDialog() {
        mDialogManager.showIndeterminateProgressDialog();
    }

    @Override
    public void onDismissProgressDialog() {
        mDialogManager.dismissProgressDialog();
    }

    @Override
    public void onReloadData() {
        mPresenter.getStatistics();
    }
}
