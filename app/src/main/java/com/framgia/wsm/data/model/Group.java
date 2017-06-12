package com.framgia.wsm.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tri on 31/05/2017.
 */

public class Group extends BaseModel implements Parcelable {
    public static final Creator<Group> CREATOR = new Creator<Group>() {
        @Override
        public Group createFromParcel(Parcel source) {
            return new Group(source);
        }

        @Override
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };
    private String mGroupId;
    private String mGroupName;

    public Group() {
    }

    public Group(String groupId, String groupName) {
        mGroupId = groupId;
        mGroupName = groupName;
    }

    protected Group(Parcel in) {
        this.mGroupId = in.readString();
        this.mGroupName = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mGroupId);
        dest.writeString(this.mGroupName);
    }
}
