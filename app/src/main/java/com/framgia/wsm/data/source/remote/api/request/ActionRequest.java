package com.framgia.wsm.data.source.remote.api.request;

import com.framgia.wsm.data.model.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tri on 24/07/2017.
 */

public class ActionRequest extends BaseModel {

    @Expose
    @SerializedName("request")
    private String mTypeRequest;
    @Expose
    @SerializedName("status")
    private String mStatus;
    @Expose
    @SerializedName("request_id")
    private int mRequestId;

    public String getTypeRequest() {
        return mTypeRequest;
    }

    public void setTypeRequest(String typeRequest) {
        mTypeRequest = typeRequest;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public int getRequestId() {
        return mRequestId;
    }

    public void setRequestId(int requestId) {
        mRequestId = requestId;
    }
}
