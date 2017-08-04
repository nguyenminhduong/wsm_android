package com.framgia.wsm.screen.statistics;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.framgia.wsm.R;
import com.framgia.wsm.screen.holidaycalendar.HolidayCalendarFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguyenhuy95dn on 8/3/2017.
 */

public class StatisticContainerAdapter extends FragmentPagerAdapter {
    private static final int TOTAL_TAB = 2;

    private Context mContext;
    private List<Fragment> mFragments;

    StatisticContainerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        mFragments = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case SearchResultsTab.TAB_MONTH:
                //Todo edit later
                fragment = HolidayCalendarFragment.newInstance();
                mFragments.add(SearchResultsTab.TAB_MONTH, fragment);
                return fragment;
            case SearchResultsTab.TAB_YEAR:
                //Todo edit later
                fragment = HolidayCalendarFragment.newInstance();
                mFragments.add(SearchResultsTab.TAB_YEAR, fragment);
                return fragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return TOTAL_TAB;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case SearchResultsTab.TAB_MONTH:
                return mContext.getString(R.string.month);
            case SearchResultsTab.TAB_YEAR:
                return mContext.getString(R.string.year);
            default:
                return null;
        }
    }

    public Fragment getFragment(@SearchResultsTab int position) {
        return mFragments.get(position);
    }

    @IntDef({ SearchResultsTab.TAB_MONTH, SearchResultsTab.TAB_YEAR })
    public @interface SearchResultsTab {
        int TAB_MONTH = 0;
        int TAB_YEAR = 1;
    }
}

