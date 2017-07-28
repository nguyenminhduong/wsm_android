package com.framgia.wsm.screen.timesheet;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import com.framgia.wsm.data.source.TimeSheetRepository;
import com.framgia.wsm.data.source.remote.TimeSheetRemoteDataSource;
import com.framgia.wsm.utils.dagger.FragmentScope;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import com.framgia.wsm.widget.dialog.DialogManager;
import com.framgia.wsm.widget.dialog.DialogManagerImpl;
import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the {@link TimeSheetPresenter}.
 */
@Module
public class TimeSheetModule {

    private Fragment mFragment;

    public TimeSheetModule(@NonNull Fragment fragment) {
        mFragment = fragment;
    }

    @FragmentScope
    @Provides
    public TimeSheetContract.ViewModel provideViewModel(Context context,
            TimeSheetContract.Presenter presenter, Navigator navigator,
            DialogManager dialogManager) {
        return new TimeSheetViewModel(context, presenter, navigator, dialogManager);
    }

    @FragmentScope
    @Provides
    public TimeSheetContract.Presenter providePresenter(TimeSheetRepository timeSheetRepository,
            BaseSchedulerProvider baseSchedulerProvider) {
        return new TimeSheetPresenter(timeSheetRepository, baseSchedulerProvider);
    }

    @FragmentScope
    @Provides
    public TimeSheetRepository provideTimeSheetRepository(
            TimeSheetRemoteDataSource remoteDataSource) {
        return new TimeSheetRepository(remoteDataSource);
    }

    @FragmentScope
    @Provides
    Navigator provideNavigator() {
        return new Navigator(mFragment);
    }
}
