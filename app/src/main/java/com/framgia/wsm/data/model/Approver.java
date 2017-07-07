package com.framgia.wsm.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ASUS on 07/07/2017.
 */

public class Approver extends BaseModel implements Parcelable {
    @Expose
    @SerializedName("id")
    private int mId;
    @Expose
    @SerializedName("name")
    private String mName;
    @Expose
    @SerializedName("gender")
    private String mGender;
    @Expose
    @SerializedName("role")
    private String mRole;

    protected Approver(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mGender = in.readString();
        mRole = in.readString();
    }

    public static final Creator<Approver> CREATOR = new Creator<Approver>() {
        @Override
        public Approver createFromParcel(Parcel in) {
            return new Approver(in);
        }

        @Override
        public Approver[] newArray(int size) {
            return new Approver[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeString(mGender);
        dest.writeString(mRole);
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

    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        mGender = gender;
    }

    public String getRolo() {
        return mRole;
    }

    public void setRolo(String rolo) {
        mRole = rolo;
    }
}
