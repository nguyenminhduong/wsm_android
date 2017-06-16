package com.framgia.wsm.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ASUS on 06/06/2017.
 */

public class CompanyPay extends BaseModel implements Parcelable {

    public static final Creator<CompanyPay> CREATOR = new Creator<CompanyPay>() {
        @Override
        public CompanyPay createFromParcel(Parcel in) {
            return new CompanyPay(in);
        }

        @Override
        public CompanyPay[] newArray(int size) {
            return new CompanyPay[size];
        }
    };
    private String mAnnualLeave;
    private String mLeaveForMarriage;
    private String mLeaveForChildMarriage;
    private String mFuneralLeave;

    protected CompanyPay(Parcel in) {
        mAnnualLeave = in.readString();
        mLeaveForMarriage = in.readString();
        mLeaveForChildMarriage = in.readString();
        mFuneralLeave = in.readString();
    }

    public CompanyPay() {
    }

    public String getAnnualLeave() {
        return mAnnualLeave;
    }

    public void setAnnualLeave(String annualLeave) {
        mAnnualLeave = annualLeave;
    }

    public String getLeaveForMarriage() {
        return mLeaveForMarriage;
    }

    public void setLeaveForMarriage(String leaveForMarriage) {
        mLeaveForMarriage = leaveForMarriage;
    }

    public String getLeaveForChildMarriage() {
        return mLeaveForChildMarriage;
    }

    public void setLeaveForChildMarriage(String leaveForChildMarriage) {
        mLeaveForChildMarriage = leaveForChildMarriage;
    }

    public String getFuneralLeave() {
        return mFuneralLeave;
    }

    public void setFuneralLeave(String funeralLeave) {
        mFuneralLeave = funeralLeave;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAnnualLeave);
        dest.writeString(mLeaveForMarriage);
        dest.writeString(mLeaveForChildMarriage);
        dest.writeString(mFuneralLeave);
    }
}
