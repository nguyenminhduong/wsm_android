package com.framgia.wsm.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tri on 31/05/2017.
 */

public class Group extends BaseModel implements Parcelable {
    public static final Creator<Group> CREATOR = new Creator<Group>() {
        @Override
        public Group createFromParcel(Parcel in) {
            return new Group(in);
        }

        @Override
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };

    @Expose
    @SerializedName("id")
    private int mGroupId;
    @Expose
    @SerializedName("name")
    private String mGroupName;
    @Expose
    @SerializedName("description")
    private String mDescription;
    @Expose
    @SerializedName("closest_parent_id")
    private int mClosestParentId;
    @Expose
    @SerializedName("full_name")
    private String mFullName;

    public Group() {
    }

    public Group(int groupId, String groupName, String description, int closestParentId,
            String fullName) {
        mGroupId = groupId;
        mGroupName = groupName;
        mDescription = description;
        mClosestParentId = closestParentId;
        mFullName = fullName;
    }

    protected Group(Parcel in) {
        mGroupId = in.readInt();
        mGroupName = in.readString();
        mDescription = in.readString();
        mClosestParentId = in.readInt();
        mFullName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mGroupId);
        dest.writeString(mGroupName);
        dest.writeString(mDescription);
        dest.writeInt(mClosestParentId);
        dest.writeString(mFullName);
    }

    public int getGroupId() {
        return mGroupId;
    }

    public void setGroupId(int groupId) {
        mGroupId = groupId;
    }

    public String getGroupName() {
        return mGroupName;
    }

    public void setGroupName(String groupName) {
        mGroupName = groupName;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public int getClosestParentId() {
        return mClosestParentId;
    }

    public void setClosestParentId(int closestParentId) {
        mClosestParentId = closestParentId;
    }

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String fullName) {
        mFullName = fullName;
    }
}
