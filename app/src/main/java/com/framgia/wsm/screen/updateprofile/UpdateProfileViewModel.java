package com.framgia.wsm.screen.updateprofile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import com.framgia.wsm.BR;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.widget.dialog.DialogManager;
import com.fstyle.library.DialogAction;
import com.fstyle.library.MaterialDialog;

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
    private String mEmployeeNameError;
    private String mBirthdayError;
    private String mAvatar;

    UpdateProfileViewModel(Context context, User user, Navigator navigator,
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

    @Override
    public void onInputEmployeeNameError(String employeeName) {
        mEmployeeNameError = employeeName;
        notifyPropertyChanged(BR.employeeNameError);
    }

    @Override
    public void onInputBirthdayError(String birthday) {
        mBirthdayError = birthday;
        notifyPropertyChanged(BR.birthdayError);
    }

    @Override
    public void onUpdateProfileSuccess(User user) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.EXTRA_USER, user);
        mNavigator.finishActivityWithResult(bundle, Activity.RESULT_OK);
    }

    @Override
    public void onUpdateProfileError(BaseException exception) {
        mDialogManager.dialogError(exception, new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog,
                    @NonNull DialogAction dialogAction) {
                mNavigator.finishActivity();
            }
        });
    }

    @Override
    public void setAvatarUser(String avatar) {
        mAvatar = avatar;
        mUser.getAvatar().setUrl(avatar);
        notifyPropertyChanged(BR.avatar);
    }

    @Bindable
    public String getEmployeeNameError() {
        return mEmployeeNameError;
    }

    @Bindable
    public String getBirthdayError() {
        return mBirthdayError;
    }

    @Bindable
    public String getAvatar() {
        return mAvatar;
    }

    public void onClickArrowBack(View view) {
        mNavigator.finishActivity();
    }

    public void onClickPickAvatar(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        mNavigator.startActivityForResult(intent, Constant.RequestCode.REQUEST_SELECT_AVATAR);
    }

    public void onCickSubmit(View view) {
        if (mUser == null) {
            return;
        }
        if (!mPresenter.validateData(mUser)) {
            mDialogManager.dialogError(
                    mContext.getString(R.string.the_field_required_can_not_be_blank));
            return;
        }
        mPresenter.updateProfile(mUser);
    }
}
