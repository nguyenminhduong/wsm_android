package com.framgia.wsm.data.source.remote.api.request;

import com.framgia.wsm.data.model.RequestOff;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ASUS on 14/06/2017.
 */

public class RequestOffRequest extends BaseRequest {

    @SerializedName("request_off")
    @Expose
    private RequestOff mRequestOff;

    public RequestOff getRequestOff() {
        return mRequestOff;
    }

    public void setRequestOff(RequestOff requestOff) {
        mRequestOff = requestOff;
    }
}
