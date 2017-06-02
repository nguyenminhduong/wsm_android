package com.framgia.wsm.screen.requestovertime;

import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.error.RequestError;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Listens to user actions from the UI ({@link RequestOvertimeActivity}), retrieves the data and
 * updates
 * the UI as required.
 */
final class RequestOvertimePresenter implements RequestOvertimeContract.Presenter {
    private static final String TAG = RequestOvertimePresenter.class.getName();

    private RequestOvertimeContract.ViewModel mViewModel;
    private UserRepository mUserRepository;
    private BaseSchedulerProvider mSchedulerProvider;
    private CompositeDisposable mCompositeDisposable;

    RequestOvertimePresenter(UserRepository userRepository,
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
    public void setViewModel(RequestOvertimeContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void getUser() {
        Disposable disposable = mUserRepository.getUser()
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(@NonNull User user) throws Exception {
                        mViewModel.onGetUserSuccess(user);
                    }
                }, new RequestError() {
                    @Override
                    public void onRequestError(BaseException error) {
                        mViewModel.onGetUserError(error);
                    }
                });
        mCompositeDisposable.add(disposable);
    }
}
