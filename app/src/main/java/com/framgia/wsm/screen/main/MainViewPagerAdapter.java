package com.framgia.wsm.screen.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

/**
 * Created by tri on 24/05/2017.
 */

public class MainViewPagerAdapter extends FragmentStatePagerAdapter {
    private static final int ITEM_NUMBER = 12;

    private Fragment mCurrentFragment;

    MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return MainContainerFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return ITEM_NUMBER;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mCurrentFragment = ((Fragment) object);
        super.setPrimaryItem(container, position, object);
    }

    Fragment getCurrentFragment() {
        return mCurrentFragment;
    }
}
