package com.framgia.wsm;

import android.content.Context;
import com.framgia.wsm.data.source.local.sharedprf.SharedPrefsApi;
import com.framgia.wsm.data.source.local.sharedprf.SharedPrefsImpl;
import com.framgia.wsm.utils.dagger.AppScope;
import dagger.Module;
import dagger.Provides;

/**
 * Created by le.quang.dao on 21/03/2017.
 */

@Module
public class ApplicationModule {

    private Context mContext;

    public ApplicationModule(Context mContext) {
        this.mContext = mContext;
    }

    @Provides
    @AppScope
    public Context provideApplicationContext() {
        return mContext;
    }

    @Provides
    @AppScope
    public SharedPrefsApi provideSharedPrefsApi() {
        return new SharedPrefsImpl(mContext);
    }
}
