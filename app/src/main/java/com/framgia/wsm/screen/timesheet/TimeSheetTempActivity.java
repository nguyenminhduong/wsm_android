package com.framgia.wsm.screen.timesheet;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.framgia.wsm.R;
import com.framgia.wsm.databinding.ActivityTimeSheetTempBinding;
import com.framgia.wsm.screen.BaseActivity;

/**
 * Created by Duong on 5/24/2017.
 */

public class TimeSheetTempActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTimeSheetTempBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_time_sheet_temp);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_content, TimeSheetFragment.newInstance()).commit();
    }
}
