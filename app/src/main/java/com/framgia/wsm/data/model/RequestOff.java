package com.framgia.wsm.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vinh on 06/06/2017.
 */

public class RequestOff extends BaseModel implements Parcelable {

    private int mPositionRequestOffType;
    private String mRequestOffName;

    @Expose
    @SerializedName("created_at")
    private String mCreatedAt;
    @Expose
    @SerializedName("project")
    private String mProject;
    @Expose
    @SerializedName("position")
    private String mPosition;
    @Expose
    @SerializedName("branch")
    private Branch mBranch;
    @Expose
    @SerializedName("group")
    private Group mGroup;
    @Expose
    @SerializedName("company_pay")
    private CompanyPay mCompanyPay;
    @Expose
    @SerializedName("insurance_coverage")
    private InsuranceCoverage mInsuranceCoverage;
    @Expose
    @SerializedName("from")
    private String mFromTime;
    @Expose
    @SerializedName("to")
    private String mToTime;
    @Expose
    @SerializedName("reason")
    private String mReason;

    protected RequestOff(Parcel in) {
        mPositionRequestOffType = in.readInt();
        mRequestOffName = in.readString();
        mCreatedAt = in.readString();
        mProject = in.readString();
        mPosition = in.readString();
        mFromTime = in.readString();
        mToTime = in.readString();
        mReason = in.readString();
    }

    public static final Creator<RequestOff> CREATOR = new Creator<RequestOff>() {
        @Override
        public RequestOff createFromParcel(Parcel in) {
            return new RequestOff(in);
        }

        @Override
        public RequestOff[] newArray(int size) {
            return new RequestOff[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mPositionRequestOffType);
        dest.writeString(mRequestOffName);
        dest.writeString(mCreatedAt);
        dest.writeString(mProject);
        dest.writeString(mPosition);
        dest.writeString(mFromTime);
        dest.writeString(mToTime);
        dest.writeString(mReason);
    }

    public int getPositionRequestOffType() {
        return mPositionRequestOffType;
    }

    public void setPositionRequestOffType(int positionRequestOffType) {
        mPositionRequestOffType = positionRequestOffType;
    }

    public String getRequestOffName() {
        return mRequestOffName;
    }

    public void setRequestOffName(String requestOffName) {
        mRequestOffName = requestOffName;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
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

    public Branch getBranch() {
        return mBranch;
    }

    public void setBranch(Branch branch) {
        mBranch = branch;
    }

    public Group getGroup() {
        return mGroup;
    }

    public void setGroup(Group group) {
        mGroup = group;
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
