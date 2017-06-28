package com.framgia.wsm.screen.holidaycalendar;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import com.framgia.wsm.data.source.HolidayCalendarRepository;
import com.framgia.wsm.data.source.remote.HolidayCalendarRemoteDataSource;
import com.framgia.wsm.utils.dagger.FragmentScope;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the {@link HolidayCalendarPresenter}.
 */
@Module
public class HolidayCalendarModule {

    private Fragment mFragment;

    public HolidayCalendarModule(@NonNull Fragment fragment) {
        mFragment = fragment;
    }

    @FragmentScope
    @Provides
    public HolidayCalendarContract.ViewModel provideViewModel(
            HolidayCalendarContract.Presenter presenter,
            HolidayCalendarAdapter holidayCalendarAdapter) {
        return new HolidayCalendarViewModel(mFragment.getActivity(), presenter,
                holidayCalendarAdapter);
    }

    @FragmentScope
    @Provides
    public HolidayCalendarContract.Presenter providePresenter(
            HolidayCalendarRepository holidayCalendarRepository,
            BaseSchedulerProvider baseSchedulerProvider) {
        return new HolidayCalendarPresenter(holidayCalendarRepository, baseSchedulerProvider);
    }

    @FragmentScope
    @Provides
    public HolidayCalendarAdapter provideHolidayCalendarAdapter() {
        return new HolidayCalendarAdapter(mFragment.getActivity());
    }

    @FragmentScope
    @Provides
    public HolidayCalendarRepository provideHolidayCalendarRepository(
            HolidayCalendarRemoteDataSource remoteDataSource) {
        return new HolidayCalendarRepository(remoteDataSource);
    }
}
