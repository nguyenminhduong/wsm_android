package com.framgia.wsm.screen.managelistrequests.memberrequestdetail;

import com.framgia.wsm.data.source.RequestRepository;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.error.RequestError;
import com.framgia.wsm.data.source.remote.api.request.ActionRequest;
import com.framgia.wsm.data.source.remote.api.response.ActionRequestResponse;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by ths on 11/07/2017.
 */

final class MemberRequestDetailPresenter implements MemberRequestDetailContract.Presenter {

    private MemberRequestDetailContract.ViewModel mViewModel;
    private BaseSchedulerProvider mSchedulerProvider;
    private CompositeDisposable mCompositeDisposable;
    private RequestRepository mRequestRepository;
    private UserRepository mUserRepository;

    MemberRequestDetailPresenter(BaseSchedulerProvider baseSchedulerProvider,
            RequestRepository requestRepository, UserRepository userRepository) {
        mSchedulerProvider = baseSchedulerProvider;
        mRequestRepository = requestRepository;
        mCompositeDisposable = new CompositeDisposable();
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
    public void setViewModel(MemberRequestDetailContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void approveOrRejectRequest(ActionRequest actionRequest) {
        Disposable disposable = mRequestRepository.actionApproveRejectRequest(actionRequest)
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
                        mViewModel.onError(error);
                    }
                });
        mCompositeDisposable.add(disposable);
    }
}
