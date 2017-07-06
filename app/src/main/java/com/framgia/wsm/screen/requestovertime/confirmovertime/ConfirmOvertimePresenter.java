package com.framgia.wsm.screen.requestovertime.confirmovertime;

import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.RequestRepository;
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
 * Listens to user actions from the UI ({@link ConfirmOvertimeActivity}), retrieves the data and
 * updates
 * the UI as required.
 */
final class ConfirmOvertimePresenter implements ConfirmOvertimeContract.Presenter {
    private static final String TAG = ConfirmOvertimePresenter.class.getName();

    private ConfirmOvertimeContract.ViewModel mViewModel;
    private RequestRepository mRequestRepository;
    private UserRepository mUserRepository;
    private BaseSchedulerProvider mSchedulerProvider;
    private CompositeDisposable mCompositeDisposable;

    ConfirmOvertimePresenter(UserRepository userRepository, RequestRepository requestRepository,
            BaseSchedulerProvider schedulerProviderA) {
        mUserRepository = userRepository;
        mRequestRepository = requestRepository;
        mSchedulerProvider = schedulerProviderA;
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
    public void setViewModel(ConfirmOvertimeContract.ViewModel viewModel) {
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

    @Override
    public void createFormRequestOverTime(RequestOverTime requestOverTime) {
        Disposable disposable = mRequestRepository.createFormRequestOverTime(requestOverTime)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mViewModel.onShowIndeterminateProgressDialog();
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        mViewModel.onDismissProgressDialog();
                    }
                })
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        mViewModel.onCreateFormOverTimeSuccess();
                    }
                }, new RequestError() {
                    @Override
                    public void onRequestError(BaseException error) {
                        mViewModel.onCreateFormOverTimeError(error);
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void editFormRequestOvertime(RequestOverTime requestOverTime) {
        Disposable disposable = mRequestRepository.editFormRequestOverTime(requestOverTime)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mViewModel.onShowIndeterminateProgressDialog();
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        mViewModel.onDismissProgressDialog();
                    }
                })
                .subscribe(new Consumer<BaseResponse<RequestOverTime>>() {
                    @Override
                    public void accept(@NonNull
                            BaseResponse<RequestOverTime> requestOverTimeResponseBaseResponse)
                            throws Exception {

                        mViewModel.onEditFormOverTimeSuccess(
                                requestOverTimeResponseBaseResponse.getData());
                    }
                }, new RequestError() {
                    @Override
                    public void onRequestError(BaseException error) {
                        mViewModel.onEditFormOverTimeError(error);
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void deleteFormRequestOvertime(int requestOverTimeId) {
        Disposable disposable = mRequestRepository.deleteFormRequestOverTime(requestOverTimeId)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mViewModel.onShowIndeterminateProgressDialog();
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        mViewModel.onDismissProgressDialog();
                    }
                })
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        mViewModel.onDeleteFormOverTimeSuccess();
                    }
                }, new RequestError() {
                    @Override
                    public void onRequestError(BaseException error) {
                        mViewModel.onDeleteFormOverTimeError(error);
                    }
                });
        mCompositeDisposable.add(disposable);
    }
}
