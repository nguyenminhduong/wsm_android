package com.framgia.wsm.data.model;

/**
 * Created by ASUS on 06/06/2017.
 */

public class CompanyPay extends BaseModel {

    private String mAnnualLeave;
    private String mLeaveForMarriage;
    private String mLeaveForChildMarriage;
    private String mFuneralLeave;

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
}
