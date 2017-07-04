package com.framgia.wsm.screen.confirmrequestleave;

import com.framgia.wsm.data.model.LeaveRequest;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.RequestRepository;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.error.RequestError;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
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

    ConfirmRequestLeavePresenter(UserRepository userRepository, RequestRepository requestRepository,
            BaseSchedulerProvider baseSchedulerProvider) {
        mUserRepository = userRepository;
        mRequestRepository = requestRepository;
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
    public void setViewModel(ConfirmRequestLeaveContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void createFormRequestLeave(LeaveRequest request) {
        Disposable disposable = mRequestRepository.createFormRequestLeave(request)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        mViewModel.onCreateFormRequestLeaveSuccess();
                    }
                }, new RequestError() {
                    @Override
                    public void onRequestError(BaseException error) {
                        mViewModel.onCreateFormFormRequestLeaveError(error);
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void editFormRequestLeave(LeaveRequest request) {
        Disposable disposable = mRequestRepository.editFormRequestLeave(request)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        mViewModel.onEditFormRequestLeaveSuccess();
                    }
                }, new RequestError() {
                    @Override
                    public void onRequestError(BaseException error) {
                        mViewModel.onEditFormFormRequestLeaveError(error);
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void deleteFormRequestLeave(int requestId) {
        Disposable disposable = mRequestRepository.deleteFormRequestLeave(requestId)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        mViewModel.onDeleteFormRequestLeaveSuccess();
                    }
                }, new RequestError() {
                    @Override
                    public void onRequestError(BaseException error) {
                        mViewModel.onDeleteFormFormRequestLeaveError(error);
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
