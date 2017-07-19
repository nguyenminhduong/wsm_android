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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinh on 06/06/2017.
 */

public class OffRequest extends BaseModel implements Parcelable {

    public static final Creator<OffRequest> CREATOR = new Creator<OffRequest>() {
        @Override
        public OffRequest createFromParcel(Parcel in) {
            return new OffRequest(in);
        }

        @Override
        public OffRequest[] newArray(int size) {
            return new OffRequest[size];
        }
    };

    private int mPositionRequestOffType;
    private String mRequestOffName;

    private String mAnnualLeave;
    private String mLeaveForMarriage;
    private String mLeaveForChildMarriage;
    private String mFuneralLeave;

    private String mLeaveForCareOfSickChild;
    private String mPregnancyExaminationLeave;
    private String mSickLeave;
    private String mMiscarriageLeave;
    private String mMaternityLeave;
    private String mWifeLaborLeave;

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
    @SerializedName("workspace")
    private Branch mBranch;
    @Expose
    @SerializedName("group")
    private Group mGroup;
    @Expose
    @SerializedName("number_dayoff_normal")
    private Double mNumberDayOffNormal;
    @Expose
    @SerializedName("workspace_id")
    private int mWorkSpaceId;
    @Expose
    @SerializedName("group_id")
    private int mGroupId;
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
    @SerializedName("handle_by_group_name")
    private String mBeingHandledBy;
    @Expose
    @SerializedName("status")
    private String mStatus;
    @Expose
    @SerializedName("approver")
    private Approver mApprover;
    @Expose
    @SerializedName("request_dayoff_type")
    private List<RequestDayOffType> mRequestDayOffTypes = new ArrayList<>();
    @Expose
    @SerializedName("request_dayoff_types_attributes")
    private List<RequestDayOffTypesAttribute> mRequestDayOffTypesAttributes = new ArrayList<>();

    @Expose
    @SerializedName("user")
    private User mUser;

    public OffRequest() {
        mInsuranceCoverage = new InsuranceCoverage();
        mCompanyPay = new CompanyPay();
    }

