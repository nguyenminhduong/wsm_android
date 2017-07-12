package com.framgia.wsm.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ths on 24/06/2017.
 */

public class OffType extends BaseModel implements Parcelable {
    @Expose
    @SerializedName("id")
    private int mId;
    @Expose
    @SerializedName("name")
    private String mName;
    @Expose
    @SerializedName("pay_type")
    private String mPayType;
    @Expose
    @SerializedName("amount")
    private float mAmount;

    public OffType(int id, String name, String payType, int amount) {
        mId = id;
        mName = name;
        mPayType = payType;
        mAmount = amount;
    }

    protected OffType(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mPayType = in.readString();
        mAmount = in.readInt();
    }

    public static final Creator<OffType> CREATOR = new Creator<OffType>() {
        @Override
        public OffType createFromParcel(Parcel in) {
            return new OffType(in);
        }

        @Override
        public OffType[] newArray(int size) {
            return new OffType[size];
        }
    };

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPayType() {
        return mPayType;
    }

    public void setPayType(String payType) {
        mPayType = payType;
    }

    public float getAmount() {
        return mAmount;
    }

    public void setAmount(float amount) {
        mAmount = amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mName);
        parcel.writeString(mPayType);
        parcel.writeFloat(mAmount);
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }
}
