package com.framgia.wsm.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tri on 31/05/2017.
 */

public class Branch extends BaseModel implements Parcelable {
    public static final Creator<Branch> CREATOR = new Creator<Branch>() {
        @Override
        public Branch createFromParcel(Parcel source) {
            return new Branch(source);
        }

        @Override
        public Branch[] newArray(int size) {
            return new Branch[size];
        }
    };
    private String mBranchId;
    private String mBranchName;

    public Branch() {
    }

    public Branch(String branchId, String branchName) {
        mBranchId = branchId;
        mBranchName = branchName;
    }

    protected Branch(Parcel in) {
        this.mBranchId = in.readString();
        this.mBranchName = in.readString();
    }

    public String getBranchId() {
        return mBranchId;
    }

    public void setBranchId(String branchId) {
        mBranchId = branchId;
    }

    public String getBranchName() {
        return mBranchName;
    }

    public void setBranchName(String branchName) {
        mBranchName = branchName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mBranchId);
        dest.writeString(this.mBranchName);
    }
}
