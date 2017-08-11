package com.framgia.wsm.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Duong on 6/9/2017.
 */

public class LeaveType extends BaseModel implements Parcelable {
    public static final Creator<LeaveType> CREATOR = new Creator<LeaveType>() {
        @Override
        public LeaveType createFromParcel(Parcel in) {
            return new LeaveType(in);
        }

        @Override
        public LeaveType[] newArray(int size) {
            return new LeaveType[size];
        }
    };

    @Expose
    @SerializedName("id")
    private int mId;
    @Expose
    @SerializedName("name")
    private String mName;
    @Expose
    @SerializedName("code")
    private String mCode;
    @Expose
    @SerializedName("block_minutes")
    private int mBlockMinutes;

    public LeaveType() {
    }

    protected LeaveType(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mCode = in.readString();
        mBlockMinutes = in.readInt();
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

    public int getBlockMinutes() {
        return mBlockMinutes;
    }

    public void setBlockMinutes(int blockMinutes) {
        mBlockMinutes = blockMinutes;
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
        dest.writeInt(mBlockMinutes);
    }
}
