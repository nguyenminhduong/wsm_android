package com.framgia.wsm.screen.statisticsbymonth;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;
import com.framgia.wsm.BR;
import com.framgia.wsm.data.model.StatisticOfPersonal;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;

/**
 * Exposes the data to be used in the Statisticsbymonth screen.
 */

public class StatisticsByMonthViewModel extends BaseObservable
        implements StatisticsByMonthContract.ViewModel {

    private static final String TAG = "StatisticsByMonth";

    private final StatisticsByMonthContract.Presenter mPresenter;
    private String mLeaveTimeApprovedInMonth;
    private String mLeaveTimeRejectedInMonth;
    private String mNumberOfDayOffApprovedInMonth;
    private String mNumberOfDayOffRejectedInMonth;
    private String mDayOffHaveSalaryPayByCompany;
    private String mDayOffHaveSalaryPayByInsurance;
    private String mDayOffNoSalary;
    private String mNumberOfOverTime;
    private String mNumberRemainDayPreviousYear;
    private String mNumberRemainDayInYear;
    private String mNumberDayOffAddInYear;
    private String mTotalDayOffRemainInYear;
    private boolean mIsEmployeeFullTime;

    StatisticsByMonthViewModel(StatisticsByMonthContract.Presenter presenter) {
        mPresenter = presenter;
        mPresenter.setViewModel(this);
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Override
    public void fillData(StatisticOfPersonal statistic) {
        getDataStatistic(statistic);
    }

    @Override
    public void onGetUserSuccess(User user) {
        mIsEmployeeFullTime = user.isFullTime();
        notifyPropertyChanged(BR.employeeFullTime);
    }

    @Override
    public void onGetUserError(BaseException error) {
        Log.e(TAG, "onGetUserError: ", error);
    }

    private void getDataStatistic(StatisticOfPersonal statistic) {
        if (statistic.getDaysOff() == null
                || statistic.getLeaveTimes() == null
                || statistic.getOverTime() == null) {
            return;
        }
        setLeaveTimeApprovedInMonth(String.valueOf(statistic.getLeaveTimes().getApprovedInMonth()));
        setLeaveTimeRejectedInMonth(String.valueOf(statistic.getLeaveTimes().getRejectedInMonth()));
        setNumberOfDayOffApprovedInMonth(
                String.valueOf(statistic.getDaysOff().getApprovedInMonth()));
        setNumberOfDayOffRejectedInMonth(
                String.valueOf(statistic.getDaysOff().getRejectedInMonth()));
        setDayOffHaveSalaryPayByCompany(
                String.valueOf(statistic.getDaysOff().getSalaryPayByCompanyInMonth()));
        setDayOffHaveSalaryPayByInsurance(
                String.valueOf(statistic.getDaysOff().getSalaryPayByInsuranceInMonth()));
        setDayOffNoSalary(String.valueOf(statistic.getDaysOff().getNoSalaryInMonth()));
        setNumberOfOverTime(String.valueOf(statistic.getOverTime().getApprovedInMonth()));
        setNumberRemainDayPreviousYear(
                String.valueOf(statistic.getRemainingDaysOff().getPreviousYear()));
        setNumberRemainDayInYear(String.valueOf(statistic.getRemainingDaysOff().getInYear()));
        setNumberDayOffAddInYear(String.valueOf(statistic.getRemainingDaysOff().getBonusInYear()));
        setTotalDayOffRemainInYear(
                String.valueOf(statistic.getRemainingDaysOff().getTotalRemainInYear()));
    }

    @Bindable
    public String getLeaveTimeApprovedInMonth() {
        return mLeaveTimeApprovedInMonth;
    }

    private void setLeaveTimeApprovedInMonth(String leaveTimeApprovedInMonth) {
        mLeaveTimeApprovedInMonth = leaveTimeApprovedInMonth;
        notifyPropertyChanged(BR.leaveTimeApprovedInMonth);
    }

    @Bindable
    public String getLeaveTimeRejectedInMonth() {
        return mLeaveTimeRejectedInMonth;
    }

    private void setLeaveTimeRejectedInMonth(String leaveTimeRejectedInMonth) {
        mLeaveTimeRejectedInMonth = leaveTimeRejectedInMonth;
        notifyPropertyChanged(BR.leaveTimeRejectedInMonth);
    }

    @Bindable
    public String getNumberOfDayOffApprovedInMonth() {
        return mNumberOfDayOffApprovedInMonth;
    }

    private void setNumberOfDayOffApprovedInMonth(String numberOfDayOffApprovedInMonth) {
        mNumberOfDayOffApprovedInMonth = numberOfDayOffApprovedInMonth;
        notifyPropertyChanged(BR.numberOfDayOffApprovedInMonth);
    }

    @Bindable
    public String getNumberOfDayOffRejectedInMonth() {
        return mNumberOfDayOffRejectedInMonth;
    }

    private void setNumberOfDayOffRejectedInMonth(String numberOfDayOffRejectedInMonth) {
        mNumberOfDayOffRejectedInMonth = numberOfDayOffRejectedInMonth;
        notifyPropertyChanged(BR.numberOfDayOffRejectedInMonth);
    }

    @Bindable
    public String getDayOffHaveSalaryPayByCompany() {
        return mDayOffHaveSalaryPayByCompany;
    }

    private void setDayOffHaveSalaryPayByCompany(String dayOffHaveSalaryPayByCompany) {
        mDayOffHaveSalaryPayByCompany = dayOffHaveSalaryPayByCompany;
        notifyPropertyChanged(BR.dayOffHaveSalaryPayByCompany);
    }

    @Bindable
    public String getDayOffHaveSalaryPayByInsurance() {
        return mDayOffHaveSalaryPayByInsurance;
    }

    private void setDayOffHaveSalaryPayByInsurance(String dayOffHaveSalaryPayByInsurance) {
        mDayOffHaveSalaryPayByInsurance = dayOffHaveSalaryPayByInsurance;
        notifyPropertyChanged(BR.dayOffHaveSalaryPayByInsurance);
    }

    @Bindable
    public String getDayOffNoSalary() {
        return mDayOffNoSalary;
    }

    private void setDayOffNoSalary(String dayOffNoSalary) {
        mDayOffNoSalary = dayOffNoSalary;
        notifyPropertyChanged(BR.dayOffNoSalary);
    }

    @Bindable
    public String getNumberOfOverTime() {
        return mNumberOfOverTime;
    }

    private void setNumberOfOverTime(String numberOfOverTime) {
        mNumberOfOverTime = numberOfOverTime;
        notifyPropertyChanged(BR.numberOfOverTime);
    }

    @Bindable
    public String getNumberRemainDayPreviousYear() {
        return mNumberRemainDayPreviousYear;
    }

    private void setNumberRemainDayPreviousYear(String numberRemainDayPreviousYear) {
        mNumberRemainDayPreviousYear = numberRemainDayPreviousYear;
        notifyPropertyChanged(BR.numberRemainDayPreviousYear);
    }

    @Bindable
    public String getNumberRemainDayInYear() {
        return mNumberRemainDayInYear;
    }

    private void setNumberRemainDayInYear(String numberRemainDayInYear) {
        mNumberRemainDayInYear = numberRemainDayInYear;
        notifyPropertyChanged(BR.numberRemainDayInYear);
    }

    @Bindable
    public String getNumberDayOffAddInYear() {
        return mNumberDayOffAddInYear;
    }

    private void setNumberDayOffAddInYear(String numberDayOffAddInYear) {
        mNumberDayOffAddInYear = numberDayOffAddInYear;
        notifyPropertyChanged(BR.numberDayOffAddInYear);
    }

    @Bindable
    public String getTotalDayOffRemainInYear() {
        return mTotalDayOffRemainInYear;
    }

    private void setTotalDayOffRemainInYear(String totalDayOffRemainInYear) {
        mTotalDayOffRemainInYear = totalDayOffRemainInYear;
        notifyPropertyChanged(BR.totalDayOffRemainInYear);
    }

    @Bindable
    public boolean isEmployeeFullTime() {
        return mIsEmployeeFullTime;
    }
}
