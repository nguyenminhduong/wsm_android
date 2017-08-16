package com.framgia.wsm.screen.managelistrequests;

import com.framgia.wsm.data.model.LeaveRequest;
import com.framgia.wsm.data.model.OffRequest;
import com.framgia.wsm.data.model.QueryRequest;
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.RequestRepository;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.error.RequestError;
import com.framgia.wsm.data.source.remote.api.request.ActionRequest;
import com.framgia.wsm.data.source.remote.api.response.ActionRequestResponse;
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
 * Listens to user actions from the UI ({@link ManageListRequestsFragment}), retrieves the data and
 * updates
 * the UI as required.
 */
final class ManageListRequestsPresenter implements ManageListRequestsContract.Presenter {
    private static final String TAG = ManageListRequestsPresenter.class.getName();

    private ManageListRequestsContract.ViewModel mViewModel;
    private RequestRepository mRequestRepository;
    private CompositeDisposable mCompositeDisposable;
    private BaseSchedulerProvider mBaseSchedulerProvider;
    private UserRepository mUserRepository;

    ManageListRequestsPresenter(RequestRepository requestRepository,
            BaseSchedulerProvider baseSchedulerProvider, UserRepository userRepository) {
        mRequestRepository = requestRepository;
        mCompositeDisposable = new CompositeDisposable();
        mBaseSchedulerProvider = baseSchedulerProvider;
        mUserRepository = userRepository;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
        mCompositeDisposable.clear();
    }

    @Override
    public void setViewModel(ManageListRequestsContract.ViewModel viewModel) {
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
    public void getListAllRequestManage(int requestType, QueryRequest queryRequest,
            final boolean isLoadMore) {
        Disposable disposable;
        switch (requestType) {
            case RequestType.REQUEST_LATE_EARLY:
                disposable = mRequestRepository.getListRequestLeaveManage(queryRequest)
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
                                mViewModel.onGetListRequestManageSuccess(listBaseResponse.getData(),
                                        isLoadMore);
                            }
                        }, new RequestError() {
                            @Override
                            public void onRequestError(BaseException error) {
                                mViewModel.onGetListRequestManageError(error);
                            }
                        });
                mCompositeDisposable.add(disposable);
                break;
            case RequestType.REQUEST_OFF:
                disposable = mRequestRepository.getListRequestOffManage(queryRequest)
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
                                mViewModel.onGetListRequestManageSuccess(listBaseResponse.getData(),
                                        isLoadMore);
                            }
                        }, new RequestError() {
                            @Override
                            public void onRequestError(BaseException error) {
                                mViewModel.onGetListRequestManageError(error);
                            }
                        });
                mCompositeDisposable.add(disposable);
                break;
            case RequestType.REQUEST_OVERTIME:
                disposable = mRequestRepository.getListRequestOvertimeManage(queryRequest)
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
                                mViewModel.onGetListRequestManageSuccess(listBaseResponse.getData(),
                                        isLoadMore);
                            }
                        }, new RequestError() {
                            @Override
                            public void onRequestError(BaseException error) {
                                mViewModel.onGetListRequestManageError(error);
                            }
                        });
                mCompositeDisposable.add(disposable);
                break;
            default:
                break;
        }
    }

    @Override
    public void getListAllRequestManageNoProgressDialog(int requestType, QueryRequest queryRequest,
            final boolean isLoadMore) {
        Disposable disposable;
        switch (requestType) {
            case RequestType.REQUEST_LATE_EARLY:
                disposable = mRequestRepository.getListRequestLeaveManage(queryRequest)
                        .subscribeOn(mBaseSchedulerProvider.io())
                        .observeOn(mBaseSchedulerProvider.ui())
                        .subscribe(new Consumer<BaseResponse<List<LeaveRequest>>>() {
                            @Override
                            public void accept(
                                    @NonNull BaseResponse<List<LeaveRequest>> listBaseResponse)
                                    throws Exception {
                                mViewModel.onGetListRequestManageSuccess(listBaseResponse.getData(),
                                        isLoadMore);
                            }
                        }, new RequestError() {
                            @Override
                            public void onRequestError(BaseException error) {
                                mViewModel.onGetListRequestManageError(error);
                            }
                        });
                mCompositeDisposable.add(disposable);
                break;
            case RequestType.REQUEST_OFF:
                disposable = mRequestRepository.getListRequestOffManage(queryRequest)
                        .subscribeOn(mBaseSchedulerProvider.io())
                        .observeOn(mBaseSchedulerProvider.ui())
                        .subscribe(new Consumer<BaseResponse<List<OffRequest>>>() {
                            @Override
                            public void accept(
                                    @NonNull BaseResponse<List<OffRequest>> listBaseResponse)
                                    throws Exception {
                                mViewModel.onGetListRequestManageSuccess(listBaseResponse.getData(),
                                        isLoadMore);
                            }
                        }, new RequestError() {
                            @Override
                            public void onRequestError(BaseException error) {
                                mViewModel.onGetListRequestManageError(error);
                            }
                        });
                mCompositeDisposable.add(disposable);
                break;
            case RequestType.REQUEST_OVERTIME:
                disposable = mRequestRepository.getListRequestOvertimeManage(queryRequest)
                        .subscribeOn(mBaseSchedulerProvider.io())
                        .observeOn(mBaseSchedulerProvider.ui())
                        .subscribe(new Consumer<BaseResponse<List<RequestOverTime>>>() {
                            @Override
                            public void accept(
                                    @NonNull BaseResponse<List<RequestOverTime>> listBaseResponse)
                                    throws Exception {
                                mViewModel.onGetListRequestManageSuccess(listBaseResponse.getData(),
                                        isLoadMore);
                            }
                        }, new RequestError() {
                            @Override
                            public void onRequestError(BaseException error) {
                                mViewModel.onGetListRequestManageError(error);
                            }
                        });
                mCompositeDisposable.add(disposable);
                break;
            default:
                break;
        }
    }

    @Override
    public void approveOrRejectRequest(ActionRequest actionRequest) {
        Disposable disposable = mRequestRepository.actionApproveRejectRequest(actionRequest)
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
                .subscribe(new Consumer<BaseResponse<ActionRequestResponse>>() {
                    @Override
                    public void accept(@NonNull
                            BaseResponse<ActionRequestResponse> actionRequestResponseBaseResponse)
                            throws Exception {
                        mViewModel.onApproveOrRejectRequestSuccess(
                                actionRequestResponseBaseResponse.getData());
                    }
                }, new RequestError() {
                    @Override
                    public void onRequestError(BaseException error) {
                        mViewModel.onApproveOrRejectRequestError(error);
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void approveAllRequest(ActionRequest actionRequest) {
        Disposable disposable = mRequestRepository.approveAllRequest(actionRequest)
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
                .subscribe(new Consumer<BaseResponse<List<ActionRequestResponse>>>() {
                    @Override
                    public void accept(
                            @NonNull BaseResponse<List<ActionRequestResponse>> listBaseResponse)
                            throws Exception {
                        mViewModel.onApproveAllRequestSuccess(listBaseResponse.getData());
                    }
                }, new RequestError() {
                    @Override
                    public void onRequestError(BaseException error) {
                        mViewModel.onApproveAllRequestError(error);
                    }
                });
        mCompositeDisposable.add(disposable);
    }
}
