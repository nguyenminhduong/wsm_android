package com.framgia.wsm.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tri on 31/05/2017.
 */

public class Branch extends BaseModel implements Parcelable {
    public static final Creator<Branch> CREATOR = new Creator<Branch>() {
        @Override
        public Branch createFromParcel(Parcel in) {
            return new Branch(in);
        }

        @Override
        public Branch[] newArray(int size) {
            return new Branch[size];
        }
    };

    @Expose
    @SerializedName("id")
    private int mBranchId;
    @Expose
    @SerializedName("name")
    private String mBranchName;

    public Branch() {
    }

    public Branch(int branchId, String branchName) {
        mBranchId = branchId;
        mBranchName = branchName;
    }

    protected Branch(Parcel in) {
        mBranchId = in.readInt();
        mBranchName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mBranchId);
        dest.writeString(mBranchName);
    }

    public int getBranchId() {
        return mBranchId;
    }

    public void setBranchId(int branchId) {
        mBranchId = branchId;
    }

    public String getBranchName() {
        return mBranchName;
    }

    public void setBranchName(String branchName) {
        mBranchName = branchName;
    }
}
