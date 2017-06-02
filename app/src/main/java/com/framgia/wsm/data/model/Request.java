package com.framgia.wsm.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tri on 02/06/2017.
 */

public class Request extends BaseModel implements Parcelable {

    public static final Creator<Request> CREATOR = new Creator<Request>() {
        @Override
        public Request createFromParcel(Parcel in) {
            return new Request(in);
        }

        @Override
        public Request[] newArray(int size) {
            return new Request[size];
        }
    };
    private int mPositionLeaveType;
    private String mRequestName;

    public Request() {
    }

    protected Request(Parcel in) {
        mPositionLeaveType = in.readInt();
        mRequestName = in.readString();
    }

    public int getPositionLeaveType() {
        return mPositionLeaveType;
    }

    public void setPositionLeaveType(int positionLeaveType) {
        mPositionLeaveType = positionLeaveType;
    }

    public String getRequestName() {
        return mRequestName;
    }

    public void setRequestName(String requestName) {
        mRequestName = requestName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mPositionLeaveType);
        dest.writeString(mRequestName);
    }
}
