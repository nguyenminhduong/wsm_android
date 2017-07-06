package com.framgia.wsm.data.model;

import com.framgia.wsm.utils.common.DateTimeUtils;
import com.framgia.wsm.utils.common.StringUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Date;

/**
 * Created by minhd on 7/6/2017.
 */

public class TimeSheetDate extends BaseModel {
    @Expose
    @SerializedName("date")
    private String mDate;
    @Expose
    @SerializedName("time_in")
    private String mTimeIn;
    @Expose
    @SerializedName("time_out")
    private String mTimeOut;
    @Expose
    @SerializedName("text_morning")
    private String mTextMorning;
    @Expose
    @SerializedName("text_afternoon")
    private String mTextAfternoon;
    @Expose
    @SerializedName("color_morning")
    private String mColorMorning;
    @Expose
    @SerializedName("color_afternoon")
    private String mColorAfternoon;

    public TimeSheetDate() {
    }

    public TimeSheetDate(String date, String timeIn, String timeOut, String textMorning,
            String textAfternoon, String colorMorning, String colorAfternoon) {
        mDate = date;
        mTimeIn = timeIn;
        mTimeOut = timeOut;
        mTextMorning = textMorning;
        mTextAfternoon = textAfternoon;
        mColorMorning = colorMorning;
        mColorAfternoon = colorAfternoon;
    }

    public String getDateString() {
        return mDate;
    }

    public Date getDate() {
        return DateTimeUtils.convertStringToDate(mDate);
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getTimeIn() {
        return mTimeIn;
    }

    public void setTimeIn(String timeIn) {
        mTimeIn = timeIn;
    }

    public String getTimeOut() {
        return mTimeOut;
    }

    public void setTimeOut(String timeOut) {
        mTimeOut = timeOut;
    }

    public String getTextMorning() {
        return mTextMorning;
    }

    public void setTextMorning(String textMorning) {
        mTextMorning = textMorning;
    }

    public String getTextAfternoon() {
        return mTextAfternoon;
    }

    public void setTextAfternoon(String textAfternoon) {
        mTextAfternoon = textAfternoon;
    }

    public String getColorMorning() {
        return mColorMorning;
    }

    public void setColorMorning(String colorMorning) {
        mColorMorning = colorMorning;
    }

    public String getColorAfternoon() {
        return mColorAfternoon;
    }

    public void setColorAfternoon(String colorAfternoon) {
        mColorAfternoon = colorAfternoon;
    }

    public boolean isNormalDay() {
        return StringUtils.isBlank(mTextMorning)
                && StringUtils.isBlank(mTextAfternoon)
                && StringUtils.isBlank(mColorMorning)
                && StringUtils.isBlank(mColorAfternoon);
    }

    public boolean isDayOffMorning() {
        return StringUtils.isNotBlank(mTextMorning) && StringUtils.isBlank(mTextAfternoon);
    }

    public boolean isDayOffAfternoon() {
        return StringUtils.isBlank(mTextMorning) && StringUtils.isNotBlank(mTextAfternoon);
    }

    public boolean isDayOffAllDay() {
        return StringUtils.isNotBlank(mTextMorning) && StringUtils.isNotBlank(mTextAfternoon);
    }

    public boolean isColorMorning() {
        return StringUtils.isNotBlank(mColorMorning) && StringUtils.isBlank(mColorAfternoon);
    }

    public boolean isColorAfternoon() {
        return StringUtils.isNotBlank(mColorAfternoon) && StringUtils.isBlank(mColorMorning);
    }

    public boolean isColorAllDay() {
        return StringUtils.isNotBlank(mColorAfternoon) && StringUtils.isNotBlank(mColorMorning);
    }
}
