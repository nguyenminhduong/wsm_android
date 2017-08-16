package com.framgia.wsm.screen.listrequest;

import com.framgia.wsm.data.model.LeaveRequest;
import com.framgia.wsm.data.model.OffRequest;
import com.framgia.wsm.data.model.QueryRequest;
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
import io.reactivex.functions.Action;
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
    public void getListAllRequest(int requestType, QueryRequest queryRequest,
            final boolean isLoadMore) {
        Disposable disposable;
        switch (requestType) {
            case RequestType.REQUEST_OVERTIME:
                disposable = mRequestRepository.getListRequestOverTime(queryRequest)
                        .subscribeOn(mBaseSchedulerProvider.io())
                        .observeOn(mBaseSchedulerProvider.ui())
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
                        .subscribe(new Consumer<BaseResponse<List<RequestOverTime>>>() {
                            @Override
                            public void accept(
                                    @NonNull BaseResponse<List<RequestOverTime>> listBaseResponse)
                                    throws Exception {
                                mViewModel.onGetListRequestSuccess(RequestType.REQUEST_OVERTIME,
                                        listBaseResponse.getData(), isLoadMore);
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
                disposable = mRequestRepository.getListRequestOff(queryRequest)
                        .subscribeOn(mBaseSchedulerProvider.io())
                        .observeOn(mBaseSchedulerProvider.ui())
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
                        .subscribe(new Consumer<BaseResponse<List<OffRequest>>>() {
                            @Override
                            public void accept(
                                    @NonNull BaseResponse<List<OffRequest>> listBaseResponse)
                                    throws Exception {
                                mViewModel.onGetListRequestSuccess(RequestType.REQUEST_OFF,
                                        listBaseResponse.getData(), isLoadMore);
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
                disposable = mRequestRepository.getListRequestLateEarly(queryRequest)
                        .subscribeOn(mBaseSchedulerProvider.io())
                        .observeOn(mBaseSchedulerProvider.ui())
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
                        .subscribe(new Consumer<BaseResponse<List<LeaveRequest>>>() {
                            @Override
                            public void accept(
                                    @NonNull BaseResponse<List<LeaveRequest>> listBaseResponse)
                                    throws Exception {
                                mViewModel.onGetListRequestSuccess(RequestType.REQUEST_LATE_EARLY,
                                        listBaseResponse.getData(), isLoadMore);
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
    public void getListAllRequestNoProgressDialog(@RequestType int requestType,
            QueryRequest queryRequest, final boolean isLoadMore) {
        Disposable disposable;
        switch (requestType) {
            case RequestType.REQUEST_OVERTIME:
                disposable = mRequestRepository.getListRequestOverTime(queryRequest)
                        .subscribeOn(mBaseSchedulerProvider.io())
                        .observeOn(mBaseSchedulerProvider.ui())
                        .subscribe(new Consumer<BaseResponse<List<RequestOverTime>>>() {
                            @Override
                            public void accept(
                                    @NonNull BaseResponse<List<RequestOverTime>> listBaseResponse)
                                    throws Exception {
                                mViewModel.onGetListRequestSuccess(RequestType.REQUEST_OVERTIME,
                                        listBaseResponse.getData(), isLoadMore);
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
                disposable = mRequestRepository.getListRequestOff(queryRequest)
                        .subscribeOn(mBaseSchedulerProvider.io())
                        .observeOn(mBaseSchedulerProvider.ui())
                        .subscribe(new Consumer<BaseResponse<List<OffRequest>>>() {
                            @Override
                            public void accept(
                                    @NonNull BaseResponse<List<OffRequest>> listBaseResponse)
                                    throws Exception {
                                mViewModel.onGetListRequestSuccess(RequestType.REQUEST_OFF,
                                        listBaseResponse.getData(), isLoadMore);
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
                disposable = mRequestRepository.getListRequestLateEarly(queryRequest)
                        .subscribeOn(mBaseSchedulerProvider.io())
                        .observeOn(mBaseSchedulerProvider.ui())
                        .subscribe(new Consumer<BaseResponse<List<LeaveRequest>>>() {
                            @Override
                            public void accept(
                                    @NonNull BaseResponse<List<LeaveRequest>> listBaseResponse)
                                    throws Exception {
                                mViewModel.onGetListRequestSuccess(RequestType.REQUEST_LATE_EARLY,
                                        listBaseResponse.getData(), isLoadMore);
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
}
