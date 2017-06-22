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
    @SerializedName("leave_type")
    private LeaveType mLeaveType;
    @Expose
    @SerializedName("project")
    private String mProject;
    @Expose
    @SerializedName("position")
    private String mPosition;
    @Expose
    @SerializedName("company_pay")
    private CompanyPay mCompanyPay;
    @Expose
    @SerializedName("insurance_coverage")
    private InsuranceCoverage mInsuranceCoverage;
    @SerializedName("reason")
    private String mReason;
    @Expose
    @SerializedName("from_time")
    private String mFromTime;
    @Expose
    @SerializedName("end_time")
    private String mToTime;
    @Expose
    @SerializedName("checkin_time")
    private String mCheckinTime;
    @Expose
    @SerializedName("being_handled_by")
    private String mBeingHandledBy;
    @Expose
    @SerializedName("checkout_time")
    private String mCheckoutTime;
    @Expose
    @SerializedName("status")
    private int mStatus;
    @Expose
    @SerializedName("compensation")
    private Compensation mCompensation;

    public Request() {
    }

    protected Request(Parcel in) {
        mCreatedAt = in.readString();
        mGroup = in.readParcelable(Group.class.getClassLoader());
        mBranch = in.readParcelable(Branch.class.getClassLoader());
        mLeaveType = in.readParcelable(LeaveType.class.getClassLoader());
        mProject = in.readString();
        mPosition = in.readString();
        mCompanyPay = in.readParcelable(CompanyPay.class.getClassLoader());
        mInsuranceCoverage = in.readParcelable(InsuranceCoverage.class.getClassLoader());
        mReason = in.readString();
        mFromTime = in.readString();
        mToTime = in.readString();
        mCheckinTime = in.readString();
        mCheckoutTime = in.readString();
        mBeingHandledBy=in.readString();
        mStatus = in.readInt();
        mCompensation = in.readParcelable(Compensation.class.getClassLoader());
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

    public LeaveType getLeaveType() {
        return mLeaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        mLeaveType = leaveType;
    }

    public String getProject() {
        return mProject;
    }

    public void setProject(String project) {
        mProject = project;
    }

    public String getPosition() {
        return mPosition;
    }

    public void setPosition(String position) {
        mPosition = position;
    }

    public CompanyPay getCompanyPay() {
        return mCompanyPay;
    }

    public void setCompanyPay(CompanyPay companyPay) {
        mCompanyPay = companyPay;
    }

    public InsuranceCoverage getInsuranceCoverage() {
        return mInsuranceCoverage;
    }

    public void setInsuranceCoverage(InsuranceCoverage insuranceCoverage) {
        mInsuranceCoverage = insuranceCoverage;
    }

    public String getReason() {
        return mReason;
    }

    public void setReason(String reason) {
        mReason = reason;
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

    public String getCheckinTime() {
        return mCheckinTime;
    }

    public void setCheckinTime(String checkinTime) {
        mCheckinTime = checkinTime;
    }

    public String getCheckoutTime() {
        return mCheckoutTime;
    }

    public void setCheckoutTime(String checkoutTime) {
        mCheckoutTime = checkoutTime;
    }

    public String getBeingHandledBy() {
        return mBeingHandledBy;
    }

    public void setBeingHandledBy(String beingHandledBy) {
        mBeingHandledBy = beingHandledBy;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }

    public Compensation getCompensation() {
        return mCompensation;
    }

    public void setCompensation(Compensation compensation) {
        mCompensation = compensation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mCreatedAt);
        dest.writeParcelable(mGroup, flags);
        dest.writeParcelable(mBranch, flags);
        dest.writeParcelable(mLeaveType, flags);
        dest.writeString(mProject);
        dest.writeString(mPosition);
        dest.writeParcelable(mCompanyPay, flags);
        dest.writeParcelable(mInsuranceCoverage, flags);
        dest.writeString(mReason);
        dest.writeString(mFromTime);
        dest.writeString(mToTime);
        dest.writeString(mCheckinTime);
        dest.writeString(mCheckoutTime);
        dest.writeString(mBeingHandledBy);
        dest.writeInt(mStatus);
        dest.writeParcelable(mCompensation, flags);
    }

    public static class Compensation implements Parcelable {
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
        @SerializedName("compensation_from")
        private String mFromTime;
        @Expose
        @SerializedName("compensation_to")
        private String mToTime;
        @Expose
        @SerializedName("status")
        private int mStatus;

        protected Compensation(Parcel in) {
            mFromTime = in.readString();
            mToTime = in.readString();
            mStatus = in.readInt();
        }

        public Compensation() {
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
            dest.writeString(mFromTime);
            dest.writeString(mToTime);
            dest.writeInt(mStatus);
        }
    }
}
