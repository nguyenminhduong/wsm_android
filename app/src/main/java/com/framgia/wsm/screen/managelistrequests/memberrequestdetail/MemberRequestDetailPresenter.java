package com.framgia.wsm.screen.managelistrequests.memberrequestdetail;

import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by ths on 11/07/2017.
 */

final class MemberRequestDetailPresenter implements MemberRequestDetailContract.Presenter {

    private MemberRequestDetailContract.ViewModel mViewModel;
    private BaseSchedulerProvider mSchedulerProvider;
    private CompositeDisposable mCompositeDisposable;

    MemberRequestDetailPresenter(BaseSchedulerProvider baseSchedulerProvider) {
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
    public void setViewModel(MemberRequestDetailContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }
}
