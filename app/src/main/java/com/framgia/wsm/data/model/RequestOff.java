package com.framgia.wsm.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.framgia.wsm.R;
import com.framgia.wsm.utils.StatusCode;
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
    @SerializedName("project_name")
    private String mProject;
    @Expose
    @SerializedName("position_name")
    private String mPosition;
    @Expose
    @SerializedName("workspaces")
    private Branch mBranch;
    @Expose
    @SerializedName("groups")
    private Group mGroup;
    @Expose
    @SerializedName("company_pay")
    private CompanyPay mCompanyPay;
    @Expose
    @SerializedName("insurance_coverage")
    private InsuranceCoverage mInsuranceCoverage;
    @Expose
    @SerializedName("off_have_salary_from")
    private OffHaveSalaryFrom mStartDayHaveSalary;
    @Expose
    @SerializedName("off_have_salary_to")
    private OffHaveSalaryTo mEndDayHaveSalary;
    @Expose
    @SerializedName("off_no_salary_from")
    private OffNoSalaryFrom mStartDayNoSalary;
    @Expose
    @SerializedName("off_no_salary_to")
    private OffNoSalaryTo mEndDayNoSalary;
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
    private String mStatus;

    public RequestOff() {
        mInsuranceCoverage = new InsuranceCoverage();
        mCompanyPay = new CompanyPay();
    }

    protected RequestOff(Parcel in) {
        mPositionRequestOffType = in.readInt();
        mRequestOffName = in.readString();
        mId = in.readInt();
        mCreatedAt = in.readString();
        mProject = in.readString();
        mPosition = in.readString();
        mBranch = in.readParcelable(Branch.class.getClassLoader());
        mGroup = in.readParcelable(Group.class.getClassLoader());
        mCompanyPay = in.readParcelable(CompanyPay.class.getClassLoader());
        mInsuranceCoverage = in.readParcelable(InsuranceCoverage.class.getClassLoader());
        mStartDayHaveSalary = in.readParcelable(OffHaveSalaryFrom.class.getClassLoader());
        mEndDayHaveSalary = in.readParcelable(OffHaveSalaryTo.class.getClassLoader());
        mStartDayNoSalary = in.readParcelable(OffNoSalaryFrom.class.getClassLoader());
        mEndDayNoSalary = in.readParcelable(OffNoSalaryTo.class.getClassLoader());
        mReason = in.readString();
        mBeingHandledBy = in.readString();
        mStatus = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mPositionRequestOffType);
        dest.writeString(mRequestOffName);
        dest.writeInt(mId);
        dest.writeString(mCreatedAt);
        dest.writeString(mProject);
        dest.writeString(mPosition);
        dest.writeParcelable(mBranch, flags);
        dest.writeParcelable(mGroup, flags);
        dest.writeParcelable(mCompanyPay, flags);
        dest.writeParcelable(mInsuranceCoverage, flags);
        dest.writeParcelable(mStartDayHaveSalary, flags);
        dest.writeParcelable(mEndDayHaveSalary, flags);
        dest.writeParcelable(mStartDayNoSalary, flags);
        dest.writeParcelable(mEndDayNoSalary, flags);
        dest.writeString(mReason);
        dest.writeString(mBeingHandledBy);
        dest.writeString(mStatus);
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

    public OffHaveSalaryFrom getStartDayHaveSalary() {
        return mStartDayHaveSalary;
    }

    public void setStartDayHaveSalary(OffHaveSalaryFrom startDayHaveSalary) {
        mStartDayHaveSalary = startDayHaveSalary;
    }

    public OffHaveSalaryTo getEndDayHaveSalary() {
        return mEndDayHaveSalary;
    }

    public void setEndDayHaveSalary(OffHaveSalaryTo endDayHaveSalary) {
        mEndDayHaveSalary = endDayHaveSalary;
    }

    public OffNoSalaryFrom getStartDayNoSalary() {
        return mStartDayNoSalary;
    }

    public void setStartDayNoSalary(OffNoSalaryFrom startDayNoSalary) {
        mStartDayNoSalary = startDayNoSalary;
    }

    public OffNoSalaryTo getEndDayNoSalary() {
        return mEndDayNoSalary;
    }

    public void setEndDayNoSalary(OffNoSalaryTo endDayNoSalary) {
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

    @StatusCode
    public String getStatus() {
        return mStatus;
    }

    public void setStatus(@StatusCode String status) {
        mStatus = status;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    // OffHaveSalaryFrom
    public static class OffHaveSalaryFrom implements Parcelable {
        @Expose
        @SerializedName("off_paid_from")
        private String mOffPaidFrom;
        @Expose
        @SerializedName("paid_from_period")
        private String mPaidFromPeriod;

        public OffHaveSalaryFrom() {
        }

        public OffHaveSalaryFrom(Parcel in) {
            mOffPaidFrom = in.readString();
            mPaidFromPeriod = in.readString();
        }

        public static final Creator<OffHaveSalaryFrom> CREATOR = new Creator<OffHaveSalaryFrom>() {
            @Override
            public OffHaveSalaryFrom createFromParcel(Parcel in) {
                return new OffHaveSalaryFrom(in);
            }

            @Override
            public OffHaveSalaryFrom[] newArray(int size) {
                return new OffHaveSalaryFrom[size];
            }
        };

        public String getOffPaidFrom() {
            return mOffPaidFrom;
        }

        public void setOffPaidFrom(String offPaidFrom) {
            mOffPaidFrom = offPaidFrom;
        }

        public String getPaidFromPeriod() {
            return mPaidFromPeriod;
        }

        public void setPaidFromPeriod(String paidFromPeriod) {
            mPaidFromPeriod = paidFromPeriod;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(mOffPaidFrom);
            dest.writeString(mPaidFromPeriod);
        }
    }

    // OffHaveSalaryTo
    public static class OffHaveSalaryTo implements Parcelable {

        @Expose
        @SerializedName("off_paid_to")
        private String mOffPaidTo;
        @Expose
        @SerializedName("paid_to_period")
        private String mPaidToPeriod;

        public OffHaveSalaryTo() {
        }

        public OffHaveSalaryTo(Parcel in) {
            mOffPaidTo = in.readString();
            mPaidToPeriod = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(mOffPaidTo);
            dest.writeString(mPaidToPeriod);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<OffHaveSalaryTo> CREATOR = new Creator<OffHaveSalaryTo>() {
            @Override
            public OffHaveSalaryTo createFromParcel(Parcel in) {
                return new OffHaveSalaryTo(in);
            }

            @Override
            public OffHaveSalaryTo[] newArray(int size) {
                return new OffHaveSalaryTo[size];
            }
        };

        public String getOffPaidTo() {
            return mOffPaidTo;
        }

        public void setOffPaidTo(String offPaidTo) {
            mOffPaidTo = offPaidTo;
        }

        public String getPaidToPeriod() {
            return mPaidToPeriod;
        }

        public void setPaidToPeriod(String paidToPeriod) {
            mPaidToPeriod = paidToPeriod;
        }
    }

    // OffNoSalaryFrom
    public static class OffNoSalaryFrom implements Parcelable {

        @Expose
        @SerializedName("off_from")
        private String mOffFrom;
        @Expose
        @SerializedName("off_from_period")
        private String mOffFromPeriod;

        public OffNoSalaryFrom() {
        }

        public OffNoSalaryFrom(Parcel in) {
            mOffFrom = in.readString();
            mOffFromPeriod = in.readString();
        }

        public static final Creator<OffNoSalaryFrom> CREATOR = new Creator<OffNoSalaryFrom>() {
            @Override
            public OffNoSalaryFrom createFromParcel(Parcel in) {
                return new OffNoSalaryFrom(in);
            }

            @Override
            public OffNoSalaryFrom[] newArray(int size) {
                return new OffNoSalaryFrom[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(mOffFrom);
            dest.writeString(mOffFromPeriod);
        }

        public String getOffFrom() {
            return mOffFrom;
        }

        public void setOffFrom(String offFrom) {
            mOffFrom = offFrom;
        }

        public String getOffFromPeriod() {
            return mOffFromPeriod;
        }

        public void setOffFromPeriod(String offFromPeriod) {
            mOffFromPeriod = offFromPeriod;
        }
    }

    // OffNoSalaryTo
    public static class OffNoSalaryTo implements Parcelable {
        @Expose
        @SerializedName("off_to")
        private String mOffTo;
        @Expose
        @SerializedName("off_to_period")
        private String mOffToPeriod;

        public OffNoSalaryTo() {
        }

        public OffNoSalaryTo(Parcel in) {
            mOffTo = in.readString();
            mOffToPeriod = in.readString();
        }

        public static final Creator<OffNoSalaryTo> CREATOR = new Creator<OffNoSalaryTo>() {
            @Override
            public OffNoSalaryTo createFromParcel(Parcel in) {
                return new OffNoSalaryTo(in);
            }

            @Override
            public OffNoSalaryTo[] newArray(int size) {
                return new OffNoSalaryTo[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(mOffTo);
            dest.writeString(mOffToPeriod);
        }

        public String getOffTo() {
            return mOffTo;
        }

        public void setOffTo(String offTo) {
            mOffTo = offTo;
        }

        public String getOffToPeriod() {
            return mOffToPeriod;
        }

        public void setOffToPeriod(String offToPeriod) {
            mOffToPeriod = offToPeriod;
        }
    }
}
