package com.framgia.wsm.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tri on 02/06/2017.
 */

public class Request extends BaseModel implements Parcelable {
    public static final Creator<Request> CREATOR = new Creator<Request>() {
        @Override
        public Request createFromParcel(Parcel in) {
            return new Request(in);
        }

        @Override
        public Request[] newArray(int size) {
            return new Request[size];
        }
    };

    private int mPositionLeaveType;
    private String mRequestName;
    @Expose
    @SerializedName("group")
    private Group mGroup;
    @Expose
    @SerializedName("branch")
    private Branch mBranch;
    @Expose
    @SerializedName("project")
    private String mProject;
    @Expose
    @SerializedName("from")
    private String mFromTime;
    @Expose
    @SerializedName("to")
    private String mToTime;
    @Expose
    @SerializedName("reason")
    private String mReason;

    protected Request(Parcel in) {
        mPositionLeaveType = in.readInt();
        mRequestName = in.readString();
        mProject = in.readString();
        mFromTime = in.readString();
        mToTime = in.readString();
        mReason = in.readString();
    }

    public Request() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mPositionLeaveType);
        dest.writeString(mRequestName);
        dest.writeString(mProject);
        dest.writeString(mFromTime);
        dest.writeString(mToTime);
        dest.writeString(mReason);
    }

    public int getPositionLeaveType() {
        return mPositionLeaveType;
    }

    public void setPositionLeaveType(int positionLeaveType) {
        mPositionLeaveType = positionLeaveType;
    }

    public String getRequestName() {
        return mRequestName;
    }

    public void setRequestName(String requestName) {
        mRequestName = requestName;
    }

    public Group getGroup() {
        return mGroup;
    }

    public void setGroup(Group group) {
        mGroup = group;
    }

    public Branch getBranch() {
        return mBranch;
    }

    public void setBranch(Branch branch) {
        mBranch = branch;
    }

    public String getProject() {
        return mProject;
    }

    public void setProject(String project) {
        mProject = project;
    }

    public String getFromTime() {
        return mFromTime;
    }

    public void setFromTime(String fromTime) {
        mFromTime = fromTime;
    }

    public String getToTime() {
        return mToTime;
    }

    public void setToTime(String toTime) {
        mToTime = toTime;
    }

    public String getReason() {
        return mReason;
    }

    public void setReason(String reason) {
        mReason = reason;
    }
}
