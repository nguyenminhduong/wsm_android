package com.framgia.wsm.data.source.local;

import android.support.annotation.NonNull;
import com.framgia.wsm.data.source.TokenDataSource;
import com.framgia.wsm.data.source.local.sharedprf.SharedPrefsApi;
import com.framgia.wsm.data.source.local.sharedprf.SharedPrefsKey;
import javax.inject.Inject;

/**
 * Created by Duong on 6/6/2017.
 */

public class TokenLocalDataSource implements TokenDataSource.LocalDataSource {
    private SharedPrefsApi mSharedPrefsApi;

    @Inject
    public TokenLocalDataSource(@NonNull SharedPrefsApi prefsApi) {
        mSharedPrefsApi = prefsApi;
    }

    @Override
    public void saveToken(String token) {
        mSharedPrefsApi.put(SharedPrefsKey.KEY_TOKEN, token);
    }

    @Override
    public String getToken() {
        return mSharedPrefsApi.get(SharedPrefsKey.KEY_TOKEN, String.class);
    }
}
