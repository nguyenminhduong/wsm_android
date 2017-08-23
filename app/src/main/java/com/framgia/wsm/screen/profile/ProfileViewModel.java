package com.framgia.wsm.screen.profile;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.framgia.wsm.BR;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.changepassword.ChangePasswordActivity;
import com.framgia.wsm.screen.profile.branch.BranchAdapter;
import com.framgia.wsm.screen.profile.group.GroupAdapter;
import com.framgia.wsm.screen.updateprofile.UpdateProfileActivity;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.common.DateTimeUtils;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.utils.string.StringUtils;

/**
 * Exposes the data to be used in the Profile screen.
 */

public class ProfileViewModel extends BaseObservable implements ProfileContract.ViewModel {
    private static final String TAG = "ProfileViewModel";

    private ProfileContract.Presenter mPresenter;
    private Navigator mNavigator;
    private BranchAdapter mBranchAdapter;
    private GroupAdapter mGroupAdapter;
    private User mUser;
    private boolean mIsLoadDataFirstTime;
    private boolean mIsVisibleStartProbation;
    private boolean mIsVisibleEndProbation;
    private boolean mIsVisibleContractDate;

    public ProfileViewModel(ProfileContract.Presenter presenter, Navigator navigator,
            BranchAdapter branchAdapter, GroupAdapter groupAdapter) {
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mNavigator = navigator;
        mBranchAdapter = branchAdapter;
        mGroupAdapter = groupAdapter;
        mIsLoadDataFirstTime = true;
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Override
    public void onGetUserSuccess(User user) {
        if (user == null) {
            return;
        }
        mUser = user;
        notifyPropertyChanged(BR.user);
        notifyPropertyChanged(BR.avatar);
        notifyPropertyChanged(BR.birthday);
        mBranchAdapter.updateDataBranch(mUser.getBranches());
        mGroupAdapter.updateDataGroup(mUser.getGroups());
        mIsLoadDataFirstTime = false;
        setVisibleStartProbation(StringUtils.isBlank(mUser.getStartProbationDate()));
        setVisibleEndProbation(StringUtils.isBlank(mUser.getEndProbationDate()));
        setVisibleContractDate(StringUtils.isBlank(mUser.getContractDate()));
    }

    @Override
    public void onGetUserError(BaseException exception) {
        Log.e(TAG, "onGetUserError", exception);
    }

    @Override
    public void reloadData() {
        if (mIsLoadDataFirstTime) {
            mPresenter.getUser();
        }
    }

    @Bindable
    public User getUser() {
        return mUser;
    }

    @Bindable
    public String getAvatar() {
        return mUser != null ? Constant.END_POINT_URL + mUser.getAvatar() : "";
    }

    @Bindable
    public String getBirthday() {
        return mUser != null ? DateTimeUtils.convertUiFormatToDataFormat(mUser.getBirthday(),
                DateTimeUtils.INPUT_TIME_FORMAT, DateTimeUtils.FORMAT_DATE) : "";
    }

    public void setVisibleStartProbation(boolean visibleStartProbation) {
        mIsVisibleStartProbation = visibleStartProbation;
        notifyPropertyChanged(BR.visibleStartProbation);
    }

    public void setVisibleEndProbation(boolean visibleEndProbation) {
        mIsVisibleEndProbation = visibleEndProbation;
        notifyPropertyChanged(BR.visibleEndProbation);
    }

    @Bindable
    public boolean isVisibleStartProbation() {
        return mIsVisibleStartProbation;
    }

    @Bindable
    public boolean isVisibleEndProbation() {
        return mIsVisibleEndProbation;
    }

    @Bindable
    public boolean isVisibleContractDate() {
        return mIsVisibleContractDate;
    }

    public void setVisibleContractDate(boolean visibleContractDate) {
        mIsVisibleContractDate = visibleContractDate;
        notifyPropertyChanged(BR.visibleContractDate);
    }

    public BranchAdapter getBranchAdapter() {
        return mBranchAdapter;
    }

    public GroupAdapter getGroupAdapter() {
        return mGroupAdapter;
    }

    public void onClickEditProfile(View view) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.EXTRA_USER, mUser);
        mNavigator.startActivityForResultFromFragment(UpdateProfileActivity.class, bundle,
                Constant.RequestCode.PROFILE_USER);
    }

    public void onClickChangePassword(View view) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.EXTRA_USER, mUser);
        mNavigator.startActivity(ChangePasswordActivity.class, bundle);
    }
}
