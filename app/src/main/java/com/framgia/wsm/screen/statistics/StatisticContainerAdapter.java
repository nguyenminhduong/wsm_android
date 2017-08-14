package com.framgia.wsm.screen.statistics;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.framgia.wsm.R;
import com.framgia.wsm.screen.statisticsbymonth.StatisticsByMonthFragment;
import com.framgia.wsm.screen.statisticsbyyear.StatisticsByYearFragment;
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
            case StatisticResultTab.TAB_MONTH:
                fragment = StatisticsByMonthFragment.newInstance();
                mFragments.add(StatisticResultTab.TAB_MONTH, fragment);
                return fragment;
            case StatisticResultTab.TAB_YEAR:
                fragment = StatisticsByYearFragment.newInstance();
                mFragments.add(StatisticResultTab.TAB_YEAR, fragment);
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
            case StatisticResultTab.TAB_MONTH:
                return mContext.getString(R.string.current_month);
            case StatisticResultTab.TAB_YEAR:
                return mContext.getString(R.string.year);
            default:
                return null;
        }
    }

    public Fragment getFragment(@StatisticResultTab int position) {
        return mFragments.get(position);
    }

    @IntDef({ StatisticResultTab.TAB_MONTH, StatisticResultTab.TAB_YEAR })
    public @interface StatisticResultTab {
        int TAB_MONTH = 0;
        int TAB_YEAR = 1;
    }
}

