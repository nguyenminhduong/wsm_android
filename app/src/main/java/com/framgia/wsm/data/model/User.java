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
    @SerializedName("email")
    private String mEmail;
    @Expose
    @SerializedName("avatar")
    private String mAvatar;
    @Expose
    @SerializedName("code")
    private String mCode;
    @Expose
    @SerializedName("branch")
    private List<Branch> mBranches;
    @Expose
    @SerializedName("group")
    private List<Group> mGroups;
    private List<LeaveType> mLeaveTypes;

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

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        mAvatar = avatar;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
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
}
