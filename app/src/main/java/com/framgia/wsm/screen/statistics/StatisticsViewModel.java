package com.framgia.wsm.screen.statistics;

import android.content.Context;
import android.graphics.Color;
import com.framgia.wsm.R;
import com.framgia.wsm.utils.string.StringUtils;
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

public class StatisticsViewModel implements StatisticsContract.ViewModel {
    private static final int MONTH_IN_YEAR = 12;

    private Context mContext;
    private StatisticsContract.Presenter mPresenter;
    private final StatisticContainerAdapter mAdapter;

    StatisticsViewModel(Context context, StatisticsContract.Presenter presenter,
            StatisticContainerAdapter adapter) {
        mContext = context;
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mAdapter = adapter;
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

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
        dataSet.setFillColor(Color.BLUE);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setDrawValues(true);
        dataSet.setValueTextSize(12f);
        dataSet.setValueTextColor(color);
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        return dataSet;
    }

    private DataSet requestOff() {
        ArrayList<Entry> entries = new ArrayList<>();
        //Todo edit later
        for (int index = 0; index < MONTH_IN_YEAR; index++) {
            entries.add(new Entry(index, 2));
        }
        return setLineDataSet(entries, mContext.getString(R.string.request_off_approved),
                Color.BLUE);
    }

    private DataSet requestLeave() {
        ArrayList<Entry> entries = new ArrayList<>();
        //Todo edit later
        for (int index = 0; index < MONTH_IN_YEAR; index++) {
            entries.add(new Entry(index, 4));
        }
        return setLineDataSet(entries, mContext.getString(R.string.request_leave_legend),
                Color.GRAY);
    }

    private DataSet requestOT() {
        ArrayList<Entry> entries = new ArrayList<>();
        //Todo edit later
        for (int index = 0; index < MONTH_IN_YEAR; index++) {
            entries.add(new Entry(index, 0));
        }
        return setLineDataSet(entries, mContext.getString(R.string.request_ot_approved),
                Color.GREEN);
    }

    private LineData getLineDatas() {
        LineData lineDatas = new LineData();
        lineDatas.addDataSet((ILineDataSet) requestOff());
        lineDatas.addDataSet((ILineDataSet) requestLeave());
        lineDatas.addDataSet((ILineDataSet) requestOT());
        return lineDatas;
    }
}
