package com.framgia.wsm.screen;

import android.support.v7.app.AppCompatActivity;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
