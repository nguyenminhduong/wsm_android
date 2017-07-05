package com.framgia.wsm.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.framgia.wsm.R;
import com.framgia.wsm.utils.common.DateTimeUtils;
import com.framgia.wsm.utils.validator.Rule;
import com.framgia.wsm.utils.validator.ValidType;
import com.framgia.wsm.utils.validator.Validation;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by tri on 24/05/2017.
 */
public class User extends BaseModel implements Parcelable {
    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
    @Expose
    @SerializedName("name")
    @Validation({
            @Rule(types = ValidType.NON_EMPTY, message = R.string.is_empty)
    })
    private String mName;
    @Expose
    @SerializedName("employee_code")
    private String mCode;
    @Expose
    @SerializedName("email")
    private String mEmail;
    @Expose
    @SerializedName("gender")
    private String mGender;
    @Expose
    @SerializedName("birthday")
    @Validation({
            @Rule(types = ValidType.NON_EMPTY, message = R.string.is_empty)
    })
    private String mBirthday;
    @Expose
    @SerializedName("company_name")
    private String mCompanyName;
    @Expose
    @SerializedName("contract_date")
    private String mContractDate;
    @Expose
    @SerializedName("avatar")
    private String mAvatar;
    @Expose
    @SerializedName("workspaces")
    private List<Branch> mBranches;
    @Expose
    @SerializedName("groups")
    private List<Group> mGroups;
    @Expose
    @SerializedName("start_probation_date")
    private String mStartProbationDate;
    @Expose
    @SerializedName("end_probation_date")
    private String mEndProbationDate;
    @Expose
    @SerializedName("individual_code")
    private String mIndividualCode;
    @Expose
    @SerializedName("name_position")
    private String mNamePosition;
    @Expose
    @SerializedName("name_staff_type")
    private String mNameStaffType;

    private List<LeaveType> mLeaveTypes;

    private List<OffType> mTypesCompany;

    private List<OffType> mTypesInsurance;
    @Expose
    @SerializedName("id")
    private int mId;

    public User() {
    }

    protected User(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mCode = in.readString();
        mEmail = in.readString();
        mGender = in.readString();
        mBirthday = in.readString();
        mCompanyName = in.readString();
        mContractDate = in.readString();
        mAvatar = in.readString();
        mBranches = in.createTypedArrayList(Branch.CREATOR);
        mGroups = in.createTypedArrayList(Group.CREATOR);
        mStartProbationDate = in.readString();
        mEndProbationDate = in.readString();
        mIndividualCode = in.readString();
        mNamePosition = in.readString();
        mNameStaffType = in.readString();
        mLeaveTypes = in.createTypedArrayList(LeaveType.CREATOR);
        mTypesCompany = in.createTypedArrayList(OffType.CREATOR);
        mTypesInsurance = in.createTypedArrayList(OffType.CREATOR);
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        mGender = gender;
    }

    public String getBirthday() {
        return DateTimeUtils.convertUiFormatToDataFormat(mBirthday, DateTimeUtils.INPUT_TIME_FORMAT,
                DateTimeUtils.FORMAT_DATE);
    }

    public void setBirthday(String birthday) {
        mBirthday = birthday;
    }

    public String getCompanyName() {
        return mCompanyName;
    }

    public void setCompanyName(String companyName) {
        mCompanyName = companyName;
    }

    public String getContractDate() {
        return DateTimeUtils.convertUiFormatToDataFormat(mContractDate,
                DateTimeUtils.DATE_FORMAT_YYYY_MM_DD_2, DateTimeUtils.FORMAT_DATE);
    }

    public void setContractDate(String contractDate) {
        mContractDate = contractDate;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        mAvatar = avatar;
    }

    public List<Branch> getBranches() {
        return mBranches;
    }

    public void setBranches(List<Branch> branches) {
        mBranches = branches;
    }

    public List<Group> getGroups() {
        return mGroups;
    }

    public void setGroups(List<Group> groups) {
        mGroups = groups;
    }

    public String getStartProbationDate() {
        return mStartProbationDate;
    }

    public void setStartProbationDate(String startProbationDate) {
        mStartProbationDate = startProbationDate;
    }

    public String getEndProbationDate() {
        return mEndProbationDate;
    }

    public void setEndProbationDate(String endProbationDate) {
        mEndProbationDate = endProbationDate;
    }

    public String getIndividualCode() {
        return mIndividualCode;
    }

    public void setIndividualCode(String individualCode) {
        mIndividualCode = individualCode;
    }

    public String getNamePosition() {
        return mNamePosition;
    }

    public void setNamePosition(String namePosition) {
        mNamePosition = namePosition;
    }

    public String getNameStaffType() {
        return mNameStaffType;
    }

    public void setNameStaffType(String nameStaffType) {
        mNameStaffType = nameStaffType;
    }

    public List<LeaveType> getLeaveTypes() {
        return mLeaveTypes;
    }

    public void setLeaveTypes(List<LeaveType> leaveTypes) {
        mLeaveTypes = leaveTypes;
    }

    public List<OffType> getTypesCompany() {
        return mTypesCompany;
    }

    public void setTypesCompany(List<OffType> typesCompany) {
        mTypesCompany = typesCompany;
    }

    public List<OffType> getTypesInsurance() {
        return mTypesInsurance;
    }

    public void setTypesInsurance(List<OffType> typesInsurance) {
        mTypesInsurance = typesInsurance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeString(mCode);
        dest.writeString(mEmail);
        dest.writeString(mGender);
        dest.writeString(mBirthday);
        dest.writeString(mCompanyName);
        dest.writeString(mContractDate);
        dest.writeString(mAvatar);
        dest.writeTypedList(mBranches);
        dest.writeTypedList(mGroups);
        dest.writeString(mStartProbationDate);
        dest.writeString(mEndProbationDate);
        dest.writeString(mIndividualCode);
        dest.writeString(mNamePosition);
        dest.writeString(mNameStaffType);
        dest.writeTypedList(mLeaveTypes);
        dest.writeTypedList(mTypesCompany);
        dest.writeTypedList(mTypesInsurance);
    }
}
