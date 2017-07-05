package com.framgia.wsm.data.source.remote.api.request;

import com.framgia.wsm.data.model.OffRequest;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ASUS on 14/06/2017.
 */

public class RequestOffRequest extends BaseRequest {

    @SerializedName("request_off")
    @Expose
    private OffRequest mRequestOff;

    public OffRequest getRequestOff() {
        return mRequestOff;
    }

    public void setRequestOff(OffRequest requestOff) {
        mRequestOff = requestOff;
    }
}
