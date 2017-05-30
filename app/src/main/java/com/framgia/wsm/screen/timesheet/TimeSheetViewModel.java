package com.framgia.wsm.screen.timesheet;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.android.databinding.library.baseAdapters.BR;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.TimeSheetDate;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Exposes the data to be used in the TimeSheet screen.
 */

public class TimeSheetViewModel extends BaseObservable implements TimeSheetContract.ViewModel {

    private Context mContext;
    private TimeSheetContract.Presenter mPresenter;
    private List<TimeSheetDate> mTimeSheetDates;
    private int mMonth;
    private int mYear;
    private boolean isShowInformation;
    private TimeSheetDate mTimeSheetDate;

    public TimeSheetViewModel(Context context, TimeSheetContract.Presenter presenter) {
        mContext = context;
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mTimeSheetDates = new ArrayList<>();
        mTimeSheetDate = new TimeSheetDate();
        Calendar calendar = Calendar.getInstance();
        mMonth = calendar.get(Calendar.MONTH);
        mYear = calendar.get(Calendar.YEAR);
        //todo edit at next pull, add param month, year
        mPresenter.getTimeSheet();
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Override
    public void onGetTimeSheetError(BaseException throwable) {
        //todo show error message
    }

    @Override
    public void onGetTimeSheetSuccess(List<TimeSheetDate> list) {
        mTimeSheetDates.addAll(list);
    }

    @Bindable
    public String getDate() {
        //todo edit later
        return mTimeSheetDate.getDateString();
    }

    @Bindable
    public String getTimeIn() {
        return mContext.getString(R.string.time_in, mTimeSheetDate.getTimeIn());
    }

    @Bindable
    public String getTimeOut() {
        return mContext.getString(R.string.time_out, mTimeSheetDate.getTimeOut());
    }

    public List<TimeSheetDate> getTimeSheetDates() {
        return mTimeSheetDates;
    }

    public int getMonth() {
        return mMonth;
    }

    public int getYear() {
        return mYear;
    }

    @Bindable
    public boolean isShowInformation() {
        return isShowInformation;
    }

    public void onDayClicked(TimeSheetDate timeSheetDate) {
        mTimeSheetDate = timeSheetDate;
        isShowInformation = true;
        notifyPropertyChanged(BR.showInformation);
        notifyPropertyChanged(BR.date);
        notifyPropertyChanged(BR.timeIn);
        notifyPropertyChanged(BR.timeOut);
    }
}
