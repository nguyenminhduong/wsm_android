package com.framgia.wsm.data.source.remote.api.request;

import com.framgia.wsm.data.model.LeaveRequest;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ths on 06/07/2017.
 */

public class RequestLeaveRequest extends BaseRequest {

    @Expose
    @SerializedName("request_leave")
    private LeaveRequest mLeaveRequest;

    public LeaveRequest getLeaveRequest() {
        return mLeaveRequest;
    }

    public void setLeaveRequest(LeaveRequest leaveRequest) {
        mLeaveRequest = leaveRequest;
    }
}
