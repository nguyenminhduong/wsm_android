package com.framgia.wsm.data.model;

/**
 * Created by ths on 04/07/2017.
 */

public class Setting {

    private boolean mNotifiAll;
    private boolean mEmailAll;
    private boolean mNotifiList;
    private boolean mEmailList;
    private boolean mNotifiGroup;
    private boolean mEmailGroup;
    private boolean mNotifiWorkspace;
    private boolean mEmailWorkspace;

    public boolean isNotifiAll() {
        return mNotifiAll;
    }

    public void setNotifiAll(boolean notifiAll) {
        mNotifiAll = notifiAll;
    }

    public boolean isEmailAll() {
        return mEmailAll;
    }

    public void setEmailAll(boolean emailAll) {
        mEmailAll = emailAll;
    }

    public boolean isNotifiList() {
        return mNotifiList;
    }

    public void setNotifiList(boolean notifiList) {
        mNotifiList = notifiList;
    }

    public boolean isEmailList() {
        return mEmailList;
    }

    public void setEmailList(boolean emailList) {
        mEmailList = emailList;
    }

    public boolean isNotifiGroup() {
        return mNotifiGroup;
    }

    public void setNotifiGroup(boolean notifiGroup) {
        mNotifiGroup = notifiGroup;
    }

    public boolean isEmailGroup() {
        return mEmailGroup;
    }

    public void setEmailGroup(boolean emailGroup) {
        mEmailGroup = emailGroup;
    }

    public boolean isNotifiWorkspace() {
        return mNotifiWorkspace;
    }

    public void setNotifiWorkspace(boolean notifiWorkspace) {
        mNotifiWorkspace = notifiWorkspace;
    }

    public boolean isEmailWorkspace() {
        return mEmailWorkspace;
    }

    public void setEmailWorkspace(boolean emailWorkspace) {
        mEmailWorkspace = emailWorkspace;
    }
}
