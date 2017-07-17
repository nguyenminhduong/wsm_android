package com.framgia.wsm.data.model;

/**
 * Created by tri on 14/07/2017.
 */

public class QueryRequest extends BaseModel {

    private String mUserName;
    private String mFromTime;
    private String mToTime;
    private String mStatus;
    private String mGroupId;
    private String mWorkspaceId;
    private int mRequestType;

    public QueryRequest() {
    }

    public String getUserName() {
        if (mUserName == null) {
            return "";
        }
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getFromTime() {
        if (mFromTime == null) {
            return "";
        }
        return mFromTime;
    }

    public void setFromTime(String fromTime) {
        mFromTime = fromTime;
    }

    public String getToTime() {
        if (mToTime == null) {
            return "";
        }
        return mToTime;
    }

    public void setToTime(String toTime) {
        mToTime = toTime;
    }

    public String getStatus() {
        if (mStatus == null) {
            return "";
        }
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getGroupId() {
        if (mGroupId == null) {
            return "";
        }
        return mGroupId;
    }

    public void setGroupId(String groupId) {
        mGroupId = groupId;
    }

    public String getWorkspaceId() {
        if (mWorkspaceId == null) {
            return "";
        }
        return mWorkspaceId;
    }

    public void setWorkspaceId(String workspaceId) {
        mWorkspaceId = workspaceId;
    }

    public int getRequestType() {
        return mRequestType;
    }

    public void setRequestType(int requestType) {
        mRequestType = requestType;
    }
}
