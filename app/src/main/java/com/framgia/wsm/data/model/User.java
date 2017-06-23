package com.framgia.wsm.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by tri on 24/05/2017.
 */
public class User {
    @Expose
    @SerializedName("id")
    private Integer mId;
    @Expose
    @SerializedName("name")
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
    private String mBirthday;
    @Expose
    @SerializedName("contract_date")
    private String mContractDate;
    @Expose
    @SerializedName("avatar")
    private Avatar mAvatar;
    @Expose
    @SerializedName("workspaces")
    private List<Branch> mBranches;
    @Expose
    @SerializedName("groups")
    private List<Group> mGroups;

    private List<LeaveType> mLeaveTypes;

    private List<OffType> mTypesCompany;

    private List<OffType> mTypesInsurance;

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
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
        return mBirthday;
    }

    public void setBirthday(String birthday) {
        mBirthday = birthday;
    }

    public String getContractDate() {
        return mContractDate;
    }

    public void setContractDate(String contractDate) {
        mContractDate = contractDate;
    }

    public Avatar getAvatar() {
        return mAvatar;
    }

    public void setAvatar(Avatar avatar) {
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
}
