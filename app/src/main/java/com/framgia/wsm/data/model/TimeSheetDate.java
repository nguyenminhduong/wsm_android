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
    private static final int COLOR_CODE_THREE_DIGIT = 4;
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
        // todo edit later
        return convertColor(mColorMorning);
    }

    public String getColorAfternoon() {
        // todo edit later
        return convertColor(mColorAfternoon);
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

    public void setColorMorning(String colorMorning) {
        mColorMorning = colorMorning;
    }

    public boolean isColorAfternoon() {
        return StringUtils.isNotBlank(mColorAfternoon) && StringUtils.isBlank(mColorMorning);
    }

    public void setColorAfternoon(String colorAfternoon) {
        mColorAfternoon = colorAfternoon;
    }

    public boolean isColorAllDay() {
        return StringUtils.isNotBlank(mColorAfternoon) && StringUtils.isNotBlank(mColorMorning);
    }

    private String convertColor(String color) {
        if (StringUtils.isBlank(color)) {
            return null;
        }
        if (color.length() == COLOR_CODE_THREE_DIGIT) {
            String result = "#";
            for (int i = 1; i < color.length(); i++) {
                char c = color.charAt(i);
                result = result + c + c;
            }
            return result;
        }
        return color;
    }
}
