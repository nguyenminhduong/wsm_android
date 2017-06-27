package com.framgia.wsm.data.source.remote.api.response;

import com.framgia.wsm.data.model.BaseModel;
import com.framgia.wsm.data.model.LeaveType;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by ths on 23/06/2017.
 */

public class LeaveTypeResponse extends BaseModel {
    @Expose
    @SerializedName("leave_types")
    private List<LeaveType> mLeaveType;

    public List<LeaveType> getLeaveType() {
        return mLeaveType;
    }

    public void setLeaveType(List<LeaveType> leaveType) {
        mLeaveType = leaveType;
    }
}
