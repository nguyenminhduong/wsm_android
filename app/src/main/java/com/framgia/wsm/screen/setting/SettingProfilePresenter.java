package com.framgia.wsm.screen.setting;

import com.framgia.wsm.data.model.Setting;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.error.RequestError;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
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
                        mViewModel.onError(error);
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void changeSetting(final User user) {
        Disposable disposable = mUserRepository.changeSetting(user.getSetting())
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mViewModel.onShowDialog();
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        mViewModel.onDismissDialog();
                    }
                })
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        saveUser(user);
                        mViewModel.onChangeSettingSuccess();
                    }
                }, new RequestError() {
                    @Override
                    public void onRequestError(BaseException error) {
                        mViewModel.onError(error);
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void getSetting() {
        Disposable disposable = mUserRepository.getSetting()
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mViewModel.onShowDialog();
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        mViewModel.onDismissDialog();
                    }
                })
                .subscribe(new Consumer<BaseResponse<Setting>>() {
                    @Override
                    public void accept(@NonNull BaseResponse<Setting> settingBaseResponse)
                            throws Exception {
                        mViewModel.onGetSettingSuccess(settingBaseResponse.getData());
                    }
                }, new RequestError() {
                    @Override
                    public void onRequestError(BaseException error) {
                        mViewModel.onGetSettingError(error);
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void saveUser(User user) {
        mUserRepository.saveUser(user);
    }
}
