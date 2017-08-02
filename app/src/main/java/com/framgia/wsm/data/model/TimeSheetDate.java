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
        return DateTimeUtils.convertStringToDate(mDate, DateTimeUtils.DATE_FORMAT_YYYY_MM_DD_2);
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getTimeIn() {
        if (StringUtils.isBlank(mTimeIn) && StringUtils.isBlank(mTextMorning)) {
            return "";
        }
        if (StringUtils.isNotBlank(mTextMorning) && mTextMorning.equals(mTimeIn)) {
            return DateTimeUtils.convertUiFormatToDataFormat(mTimeIn,
                    DateTimeUtils.INPUT_TIME_FORMAT, DateTimeUtils.TIME_FORMAT_HH_MM);
        }
        return mTextMorning;
    }

    public void setTimeIn(String timeIn) {
        mTimeIn = timeIn;
    }

    public String getTimeOut() {
        if (StringUtils.isBlank(mTimeOut) && StringUtils.isBlank(mTextAfternoon)) {
            return "";
        }
        if (StringUtils.isNotBlank(mTextAfternoon) && mTextAfternoon.equals(mTimeOut)) {
            return DateTimeUtils.convertUiFormatToDataFormat(mTimeOut,
                    DateTimeUtils.INPUT_TIME_FORMAT, DateTimeUtils.TIME_FORMAT_HH_MM);
        }
        return mTextAfternoon;
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

    public String getColorAfternoon() {
        return mColorAfternoon;
    }

    public boolean isDayOffMorning() {
        return StringUtils.isNotBlank(mTextMorning)
                && !mTextMorning.equals(mTimeIn);
    }

    public boolean isDayOffAfternoon() {
        return StringUtils.isNotBlank(mTextAfternoon)
                && !mTextAfternoon.equals(mTimeOut);
    }

    public void setColorMorning(String colorMorning) {
        mColorMorning = colorMorning;
    }

    public void setColorAfternoon(String colorAfternoon) {
        mColorAfternoon = colorAfternoon;
    }
}
