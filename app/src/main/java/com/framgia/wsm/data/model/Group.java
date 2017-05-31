package com.framgia.wsm.data.model;

/**
 * Created by tri on 31/05/2017.
 */

public class Group extends BaseModel {
    private String mGroupId;
    private String mGroupName;

    public Group() {
    }

    public Group(String groupId, String groupName) {
        mGroupId = groupId;
        mGroupName = groupName;
    }

    public String getGroupId() {
        return mGroupId;
    }

    public void setGroupId(String groupId) {
        mGroupId = groupId;
    }

    public String getGroupName() {
        return mGroupName;
    }

    public void setGroupName(String groupName) {
        mGroupName = groupName;
    }
}
