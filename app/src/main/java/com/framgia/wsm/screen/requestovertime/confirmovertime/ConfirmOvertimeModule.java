package com.framgia.wsm.screen.requestovertime.confirmovertime;

import android.app.Activity;
import android.support.annotation.NonNull;
import com.framgia.wsm.data.model.Request;
import com.framgia.wsm.utils.dagger.ActivityScope;
import dagger.Module;
import dagger.Provides;

import static com.framgia.wsm.utils.Constant.EXTRA_REQUEST_OVERTIME;

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the {@link ConfirmOvertimePresenter}.
 */
@Module
public class ConfirmOvertimeModule {

    private Activity mActivity;

    public ConfirmOvertimeModule(@NonNull Activity activity) {
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    public ConfirmOvertimeContract.ViewModel provideViewModel(
            ConfirmOvertimeContract.Presenter presenter) {
        Request request =
                mActivity.getIntent().getExtras().getParcelable(EXTRA_REQUEST_OVERTIME);
        return new ConfirmOvertimeViewModel(presenter, request);
    }

    @ActivityScope
    @Provides
    public ConfirmOvertimeContract.Presenter providePresenter() {
        return new ConfirmOvertimePresenter();
    }
}
