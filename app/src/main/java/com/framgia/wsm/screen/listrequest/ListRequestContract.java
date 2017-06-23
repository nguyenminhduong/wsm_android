package com.framgia.wsm.screen.listrequest;

import com.framgia.wsm.data.model.Request;
import com.framgia.wsm.data.model.RequestOff;
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.BasePresenter;
import com.framgia.wsm.screen.BaseViewModel;
import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
interface ListRequestContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel {
        void onGetListRequestError(BaseException exception);

        void onGetListRequestLeaveSuccess(List<Request> requests);

        void onGetListRequestOffSuccess(List<RequestOff> requestOffs);

        void onGetListRequestOverTimeSuccess(List<RequestOverTime> requestOverTimes);

        void onGetUserSuccess(User user);

        void onGetUserError(BaseException exception);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<ViewModel> {
        void getListAllRequest(int requestType, int userId);

        void getUser();
    }
}
