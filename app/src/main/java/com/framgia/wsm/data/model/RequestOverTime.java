package com.framgia.wsm.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.framgia.wsm.R;
import com.framgia.wsm.utils.validator.Rule;
import com.framgia.wsm.utils.validator.ValidType;
import com.framgia.wsm.utils.validator.Validation;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tri on 20/06/2017.
 */

public class RequestOverTime extends BaseModel implements Parcelable {

    public static final Creator<RequestOverTime> CREATOR = new Creator<RequestOverTime>() {
        @Override
        public RequestOverTime createFromParcel(Parcel source) {
            return new RequestOverTime(source);
        }

        @Override
        public RequestOverTime[] newArray(int size) {
            return new RequestOverTime[size];
        }
    };

    @Expose
    @SerializedName("id")
    private int mId;
    @Expose
    @SerializedName("created_at")
    private String mCreatedAt;
    @Expose
    @SerializedName("group")
    private Group mGroup;
    @Expose
    @SerializedName("branch")
    private Branch mBranch;
    @Expose
    @SerializedName("project")
    @Validation({
            @Rule(types = ValidType.NON_EMPTY, message = R.string.is_empty)
    })
    private String mProject;
    @Expose
    @SerializedName("from_time")
    @Validation({
            @Rule(types = ValidType.NON_EMPTY, message = R.string.is_empty)
    })
    private String mFromTime;
    @Expose
    @SerializedName("end_time")
    @Validation({
            @Rule(types = ValidType.NON_EMPTY, message = R.string.is_empty)
    })
    private String mToTime;
    @Expose
    @SerializedName("reason")
    @Validation({
            @Rule(types = ValidType.NON_EMPTY, message = R.string.is_empty)
    })
    private String mReason;
    @Expose
    @SerializedName("status")
    private int mStatus;

    public RequestOverTime() {
    }

    protected RequestOverTime(Parcel in) {
        this.mId = in.readInt();
        this.mCreatedAt = in.readString();
        this.mGroup = in.readParcelable(Group.class.getClassLoader());
        this.mBranch = in.readParcelable(Branch.class.getClassLoader());
        this.mProject = in.readString();
        this.mFromTime = in.readString();
        this.mToTime = in.readString();
        this.mReason = in.readString();
        this.mStatus = in.readInt();
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
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

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.mCreatedAt);
        dest.writeParcelable(this.mGroup, flags);
        dest.writeParcelable(this.mBranch, flags);
        dest.writeString(this.mProject);
        dest.writeString(this.mFromTime);
        dest.writeString(this.mToTime);
        dest.writeString(this.mReason);
        dest.writeInt(this.mStatus);
    }
}
