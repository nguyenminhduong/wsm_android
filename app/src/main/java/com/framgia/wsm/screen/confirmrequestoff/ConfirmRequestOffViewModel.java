package com.framgia.wsm.screen.confirmrequestoff;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import com.android.databinding.library.baseAdapters.BR;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.OffRequest;
import com.framgia.wsm.data.model.OffType;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.request.RequestOffRequest;
import com.framgia.wsm.screen.requestoff.RequestOffActivity;
import com.framgia.wsm.screen.requestoff.RequestOffViewModel;
import com.framgia.wsm.utils.ActionType;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.RequestType;
import com.framgia.wsm.utils.StatusCode;
import com.framgia.wsm.utils.TypeToast;
import com.framgia.wsm.utils.common.DateTimeUtils;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.widget.dialog.DialogManager;
import com.fstyle.library.DialogAction;
import com.fstyle.library.MaterialDialog;
import java.util.List;

import static com.framgia.wsm.utils.common.DateTimeUtils.DATE_FORMAT_YYYY_MM_DD;
import static com.framgia.wsm.utils.common.DateTimeUtils.FORMAT_DATE;

/**
 * Exposes the data to be used in the ConfirmRequestOff screen.
 */

public class ConfirmRequestOffViewModel extends BaseObservable
        implements ConfirmRequestOffContract.ViewModel {
    private static final String TAG = "ConfirmRequestOffViewModel";
    private static final String ANNUAL = "Annual";

    private Context mContext;
    private ConfirmRequestOffContract.Presenter mPresenter;
    private Navigator mNavigator;
    private DialogManager mDialogManager;
    private User mUser;
    private OffRequest mOffRequest;
    private RequestOffRequest mRequestOffRequest;
    private int mActionType;

    private String mAnnualLeaveAmount;
    private String mLeaveForMarriageAmount;
    private String mLeaveForChildMarriageAmount;
    private String mFuneralLeaveAmount;

    private String mLeaveForCareOfSickChildAmount;
    private String mPregnancyExaminationLeaveAmount;
    private String mSickLeaveAmount;
    private String mMiscarriageLeaveAmount;
    private String mMaternityLeaveAmount;
    private String mWifeLaborLeaveAmount;

    ConfirmRequestOffViewModel(Context context, ConfirmRequestOffContract.Presenter presenter,
            Navigator navigator, DialogManager dialogManager, OffRequest requestOff,
            int actionType) {
        mContext = context;
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mDialogManager = dialogManager;
        mOffRequest = requestOff;
        mNavigator = navigator;
        mPresenter.getUser();
        mActionType = actionType;
        mRequestOffRequest = new RequestOffRequest();
        initData(mOffRequest);
    }

    private void initData(OffRequest offRequest) {
        OffRequest.OffHaveSalaryFrom offHaveSalaryFrom = new OffRequest.OffHaveSalaryFrom();
        OffRequest.OffHaveSalaryTo offHaveSalaryTo = new OffRequest.OffHaveSalaryTo();
        OffRequest.OffNoSalaryFrom offNoSalaryFrom = new OffRequest.OffNoSalaryFrom();
        OffRequest.OffNoSalaryTo offNoSalaryTo = new OffRequest.OffNoSalaryTo();

        if (offRequest.getStartDayHaveSalary().getOffPaidFrom() == null) {
            offHaveSalaryFrom.setOffPaidFrom("");
            offHaveSalaryFrom.setPaidFromPeriod("");
            mOffRequest.setStartDayHaveSalary(offHaveSalaryFrom);

            offHaveSalaryTo.setOffPaidTo("");
            offHaveSalaryTo.setPaidToPeriod("");
            mOffRequest.setEndDayHaveSalary(offHaveSalaryTo);
        }
        if (offRequest.getStartDayNoSalary().getOffFrom() == null) {
            offNoSalaryFrom.setOffFrom("");
            offNoSalaryFrom.setOffFromPeriod("");
            mOffRequest.setStartDayNoSalary(offNoSalaryFrom);

            offNoSalaryTo.setOffTo("");
            offNoSalaryTo.setOffToPeriod("");
            mOffRequest.setEndDayNoSalary(offNoSalaryTo);
        }
        if (mActionType == ActionType.ACTION_DETAIL) {
            initDataDetail();
            if (offRequest.getStartDayNoSalary().getOffFrom() != null) {
                offNoSalaryFrom.setOffFrom(DateTimeUtils.convertUiFormatToDataFormat(
                        mOffRequest.getStartDayNoSalary().getOffFrom(), FORMAT_DATE,
                        DATE_FORMAT_YYYY_MM_DD));
                offNoSalaryFrom.setOffFromPeriod(
                        mOffRequest.getStartDayNoSalary().getOffFromPeriod());
                mOffRequest.setStartDayNoSalary(offNoSalaryFrom);

                offNoSalaryTo.setOffTo(DateTimeUtils.convertUiFormatToDataFormat(
                        mOffRequest.getEndDayNoSalary().getOffTo(), FORMAT_DATE,
                        DATE_FORMAT_YYYY_MM_DD));
                offNoSalaryTo.setOffToPeriod(mOffRequest.getEndDayNoSalary().getOffToPeriod());
                mOffRequest.setEndDayNoSalary(offNoSalaryTo);
            }
            if (offRequest.getStartDayHaveSalary().getOffPaidFrom() != null) {
                offHaveSalaryFrom.setOffPaidFrom(DateTimeUtils.convertUiFormatToDataFormat(
                        mOffRequest.getStartDayHaveSalary().getOffPaidFrom(), FORMAT_DATE,
                        DATE_FORMAT_YYYY_MM_DD));
                offHaveSalaryFrom.setPaidFromPeriod(
                        mOffRequest.getStartDayHaveSalary().getPaidFromPeriod());
                mOffRequest.setStartDayHaveSalary(offHaveSalaryFrom);

                offHaveSalaryTo.setOffPaidTo(DateTimeUtils.convertUiFormatToDataFormat(
                        mOffRequest.getEndDayHaveSalary().getOffPaidTo(), FORMAT_DATE,
                        DATE_FORMAT_YYYY_MM_DD));
                offHaveSalaryTo.setPaidToPeriod(
                        mOffRequest.getEndDayHaveSalary().getPaidToPeriod());
                mOffRequest.setEndDayHaveSalary(offHaveSalaryTo);
            }
        }
        if (mActionType == ActionType.ACTION_EDIT || mActionType == ActionType.ACTION_CREATE) {
            if (mOffRequest.getAnnualLeave() != null
                    && !"".equals(mOffRequest.getAnnualLeave())
                    && !Constant.DEFAULT_DOUBLE_VALUE.equals(mOffRequest.getAnnualLeave())) {
                mOffRequest.setNumberDayOffNormal(Double.parseDouble(mOffRequest.getAnnualLeave()));
            } else {
                mOffRequest.setNumberDayOffNormal(Double.valueOf(Constant.DEFAULT_DOUBLE_VALUE));
            }
        }
    }

    private void initDataDetail() {
        if (mOffRequest.getNumberDayOffNormal() == null) {
            mOffRequest.setAnnualLeave("");
        } else {
            mOffRequest.setAnnualLeave(String.valueOf(mOffRequest.getNumberDayOffNormal()));
        }
        if (mOffRequest.getRequestDayOffTypes() == null
                || mOffRequest.getRequestDayOffTypes().size() == 0) {
            return;
        }
        int[] requestDayOffTypes = new int[mOffRequest.getRequestDayOffTypes().size()];
        for (int i = 0; i < requestDayOffTypes.length; i++) {
            requestDayOffTypes[i] =
                    mOffRequest.getRequestDayOffTypes().get(i).getSpecialDayOffSettingId();
            if (requestDayOffTypes[i] == Integer.parseInt(
                    RequestOffViewModel.TypeOfDays.LEAVE_FOR_MARRIAGE)) {
                mOffRequest.setLeaveForMarriage(String.valueOf(
                        mOffRequest.getRequestDayOffTypes().get(i).getNumberDayOff()));
            }
            if (requestDayOffTypes[i] == Integer.parseInt(
                    RequestOffViewModel.TypeOfDays.LEAVE_FOR_CHILD_MARRIAGE)) {
                mOffRequest.setLeaveForChildMarriage(String.valueOf(
                        mOffRequest.getRequestDayOffTypes().get(i).getNumberDayOff()));
            }
            if (requestDayOffTypes[i] == Integer.parseInt(
                    RequestOffViewModel.TypeOfDays.MATERNTY_LEAVE)) {
                mOffRequest.setMaternityLeave(String.valueOf(
                        mOffRequest.getRequestDayOffTypes().get(i).getNumberDayOff()));
            }
            if (requestDayOffTypes[i] == Integer.parseInt(
                    RequestOffViewModel.TypeOfDays.LEAVE_FOR_CARE_OF_SICK_CHILD)) {
                mOffRequest.setLeaveForCareOfSickChild(String.valueOf(
                        mOffRequest.getRequestDayOffTypes().get(i).getNumberDayOff()));
            }
            if (requestDayOffTypes[i] == Integer.parseInt(
                    RequestOffViewModel.TypeOfDays.FUNERAL_LEAVE)) {
                mOffRequest.setFuneralLeave(String.valueOf(
                        mOffRequest.getRequestDayOffTypes().get(i).getNumberDayOff()));
            }
            if (requestDayOffTypes[i] == Integer.parseInt(
                    RequestOffViewModel.TypeOfDays.WIFE_LABOR_LEAVE)) {
                mOffRequest.setWifeLaborLeave(String.valueOf(
                        mOffRequest.getRequestDayOffTypes().get(i).getNumberDayOff()));
            }
            if (requestDayOffTypes[i] == Integer.parseInt(
                    RequestOffViewModel.TypeOfDays.MISCARRIAGE_LEAVE)) {
                mOffRequest.setMiscarriageLeave(String.valueOf(
                        mOffRequest.getRequestDayOffTypes().get(i).getNumberDayOff()));
            }
            if (requestDayOffTypes[i] == Integer.parseInt(
                    RequestOffViewModel.TypeOfDays.SICK_LEAVE)) {
                mOffRequest.setSickLeave(String.valueOf(
                        mOffRequest.getRequestDayOffTypes().get(i).getNumberDayOff()));
            }
            if (requestDayOffTypes[i] == Integer.parseInt(
                    RequestOffViewModel.TypeOfDays.PREGNANCY_EXAMINATON)) {
                mOffRequest.setPregnancyExaminationLeave(String.valueOf(
                        mOffRequest.getRequestDayOffTypes().get(i).getNumberDayOff()));
            }
        }
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
    public void onCreateFormRequestOffSuccess() {
        mNavigator.finishActivityWithResult(Activity.RESULT_OK);
        mNavigator.showToastCustom(TypeToast.SUCCESS,
                mContext.getString(R.string.create_form_success));
    }

    @Override
    public void onCreateFormFormRequestOffError(BaseException exception) {
        mDialogManager.dialogError(exception);
    }

    @Override
    public void onGetUserSuccess(User user) {
        if (user == null) {
            return;
        }
        mUser = user;
        notifyPropertyChanged(BR.user);
        getAmountOffDayCompany(mUser);
        getAmountOffDayInsurance(mUser);
    }

    @Override
    public void onGetUserError(BaseException e) {
        Log.e(TAG, "ConfirmRequestOffViewModel", e);
    }

    @Override
    public void onDeleteFormRequestOffSuccess() {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.EXTRA_REQUEST_TYPE_CODE, RequestType.REQUEST_OFF);
        mNavigator.finishActivityWithResult(bundle, Activity.RESULT_OK);
        mNavigator.showToastCustom(TypeToast.SUCCESS,
                mContext.getString(R.string.delete_form_success));
    }

    @Override
    public void onDeleteFormRequestOffError(BaseException exception) {
        mDialogManager.dialogError(exception);
    }

    @Override
    public void onEditFormRequestOffSuccess(OffRequest requestOff) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.EXTRA_REQUEST_TYPE_CODE, RequestType.REQUEST_OFF);
        mNavigator.finishActivityWithResult(bundle, Activity.RESULT_OK);
        mNavigator.showToastCustom(TypeToast.SUCCESS,
                mContext.getString(R.string.edit_form_success));
    }

    @Override
    public void onEditFormRequestOffError(BaseException exception) {
        mDialogManager.dialogError(exception, new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog,
                    @NonNull DialogAction dialogAction) {
                mNavigator.finishActivity();
            }
        });
    }

    @Override
    public void onDismissProgressDialog() {
        mDialogManager.dismissProgressDialog();
    }

    @Override
    public void onShowIndeterminateProgressDialog() {
        mDialogManager.showIndeterminateProgressDialog();
    }

    @Bindable
    public User getUser() {
        return mUser;
    }

    @Bindable
    public OffRequest getRequestOff() {
        return mOffRequest;
    }

    @Bindable
    public boolean isVisiableProjectName() {
        return mOffRequest.getProject() != null;
    }

    @Bindable
    public boolean isVisiablePosition() {
        return mOffRequest.getPosition() != null;
    }

    @Bindable
    public boolean isVisiableLayoutCompanyPay() {
        return (mOffRequest.getAnnualLeave() != null
                && !"".equals(mOffRequest.getAnnualLeave())
                && !Constant.DEFAULT_DOUBLE_VALUE.equals(mOffRequest.getAnnualLeave()))
                || (mOffRequest.getLeaveForChildMarriage() != null
                && !"".equals(mOffRequest.getLeaveForChildMarriage()))
                || (mOffRequest.getLeaveForMarriage() != null && !"".equals(
                mOffRequest.getLeaveForMarriage()))
                || (mOffRequest.getFuneralLeave() != null && !"".equals(
                mOffRequest.getFuneralLeave()));
    }

    @Bindable
    public boolean isVisiableLayoutInsurance() {
        return (mOffRequest.getLeaveForCareOfSickChild() != null && !"".equals(
                mOffRequest.getLeaveForCareOfSickChild()))
                || (mOffRequest.getSickLeave() != null
                && !"".equals(mOffRequest.getSickLeave()))
                || (mOffRequest.getMaternityLeave()
                != null && !"".equals(mOffRequest.getMaternityLeave()))
                || (mOffRequest.getPregnancyExaminationLeave() != null && !"".equals(
                mOffRequest.getPregnancyExaminationLeave()))
                || (mOffRequest.getMiscarriageLeave() != null && !"".equals(
                mOffRequest.getMiscarriageLeave()))
                || (mOffRequest.getWifeLaborLeave() != null && !"".equals(
                mOffRequest.getWifeLaborLeave()));
    }

    @Bindable
    public boolean isVisiableLayoutHaveSalary() {
        return !"".equals(mOffRequest.getStartDayHaveSalary().getOffPaidFrom());
    }

    @Bindable
    public boolean isVisiableLayoutOffNoSalary() {
        return mOffRequest.getStartDayNoSalary().getOffFrom() != null && !"".equals(
                mOffRequest.getStartDayNoSalary().getOffFrom());
    }

    @Bindable
    public boolean isVisiableAnnualLeave() {
        return mOffRequest.getAnnualLeave() != null
                && !"".equals(mOffRequest.getAnnualLeave())
                && !Constant.DEFAULT_DOUBLE_VALUE.equals(mOffRequest.getAnnualLeave());
    }

    @Bindable
    public boolean isVisiableLeaveForChildMarriage() {
        return mOffRequest.getLeaveForChildMarriage() != null && !"".equals(
                mOffRequest.getLeaveForChildMarriage());
    }

    @Bindable
    public boolean isVisiableLeaveForMarriage() {
        return mOffRequest.getLeaveForMarriage() != null && !"".equals(
                mOffRequest.getLeaveForMarriage());
    }

    @Bindable
    public boolean isVisiableFuneralLeave() {
        return mOffRequest.getFuneralLeave() != null && !"".equals(mOffRequest.getFuneralLeave());
    }

    @Bindable
    public boolean isVisiableLeaveForCareOfSickChild() {
        return mOffRequest.getLeaveForCareOfSickChild() != null && !"".equals(
                mOffRequest.getLeaveForCareOfSickChild());
    }

    @Bindable
    public boolean isVisiableSickLeave() {
        return mOffRequest.getSickLeave() != null && !"".equals(mOffRequest.getSickLeave());
    }

    @Bindable
    public boolean isVisiableMaternityLeave() {
        return mOffRequest.getMaternityLeave() != null && !"".equals(
                mOffRequest.getMaternityLeave());
    }

    @Bindable
    public boolean isVisiablePregnancyExaminationLeave() {
        return mOffRequest.getPregnancyExaminationLeave() != null && !"".equals(
                mOffRequest.getPregnancyExaminationLeave());
    }

    @Bindable
    public boolean isVisiableMiscarriageLeave() {
        return mOffRequest.getMiscarriageLeave() != null && !"".equals(
                mOffRequest.getMiscarriageLeave());
    }

    @Bindable
    public boolean isVisiableWifeLaborLeave() {
        return mOffRequest.getWifeLaborLeave() != null && !"".equals(
                mOffRequest.getWifeLaborLeave());
    }

    @Bindable
    public String getAnnualLeaveAmount() {
        return String.format(mContext.getString(R.string.annual_leave), mAnnualLeaveAmount);
    }

    private void setAnnualLeaveAmount(String annualLeaveAmount) {
        mAnnualLeaveAmount = annualLeaveAmount;
        notifyPropertyChanged(BR.annualLeaveAmount);
    }

    @Bindable
    public String getLeaveForMarriageAmount() {
        return String.format(mContext.getString(R.string.leave_for_marriage),
                mLeaveForMarriageAmount);
    }

    private void setLeaveForMarriageAmount(String leaveForMarriageAmount) {
        mLeaveForMarriageAmount = leaveForMarriageAmount;
        notifyPropertyChanged(BR.leaveForMarriageAmount);
    }

    @Bindable
    public String getLeaveForChildMarriageAmount() {
        return String.format(mContext.getString(R.string.leave_for_child_marriage),
                mLeaveForChildMarriageAmount);
    }

    private void setLeaveForChildMarriageAmount(String leaveForChildMarriageAmount) {
        mLeaveForChildMarriageAmount = leaveForChildMarriageAmount;
        notifyPropertyChanged(BR.leaveForChildMarriageAmount);
    }

    @Bindable
    public String getFuneralLeaveAmount() {
        return String.format(mContext.getString(R.string.funeral_leave), mFuneralLeaveAmount);
    }

    private void setFuneralLeaveAmount(String funeralLeaveAmount) {
        mFuneralLeaveAmount = funeralLeaveAmount;
        notifyPropertyChanged(BR.funeralLeaveAmount);
    }

    @Bindable
    public String getLeaveForCareOfSickChildAmount() {
        return String.format(mContext.getString(R.string.leave_for_care_of_sick_child),
                mLeaveForCareOfSickChildAmount);
    }

    private void setLeaveForCareOfSickChildAmount(String leaveForCareOfSickChildAmount) {
        mLeaveForCareOfSickChildAmount = leaveForCareOfSickChildAmount;
        notifyPropertyChanged(BR.leaveForCareOfSickChildAmount);
    }

    @Bindable
    public String getPregnancyExaminationLeaveAmount() {
        return String.format(mContext.getString(R.string.pregnancy_examination_leave),
                mPregnancyExaminationLeaveAmount);
    }

    private void setPregnancyExaminationLeaveAmount(String pregnancyExaminationLeaveAmount) {
        mPregnancyExaminationLeaveAmount = pregnancyExaminationLeaveAmount;
        notifyPropertyChanged(BR.pregnancyExaminationLeaveAmount);
    }

    @Bindable
    public String getSickLeaveAmount() {
        return String.format(mContext.getString(R.string.sick_leave), mSickLeaveAmount);
    }

    private void setSickLeaveAmount(String sickLeaveAmount) {
        mSickLeaveAmount = sickLeaveAmount;
        notifyPropertyChanged(BR.sickLeaveAmount);
    }

    @Bindable
    public String getMiscarriageLeaveAmount() {
        return String.format(mContext.getString(R.string.miscarriage_leave),
                mMiscarriageLeaveAmount);
    }

    private void setMiscarriageLeaveAmount(String miscarriageLeaveAmount) {
        mMiscarriageLeaveAmount = miscarriageLeaveAmount;
        notifyPropertyChanged(BR.miscarriageLeaveAmount);
    }

    @Bindable
    public String getMaternityLeaveAmount() {
        return String.format(mContext.getString(R.string.maternity_leave), mMaternityLeaveAmount);
    }

    private void setMaternityLeaveAmount(String maternityLeaveAmount) {
        mMaternityLeaveAmount = maternityLeaveAmount;
        notifyPropertyChanged(BR.maternityLeaveAmount);
    }

    @Bindable
    public String getWifeLaborLeaveAmount() {
        return String.format(mContext.getString(R.string.wife_labor_leave), mWifeLaborLeaveAmount);
    }

    private void setWifeLaborLeaveAmount(String wifeLaborLeaveAmount) {
        mWifeLaborLeaveAmount = wifeLaborLeaveAmount;
        notifyPropertyChanged(BR.wifeLaborLeaveAmount);
    }

    public boolean isDetail() {
        return mActionType == ActionType.ACTION_DETAIL;
    }

    public boolean isAcceptStatus() {
        return StatusCode.ACCEPT_CODE.equals(mOffRequest.getStatus());
    }

    public boolean isPendingStatus() {
        return StatusCode.PENDING_CODE.equals(mOffRequest.getStatus());
    }

    public boolean isRejectStatus() {
        return StatusCode.REJECT_CODE.equals(mOffRequest.getStatus());
    }

    public boolean isForwardStatus() {
        return StatusCode.FORWARD_CODE.equals(mOffRequest.getStatus());
    }

    public String getTitleToolbar() {
        if (mActionType == ActionType.ACTION_CREATE) {
            return mContext.getString(R.string.confirm_request_off);
        }
        if (mActionType == ActionType.ACTION_DETAIL) {
            return mContext.getString(R.string.detail_request_off);
        }
        return mContext.getString(R.string.confirm_edit_request_off);
    }

    public String getStartDateHaveSalary() {
        if (mOffRequest != null && mOffRequest.getStartDayHaveSalary() != null) {
            if (!"".equals(mOffRequest.getStartDayHaveSalary().getOffPaidFrom())) {
                return DateTimeUtils.convertUiFormatToDataFormat(
                        mOffRequest.getStartDayHaveSalary().getOffPaidFrom(),
                        DATE_FORMAT_YYYY_MM_DD, FORMAT_DATE)
                        + Constant.BLANK
                        + mOffRequest.getStartDayHaveSalary().getPaidFromPeriod();
            }
        }
        return "";
    }

    public String getEndDateHaveSalary() {
        if (mOffRequest != null && mOffRequest.getEndDayHaveSalary() != null) {
            if (!"".equals(mOffRequest.getEndDayHaveSalary().getOffPaidTo())) {
                return DateTimeUtils.convertUiFormatToDataFormat(
                        mOffRequest.getEndDayHaveSalary().getOffPaidTo(), DATE_FORMAT_YYYY_MM_DD,
                        FORMAT_DATE) + Constant.BLANK + mOffRequest.getEndDayHaveSalary()
                        .getPaidToPeriod();
            }
        }
        return "";
    }

    public String getStartDateNoSalary() {
        if (mOffRequest != null && mOffRequest.getStartDayNoSalary() != null) {
            if (!"".equals(mOffRequest.getStartDayNoSalary().getOffFrom())) {
                return DateTimeUtils.convertUiFormatToDataFormat(
                        mOffRequest.getStartDayNoSalary().getOffFrom(), DATE_FORMAT_YYYY_MM_DD,
                        FORMAT_DATE) + Constant.BLANK + mOffRequest.getStartDayNoSalary()
                        .getOffFromPeriod();
            }
        }
        return "";
    }

    public String getEndDateNoSalary() {
        if (mOffRequest != null && mOffRequest.getEndDayNoSalary() != null) {
            if (!"".equals(mOffRequest.getEndDayNoSalary().getOffTo())) {
                return DateTimeUtils.convertUiFormatToDataFormat(
                        mOffRequest.getEndDayNoSalary().getOffTo(), DATE_FORMAT_YYYY_MM_DD,
                        FORMAT_DATE) + Constant.BLANK + mOffRequest.getEndDayNoSalary()
                        .getOffToPeriod();
            }
        }
        return "";
    }

    private void getAmountOffDayCompany(User user) {
        List<OffType> offTypesCompanyPay = user.getTypesCompany();
        for (int i = 0; i < offTypesCompanyPay.size(); i++) {
            if (offTypesCompanyPay.get(i).getName().equals(ANNUAL)) {
                setAnnualLeaveAmount(String.valueOf(offTypesCompanyPay.get(i).getAmount()));
            }
            if (offTypesCompanyPay.get(i).getId() == Integer.parseInt(
                    RequestOffViewModel.TypeOfDays.LEAVE_FOR_MARRIAGE)) {
                setLeaveForMarriageAmount(String.valueOf(offTypesCompanyPay.get(i).getAmount()));
            }
            if (offTypesCompanyPay.get(i).getId() == Integer.parseInt(
                    RequestOffViewModel.TypeOfDays.LEAVE_FOR_CHILD_MARRIAGE)) {
                setLeaveForChildMarriageAmount(
                        String.valueOf(offTypesCompanyPay.get(i).getAmount()));
            }
            if (offTypesCompanyPay.get(i).getId() == Integer.parseInt(
                    RequestOffViewModel.TypeOfDays.FUNERAL_LEAVE)) {
                setFuneralLeaveAmount(String.valueOf(offTypesCompanyPay.get(i).getAmount()));
            }
        }
    }

    private void getAmountOffDayInsurance(User user) {
        List<OffType> offTypesInsurancePay = user.getTypesInsurance();
        for (int i = 0; i < offTypesInsurancePay.size(); i++) {
            if (offTypesInsurancePay.get(i).getId() == Integer.parseInt(
                    RequestOffViewModel.TypeOfDays.LEAVE_FOR_CARE_OF_SICK_CHILD)) {
                setLeaveForCareOfSickChildAmount(
                        String.valueOf(offTypesInsurancePay.get(i).getAmount()));
            }
            if (offTypesInsurancePay.get(i).getId() == Integer.parseInt(
                    RequestOffViewModel.TypeOfDays.PREGNANCY_EXAMINATON)) {
                setPregnancyExaminationLeaveAmount(
                        String.valueOf(offTypesInsurancePay.get(i).getAmount()));
            }
            if (offTypesInsurancePay.get(i).getId() == Integer.parseInt(
                    RequestOffViewModel.TypeOfDays.SICK_LEAVE)) {
                setSickLeaveAmount(String.valueOf(offTypesInsurancePay.get(i).getAmount()));
            }
            if (offTypesInsurancePay.get(i).getId() == Integer.parseInt(
                    RequestOffViewModel.TypeOfDays.MISCARRIAGE_LEAVE)) {
                setMiscarriageLeaveAmount(String.valueOf(offTypesInsurancePay.get(i).getAmount()));
            }
            if (offTypesInsurancePay.get(i).getId() == Integer.parseInt(
                    RequestOffViewModel.TypeOfDays.MATERNTY_LEAVE)) {
                setMaternityLeaveAmount(String.valueOf(offTypesInsurancePay.get(i).getAmount()));
            }
            if (offTypesInsurancePay.get(i).getId() == Integer.parseInt(
                    RequestOffViewModel.TypeOfDays.WIFE_LABOR_LEAVE)) {
                setWifeLaborLeaveAmount(String.valueOf(offTypesInsurancePay.get(i).getAmount()));
            }
        }
    }

    private void setTimeRequestOff() {
        if (mOffRequest.getStartDayHaveSalary().getOffPaidFrom() != null) {
            OffRequest.OffHaveSalaryFrom offHaveSalaryFrom = new OffRequest.OffHaveSalaryFrom();
            OffRequest.OffHaveSalaryTo offHaveSalaryTo = new OffRequest.OffHaveSalaryTo();
            offHaveSalaryFrom.setOffPaidFrom(DateTimeUtils.convertUiFormatToDataFormat(
                    mOffRequest.getStartDayHaveSalary().getOffPaidFrom(), DATE_FORMAT_YYYY_MM_DD,
                    FORMAT_DATE));
            offHaveSalaryFrom.setPaidFromPeriod(
                    mOffRequest.getStartDayHaveSalary().getPaidFromPeriod());
            mOffRequest.setStartDayHaveSalary(offHaveSalaryFrom);

            offHaveSalaryTo.setOffPaidTo(DateTimeUtils.convertUiFormatToDataFormat(
                    mOffRequest.getEndDayHaveSalary().getOffPaidTo(), DATE_FORMAT_YYYY_MM_DD,
                    FORMAT_DATE));
            offHaveSalaryTo.setPaidToPeriod(mOffRequest.getEndDayHaveSalary().getPaidToPeriod());
            mOffRequest.setEndDayHaveSalary(offHaveSalaryTo);
        }
        if (mOffRequest.getStartDayNoSalary().getOffFrom() != null) {
            OffRequest.OffNoSalaryFrom offNoSalaryFrom = new OffRequest.OffNoSalaryFrom();
            OffRequest.OffNoSalaryTo offNoSalaryTo = new OffRequest.OffNoSalaryTo();
            offNoSalaryFrom.setOffFrom(DateTimeUtils.convertUiFormatToDataFormat(
                    mOffRequest.getStartDayNoSalary().getOffFrom(), DATE_FORMAT_YYYY_MM_DD,
                    FORMAT_DATE));
            offNoSalaryFrom.setOffFromPeriod(mOffRequest.getStartDayNoSalary().getOffFromPeriod());
            mOffRequest.setStartDayNoSalary(offNoSalaryFrom);

            offNoSalaryTo.setOffTo(DateTimeUtils.convertUiFormatToDataFormat(
                    mOffRequest.getEndDayNoSalary().getOffTo(), DATE_FORMAT_YYYY_MM_DD,
                    FORMAT_DATE));
            offNoSalaryTo.setOffToPeriod(mOffRequest.getEndDayNoSalary().getOffToPeriod());
            mOffRequest.setEndDayNoSalary(offNoSalaryTo);
        }
    }

    public String getAnnualLeave() {
        if (mOffRequest != null
                && mOffRequest.getAnnualLeave() != null
                && !Constant.DEFAULT_DOUBLE_VALUE.equals(mOffRequest.getAnnualLeave())) {
            return mOffRequest.getAnnualLeave();
        }
        return "";
    }

    public String getLeaveForMarriage() {
        if (mOffRequest != null && mOffRequest.getLeaveForMarriage() != null && !"".equals(
                mOffRequest.getLeaveForMarriage())) {
            return mOffRequest.getLeaveForMarriage();
        }
        return "";
    }

    public String getLeaveForChildMarriage() {
        if (mOffRequest != null && mOffRequest.getLeaveForChildMarriage() != null && !"".equals(
                mOffRequest.getLeaveForChildMarriage())) {
            return mOffRequest.getLeaveForChildMarriage();
        }
        return "";
    }

    public String getFuneralLeave() {
        if (mOffRequest != null && mOffRequest.getFuneralLeave() != null && !"".equals(
                mOffRequest.getFuneralLeave())) {
            return mOffRequest.getFuneralLeave();
        }
        return "";
    }

    public String getLeaveForCareOfSickChild() {
        if (mOffRequest != null && mOffRequest.getLeaveForCareOfSickChild() != null && !"".equals(
                mOffRequest.getLeaveForCareOfSickChild())) {
            return mOffRequest.getLeaveForCareOfSickChild();
        }
        return "";
    }

    public String getPregnancyExaminationLeave() {
        if (mOffRequest != null && mOffRequest.getPregnancyExaminationLeave() != null && !"".equals(
                mOffRequest.getPregnancyExaminationLeave())) {
            return mOffRequest.getPregnancyExaminationLeave();
        }
        return "";
    }

    public String getSickLeave() {
        if (mOffRequest != null && mOffRequest.getSickLeave() != null && !"".equals(
                mOffRequest.getSickLeave())) {
            return mOffRequest.getSickLeave();
        }
        return "";
    }

    public String getMiscarriageLeave() {
        if (mOffRequest != null && mOffRequest.getMiscarriageLeave() != null && !"".equals(
                mOffRequest.getMiscarriageLeave())) {
            return mOffRequest.getMiscarriageLeave();
        }
        return "";
    }

    public String getMaternityLeave() {
        if (mOffRequest != null && mOffRequest.getMaternityLeave() != null && !"".equals(
                mOffRequest.getMaternityLeave())) {
            return mOffRequest.getMaternityLeave();
        }
        return "";
    }

    public String getWifeLaborLeave() {
        if (mOffRequest != null && mOffRequest.getWifeLaborLeave() != null && !"".equals(
                mOffRequest.getWifeLaborLeave())) {
            return mOffRequest.getWifeLaborLeave();
        }
        return "";
    }

    public boolean isVisibleButtonSubmit() {
        return mActionType == ActionType.ACTION_CREATE || mOffRequest.getStatus()
                .equals(StatusCode.PENDING_CODE);
    }

    public void onCickArrowBack(View view) {
        mNavigator.finishActivity();
    }

    public void onClickSubmit(View view) {
        if (mOffRequest == null) {
            return;
        }
        setTimeRequestOff();
        mRequestOffRequest.setRequestOff(mOffRequest);
        if (mActionType == ActionType.ACTION_CREATE) {
            mPresenter.createFormRequestOff(mRequestOffRequest);
            return;
        }
        mPresenter.editFormRequestOff(mRequestOffRequest);
    }

    public void onClickDelete(View view) {
        if (mOffRequest == null) {
            return;
        }
        mDialogManager.dialogBasic(mContext.getString(R.string.confirm_delete),
                mContext.getString(R.string.do_you_want_delete_this_request),
                new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog,
                            @NonNull DialogAction dialogAction) {
                        mPresenter.deleteFormRequestOff(mOffRequest.getId());
                    }
                });
    }

    public void onClickEdit(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.EXTRA_ACTION_TYPE, ActionType.ACTION_EDIT);
        bundle.putParcelable(Constant.EXTRA_REQUEST_OFF, mOffRequest);
        mNavigator.startActivityForResult(RequestOffActivity.class, bundle,
                Constant.RequestCode.REQUEST_OFF);
    }
}
