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
    @Expose
    @SerializedName("can_approve_request")
    private boolean mIsCanApproveReject;
    @Expose
    @SerializedName("request_id")
    private int mRequestId;
    @Expose
    @SerializedName("current_handle")
    private String mCurrentHandle;

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public boolean isCanApproveReject() {
        return mIsCanApproveReject;
    }

    public void setCanApproveReject(boolean canApproveReject) {
        mIsCanApproveReject = canApproveReject;
    }

    public int getRequestId() {
        return mRequestId;
    }

    public void setRequestId(int requestId) {
        mRequestId = requestId;
    }

    public String getCurrentHandle() {
        return mCurrentHandle;
    }

    public void setCurrentHandle(String currentHandle) {
        mCurrentHandle = currentHandle;
    }
}
