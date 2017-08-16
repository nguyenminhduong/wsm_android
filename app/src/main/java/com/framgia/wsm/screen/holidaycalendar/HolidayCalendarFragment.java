package com.framgia.wsm.screen.holidaycalendar;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.wsm.R;
import com.framgia.wsm.databinding.FragmentHolidayCalendarBinding;
import com.framgia.wsm.screen.BaseFragment;
import com.framgia.wsm.screen.main.MainActivity;
import javax.inject.Inject;

/**
 * HolidayCalendar Screen.
 */
public class HolidayCalendarFragment extends BaseFragment {
    public static final String TAG = "HolidayCalendarFragment";

    @Inject
    HolidayCalendarContract.ViewModel mViewModel;

    public static HolidayCalendarFragment newInstance() {
        return new HolidayCalendarFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        DaggerHolidayCalendarComponent.builder()
                .mainComponent(((MainActivity) getActivity()).getMainComponent())
                .holidayCalendarModule(new HolidayCalendarModule(this))
                .build()
                .inject(this);

        FragmentHolidayCalendarBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_holiday_calendar, container,
                        false);
        binding.setViewModel((HolidayCalendarViewModel) mViewModel);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    public void onStop() {
        mViewModel.onStop();
        super.onStop();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser) {
            return;
        }
        mViewModel.onReloadData();
    }
}