    protected OffRequest(Parcel in) {
        mPositionRequestOffType = in.readInt();
        mRequestOffName = in.readString();
        mAnnualLeave = in.readString();
        mLeaveForMarriage = in.readString();
        mLeaveForChildMarriage = in.readString();
        mFuneralLeave = in.readString();
        mLeaveForCareOfSickChild = in.readString();
        mPregnancyExaminationLeave = in.readString();
        mSickLeave = in.readString();
        mMiscarriageLeave = in.readString();
        mMaternityLeave = in.readString();
        mWifeLaborLeave = in.readString();
        mId = in.readInt();
        mCreatedAt = in.readString();
        mProject = in.readString();
        mPosition = in.readString();
        mBranch = in.readParcelable(Branch.class.getClassLoader());
        mGroup = in.readParcelable(Group.class.getClassLoader());
        if (in.readByte() == 0) {
            mNumberDayOffNormal = null;
        } else {
            mNumberDayOffNormal = in.readDouble();
        }
        mWorkSpaceId = in.readInt();
        mGroupId = in.readInt();
        mCompanyPay = in.readParcelable(CompanyPay.class.getClassLoader());
        mInsuranceCoverage = in.readParcelable(InsuranceCoverage.class.getClassLoader());
        mStartDayHaveSalary = in.readParcelable(OffHaveSalaryFrom.class.getClassLoader());
        mEndDayHaveSalary = in.readParcelable(OffHaveSalaryTo.class.getClassLoader());
        mStartDayNoSalary = in.readParcelable(OffNoSalaryFrom.class.getClassLoader());
        mEndDayNoSalary = in.readParcelable(OffNoSalaryTo.class.getClassLoader());
        mReason = in.readString();
        mBeingHandledBy = in.readString();
        mStatus = in.readString();
        mApprover = in.readParcelable(Approver.class.getClassLoader());
        mRequestDayOffTypes = in.createTypedArrayList(RequestDayOffType.CREATOR);
        mRequestDayOffTypesAttributes =
                in.createTypedArrayList(RequestDayOffTypesAttribute.CREATOR);
        mUser = in.readParcelable(User.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mPositionRequestOffType);
        dest.writeString(mRequestOffName);
        dest.writeString(mAnnualLeave);
        dest.writeString(mLeaveForMarriage);
        dest.writeString(mLeaveForChildMarriage);
        dest.writeString(mFuneralLeave);
        dest.writeString(mLeaveForCareOfSickChild);
        dest.writeString(mPregnancyExaminationLeave);
        dest.writeString(mSickLeave);
        dest.writeString(mMiscarriageLeave);
        dest.writeString(mMaternityLeave);
        dest.writeString(mWifeLaborLeave);
        dest.writeInt(mId);
        dest.writeString(mCreatedAt);
        dest.writeString(mProject);
        dest.writeString(mPosition);
        dest.writeParcelable(mBranch, flags);
        dest.writeParcelable(mGroup, flags);
        if (mNumberDayOffNormal == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(mNumberDayOffNormal);
        }
        dest.writeInt(mWorkSpaceId);
        dest.writeInt(mGroupId);
        dest.writeParcelable(mCompanyPay, flags);
        dest.writeParcelable(mInsuranceCoverage, flags);
        dest.writeParcelable(mStartDayHaveSalary, flags);
        dest.writeParcelable(mEndDayHaveSalary, flags);
        dest.writeParcelable(mStartDayNoSalary, flags);
        dest.writeParcelable(mEndDayNoSalary, flags);
        dest.writeString(mReason);
        dest.writeString(mBeingHandledBy);
        dest.writeString(mStatus);
        dest.writeParcelable(mApprover, flags);
        dest.writeTypedList(mRequestDayOffTypes);
        dest.writeTypedList(mRequestDayOffTypesAttributes);
        dest.writeParcelable(mUser, flags);
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

    public Double getNumberDayOffNormal() {
        return mNumberDayOffNormal;
    }

    public void setNumberDayOffNormal(Double numberDayOffNormal) {
        mNumberDayOffNormal = numberDayOffNormal;
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

    public Approver getApprover() {
        return mApprover;
    }

    public void setApprover(Approver approver) {
        mApprover = approver;
    }

    public int getWorkSpaceId() {
        return mWorkSpaceId;
    }

    public void setWorkSpaceId(int workSpaceId) {
        mWorkSpaceId = workSpaceId;
    }

    public int getGroupId() {
        return mGroupId;
    }

    public void setGroupId(int groupId) {
        mGroupId = groupId;
    }

    public List<RequestDayOffType> getRequestDayOffTypes() {
        return mRequestDayOffTypes;
    }

    public void setRequestDayOffTypes(List<RequestDayOffType> requestDayOffTypes) {
        mRequestDayOffTypes = requestDayOffTypes;
    }

    public List<RequestDayOffTypesAttribute> getRequestDayOffTypesAttributes() {
        return mRequestDayOffTypesAttributes;
    }

    public void setRequestDayOffTypesAttributes(
            List<RequestDayOffTypesAttribute> requestDayOffTypesAttributes) {
        mRequestDayOffTypesAttributes = requestDayOffTypesAttributes;
    }

    public String getAnnualLeave() {
        return mAnnualLeave;
    }

    public void setAnnualLeave(String annualLeave) {
        mAnnualLeave = annualLeave;
    }

    public String getLeaveForMarriage() {
        return mLeaveForMarriage;
    }

    public void setLeaveForMarriage(String leaveForMarriage) {
        mLeaveForMarriage = leaveForMarriage;
    }

    public String getLeaveForChildMarriage() {
        return mLeaveForChildMarriage;
    }

    public void setLeaveForChildMarriage(String leaveForChildMarriage) {
        mLeaveForChildMarriage = leaveForChildMarriage;
    }

    public String getFuneralLeave() {
        return mFuneralLeave;
    }

    public void setFuneralLeave(String funeralLeave) {
        mFuneralLeave = funeralLeave;
    }

    public String getLeaveForCareOfSickChild() {
        return mLeaveForCareOfSickChild;
    }

    public void setLeaveForCareOfSickChild(String leaveForCareOfSickChild) {
        mLeaveForCareOfSickChild = leaveForCareOfSickChild;
    }

    public String getPregnancyExaminationLeave() {
        return mPregnancyExaminationLeave;
    }

    public void setPregnancyExaminationLeave(String pregnancyExaminationLeave) {
        mPregnancyExaminationLeave = pregnancyExaminationLeave;
    }

    public String getSickLeave() {
        return mSickLeave;
    }

    public void setSickLeave(String sickLeave) {
        mSickLeave = sickLeave;
    }

    public String getMiscarriageLeave() {
        return mMiscarriageLeave;
    }

    public void setMiscarriageLeave(String miscarriageLeave) {
        mMiscarriageLeave = miscarriageLeave;
    }

    public String getMaternityLeave() {
        return mMaternityLeave;
    }

    public void setMaternityLeave(String maternityLeave) {
        mMaternityLeave = maternityLeave;
    }

    public String getWifeLaborLeave() {
        return mWifeLaborLeave;
    }

    public void setWifeLaborLeave(String wifeLaborLeave) {
        mWifeLaborLeave = wifeLaborLeave;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
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

    // RequestDayOffType
    public static class RequestDayOffType implements Parcelable {

        @Expose
        @SerializedName("id")
        private int mId;
        @Expose
        @SerializedName("special_dayoff_setting_id")
        private int mSpecialDayOffSettingId;
        @Expose
        @SerializedName("number_dayoff")
        private double mNumberDayOff;

        public RequestDayOffType() {
        }

        protected RequestDayOffType(Parcel in) {
            mId = in.readInt();
            mSpecialDayOffSettingId = in.readInt();
            mNumberDayOff = in.readDouble();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(mId);
            dest.writeInt(mSpecialDayOffSettingId);
            dest.writeDouble(mNumberDayOff);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<RequestDayOffType> CREATOR = new Creator<RequestDayOffType>() {
            @Override
            public RequestDayOffType createFromParcel(Parcel in) {
                return new RequestDayOffType(in);
            }

            @Override
            public RequestDayOffType[] newArray(int size) {
                return new RequestDayOffType[size];
            }
        };

        public int getId() {
            return mId;
        }

        public void setId(int id) {
            mId = id;
        }

        public int getSpecialDayOffSettingId() {
            return mSpecialDayOffSettingId;
        }

        public void setSpecialDayOffSettingId(int specialDayOffSettingId) {
            mSpecialDayOffSettingId = specialDayOffSettingId;
        }

        public double getNumberDayOff() {
            return mNumberDayOff;
        }

        public void setNumberDayOff(int numberDayOff) {
            mNumberDayOff = numberDayOff;
        }
    }

    // RequestDayOffTypesAttribute
    public static class RequestDayOffTypesAttribute implements Parcelable {

        @Expose
        @SerializedName("id")
        private Integer mId;
        @Expose
        @SerializedName("special_dayoff_setting_id")
        private String mSpecialDayOffSettingId;
        @Expose
        @SerializedName("number_dayoff")
        private String mNumberDayOff;
        @Expose
        @SerializedName("_destroy")
        private String mDestroy;

        public RequestDayOffTypesAttribute() {
        }

        public RequestDayOffTypesAttribute(Parcel in) {
            if (in.readByte() == 0) {
                mId = null;
            } else {
                mId = in.readInt();
            }
            mSpecialDayOffSettingId = in.readString();
            mNumberDayOff = in.readString();
            mDestroy = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            if (mId == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(mId);
            }
            dest.writeString(mSpecialDayOffSettingId);
            dest.writeString(mNumberDayOff);
            dest.writeString(mDestroy);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<RequestDayOffTypesAttribute> CREATOR =
                new Creator<RequestDayOffTypesAttribute>() {
                    @Override
                    public RequestDayOffTypesAttribute createFromParcel(Parcel in) {
                        return new RequestDayOffTypesAttribute(in);
                    }

                    @Override
                    public RequestDayOffTypesAttribute[] newArray(int size) {
                        return new RequestDayOffTypesAttribute[size];
                    }
                };

        public Integer getId() {
            return mId;
        }

        public void setId(Integer id) {
            mId = id;
        }

        public String getSpecialDayOffSettingId() {
            return mSpecialDayOffSettingId;
        }

        public void setSpecialDayOffSettingId(String specialDayOffSettingId) {
            mSpecialDayOffSettingId = specialDayOffSettingId;
        }

        public String getNumberDayOff() {
            return mNumberDayOff;
        }

        public void setNumberDayOff(String numberDayOff) {
            mNumberDayOff = numberDayOff;
        }

        public String getDestroy() {
            return mDestroy;
        }

        public void setDestroy(String destroy) {
            mDestroy = destroy;
        }
    }
}
