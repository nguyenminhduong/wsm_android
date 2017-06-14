package com.framgia.wsm.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vinh on 06/06/2017.
 */

public class InsuranceCoverage extends BaseModel implements Parcelable {

    public static final Creator<InsuranceCoverage> CREATOR = new Creator<InsuranceCoverage>() {
        @Override
        public InsuranceCoverage createFromParcel(Parcel in) {
            return new InsuranceCoverage(in);
        }

        @Override
        public InsuranceCoverage[] newArray(int size) {
            return new InsuranceCoverage[size];
        }
    };

    private String mLeaveForCareOfSickChild;
    private String mPregnancyExaminationLeave;
    private String mSickLeave;
    private String mMiscarriageLeave;
    private String mMaternityLeave;
    private String mWifeLaborLeave;

    protected InsuranceCoverage(Parcel in) {
        mLeaveForCareOfSickChild = in.readString();
        mPregnancyExaminationLeave = in.readString();
        mSickLeave = in.readString();
        mMiscarriageLeave = in.readString();
        mMaternityLeave = in.readString();
        mWifeLaborLeave = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mLeaveForCareOfSickChild);
        dest.writeString(mPregnancyExaminationLeave);
        dest.writeString(mSickLeave);
        dest.writeString(mMiscarriageLeave);
        dest.writeString(mMaternityLeave);
        dest.writeString(mWifeLaborLeave);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getLeaveForCareOfSickChild() {
        return mLeaveForCareOfSickChild;
    }

    public void setLeaveForCareOfSickChild(String leaveForCareOfSickChild) {
        mLeaveForCareOfSickChild = leaveForCareOfSickChild;
    }

    public String getPregnancyExaminationLeave() {
        return mPregnancyExaminationLeave;
    }

    public void setPregnancyExaminationLeave(String pregnancyExaminationLeave) {
        mPregnancyExaminationLeave = pregnancyExaminationLeave;
    }

    public String getSickLeave() {
        return mSickLeave;
    }

    public void setSickLeave(String sickLeave) {
        mSickLeave = sickLeave;
    }

    public String getMiscarriageLeave() {
        return mMiscarriageLeave;
    }

    public void setMiscarriageLeave(String miscarriageLeave) {
        mMiscarriageLeave = miscarriageLeave;
    }

    public String getMaternityLeave() {
        return mMaternityLeave;
    }

    public void setMaternityLeave(String maternityLeave) {
        mMaternityLeave = maternityLeave;
    }

    public String getWifeLaborLeave() {
        return mWifeLaborLeave;
    }

    public void setWifeLaborLeave(String wifeLaborLeave) {
        mWifeLaborLeave = wifeLaborLeave;
    }
}
