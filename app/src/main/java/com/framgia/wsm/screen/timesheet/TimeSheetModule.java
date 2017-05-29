package com.framgia.wsm.screen.timesheet;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import com.framgia.wsm.data.source.TimeSheetRepository;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.remote.TimeSheetRemoteDataSource;
import com.framgia.wsm.utils.dagger.FragmentScope;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
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
    public TimeSheetContract.ViewModel provideViewModel(TimeSheetContract.Presenter presenter) {
        return new TimeSheetViewModel(presenter);
    }

    @FragmentScope
    @Provides
    public TimeSheetContract.Presenter providePresenter(UserRepository userRepository,
            TimeSheetRepository timeSheetRepository, BaseSchedulerProvider baseSchedulerProvider) {
        return new TimeSheetPresenter(timeSheetRepository, baseSchedulerProvider);
    }

    @FragmentScope
    @Provides
    public TimeSheetRepository provideTimeSheetRepository(
            TimeSheetRemoteDataSource remoteDataSource) {
        return new TimeSheetRepository(remoteDataSource);
    }
}
