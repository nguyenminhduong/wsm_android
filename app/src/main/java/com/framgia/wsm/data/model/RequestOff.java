package com.framgia.wsm.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vinh on 06/06/2017.
 */

public class RequestOff extends BaseModel implements Parcelable {

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
    private String mStartDayHaveSalary;
    @Expose
    @SerializedName("to")
    private String mEndDayHaveSalary;
    @Expose
    @SerializedName("from")
    private String mStartDayNoSalary;
    @Expose
    @SerializedName("to")
    private String mEndDayNoSalary;
    @Expose
    @SerializedName("reason")
    private String mReason;

    public RequestOff() {
    }

    protected RequestOff(Parcel in) {
        mPositionRequestOffType = in.readInt();
        mRequestOffName = in.readString();
        mCreatedAt = in.readString();
        mProject = in.readString();
        mPosition = in.readString();
        mBranch = in.readParcelable(Branch.class.getClassLoader());
        mGroup = in.readParcelable(Group.class.getClassLoader());
        mCompanyPay = in.readParcelable(CompanyPay.class.getClassLoader());
        mInsuranceCoverage = in.readParcelable(InsuranceCoverage.class.getClassLoader());
        mStartDayHaveSalary = in.readString();
        mEndDayHaveSalary = in.readString();
        mStartDayNoSalary = in.readString();
        mEndDayNoSalary = in.readString();
        mReason = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mPositionRequestOffType);
        dest.writeString(mRequestOffName);
        dest.writeString(mCreatedAt);
        dest.writeString(mProject);
        dest.writeString(mPosition);
        dest.writeParcelable(mBranch, flags);
        dest.writeParcelable(mGroup, flags);
        dest.writeParcelable(mCompanyPay, flags);
        dest.writeParcelable(mInsuranceCoverage, flags);
        dest.writeString(mStartDayHaveSalary);
        dest.writeString(mEndDayHaveSalary);
        dest.writeString(mStartDayNoSalary);
        dest.writeString(mEndDayNoSalary);
        dest.writeString(mReason);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getStartDayHaveSalary() {
        return mStartDayHaveSalary;
    }

    public void setStartDayHaveSalary(String startDayHaveSalary) {
        mStartDayHaveSalary = startDayHaveSalary;
    }

    public String getEndDayHaveSalary() {
        return mEndDayHaveSalary;
    }

    public void setEndDayHaveSalary(String endDayHaveSalary) {
        mEndDayHaveSalary = endDayHaveSalary;
    }

    public String getStartDayNoSalary() {
        return mStartDayNoSalary;
    }

    public void setStartDayNoSalary(String startDayNoSalary) {
        mStartDayNoSalary = startDayNoSalary;
    }

    public String getEndDayNoSalary() {
        return mEndDayNoSalary;
    }

    public void setEndDayNoSalary(String endDayNoSalary) {
        mEndDayNoSalary = endDayNoSalary;
    }

    public String getReason() {
        return mReason;
    }

    public void setReason(String reason) {
        mReason = reason;
    }
}
