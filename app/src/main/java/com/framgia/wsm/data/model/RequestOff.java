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
    @SerializedName("id")
    private int mId;
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
    @SerializedName("from_have_salary")
    private String mStartDayHaveSalary;
    @Expose
    @SerializedName("to_have_salary")
    private String mEndDayHaveSalary;
    @Expose
    @SerializedName("from_no_salary")
    private String mStartDayNoSalary;
    @Expose
    @SerializedName("to_no_salary")
    private String mEndDayNoSalary;
    @Expose
    @SerializedName("reason")
    @Validation({
            @Rule(types = ValidType.NON_EMPTY, message = R.string.is_empty)
    })
    private String mReason;
    @Expose
    @SerializedName("being_handled_by")
    private String mBeingHandledBy;
    @Expose
    @SerializedName("status")
    private int mStatus;

    public RequestOff() {
        mInsuranceCoverage = new InsuranceCoverage();
        mCompanyPay = new CompanyPay();
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
        mBeingHandledBy = in.readString();
        mStatus = in.readInt();
        mId = in.readInt();
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
        dest.writeString(mBeingHandledBy);
        dest.writeInt(mStatus);
        dest.writeInt(mId);
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

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }
}
