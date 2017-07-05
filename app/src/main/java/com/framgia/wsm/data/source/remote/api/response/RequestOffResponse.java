package com.framgia.wsm.data.source.remote.api.response;

import com.framgia.wsm.data.model.OffRequest;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tri on 23/06/2017.
 */

public class RequestOffResponse {
    @Expose
    @SerializedName("request_off")
    private OffRequest mRequestOff;

    public OffRequest getRequestOff() {
        return mRequestOff;
    }

    public void setRequestOff(OffRequest requestOff) {
        mRequestOff = requestOff;
    }
}
