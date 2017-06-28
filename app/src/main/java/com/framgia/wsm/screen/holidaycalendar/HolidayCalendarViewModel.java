package com.framgia.wsm.screen.holidaycalendar;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.widget.Toast;
import com.android.databinding.library.baseAdapters.BR;
import com.framgia.wsm.data.model.HolidayCalendarDate;
import com.framgia.wsm.widget.holidaycalendar.HolidayDateClickListener;
import java.util.Calendar;

/**
 * Exposes the data to be used in the HolidayCalendar screen.
 */

public class HolidayCalendarViewModel extends BaseObservable
        implements HolidayCalendarContract.ViewModel, HolidayDateClickListener {

    private HolidayCalendarContract.Presenter mPresenter;
    private Context mContext;
    private HolidayCalendarAdapter mHolidayCalendarAdapter;
    private int mYear;

    HolidayCalendarViewModel(Context context, HolidayCalendarContract.Presenter presenter,
            HolidayCalendarAdapter holidayCalendarAdapter) {
        mContext = context;
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mHolidayCalendarAdapter = holidayCalendarAdapter;
        mHolidayCalendarAdapter.setHolidayDateClickListener(this);
        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
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
    public String getYear() {
        return String.valueOf(mYear);
    }

    public void setYear(int year) {
        mYear = year;
        notifyPropertyChanged(BR.year);
    }

    @Override
    public void onDayClick(HolidayCalendarDate holidayCalendarDate) {
        Toast.makeText(mContext, holidayCalendarDate.getHolidayName(), Toast.LENGTH_LONG).show();
    }

    public HolidayCalendarAdapter getHolidayCalendarAdapter() {
        return mHolidayCalendarAdapter;
    }

    public void onNextYear() {
        setYear(mYear + 1);
    }

    public void onPreviousYear() {
        setYear(mYear - 1);
    }
}
