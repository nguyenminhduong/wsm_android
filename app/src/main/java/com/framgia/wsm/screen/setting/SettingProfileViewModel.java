package com.framgia.wsm.screen.setting;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;
import android.view.View;
import com.android.databinding.library.baseAdapters.BR;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.Setting;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.widget.dialog.DialogManager;
import com.fstyle.library.MaterialDialog;

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
    private Setting mSetting;

    SettingProfileViewModel(Context context, SettingProfileContract.Presenter presenter,
            DialogManager dialogManager) {
        mContext = context;
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mPresenter.getUser();
        mDialogManager = dialogManager;
        mSetting = new Setting();
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
        if (user == null) {
            return;
        }
        mUser = user;
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
    public Setting getSetting() {
        return mSetting;
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
        mSetting.setNotifiGroup(mSetting.isNotifiAll());
        mSetting.setNotifiList(mSetting.isNotifiAll());
        mSetting.setNotifiWorkspace(mSetting.isNotifiAll());
        notifyPropertyChanged(BR.setting);
    }

    public void onEmailAllClicked() {
        mSetting.setEmailGroup(mSetting.isEmailAll());
        mSetting.setEmailList(mSetting.isEmailAll());
        mSetting.setEmailWorkspace(mSetting.isEmailAll());
        notifyPropertyChanged(BR.setting);
    }

    public void onNotifiListClicked() {
        mSetting.setNotifiAll((mSetting.isNotifiWorkspace()
                && mSetting.isNotifiGroup()
                && mSetting.isNotifiList()));
        notifyPropertyChanged(BR.setting);
    }

    public void onEmailListClicked() {
        mSetting.setEmailAll(
                (mSetting.isEmailGroup() && mSetting.isEmailList() && mSetting.isEmailWorkspace()));
        notifyPropertyChanged(BR.setting);
    }

    public void onNotifiGroupClicked() {
        mSetting.setNotifiAll((mSetting.isNotifiWorkspace()
                && mSetting.isNotifiGroup()
                && mSetting.isNotifiList()));
        notifyPropertyChanged(BR.setting);
    }

    public void onEmailGroupClicked() {
        mSetting.setEmailAll(
                (mSetting.isEmailGroup() && mSetting.isEmailList() && mSetting.isEmailWorkspace()));
        notifyPropertyChanged(BR.setting);
    }

    public void onNotifiWorkspaceClicked() {
        mSetting.setNotifiAll((mSetting.isNotifiWorkspace()
                && mSetting.isNotifiGroup()
                && mSetting.isNotifiList()));
        notifyPropertyChanged(BR.setting);
    }

    public void onEmailWorkspaceClicked() {
        mSetting.setEmailAll(
                (mSetting.isEmailGroup() && mSetting.isEmailList() && mSetting.isEmailWorkspace()));
        notifyPropertyChanged(BR.setting);
    }

    private boolean isNoneGroup() {
        return (mUser.getGroups() == null && mUser.getGroups().size() == 0);
    }

    private boolean isNoneBranch() {
        return (mUser.getBranches() == null && mUser.getBranches().size() == 0);
    }
}
