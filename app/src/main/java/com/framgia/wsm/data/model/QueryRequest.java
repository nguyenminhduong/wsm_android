package com.framgia.wsm.data.model;

import com.framgia.wsm.utils.common.StringUtils;

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
    private String mMonthWorking;
    private String mPage;

    public QueryRequest() {
    }

    public String getUserName() {
        if (StringUtils.isBlank(mUserName)) {
            return "";
        }
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getFromTime() {
        if (StringUtils.isBlank(mFromTime)) {
            return "";
        }
        return mFromTime;
    }

    public void setFromTime(String fromTime) {
        mFromTime = fromTime;
    }

    public String getToTime() {
        if (StringUtils.isBlank(mToTime)) {
            return "";
        }
        return mToTime;
    }

    public void setToTime(String toTime) {
        mToTime = toTime;
    }

    public String getStatus() {
        if (StringUtils.isBlank(mStatus)) {
            return "";
        }
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getGroupId() {
        if (StringUtils.isBlank(mGroupId)) {
            return "";
        }
        return mGroupId;
    }

    public void setGroupId(String groupId) {
        mGroupId = groupId;
    }

    public String getWorkspaceId() {
        if (StringUtils.isBlank(mWorkspaceId)) {
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

    public String getMonthWorking() {
        if (StringUtils.isBlank(mMonthWorking)) {
            return "";
        }
        return mMonthWorking;
    }

    public void setMonthWorking(String monthWorking) {
        mMonthWorking = monthWorking;
    }

    public String getPage() {
        return mPage;
    }

    public void setPage(String page) {
        mPage = page;
    }
}
