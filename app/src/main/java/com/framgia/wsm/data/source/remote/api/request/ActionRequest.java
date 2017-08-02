package com.framgia.wsm.data.source.remote.api.request;

import com.framgia.wsm.data.model.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

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
    @Expose
    @SerializedName("approve_all")
    private boolean mIsApproveAll;
    @Expose
    @SerializedName("request_ids")
    private List<Integer> mListRequestId;

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

    public boolean isApproveAll() {
        return mIsApproveAll;
    }

    public void setApproveAll(boolean approveAll) {
        mIsApproveAll = approveAll;
    }

    public List<Integer> getListRequestId() {
        return mListRequestId;
    }

    public void setListRequestId(List<Integer> listRequestId) {
        mListRequestId = listRequestId;
    }
}
