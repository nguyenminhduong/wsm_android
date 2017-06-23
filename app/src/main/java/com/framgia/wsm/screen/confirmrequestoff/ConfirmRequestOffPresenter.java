package com.framgia.wsm.screen.confirmrequestoff;

import com.framgia.wsm.data.model.RequestOff;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.RequestRepository;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.error.RequestError;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.response.RequestOffResponse;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Listens to user actions from the UI ({@link ConfirmRequestOffActivity}), retrieves the data and
 * updates
 * the UI as required.
 */
final class ConfirmRequestOffPresenter implements ConfirmRequestOffContract.Presenter {
    private static final String TAG = ConfirmRequestOffPresenter.class.getName();

    private ConfirmRequestOffContract.ViewModel mViewModel;
    private RequestRepository mRequestRepository;
    private UserRepository mUserRepository;
    private BaseSchedulerProvider mSchedulerProvider;
    private CompositeDisposable mCompositeDisposable;

    ConfirmRequestOffPresenter(UserRepository userRepository, RequestRepository requestRepository,
            BaseSchedulerProvider schedulerProvider) {
        mUserRepository = userRepository;
        mRequestRepository = requestRepository;
        mSchedulerProvider = schedulerProvider;
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
    public void setViewModel(ConfirmRequestOffContract.ViewModel viewModel) {
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
    public void createFormRequestOff(RequestOff requestOff) {
        Disposable disposable = mRequestRepository.createFormRequestOff(requestOff)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        mViewModel.onCreateFormRequestOffSuccess();
                    }
                }, new RequestError() {
                    @Override
                    public void onRequestError(BaseException error) {
                        mViewModel.onCreateFormFormRequestOffError(error);
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void deleteFormRequestOff(int requestOffId) {
        Disposable disposable = mRequestRepository.deleteFormRequestOff(requestOffId)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        mViewModel.onDeleteFormRequestOffSuccess();
                    }
                }, new RequestError() {
                    @Override
                    public void onRequestError(BaseException error) {
                        mViewModel.onDeleteFormRequestOffError(error);
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void editFormRequestOff(RequestOff requestOff) {
        Disposable disposable = mRequestRepository.editFormRequestOff(requestOff)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Consumer<BaseResponse<RequestOffResponse>>() {
                    @Override
                    public void accept(@NonNull
                            BaseResponse<RequestOffResponse> requestOffResponseBaseResponse)
                            throws Exception {

                        mViewModel.onEditFormRequestOffSuccess(
                                requestOffResponseBaseResponse.getData().getRequestOff());
                    }
                }, new RequestError() {
                    @Override
                    public void onRequestError(BaseException error) {
                        mViewModel.onEditFormRequestOffError(error);
                    }
                });
        mCompositeDisposable.add(disposable);
    }
}
