package com.framgia.wsm.screen.setting;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;
import android.view.View;
import com.android.databinding.library.baseAdapters.BR;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.Branch;
import com.framgia.wsm.data.model.Group;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.widget.dialog.DialogManager;
import com.fstyle.library.MaterialDialog;
import java.util.List;

/**
 * Created by ths on 03/07/2017.
 */

public class SettingProfileViewModel extends BaseObservable
        implements SettingProfileContract.ViewModel {

    private static final String TAG = SettingProfileViewModel.class.getName();

    private Context mContext;
    private SettingProfileContract.Presenter mPresenter;
    private User mUser;
    private DialogManager mDialogManager;
    private String mCurrentGroup;
    private String mCurrentBranch;
    private int mCurrentGroupPosition;
    private int mCurrentBranchPosition;
    private boolean mNotificationAll;
    private boolean mEmailAll;

    SettingProfileViewModel(Context context, SettingProfileContract.Presenter presenter,
            DialogManager dialogManager) {
        mContext = context;
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mUser = new User();
        mPresenter.getUser();
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

    @Override
    public void onGetUserError(BaseException exception) {
        Log.e(TAG, "onGetUserError: ", exception);
    }

    @Override
    public void onGetUserSuccess(User user) {
        if (user.getSetting() == null) {
            return;
        }
        setUser(user);
        mCurrentBranchPosition = searchCurrentPositionWorkspace(mUser.getBranches(),
                mUser.getSetting().getWorkspaceDefault());
        mCurrentGroupPosition =
                searchCurrentPositionGroup(mUser.getGroups(), mUser.getSetting().getGroupDefault());
        setNotificationAll(
                mUser.getSetting().getNotificationSetting().isWorkspace() && mUser.getSetting()
                        .getNotificationSetting()
                        .isUserSpecialType() && mUser.getSetting()
                        .getNotificationSetting()
                        .isGroup());
        setEmailAll(mUser.getSetting().getEmailSetting().isWorkspace() && mUser.getSetting()
                .getEmailSetting()
                .isUserSpecialType() && mUser.getSetting().getEmailSetting().isGroup());
    }

    private int searchCurrentPositionGroup(List<Group> groupList, int currentPositionGroup) {
        for (int i = 0; i < groupList.size(); i++) {
            if (groupList.get(i).getGroupId() == currentPositionGroup) {
                setCurrentGroup(groupList.get(i).getGroupName());
                return i;
            }
        }
        return 0;
    }

    private int searchCurrentPositionWorkspace(List<Branch> branchList,
            int currentPositionWorkspace) {
        for (int i = 0; i < branchList.size(); i++) {
            if (branchList.get(i).getBranchId() == currentPositionWorkspace) {
                setCurrentBranch(branchList.get(i).getBranchName());
                return i;
            }
        }
        return 0;
    }

    @Bindable
    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
        notifyPropertyChanged(BR.user);
    }

    @Bindable
    public String getCurrentGroup() {
        return mCurrentGroup;
    }

    public void setCurrentGroup(String currentGroup) {
        mCurrentGroup = currentGroup;
        notifyPropertyChanged(BR.currentGroup);
    }

    @Bindable
    public String getCurrentBranch() {
        return mCurrentBranch;
    }

    public void setCurrentBranch(String currentBranch) {
        mCurrentBranch = currentBranch;
        notifyPropertyChanged(BR.currentBranch);
    }

    @Bindable
    public boolean isNotificationAll() {
        return mNotificationAll;
    }

    private void setNotificationAll(boolean notificationAll) {
        mNotificationAll = notificationAll;
        notifyPropertyChanged(BR.notificationAll);
    }

    @Bindable
    public boolean isEmailAll() {
        return mEmailAll;
    }

    private void setEmailAll(boolean emailAll) {
        mEmailAll = emailAll;
        notifyPropertyChanged(BR.emailAll);
    }

    public void onClickGroup() {
        if (isNoneGroup()) {
            return;
        }
        final String[] groupNames = new String[mUser.getGroups().size()];
        for (int i = 0; i < groupNames.length; i++) {
            groupNames[i] = mUser.getGroups().get(i).getGroupName();
        }
        mDialogManager.dialogListSingleChoice(mContext.getString(R.string.group), groupNames,
                mCurrentGroupPosition, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view,
                            int positionType, CharSequence charSequence) {
                        mCurrentGroupPosition = positionType;
                        setCurrentGroup(groupNames[mCurrentGroupPosition]);
                        return true;
                    }
                });
    }

    public void onClickWorkspace() {
        if (isNoneBranch()) {
            return;
        }
        final String[] branchNames = new String[mUser.getBranches().size()];
        for (int i = 0; i < branchNames.length; i++) {
            branchNames[i] = mUser.getBranches().get(i).getBranchName();
        }
        mDialogManager.dialogListSingleChoice(mContext.getString(R.string.branch), branchNames,
                mCurrentBranchPosition, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view,
                            int positionType, CharSequence charSequence) {
                        mCurrentBranchPosition = positionType;
                        setCurrentBranch(branchNames[mCurrentBranchPosition]);
                        return true;
                    }
                });
    }

    public void onNotifiAllClicked() {
        mUser.getSetting().getNotificationSetting().setWorkspace(!isNotificationAll());
        mUser.getSetting().getNotificationSetting().setUserSpecialType(!isNotificationAll());
        mUser.getSetting().getNotificationSetting().setGroup(!isNotificationAll());
        setNotificationAll(!isNotificationAll());
        notifyPropertyChanged(BR.user);
    }

    public void onEmailAllClicked() {
        mUser.getSetting().getEmailSetting().setGroup(!isEmailAll());
        mUser.getSetting().getEmailSetting().setUserSpecialType(!isEmailAll());
        mUser.getSetting().getEmailSetting().setWorkspace(!isEmailAll());
        setEmailAll(!isEmailAll());
        notifyPropertyChanged(BR.user);
    }

    public void onNotifiUserClicked() {
        mUser.getSetting()
                .getNotificationSetting()
                .setUserSpecialType(
                        mUser.getSetting().getNotificationSetting().isUserSpecialType());
        setNotificationAll(
                mUser.getSetting().getNotificationSetting().isWorkspace() && mUser.getSetting()
                        .getNotificationSetting()
                        .isUserSpecialType() && mUser.getSetting()
                        .getNotificationSetting()
                        .isGroup());
        notifyPropertyChanged(BR.user);
    }

    public void onEmailUserClicked() {
        mUser.getSetting()
                .getEmailSetting()
                .setUserSpecialType(mUser.getSetting().getEmailSetting().isUserSpecialType());
        setEmailAll(mUser.getSetting().getEmailSetting().isWorkspace() && mUser.getSetting()
                .getEmailSetting()
                .isUserSpecialType() && mUser.getSetting().getEmailSetting().isGroup());
        notifyPropertyChanged(BR.user);
    }

    public void onNotifiGroupClicked() {
        mUser.getSetting()
                .getNotificationSetting()
                .setGroup(mUser.getSetting().getNotificationSetting().isGroup());
        setNotificationAll(
                mUser.getSetting().getNotificationSetting().isWorkspace() && mUser.getSetting()
                        .getNotificationSetting()
                        .isUserSpecialType() && mUser.getSetting()
                        .getNotificationSetting()
                        .isGroup());
        notifyPropertyChanged(BR.user);
    }

    public void onEmailGroupClicked() {
        mUser.getSetting()
                .getEmailSetting()
                .setGroup(mUser.getSetting().getEmailSetting().isGroup());
        setEmailAll(mUser.getSetting().getEmailSetting().isWorkspace() && mUser.getSetting()
                .getEmailSetting()
                .isUserSpecialType() && mUser.getSetting().getEmailSetting().isGroup());
        notifyPropertyChanged(BR.user);
    }

    public void onNotifiWorkspaceClicked() {
        mUser.getSetting()
                .getNotificationSetting()
                .setWorkspace(mUser.getSetting().getNotificationSetting().isWorkspace());
        setNotificationAll(
                mUser.getSetting().getNotificationSetting().isWorkspace() && mUser.getSetting()
                        .getNotificationSetting()
                        .isUserSpecialType() && mUser.getSetting()
                        .getNotificationSetting()
                        .isGroup());
        notifyPropertyChanged(BR.user);
    }

    public void onEmailWorkspaceClicked() {
        mUser.getSetting()
                .getEmailSetting()
                .setWorkspace(mUser.getSetting().getEmailSetting().isWorkspace());
        setEmailAll(mUser.getSetting().getEmailSetting().isWorkspace() && mUser.getSetting()
                .getEmailSetting()
                .isUserSpecialType() && mUser.getSetting().getEmailSetting().isGroup());
        notifyPropertyChanged(BR.user);
    }

    public void onUpdateClicked() {
        //TODO edit later
    }

    private boolean isNoneGroup() {
        return (mUser.getGroups() == null && mUser.getGroups().size() == 0);
    }

    private boolean isNoneBranch() {
        return (mUser.getBranches() == null && mUser.getBranches().size() == 0);
    }
}
