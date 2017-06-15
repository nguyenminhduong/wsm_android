package com.framgia.wsm.screen.listrequest;

import com.framgia.wsm.data.model.Request;
import com.framgia.wsm.data.source.RequestRepository;
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
        switch (requestType) {
            case RequestType.REQUEST_OVERTIME:
                // TODO: get list request overtime
                break;
            case RequestType.REQUEST_OFF:
                Disposable subscription = mRequestRepository.getListRequestOff(userId)
                        .subscribeOn(mBaseSchedulerProvider.io())
                        .observeOn(mBaseSchedulerProvider.ui())
                        .subscribe(new Consumer<BaseResponse<List<Request>>>() {
                            @Override
                            public void accept(
                                    @NonNull BaseResponse<List<Request>> listBaseResponse)
                                    throws Exception {
                                mViewModel.onGetListRequestSuccess(listBaseResponse.getData());
                            }
                        }, new RequestError() {
                            @Override
                            public void onRequestError(BaseException error) {
                                mViewModel.onGetListRequestError(error);
                            }
                        });
                mCompositeDisposable.add(subscription);
                break;
            case RequestType.REQUEST_LATE_EARLY:
                // TODO: get list request late early

            default:
                break;
        }
    }
}
