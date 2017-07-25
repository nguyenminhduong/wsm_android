package com.framgia.wsm.data.source.remote.api.response;

import com.framgia.wsm.data.model.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tri on 25/07/2017.
 */

public class ActionRequestResponse extends BaseModel {
    @Expose
    @SerializedName("status")
    private String mStatus;

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }
}
