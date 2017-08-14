package com.framgia.wsm.screen.statistics;

import android.app.DatePickerDialog;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
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
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Exposes the data to be used in the Statistics screen.
 */

public class StatisticsViewModel extends BaseObservable
        implements StatisticsContract.ViewModel, OnChartValueSelectedListener,
        DatePickerDialog.OnDateSetListener {

    private static final String TAG = "StatisticsViewModel";
    private static final int NUMBER_DAY_OFF = 0;
    private static final int NUMBER_LEAVE_TIME = 1;
    private static final int NUMBER_OVERTIME = 2;
    private static final int MONTH_IN_YEAR = 12;

    private final Context mContext;
    private final StatisticsContract.Presenter mPresenter;
    private final StatisticContainerAdapter mAdapter;
    private float[] mDayOffData;
    private float[] mLeaveTimeData;
    private float[] mOverTimeData;
    private boolean mIsLoading;
    private final DialogManager mDialogManager;
    private boolean mIsClickChart;
    private String mDataChartDayOff;
    private String mDataChartLeaveTime;
    private String mDataChartOverTime;
    private String mMonthSelected;
    private boolean mIsFirstTimeLoad;
    private String mYearSelected;
    private int mYear;
    private int mMonth;
    private Calendar mCalendar;

    StatisticsViewModel(Context context, StatisticsContract.Presenter presenter,
            StatisticContainerAdapter adapter, DialogManager dialogManager) {
        mContext = context;
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mAdapter = adapter;
        mDialogManager = dialogManager;
        mCalendar = Calendar.getInstance();
        mYear = mCalendar.get(Calendar.YEAR);
        setYear(String.valueOf(mYear));
        mMonth = mCalendar.get(Calendar.MONTH) + 1;
        mDialogManager.dialogYearPicker(this, mYear, mMonth);
        mIsFirstTimeLoad = true;
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
        if (mIsLoading) {
            lineDatas.addDataSet((ILineDataSet) requestOff());
            lineDatas.addDataSet((ILineDataSet) requestLeave());
            lineDatas.addDataSet((ILineDataSet) requestOT());
        }
        return lineDatas;
    }

    @Override
    public void onGetStatisticsSuccess(List<Chart> statisticOfCharts) {
        mIsLoading = true;
        mDayOffData = statisticOfCharts.get(NUMBER_DAY_OFF).getDataChart();
        mLeaveTimeData = statisticOfCharts.get(NUMBER_LEAVE_TIME).getDataChart();
        mOverTimeData = statisticOfCharts.get(NUMBER_OVERTIME).getDataChart();
        notifyPropertyChanged(BR.combinedChar);
    }

    public void onPickYear(View view) {
        mDialogManager.showYearPickerDialog();
    }

    @Bindable
    public String getYear() {
        return mYearSelected;
    }

    private void setYear(String year) {
        mYearSelected = year;
        notifyPropertyChanged(BR.year);
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
        if (mIsFirstTimeLoad) {
            mPresenter.getStatistics(Integer.parseInt(mYearSelected));
            mIsFirstTimeLoad = false;
        }
    }

    public OnChartValueSelectedListener getOnChartValueSelected() {
        return this;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        setDataChartDayOff(String.valueOf(mDayOffData[(int) e.getX()]));
        setDataChartLeaveTime(String.valueOf(mLeaveTimeData[(int) e.getX()]));
        setDataChartOverTime(String.valueOf(mOverTimeData[(int) e.getX()]));
        showMonthSelected((int) e.getX());
        setClickChart(true);
    }

    @Override
    public void onNothingSelected() {
        setClickChart(false);
    }

    @Bindable
    public boolean isClickChart() {
        return mIsClickChart;
    }

    private void setClickChart(boolean clickChart) {
        mIsClickChart = clickChart;
        notifyPropertyChanged(BR.clickChart);
    }

    @Bindable
    public String getDataChartDayOff() {
        return mDataChartDayOff;
    }

    private void setDataChartDayOff(String dataChartDayOff) {
        mDataChartDayOff = dataChartDayOff;
        notifyPropertyChanged(BR.dataChartDayOff);
    }

    @Bindable
    public String getDataChartLeaveTime() {
        return mDataChartLeaveTime;
    }

    private void setDataChartLeaveTime(String dataChartLeaveTime) {
        mDataChartLeaveTime = dataChartLeaveTime;
        notifyPropertyChanged(BR.dataChartLeaveTime);
    }

    @Bindable
    public String getDataChartOverTime() {
        return mDataChartOverTime;
    }

    private void setDataChartOverTime(String dataChartOverTime) {
        mDataChartOverTime = dataChartOverTime;
        notifyPropertyChanged(BR.dataChartOverTime);
    }

    @Bindable
    public String getMonthSelected() {
        return mMonthSelected;
    }

    private void setMonthSelected(String monthSelected) {
        mMonthSelected = monthSelected;
        notifyPropertyChanged(BR.monthSelected);
    }

    private void showMonthSelected(int month) {
        switch (month) {
            case Calendar.JANUARY:
                setMonthSelected(mContext.getString(R.string.january));
                break;
            case Calendar.FEBRUARY:
                setMonthSelected(mContext.getString(R.string.february));
                break;
            case Calendar.MARCH:
                setMonthSelected(mContext.getString(R.string.march));
                break;
            case Calendar.APRIL:
                setMonthSelected(mContext.getString(R.string.april));
                break;
            case Calendar.MAY:
                setMonthSelected(mContext.getString(R.string.may_month));
                break;
            case Calendar.JUNE:
                setMonthSelected(mContext.getString(R.string.june));
                break;
            case Calendar.JULY:
                setMonthSelected(mContext.getString(R.string.july));
                break;
            case Calendar.AUGUST:
                setMonthSelected(mContext.getString(R.string.august));
                break;
            case Calendar.SEPTEMBER:
                setMonthSelected(mContext.getString(R.string.september));
                break;
            case Calendar.OCTOBER:
                setMonthSelected(mContext.getString(R.string.october));
                break;
            case Calendar.NOVEMBER:
                setMonthSelected(mContext.getString(R.string.november));
                break;
            case Calendar.DECEMBER:
                setMonthSelected(mContext.getString(R.string.december));
                break;
            default:
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mYear = year;
        updateYear(mYear);
        setClickChart(false);
        mPresenter.getStatistics(mYear);
    }

    private void updateYear(int year) {
        setYear(String.valueOf(year));
    }
}
