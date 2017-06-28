package com.framgia.wsm.screen.listrequest;

import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.BasePresenter;
import com.framgia.wsm.screen.BaseViewModel;
import com.framgia.wsm.utils.RequestType;

/**
 * This specifies the contract between the view and the presenter.
 */
interface ListRequestContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel {
        void onGetListRequestError(BaseException exception);

        void onGetUserSuccess(User user);

        void onGetUserError(BaseException exception);

        void onGetListRequestSuccess(@RequestType int requestType, Object object);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<ViewModel> {
        void getListAllRequest(@RequestType int requestType, int userId);

        void getUser();

        void getListRequestOverTimeWithStatusAndTime(int userId, int status, String time);

        void getListRequestLeaveWithStatusAndTime(int userId, int status, String time);
    }
}
