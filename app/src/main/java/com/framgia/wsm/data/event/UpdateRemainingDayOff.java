package com.framgia.wsm.data.event;

import com.framgia.wsm.data.model.User;

/**
 * Created by ASUS on 24/08/2017.
 */

public class UpdateRemainingDayOff extends BaseEvent {
    private User mUser;

    public UpdateRemainingDayOff(User user) {
        mUser = user;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }
}
