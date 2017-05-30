package com.framgia.wsm.screen.requestleave;

import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Listens to user actions from the UI ({@link RequestLeaveActivity}), retrieves the data and
 * updates
 * the UI as required.
 */
final class RequestLeavePresenter implements RequestLeaveContract.Presenter {
    private static final String TAG = RequestLeavePresenter.class.getName();

    private CompositeDisposable mCompositeDisposable;
    private BaseSchedulerProvider mSchedulerProvider;
    private UserRepository mUserRepository;

    private RequestLeaveContract.ViewModel mViewModel;

    RequestLeavePresenter(UserRepository userRepository,
            BaseSchedulerProvider baseSchedulerProvider) {
        mUserRepository = userRepository;
        mSchedulerProvider = baseSchedulerProvider;
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void setViewModel(RequestLeaveContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }
}
