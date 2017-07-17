package com.framgia.wsm.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tri on 02/06/2017.
 */

public class LeaveRequest extends BaseModel implements Parcelable {

    @Expose
    @SerializedName("id")
    private Integer mId;
    @Expose
    @SerializedName("company_name")
    private String mCompanyName;
    @Expose
    @SerializedName("workspace_name")
    private String mWorkspaceName;
    @Expose
    @SerializedName("status")
    private String mStatus;
    @Expose
    @SerializedName("leave_type")
    private LeaveType mLeaveType;
    @Expose
    @SerializedName("project_name")
    private String mProjectName;
    @Expose
    @SerializedName("reason")
    private String mReason;
    @Expose
    @SerializedName("checkin_time")
    private String mCheckInTime;
    @Expose
    @SerializedName("checkout_time")
    private String mCheckOutTime;
    @Expose
    @SerializedName("compensation")
    private Compensation mCompensation;
    @Expose
    @SerializedName("group_id")
    private Integer mGroupId;
    @Expose
    @SerializedName("company_id")
    private Integer mCompanyId;
    @Expose
    @SerializedName("workspace_id")
    private Integer mWorkpaceId;
    @Expose
    @SerializedName("leave_type_id")
    private Integer mLeaveTypeId;
    @Expose
    @SerializedName("compensation_attributes")
    private Compensation mCompensationRequest;
    @Expose
    @SerializedName("group")
    private Group mGroup;
    @Expose
    @SerializedName("workspace")
    private Branch mBranch;
    @Expose
    @SerializedName("created_at")
    private String mCreateAt;
    @Expose
    @SerializedName("approver")
    private Approver mApprover;
    @Expose
    @SerializedName("handle_by_group_name")
    private String mBeingHandledBy;

    public LeaveRequest() {
    }

    protected LeaveRequest(Parcel in) {
        if (in.readByte() == 0) {
            mId = null;
        } else {
            mId = in.readInt();
        }
        mBeingHandledBy = in.readString();
        mCompanyName = in.readString();
        mWorkspaceName = in.readString();
        mStatus = in.readString();
        mLeaveType = in.readParcelable(LeaveType.class.getClassLoader());
        mProjectName = in.readString();
        mReason = in.readString();
        mCheckInTime = in.readString();
        mCheckOutTime = in.readString();
        mCompensation = in.readParcelable(Compensation.class.getClassLoader());
        if (in.readByte() == 0) {
            mGroupId = null;
        } else {
            mGroupId = in.readInt();
        }
        if (in.readByte() == 0) {
            mCompanyId = null;
        } else {
            mCompanyId = in.readInt();
        }
        if (in.readByte() == 0) {
            mWorkpaceId = null;
        } else {
            mWorkpaceId = in.readInt();
        }
        if (in.readByte() == 0) {
            mLeaveTypeId = null;
        } else {
            mLeaveTypeId = in.readInt();
        }
        mCompensationRequest = in.readParcelable(Compensation.class.getClassLoader());
        mGroup = in.readParcelable(Group.class.getClassLoader());
        mBranch = in.readParcelable(Branch.class.getClassLoader());
        mCreateAt = in.readString();
        mApprover = in.readParcelable(Approver.class.getClassLoader());
    }

    public static final Creator<LeaveRequest> CREATOR = new Creator<LeaveRequest>() {
        @Override
        public LeaveRequest createFromParcel(Parcel in) {
            return new LeaveRequest(in);
        }

        @Override
        public LeaveRequest[] newArray(int size) {
            return new LeaveRequest[size];
        }
    };

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public String getCompanyName() {
        return mCompanyName;
    }

    public void setCompanyName(String companyName) {
        mCompanyName = companyName;
    }

    public String getWorkspaceName() {
        return mWorkspaceName;
    }

