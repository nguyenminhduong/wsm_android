package com.framgia.wsm.firebase;

import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by ASUS on 14/06/2017.
 */

public class FireBaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "FireBaseInstanceIDService";

    @Override
    public void onTokenRefresh() {
        getToken();
        Log.d(TAG, "Refreshed token: " + getToken());
    }

    public String getToken() {
        return FirebaseInstanceId.getInstance().getToken();
    }
}
