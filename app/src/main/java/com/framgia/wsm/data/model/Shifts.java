package com.framgia.wsm.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.framgia.wsm.utils.common.DateTimeUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by minhd on 8/11/2017.
 */

public class Shifts extends BaseModel implements Parcelable {
    public static final Creator<Shifts> CREATOR = new Creator<Shifts>() {
        @Override
        public Shifts createFromParcel(Parcel in) {
            return new Shifts(in);
        }

        @Override
        public Shifts[] newArray(int size) {
            return new Shifts[size];
        }
    };
    @SerializedName("id")
    @Expose
    private int mId;
    @SerializedName("time_in")
    @Expose
    private String mTimeIn;
    @SerializedName("time_lunch")
    @Expose
    private String mTimeLunch;
    @SerializedName("time_afternoon")
    @Expose
    private String mTimeAfternoon;
    @SerializedName("time_out")
    @Expose
    private String mTimeOut;
    @SerializedName("max_come_late")
    @Expose
    private float mMaxComeLate;
    @SerializedName("max_leves_early")
    @Expose
    private float mMaxLevesEarly;

    protected Shifts(Parcel in) {
        mId = in.readInt();
        mTimeIn = in.readString();
        mTimeLunch = in.readString();
        mTimeAfternoon = in.readString();
        mTimeOut = in.readString();
        mMaxComeLate = in.readFloat();
        mMaxLevesEarly = in.readFloat();
    }

    public Shifts() {
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public String getTimeIn() {
        return mTimeIn;
    }

    public void setTimeIn(String timeIn) {
        mTimeIn = timeIn;
    }

    public String getTimeLunch() {
        return mTimeLunch;
    }

    public void setTimeLunch(String timeLunch) {
        mTimeLunch = timeLunch;
    }

    public String getTimeAfternoon() {
        return mTimeAfternoon;
    }

    public void setTimeAfternoon(String timeAfternoon) {
        mTimeAfternoon = timeAfternoon;
    }

    public String getTimeOut() {
        return mTimeOut;
    }

    public void setTimeOut(String timeOut) {
        mTimeOut = timeOut;
    }

    public float getMaxComeLate() {
        return mMaxComeLate;
    }

    public void setMaxComeLate(float maxComeLate) {
        mMaxComeLate = maxComeLate;
    }

    public float getMaxLevesEarly() {
        return mMaxLevesEarly;
    }

    public void setMaxLevesEarly(float maxLevesEarly) {
        mMaxLevesEarly = maxLevesEarly;
    }

    public float getNumberHourLunch() {
        return DateTimeUtils.getHourBetweenTwoDate(mTimeAfternoon, mTimeLunch,
                DateTimeUtils.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mTimeIn);
        dest.writeString(mTimeLunch);
        dest.writeString(mTimeAfternoon);
        dest.writeString(mTimeOut);
        dest.writeFloat(mMaxComeLate);
        dest.writeFloat(mMaxLevesEarly);
    }
}
