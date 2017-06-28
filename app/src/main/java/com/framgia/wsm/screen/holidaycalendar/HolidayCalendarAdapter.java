package com.framgia.wsm.screen.holidaycalendar;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.HolidayCalendar;
import com.framgia.wsm.databinding.ItemHolidayCalendarBinding;
import com.framgia.wsm.screen.BaseRecyclerViewAdapter;
import com.framgia.wsm.widget.holidaycalendar.HolidayDateClickListener;
import java.util.ArrayList;
import java.util.List;

public class HolidayCalendarAdapter
        extends BaseRecyclerViewAdapter<HolidayCalendarAdapter.ItemViewHolder> {
    private final List<HolidayCalendar> mHolidayCalendars;
    private HolidayDateClickListener mHolidayDateClickListener;

    public HolidayCalendarAdapter(@NonNull Context context) {
        super(context);
        mHolidayCalendars = new ArrayList<>();
    }

    @Override
    public HolidayCalendarAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent,
            int viewType) {
        ItemHolidayCalendarBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_holiday_calendar, parent, false);
        return new HolidayCalendarAdapter.ItemViewHolder(binding, mHolidayDateClickListener);
    }

    @Override
    public void onBindViewHolder(final HolidayCalendarAdapter.ItemViewHolder holder, int position) {
        holder.bind(mHolidayCalendars.get(position));
    }

    @Override
    public int getItemCount() {
        return mHolidayCalendars.size();
    }

    public void updateData(List<HolidayCalendar> holidayCalendars) {
        if (holidayCalendars == null) {
            return;
        }
        mHolidayCalendars.clear();
        mHolidayCalendars.addAll(holidayCalendars);
        notifyDataSetChanged();
    }

    public void setHolidayDateClickListener(HolidayDateClickListener holidayDateClickListener) {
        mHolidayDateClickListener = holidayDateClickListener;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final HolidayDateClickListener mHolidayDateClickListener;
        private ItemHolidayCalendarBinding mBinding;

        ItemViewHolder(ItemHolidayCalendarBinding binding,
                HolidayDateClickListener holidayDateClickListener) {
            super(binding.getRoot());
            mBinding = binding;
            mHolidayDateClickListener = holidayDateClickListener;
        }

        void bind(HolidayCalendar holidayCalendar) {
            mBinding.setViewModel(
                    new ItemHolidayCalendarViewModel(holidayCalendar, mHolidayDateClickListener));
            mBinding.executePendingBindings();
        }
    }
}
