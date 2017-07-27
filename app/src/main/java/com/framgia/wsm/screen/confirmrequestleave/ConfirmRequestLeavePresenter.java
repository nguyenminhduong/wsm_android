package com.framgia.wsm.screen.confirmrequestleave;

import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.RequestRepository;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.error.RequestError;
import com.framgia.wsm.data.source.remote.api.request.RequestLeaveRequest;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import com.framgia.wsm.widget.dialog.DialogManager;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Listens to user actions from the UI ({@link ConfirmRequestLeaveActivity}), retrieves the data and
 * updates
 * the UI as required.
 */
final class ConfirmRequestLeavePresenter implements ConfirmRequestLeaveContract.Presenter {
    private static final String TAG = ConfirmRequestLeavePresenter.class.getName();

    private ConfirmRequestLeaveContract.ViewModel mViewModel;
    private RequestRepository mRequestRepository;
    private UserRepository mUserRepository;
    private BaseSchedulerProvider mSchedulerProvider;
    private CompositeDisposable mCompositeDisposable;
    private DialogManager mDialogManager;

    ConfirmRequestLeavePresenter(UserRepository userRepository, RequestRepository requestRepository,
            BaseSchedulerProvider baseSchedulerProvider, DialogManager dialogManager) {
        mUserRepository = userRepository;
        mRequestRepository = requestRepository;
        mSchedulerProvider = baseSchedulerProvider;
        mCompositeDisposable = new CompositeDisposable();
        mDialogManager = dialogManager;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
        mCompositeDisposable.clear();
    }

    @Override
    public void setViewModel(ConfirmRequestLeaveContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void createFormRequestLeave(RequestLeaveRequest requestLeaveRequest) {
        Disposable disposable = mRequestRepository.createFormRequestLeave(requestLeaveRequest)
                .subscribeOn(mSchedulerProvider.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mDialogManager.showIndeterminateProgressDialog();
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        mDialogManager.dismissProgressDialog();
                    }
                })
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        mViewModel.onCreateFormRequestLeaveSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mViewModel.onCreateFormFormRequestLeaveError((BaseException) throwable);
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void editFormRequestLeave(RequestLeaveRequest requestLeaveRequest) {
        Disposable disposable = mRequestRepository.editFormRequestLeave(
                requestLeaveRequest.getLeaveRequest().getId(), requestLeaveRequest)
                .subscribeOn(mSchedulerProvider.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mDialogManager.showIndeterminateProgressDialog();
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        mDialogManager.dismissProgressDialog();
                    }
                })
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        mViewModel.onEditFormRequestLeaveSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mViewModel.onEditFormFormRequestLeaveError((BaseException) throwable);
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void deleteFormRequestLeave(int requestId) {
        Disposable disposable = mRequestRepository.deleteFormRequestLeave(requestId)
                .subscribeOn(mSchedulerProvider.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mDialogManager.showIndeterminateProgressDialog();
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        mDialogManager.dismissProgressDialog();
                    }
                })
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        mViewModel.onDeleteFormRequestLeaveSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mViewModel.onDeleteFormFormRequestLeaveError((BaseException) throwable);
                    }
                });
        mCompositeDisposable.add(disposable);
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
