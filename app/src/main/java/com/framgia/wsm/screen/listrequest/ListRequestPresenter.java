package com.framgia.wsm.screen.listrequest;

import com.framgia.wsm.data.source.RequestRepository;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Listens to user actions from the UI ({@link ListRequestFragment}), retrieves the data and
 * updates
 * the UI as required.
 */
final class ListRequestPresenter implements ListRequestContract.Presenter {
    private static final String TAG = ListRequestPresenter.class.getName();

    private ListRequestContract.ViewModel mViewModel;
    private RequestRepository mRequestRepository;
    private CompositeDisposable mCompositeDisposable;
    private BaseSchedulerProvider mBaseSchedulerProvider;

    ListRequestPresenter(RequestRepository requestRepository,
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
    public void setViewModel(ListRequestContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void getListAllRequest(int requestType, int userId) {
        // TODO: get list all request
    }
}
