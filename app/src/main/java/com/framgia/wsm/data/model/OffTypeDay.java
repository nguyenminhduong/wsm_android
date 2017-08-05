package com.framgia.wsm.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by ths on 24/06/2017.
 */

public class OffTypeDay {
    @Expose
    @SerializedName("special_dayoff_settings")
    List<OffType> mOffTypes;
    @Expose
    @SerializedName("remaining_days_off")
    private Float mRemainingDayOff;

    public List<OffType> getOffTypes() {
        return mOffTypes;
    }

    public void setOffTypes(List<OffType> offTypes) {
        mOffTypes = offTypes;
    }

    public Float getRemainingDayOff() {
        return mRemainingDayOff;
    }

    public void setRemainingDayOff(Float remainingDayOff) {
        mRemainingDayOff = remainingDayOff;
    }
}
