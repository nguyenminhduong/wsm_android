package com.framgia.wsm.screen.requestovertime;

/**
 * Listens to user actions from the UI ({@link RequestOvertimeActivity}), retrieves the data and
 * updates
 * the UI as required.
 */
final class RequestOvertimePresenter implements RequestOvertimeContract.Presenter {
    private static final String TAG = RequestOvertimePresenter.class.getName();

    private RequestOvertimeContract.ViewModel mViewModel;

    RequestOvertimePresenter() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void setViewModel(RequestOvertimeContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }
}
