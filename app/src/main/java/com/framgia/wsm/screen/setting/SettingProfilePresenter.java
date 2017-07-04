package com.framgia.wsm.screen.setting;

import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.error.RequestError;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Listens to user actions from the UI ({@link SettingProfileFragment}), retrieves the data and
 * updates
 * the UI as required.
 */

final class SettingProfilePresenter implements SettingProfileContract.Presenter {

    private static final String TAG = SettingProfilePresenter.class.getName();

    private SettingProfileContract.ViewModel mViewModel;
    private UserRepository mUserRepository;
    private BaseSchedulerProvider mSchedulerProvider;
    private CompositeDisposable mCompositeDisposable;

    SettingProfilePresenter(UserRepository userRepository,
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
        mCompositeDisposable.clear();
    }

    @Override
    public void setViewModel(SettingProfileContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void getUser() {
        Disposable disposable = mUserRepository.getUser()
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
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
