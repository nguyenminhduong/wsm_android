package com.framgia.wsm.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by tri on 29/06/2017.
 */

public class RequestPermissionManager {
    private Activity mActivity;

    public RequestPermissionManager(Activity activity) {
        mActivity = activity;
    }

    public boolean isPermissionWriteExternalStorageGranted() {
        int result = ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(mActivity, new String[] {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, Constant.RequestCode.REQUEST_CODE_WRITE_EXTERNAL_STORAGE);
        }
        return false;
    }
}
