package com.framgia.wsm.screen.setting;

import com.framgia.wsm.data.model.Setting;
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
        void onError(BaseException exception);

        void onGetUserSuccess(User user);

        void onShowDialog();

        void onDismissDialog();

        void onChangeSettingSuccess();

        void onGetSettingError(BaseException exception);

        void onGetSettingSuccess(Setting setting);
    }

    /**
     * Presenter
     */
    interface Presenter extends BasePresenter<ViewModel> {
        void getUser();

        void changeSetting(User user);

        void getSetting();

        void saveUser(User user);
    }
}
