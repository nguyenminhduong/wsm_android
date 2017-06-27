package com.framgia.wsm.data.source.remote.api.response;

import com.framgia.wsm.data.model.BaseModel;
import com.framgia.wsm.data.model.OffTypeDay;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ths on 24/06/2017.
 */

public class OffTypeResponse extends BaseModel {
    @Expose
    @SerializedName("dayoff_settings")
    private OffTypeDay mOffDay;

    public OffTypeDay getOffDay() {
        return mOffDay;
    }

    public void setOffDay(OffTypeDay offDay) {
        mOffDay = offDay;
    }
}
