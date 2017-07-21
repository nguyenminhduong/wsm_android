package com.framgia.wsm.utils;

import com.framgia.wsm.utils.common.StringUtils;
import java.io.File;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by tri on 03/07/2017.
 */

public class RetrofitUtils {

    private RetrofitUtils() {
        // No-op
    }

    public static RequestBody toRequestBody(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }

    public static MultipartBody.Part toMutilPartBody(File file) {
        if (file == null) {
            return null;
        }
        RequestBody requestFileAvatar =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return MultipartBody.Part.createFormData("user[avatar]", file.getName(), requestFileAvatar);
    }
}
