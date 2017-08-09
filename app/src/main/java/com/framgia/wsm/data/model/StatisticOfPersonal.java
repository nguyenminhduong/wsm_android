package com.framgia.wsm.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nguyenhuy95dn on 8/4/2017.
 */

public class StatisticOfPersonal {
    @Expose
    @SerializedName("number_leave_times")
    private LeaveOrOverTime mLeaveTimes;
    @Expose
    @SerializedName("number_days_off")
    private DaysOff mDaysOff;
    @Expose
    @SerializedName("number_ot_hours")
    private LeaveOrOverTime mOverTime;
    @Expose
    @SerializedName("number_remaining_days_off")
    private RemainingDaysOff mRemainingDaysOff;

    public LeaveOrOverTime getLeaveTimes() {
        return mLeaveTimes;
    }

    public void setLeaveTimes(LeaveOrOverTime leaveTimes) {
        mLeaveTimes = leaveTimes;
    }

    public DaysOff getDaysOff() {
        return mDaysOff;
    }

    public void setDaysOff(DaysOff daysOff) {
        mDaysOff = daysOff;
    }

    public LeaveOrOverTime getOverTime() {
        return mOverTime;
    }

    public void setOverTime(LeaveOrOverTime overTime) {
        mOverTime = overTime;
    }

    public RemainingDaysOff getRemainingDaysOff() {
        return mRemainingDaysOff;
    }

    public void setRemainingDaysOff(RemainingDaysOff remainingDaysOff) {
        mRemainingDaysOff = remainingDaysOff;
    }

    /**
     * LeaveTime-OverTime
     */
    public class LeaveOrOverTime {
        @Expose
        @SerializedName("approved_in_month")
        private double mApprovedInMonth;
        @Expose
        @SerializedName("rejected_in_month")
        private double mRejectedInMonth;
        @Expose
        @SerializedName("approved_in_year")
        private double mApprovedInYear;
        @Expose
        @SerializedName("rejected_in_year")
        private double mRejectedInYear;

        public double getApprovedInMonth() {
            return mApprovedInMonth;
        }

        public void setApprovedInMonth(double approvedInMonth) {
            mApprovedInMonth = approvedInMonth;
        }

        public double getRejectedInMonth() {
            return mRejectedInMonth;
        }

        public void setRejectedInMonth(double rejectedInMonth) {
            mRejectedInMonth = rejectedInMonth;
        }

        public double getApprovedInYear() {
            return mApprovedInYear;
        }

        public void setApprovedInYear(double approvedInYear) {
            mApprovedInYear = approvedInYear;
        }

        public double getRejectedInYear() {
            return mRejectedInYear;
        }

        public void setRejectedInYear(double rejectedInYear) {
            mRejectedInYear = rejectedInYear;
        }
    }

    /**
     * DaysOff
     */
    public class DaysOff {
        @Expose
        @SerializedName("approved_in_month")
        private double mApprovedInMonth;
        @Expose
        @SerializedName("rejected_in_month")
        private double mRejectedInMonth;
        @Expose
        @SerializedName("have_salary_pay_by_company_in_month")
        private double mSalaryPayByCompanyInMonth;
        @Expose
        @SerializedName("have_salary_pay_by_company_in_year")
        private double mSalaryPayByCompanyInYear;
        @Expose
        @SerializedName("no_salary_in_month")
        private double mNoSalaryInMonth;
        @Expose
        @SerializedName("no_salary_in_year")
        private double mNoSalaryInYear;
        @Expose
        @SerializedName("have_salary_pay_by_insurance_in_month")
        private double mSalaryPayByInsuranceInMonth;
        @Expose
        @SerializedName("have_salary_pay_by_insurance_in_year")
        private double mSalaryPayByInsuranceInYear;

        public double getApprovedInMonth() {
            return mApprovedInMonth;
        }

        public void setApprovedInMonth(double approvedInMonth) {
            mApprovedInMonth = approvedInMonth;
        }

        public double getRejectedInMonth() {
            return mRejectedInMonth;
        }

        public void setRejectedInMonth(double rejectedInMonth) {
            mRejectedInMonth = rejectedInMonth;
        }

        public double getSalaryPayByCompanyInMonth() {
            return mSalaryPayByCompanyInMonth;
        }

        public void setSalaryPayByCompanyInMonth(double salaryPayByCompanyInMonth) {
            mSalaryPayByCompanyInMonth = salaryPayByCompanyInMonth;
        }

        public double getSalaryPayByCompanyInYear() {
            return mSalaryPayByCompanyInYear;
        }

        public void setSalaryPayByCompanyInYear(double salaryPayByCompanyInYear) {
            mSalaryPayByCompanyInYear = salaryPayByCompanyInYear;
        }

        public double getNoSalaryInMonth() {
            return mNoSalaryInMonth;
        }

        public void setNoSalaryInMonth(double noSalaryInMonth) {
            mNoSalaryInMonth = noSalaryInMonth;
        }

        public double getNoSalaryInYear() {
            return mNoSalaryInYear;
        }

        public void setNoSalaryInYear(double noSalaryInYear) {
            mNoSalaryInYear = noSalaryInYear;
        }

        public double getSalaryPayByInsuranceInMonth() {
            return mSalaryPayByInsuranceInMonth;
        }

        public void setSalaryPayByInsuranceInMonth(double salaryPayByInsuranceInMonth) {
            mSalaryPayByInsuranceInMonth = salaryPayByInsuranceInMonth;
        }

        public double getSalaryPayByInsuranceInYear() {
            return mSalaryPayByInsuranceInYear;
        }

        public void setSalaryPayByInsuranceInYear(double salaryPayByInsuranceInYear) {
            mSalaryPayByInsuranceInYear = salaryPayByInsuranceInYear;
        }
    }

    /**
     * RemainingDaysOff
     */
    public class RemainingDaysOff {
        @Expose
        @SerializedName("previous_year")
        private double mPreviousYear;
        @Expose
        @SerializedName("in_year")
        private double mInYear;
        @Expose
        @SerializedName("bonus_in_year")
        private double mBonusInYear;
        @Expose
        @SerializedName("total_remain_in_year")
        private double mTotalRemainInYear;

        public double getPreviousYear() {
            return mPreviousYear;
        }

        public void setPreviousYear(double previousYear) {
            mPreviousYear = previousYear;
        }

        public double getInYear() {
            return mInYear;
        }

        public void setInYear(double inYear) {
            mInYear = inYear;
        }

        public double getBonusInYear() {
            return mBonusInYear;
        }

        public void setBonusInYear(double bonusInYear) {
            mBonusInYear = bonusInYear;
        }

        public double getTotalRemainInYear() {
            return mTotalRemainInYear;
        }

        public void setTotalRemainInYear(double totalRemainInYear) {
            mTotalRemainInYear = totalRemainInYear;
        }
    }
}
