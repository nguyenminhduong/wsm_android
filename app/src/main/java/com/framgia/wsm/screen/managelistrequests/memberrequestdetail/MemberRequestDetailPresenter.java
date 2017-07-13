package com.framgia.wsm.screen.managelistrequests.memberrequestdetail;

import com.framgia.wsm.data.source.RequestRepository;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.error.RequestError;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by ths on 11/07/2017.
 */

final class MemberRequestDetailPresenter implements MemberRequestDetailContract.Presenter {

    private MemberRequestDetailContract.ViewModel mViewModel;
    private BaseSchedulerProvider mSchedulerProvider;
    private CompositeDisposable mCompositeDisposable;
    private RequestRepository mRequestRepository;

    MemberRequestDetailPresenter(BaseSchedulerProvider baseSchedulerProvider,
            RequestRepository requestRepository) {
        mSchedulerProvider = baseSchedulerProvider;
        mRequestRepository = requestRepository;
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
    public void setViewModel(MemberRequestDetailContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void rejectRequest(int requestId) {
        //TODO edit later
        Disposable disposable = mRequestRepository.deleteFormRequestLeave(requestId)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        mViewModel.onRejectedSuccess();
                    }
                }, new RequestError() {
                    @Override
                    public void onRequestError(BaseException error) {
                        mViewModel.onRejectedOrAcceptedError(error);
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void approveRequest(int requestId) {
        //TODO edit later
        Disposable disposable = mRequestRepository.deleteFormRequestLeave(requestId)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        mViewModel.onApproveSuccess();
                    }
                }, new RequestError() {
                    @Override
                    public void onRequestError(BaseException error) {
                        mViewModel.onRejectedOrAcceptedError(error);
                    }
                });
        mCompositeDisposable.add(disposable);
    }
}
