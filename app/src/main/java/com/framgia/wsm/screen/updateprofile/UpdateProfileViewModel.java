package com.framgia.wsm.screen.updateprofile;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.widget.dialog.DialogManager;

/**
 * Exposes the data to be used in the Updateprofile screen.
 */

public class UpdateProfileViewModel extends BaseObservable
        implements UpdateProfileContract.ViewModel {

    private UpdateProfileContract.Presenter mPresenter;

    private Context mContext;
    private User mUser;
    private DialogManager mDialogManager;
    private Navigator mNavigator;

    public UpdateProfileViewModel(Context context, User user, Navigator navigator,
            DialogManager dialogManager, UpdateProfileContract.Presenter presenter) {
        mContext = context;
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mUser = user;
        mNavigator = navigator;
        mDialogManager = dialogManager;
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Bindable
    public User getUser() {
        return mUser;
    }
}
