package com.framgia.wsm.screen.listrequest;

import com.framgia.wsm.data.model.Request;
import com.framgia.wsm.data.model.RequestOff;
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.RequestRepository;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.error.RequestError;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.utils.RequestType;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.List;

/**
 * Listens to user actions from the UI ({@link ListRequestFragment}), retrieves the data and
 * updates
 * the UI as required.
 */
final class ListRequestPresenter implements ListRequestContract.Presenter {
    private static final String TAG = ListRequestPresenter.class.getName();

    private ListRequestContract.ViewModel mViewModel;
    private RequestRepository mRequestRepository;
    private UserRepository mUserRepository;
    private CompositeDisposable mCompositeDisposable;
    private BaseSchedulerProvider mBaseSchedulerProvider;

    ListRequestPresenter(RequestRepository requestRepository, UserRepository userRepository,
            BaseSchedulerProvider baseSchedulerProvider) {
        mRequestRepository = requestRepository;
        mUserRepository = userRepository;
        mCompositeDisposable = new CompositeDisposable();
        mBaseSchedulerProvider = baseSchedulerProvider;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
        mCompositeDisposable.clear();
    }

    @Override
    public void setViewModel(ListRequestContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void getListAllRequest(int requestType, int userId) {
        Disposable disposable;
        switch (requestType) {
            case RequestType.REQUEST_OVERTIME:
                disposable = mRequestRepository.getListRequestOverTime(userId)
                        .subscribeOn(mBaseSchedulerProvider.io())
                        .observeOn(mBaseSchedulerProvider.ui())
                        .subscribe(new Consumer<BaseResponse<List<RequestOverTime>>>() {
                            @Override
                            public void accept(
                                    @NonNull BaseResponse<List<RequestOverTime>> listBaseResponse)
                                    throws Exception {
                                mViewModel.onGetListRequestSuccess(RequestType.REQUEST_OVERTIME,
                                        listBaseResponse.getData());
                            }
                        }, new RequestError() {
                            @Override
                            public void onRequestError(BaseException error) {
                                mViewModel.onGetListRequestError(error);
                            }
                        });
                mCompositeDisposable.add(disposable);
                break;
            case RequestType.REQUEST_OFF:
                disposable = mRequestRepository.getListRequestOff(userId)
                        .subscribeOn(mBaseSchedulerProvider.io())
                        .observeOn(mBaseSchedulerProvider.ui())
                        .subscribe(new Consumer<BaseResponse<List<RequestOff>>>() {
                            @Override
                            public void accept(
                                    @NonNull BaseResponse<List<RequestOff>> listBaseResponse)
                                    throws Exception {
                                mViewModel.onGetListRequestSuccess(RequestType.REQUEST_OFF,
                                        listBaseResponse.getData());
                            }
                        }, new RequestError() {
                            @Override
                            public void onRequestError(BaseException error) {
                                mViewModel.onGetListRequestError(error);
                            }
                        });
                mCompositeDisposable.add(disposable);
                break;
            case RequestType.REQUEST_LATE_EARLY:
                disposable = mRequestRepository.getListRequestLateEarly(userId)
                        .subscribeOn(mBaseSchedulerProvider.io())
                        .observeOn(mBaseSchedulerProvider.ui())
                        .subscribe(new Consumer<BaseResponse<List<Request>>>() {
                            @Override
                            public void accept(
                                    @NonNull BaseResponse<List<Request>> listBaseResponse)
                                    throws Exception {
                                mViewModel.onGetListRequestSuccess(RequestType.REQUEST_LATE_EARLY,
                                        listBaseResponse.getData());
                            }
                        }, new RequestError() {
                            @Override
                            public void onRequestError(BaseException error) {
                                mViewModel.onGetListRequestError(error);
                            }
                        });
                mCompositeDisposable.add(disposable);
                break;
            default:
                break;
        }
    }

    @Override
    public void getUser() {
        Disposable disposable = mUserRepository.getUser()
                .subscribeOn(mBaseSchedulerProvider.io())
                .observeOn(mBaseSchedulerProvider.ui())
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
    public void getListRequestOverTimeWithStatusAndTime(int requestType, int userId, int status,
            String time) {
        switch (requestType) {
            case RequestType.REQUEST_OVERTIME:
                getListRequestOverTimeWithStatusAndTime(userId, status, time);
                break;
            case RequestType.REQUEST_OFF:
                break;
            case RequestType.REQUEST_LATE_EARLY:
                break;
            default:
                break;
        }
    }

    private void getListRequestOverTimeWithStatusAndTime(int userId, int status, String time) {
        Disposable disposable =
                mRequestRepository.getListRequestOverTimeWithStatusAndTime(userId, status, time)
                        .subscribeOn(mBaseSchedulerProvider.io())
                        .observeOn(mBaseSchedulerProvider.ui())
                        .subscribe(new Consumer<BaseResponse<List<RequestOverTime>>>() {
                            @Override
                            public void accept(BaseResponse<List<RequestOverTime>> listBaseResponse)
                                    throws Exception {
                                mViewModel.onGetListRequestSuccess(RequestType.REQUEST_OVERTIME,
                                        listBaseResponse.getData());
                            }
                        }, new RequestError() {
                            @Override
                            public void onRequestError(BaseException error) {
                                mViewModel.onGetListRequestError(error);
                            }
                        });
        mCompositeDisposable.add(disposable);
    }
}