    public void setWorkspaceName(String workspaceName) {
        mWorkspaceName = workspaceName;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public LeaveType getLeaveType() {
        return mLeaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        mLeaveType = leaveType;
    }

    public String getProjectName() {
        return mProjectName;
    }

    public void setProjectName(String projectName) {
        mProjectName = projectName;
    }

    public String getReason() {
        return mReason;
    }

    public void setReason(String reason) {
        mReason = reason;
    }

    public String getCheckInTime() {
        return mCheckInTime;
    }

    public void setCheckInTime(String checkInTime) {
        mCheckInTime = checkInTime;
    }

    public String getCheckOutTime() {
        return mCheckOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        mCheckOutTime = checkOutTime;
    }

    public Compensation getCompensation() {
        return mCompensation;
    }

    public void setCompensation(Compensation compensation) {
        mCompensation = compensation;
    }

    public Integer getGroupId() {
        return mGroupId;
    }

    public void setGroupId(Integer groupId) {
        mGroupId = groupId;
    }

    public Integer getCompanyId() {
        return mCompanyId;
    }

    public void setCompanyId(Integer companyId) {
        mCompanyId = companyId;
    }

    public Integer getWorkpaceId() {
        return mWorkpaceId;
    }

    public void setWorkpaceId(Integer workpaceId) {
        mWorkpaceId = workpaceId;
    }

    public Integer getLeaveTypeId() {
        return mLeaveTypeId;
    }

    public void setLeaveTypeId(Integer leaveTypeId) {
        mLeaveTypeId = leaveTypeId;
    }

    public Compensation getCompensationRequest() {
        return mCompensationRequest;
    }

    public void setCompensationRequest(Compensation compensationRequest) {
        mCompensationRequest = compensationRequest;
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

    public String getCreateAt() {
        return mCreateAt;
    }

    public void setCreateAt(String createAt) {
        mCreateAt = createAt;
    }

    public Approver getApprover() {
        return mApprover;
    }

    public void setApprover(Approver approver) {
        mApprover = approver;
    }

    public String getBeingHandledBy() {
        return mBeingHandledBy;
    }

    public void setBeingHandledBy(String beingHandledBy) {
        mBeingHandledBy = beingHandledBy;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (mId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(mId);
        }
        parcel.writeString(mBeingHandledBy);
        parcel.writeString(mCompanyName);
        parcel.writeString(mWorkspaceName);
        parcel.writeString(mStatus);
        parcel.writeParcelable(mLeaveType, i);
        parcel.writeString(mProjectName);
        parcel.writeString(mReason);
        parcel.writeString(mCheckInTime);
        parcel.writeString(mCheckOutTime);
        parcel.writeParcelable(mCompensation, i);
        if (mGroupId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(mGroupId);
        }
        if (mCompanyId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(mCompanyId);
        }
        if (mWorkpaceId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(mWorkpaceId);
        }
        if (mLeaveTypeId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(mLeaveTypeId);
        }
        parcel.writeParcelable(mCompensationRequest, i);
        parcel.writeParcelable(mGroup, i);
        parcel.writeParcelable(mBranch, i);
        parcel.writeString(mCreateAt);
        parcel.writeParcelable(mApprover, i);
    }

    /**
     * Compensation
     */
    public static class Compensation extends BaseModel implements Parcelable {
        @Expose
        @SerializedName("compensation_from")
        private String mFromTime;
        @Expose
        @SerializedName("compensation_to")
        private String mToTime;

        protected Compensation(Parcel in) {
            mFromTime = in.readString();
            mToTime = in.readString();
        }

        public Compensation() {
        }

        public static final Creator<Compensation> CREATOR = new Creator<Compensation>() {
            @Override
            public Compensation createFromParcel(Parcel in) {
                return new Compensation(in);
            }

            @Override
            public Compensation[] newArray(int size) {
                return new Compensation[size];
            }
        };

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(mFromTime);
            parcel.writeString(mToTime);
        }
    }
}
