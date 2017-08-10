package com.framgia.wsm.screen.statisticsbyyear;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;
import com.framgia.wsm.BR;
import com.framgia.wsm.data.model.StatisticOfPersonal;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;

/**
 * Exposes the data to be used in the Statisticsbyyear screen.
 */

public class StatisticsByYearViewModel extends BaseObservable
        implements StatisticsByYearContract.ViewModel {
    private static final String TAG = "StatisticsByYear";

    private final StatisticsByYearContract.Presenter mPresenter;
    private String mLeaveTimeApprovedInYear;
    private String mLeaveTimeRejectedInYear;
    private String mDayOffHaveSalaryPayByCompany;
    private String mDayOffHaveSalaryPayByInsurance;
    private String mDayOffNoSalary;
    private String mNumberOfOverTime;
    private String mNumberRemainDayPreviousYear;
    private String mNumberRemainDayInYear;
    private String mNumberDayOffAddInYear;
    private String mTotalDayOffRemainInYear;
    private boolean mIsEmployeeFullTime;

    StatisticsByYearViewModel(StatisticsByYearContract.Presenter presenter) {
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

    @Bindable
    public String getLeaveTimeApprovedInYear() {
        return mLeaveTimeApprovedInYear;
    }

    private void setLeaveTimeApprovedInYear(String leaveTimeApprovedInYear) {
        mLeaveTimeApprovedInYear = leaveTimeApprovedInYear;
        notifyPropertyChanged(BR.leaveTimeApprovedInYear);
    }

    @Bindable
    public String getLeaveTimeRejectedInYear() {
        return mLeaveTimeRejectedInYear;
    }

    private void setLeaveTimeRejectedInYear(String leaveTimeRejectedInYear) {
        mLeaveTimeRejectedInYear = leaveTimeRejectedInYear;
        notifyPropertyChanged(BR.leaveTimeRejectedInYear);
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

    private void getDataStatistic(StatisticOfPersonal statisticOfPersonal) {
        setLeaveTimeApprovedInYear(
                String.valueOf(statisticOfPersonal.getLeaveTimes().getApprovedInYear()));
        setLeaveTimeRejectedInYear(
                String.valueOf(statisticOfPersonal.getLeaveTimes().getRejectedInYear()));
        setDayOffHaveSalaryPayByCompany(
                String.valueOf(statisticOfPersonal.getDaysOff().getSalaryPayByCompanyInYear()));
        setDayOffHaveSalaryPayByInsurance(
                String.valueOf(statisticOfPersonal.getDaysOff().getSalaryPayByInsuranceInYear()));
        setDayOffNoSalary(String.valueOf(statisticOfPersonal.getDaysOff().getNoSalaryInYear()));
        setNumberOfOverTime(String.valueOf(statisticOfPersonal.getOverTime().getApprovedInYear()));
        setNumberRemainDayPreviousYear(
                String.valueOf(statisticOfPersonal.getRemainingDaysOff().getPreviousYear()));
        setNumberRemainDayInYear(
                String.valueOf(statisticOfPersonal.getRemainingDaysOff().getInYear()));
        setNumberDayOffAddInYear(
                String.valueOf(statisticOfPersonal.getRemainingDaysOff().getBonusInYear()));
        setTotalDayOffRemainInYear(
                String.valueOf(statisticOfPersonal.getRemainingDaysOff().getTotalRemainInYear()));
    }

    @Bindable
    public boolean isEmployeeFullTime() {
        return mIsEmployeeFullTime;
    }
}
