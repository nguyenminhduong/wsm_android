package com.framgia.wsm.screen.requestoff;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.IntDef;
import android.view.View;
import com.framgia.wsm.BR;
import com.framgia.wsm.R;
import com.framgia.wsm.widget.dialog.DialogManager;
import com.fstyle.library.MaterialDialog;

/**
 * Exposes the data to be used in the RequestOff screen.
 */

public class RequestOffViewModel extends BaseObservable implements RequestOffContract.ViewModel {

    private RequestOffContract.Presenter mPresenter;
    private Context mContext;
    private DialogManager mDialogManager;
    private int mCurrentPositionOffType;
    private String mCurrentOffType;
    private boolean mIsVisibleLayoutCompanyPay;
    private boolean mIsVisibleLayoutInsuranceCoverage;

    public RequestOffViewModel(Context context, RequestOffContract.Presenter presenter,
            DialogManager dialogManager) {
        mContext = context;
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mDialogManager = dialogManager;
        setVisibleLayoutCompanyPay(true);
        mCurrentPositionOffType = PositionOffType.OFF_HAVE_SALARY_COMPANY_PAY;
        mCurrentOffType = mContext.getString(R.string.off_have_salary_company_pay);
    }

    public String getTitleToolbar() {
        return mContext.getResources().getString(R.string.create_request_off);
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Bindable
    public boolean isVisibleLayoutCompanyPay() {
        return mIsVisibleLayoutCompanyPay;
    }

    public void setVisibleLayoutCompanyPay(boolean visibleLayoutCompanyPay) {
        mIsVisibleLayoutCompanyPay = visibleLayoutCompanyPay;
        notifyPropertyChanged(BR.visibleLayoutCompanyPay);
    }

    @Bindable
    public boolean isVisibleLayoutInsuranceCoverage() {
        return mIsVisibleLayoutInsuranceCoverage;
    }

    public void setVisibleLayoutInsuranceCoverage(boolean visibleLayoutInsuranceCoverage) {
        mIsVisibleLayoutInsuranceCoverage = visibleLayoutInsuranceCoverage;
        notifyPropertyChanged(BR.visibleLayoutInsuranceCoverage);
    }

    public void onPickTypeRequestOff(View view) {
        mDialogManager.dialogListSingleChoice(mContext.getString(R.string.off_type),
                R.array.off_type, mCurrentPositionOffType,
                new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view,
                            int positionType, CharSequence charSequence) {
                        setCurrentPositionOffType(positionType);
                        setCurrentOffType(String.valueOf(charSequence));
                        setLayoutOffType(positionType);
                        return true;
                    }
                });
    }

    private void setLayoutOffType(int positionType) {
        if (positionType == PositionOffType.OFF_HAVE_SALARY_COMPANY_PAY) {
            setVisibleLayoutCompanyPay(true);
        } else {
            setVisibleLayoutCompanyPay(false);
        }
        if (positionType == PositionOffType.OFF_HAVE_SALARY_INSURANCE_COVERAGE) {
            setVisibleLayoutInsuranceCoverage(true);
        } else {
            setVisibleLayoutInsuranceCoverage(false);
        }
        if (positionType == PositionOffType.OFF_NO_SALARY) {
            setVisibleLayoutCompanyPay(false);
            setVisibleLayoutInsuranceCoverage(false);
        }
    }

    @Bindable
    public String getCurrentOffType() {
        return mCurrentOffType;
    }

    public void setCurrentOffType(String currentOffType) {
        mCurrentOffType = currentOffType;
        notifyPropertyChanged(BR.currentOffType);
    }

    public void setCurrentPositionOffType(int currentPositionOffType) {
        mCurrentPositionOffType = currentPositionOffType;
    }

    @IntDef({
            PositionOffType.OFF_HAVE_SALARY_COMPANY_PAY,
            PositionOffType.OFF_HAVE_SALARY_INSURANCE_COVERAGE, PositionOffType.OFF_NO_SALARY
    })
    @interface PositionOffType {
        int OFF_HAVE_SALARY_COMPANY_PAY = 0;
        int OFF_HAVE_SALARY_INSURANCE_COVERAGE = 1;
        int OFF_NO_SALARY = 2;
    }
}
