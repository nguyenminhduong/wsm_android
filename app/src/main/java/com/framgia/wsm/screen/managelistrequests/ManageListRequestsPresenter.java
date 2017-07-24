package com.framgia.wsm.screen.managelistrequests;

import com.framgia.wsm.data.model.LeaveRequest;
import com.framgia.wsm.data.model.OffRequest;
import com.framgia.wsm.data.model.QueryRequest;
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.data.source.RequestRepository;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.error.RequestError;
import com.framgia.wsm.data.source.remote.api.request.ActionRequest;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.utils.RequestType;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.Collections;
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

    ManageListRequestsPresenter(RequestRepository requestRepository,
            BaseSchedulerProvider baseSchedulerProvider) {
        mRequestRepository = requestRepository;
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
    public void setViewModel(ManageListRequestsContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void getListAllRequestManage(@RequestType final int requestType,
            QueryRequest queryRequest) {
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
                                Collections.reverse(listBaseResponse.getData());
                                mViewModel.onGetListRequestManageSuccess(
                                        RequestType.REQUEST_LATE_EARLY, listBaseResponse.getData());
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
                                Collections.reverse(listBaseResponse.getData());
                                mViewModel.onGetListRequestManageSuccess(RequestType.REQUEST_OFF,
                                        listBaseResponse.getData());
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
                                Collections.reverse(listBaseResponse.getData());
                                mViewModel.onGetListRequestManageSuccess(
                                        RequestType.REQUEST_OVERTIME, listBaseResponse.getData());
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
    public void approveRequest(@RequestType int requestType, final int itemPosition,
            ActionRequest actionRequest) {
        Disposable disposable;
        switch (requestType) {
            case RequestType.REQUEST_LATE_EARLY:
                disposable = mRequestRepository.approveRequestLeave(actionRequest)
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
                        .subscribe(new Consumer<BaseResponse<LeaveRequest>>() {
                            @Override
                            public void accept(
                                    @NonNull BaseResponse<LeaveRequest> leaveRequestBaseResponse)
                                    throws Exception {
                                mViewModel.onApproveRequestSuccess(RequestType.REQUEST_LATE_EARLY,
                                        itemPosition, leaveRequestBaseResponse.getData());
                            }
                        }, new RequestError() {
                            @Override
                            public void onRequestError(BaseException error) {
                                mViewModel.onApproveRequestError(error);
                            }
                        });
                mCompositeDisposable.add(disposable);
                break;
            case RequestType.REQUEST_OFF:
                disposable = mRequestRepository.approveRequestOff(actionRequest)
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
                        .subscribe(new Consumer<BaseResponse<OffRequest>>() {
                            @Override
                            public void accept(
                                    @NonNull BaseResponse<OffRequest> leaveRequestBaseResponse)
                                    throws Exception {
                                mViewModel.onApproveRequestSuccess(RequestType.REQUEST_OFF,
                                        itemPosition, leaveRequestBaseResponse.getData());
                            }
                        }, new RequestError() {
                            @Override
                            public void onRequestError(BaseException error) {
                                mViewModel.onApproveRequestError(error);
                            }
                        });
                mCompositeDisposable.add(disposable);
                break;
            case RequestType.REQUEST_OVERTIME:
                disposable = mRequestRepository.approveRequestOverTime(actionRequest)
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
                        .subscribe(new Consumer<BaseResponse<RequestOverTime>>() {
                            @Override
                            public void accept(
                                    @NonNull BaseResponse<RequestOverTime> leaveRequestBaseResponse)
                                    throws Exception {
                                mViewModel.onApproveRequestSuccess(RequestType.REQUEST_OVERTIME,
                                        itemPosition, leaveRequestBaseResponse.getData());
                            }
                        }, new RequestError() {
                            @Override
                            public void onRequestError(BaseException error) {
                                mViewModel.onApproveRequestError(error);
                            }
                        });
                mCompositeDisposable.add(disposable);
                break;
            default:
                break;
        }
    }

    @Override
    public void rejectRequest(@RequestType int requestType, final int itemPosition,
            ActionRequest actionRequest) {
        Disposable disposable;
        switch (requestType) {
            case RequestType.REQUEST_LATE_EARLY:
                disposable = mRequestRepository.rejectRequestLeave(actionRequest)
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
                        .subscribe(new Consumer<BaseResponse<LeaveRequest>>() {
                            @Override
                            public void accept(
                                    @NonNull BaseResponse<LeaveRequest> leaveRequestBaseResponse)
                                    throws Exception {
                                mViewModel.onRejectRequestSuccess(RequestType.REQUEST_LATE_EARLY,
                                        itemPosition, leaveRequestBaseResponse.getData());
                            }
                        }, new RequestError() {
                            @Override
                            public void onRequestError(BaseException error) {
                                mViewModel.onRejectRequestError(error);
                            }
                        });
                mCompositeDisposable.add(disposable);
                break;
            case RequestType.REQUEST_OFF:
                disposable = mRequestRepository.rejectRequestOff(actionRequest)
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
                        .subscribe(new Consumer<BaseResponse<OffRequest>>() {
                            @Override
                            public void accept(
                                    @NonNull BaseResponse<OffRequest> leaveRequestBaseResponse)
                                    throws Exception {
                                mViewModel.onRejectRequestSuccess(RequestType.REQUEST_OFF,
                                        itemPosition, leaveRequestBaseResponse.getData());
                            }
                        }, new RequestError() {
                            @Override
                            public void onRequestError(BaseException error) {
                                mViewModel.onRejectRequestError(error);
                            }
                        });
                mCompositeDisposable.add(disposable);
                break;
            case RequestType.REQUEST_OVERTIME:
                disposable = mRequestRepository.rejectRequestOverTime(actionRequest)
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
                        .subscribe(new Consumer<BaseResponse<RequestOverTime>>() {
                            @Override
                            public void accept(
                                    @NonNull BaseResponse<RequestOverTime> leaveRequestBaseResponse)
                                    throws Exception {
                                mViewModel.onRejectRequestSuccess(RequestType.REQUEST_OVERTIME,
                                        itemPosition, leaveRequestBaseResponse.getData());
                            }
                        }, new RequestError() {
                            @Override
                            public void onRequestError(BaseException error) {
                                mViewModel.onRejectRequestError(error);
                            }
                        });
                mCompositeDisposable.add(disposable);
                break;
            default:
                break;
        }
    }
}
