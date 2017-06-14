package com.framgia.wsm.screen;

import android.databinding.BaseObservable;
import com.framgia.wsm.screen.requestoff.RequestOffViewModel;

/**
 * Created by tri on 14/06/2017.
 */

public abstract class BaseRequestOff extends BaseObservable {

    public abstract void setVisibleLayoutCompanyPay(boolean isVisible);

    public abstract void setVisibleLayoutNoSalary(boolean isVisible);

    public abstract void setVisibleLayoutInsuranceCoverage(boolean isVisible);

    public abstract void setPosistionOffType(int posistionOffType);

    public void setLayoutOffType(int positionType) {
        if (positionType == RequestOffViewModel.PositionOffType.OFF_HAVE_SALARY_COMPANY_PAY) {
            setVisibleLayoutCompanyPay(true);
            setVisibleLayoutNoSalary(false);
        } else {
            setVisibleLayoutCompanyPay(false);
            setVisibleLayoutNoSalary(false);
        }
        if (positionType
                == RequestOffViewModel.PositionOffType.OFF_HAVE_SALARY_INSURANCE_COVERAGE) {
            setVisibleLayoutInsuranceCoverage(true);
            setVisibleLayoutNoSalary(false);
        } else {
            setVisibleLayoutInsuranceCoverage(false);
            setVisibleLayoutNoSalary(false);
        }
        if (positionType == RequestOffViewModel.PositionOffType.OFF_NO_SALARY) {
            setVisibleLayoutCompanyPay(false);
            setVisibleLayoutInsuranceCoverage(false);
            setVisibleLayoutNoSalary(true);
        }
        setPosistionOffType(positionType);
    }
}
