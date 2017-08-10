package com.framgia.wsm.screen.statisticsbyyear;

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
 * Listens to user actions from the UI ({@link StatisticsByYearFragment}), retrieves the data and
 * updates
 * the UI as required.
 */
final class StatisticsByYearPresenter implements StatisticsByYearContract.Presenter {
    private static final String TAG = StatisticsByYearPresenter.class.getName();

    private StatisticsByYearContract.ViewModel mViewModel;
    private final UserRepository mUserRepository;
    private final CompositeDisposable mCompositeDisposable;
    private final BaseSchedulerProvider mSchedulerProvider;

    StatisticsByYearPresenter(UserRepository userRepository,
            BaseSchedulerProvider baseSchedulerProvider) {
        mUserRepository = userRepository;
        mCompositeDisposable = new CompositeDisposable();
        mSchedulerProvider = baseSchedulerProvider;
    }

    @Override
    public void onStart() {
        getUser();
    }

    @Override
    public void onStop() {
        mCompositeDisposable.clear();
    }

    @Override
    public void setViewModel(StatisticsByYearContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }

    private void getUser() {
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
