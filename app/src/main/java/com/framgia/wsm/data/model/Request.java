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

    private int mPositionType;
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
    @SerializedName("position")
    private String mPosition;
    @Expose
    @SerializedName("company_pay")
    private CompanyPay mCompanyPay;
    @Expose
    @SerializedName("insurance_coverage")
    private InsuranceCoverage mInsuranceCoverage;
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
    @SerializedName("checkout_time")
    private String mCheckoutTime;
    @Expose
    @SerializedName("reason")
    private String mReason;
    @Expose
    @SerializedName("status")
    private int mStatus;
    @Expose
    @SerializedName("compensation")
    private Compensation mCompensation;

    protected Request(Parcel in) {
        mPositionType = in.readInt();
        mRequestName = in.readString();
        mProject = in.readString();
        mPosition = in.readString();
        mFromTime = in.readString();
        mToTime = in.readString();
        mReason = in.readString();
        mStatus = in.readInt();
    }

    public Request() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mPositionType);
        dest.writeString(mRequestName);
        dest.writeString(mProject);
        dest.writeString(mFromTime);
        dest.writeString(mToTime);
        dest.writeString(mReason);
        dest.writeInt(mStatus);
    }

    public int getPositionType() {
        return mPositionType;
    }

    public void setPositionType(int positionType) {
        mPositionType = positionType;
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

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
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

    public Compensation getCompensation() {
        return mCompensation;
    }

    public void setCompensation(Compensation compensation) {
        mCompensation = compensation;
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
