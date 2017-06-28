package com.framgia.wsm.screen.profile.group;

import com.framgia.wsm.data.model.Group;

/**
 * Created by ASUS on 28/06/2017.
 */

public class ItemGroupViewModel {
    private final Group mGroup;

    public ItemGroupViewModel(Group group) {
        mGroup = group;
    }

    public String getGroupName() {
        if (mGroup != null) {
            return mGroup.getGroupName();
        }
        return "";
    }
}
