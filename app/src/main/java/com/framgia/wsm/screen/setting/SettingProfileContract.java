package com.framgia.wsm.screen.setting;

import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.BasePresenter;
import com.framgia.wsm.screen.BaseViewModel;

/**
 * This specifies the contract between the view and the presenter.
 */

public interface SettingProfileContract {
    /**
     * View
     */
    interface ViewModel extends BaseViewModel {
        void onGetUserError(BaseException exception);

        void onGetUserSuccess(User user);
    }

    /**
     * Presenter
     */
    interface Presenter extends BasePresenter<ViewModel> {
        void getUser();
    }
}
