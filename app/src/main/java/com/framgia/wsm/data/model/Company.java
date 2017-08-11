package com.framgia.wsm.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ths on 06/07/2017.
 */

public class Company extends BaseModel implements Parcelable {

    public static final Creator<Company> CREATOR = new Creator<Company>() {
        @Override
        public Company createFromParcel(Parcel in) {
            return new Company(in);
        }

        @Override
        public Company[] newArray(int size) {
            return new Company[size];
        }
    };
    @Expose
    @SerializedName("id")
    private int mId;
    @Expose
    @SerializedName("name")
    private String mCompanyName;
    @Expose
    @SerializedName("cutoff_date")
    private int mCutOffDate;

    protected Company(Parcel in) {
        mId = in.readInt();
        mCompanyName = in.readString();
        mCutOffDate = in.readInt();
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getCompanyName() {
        return mCompanyName;
    }

    public void setCompanyName(String companyName) {
        mCompanyName = companyName;
    }

    public int getCutOffDate() {
        return mCutOffDate;
    }

    public void setCutOffDate(int cutOffDate) {
        mCutOffDate = cutOffDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mCompanyName);
        parcel.writeInt(mCutOffDate);
    }
}
