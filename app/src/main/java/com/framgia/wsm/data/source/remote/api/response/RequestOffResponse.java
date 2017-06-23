package com.framgia.wsm.data.source.remote.api.response;

import com.framgia.wsm.data.model.RequestOff;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tri on 23/06/2017.
 */

public class RequestOffResponse {
    @Expose
    @SerializedName("request_off")
    private RequestOff mRequestOff;

    public RequestOff getRequestOff() {
        return mRequestOff;
    }

    public void setRequestOff(RequestOff requestOff) {
        mRequestOff = requestOff;
    }
}
