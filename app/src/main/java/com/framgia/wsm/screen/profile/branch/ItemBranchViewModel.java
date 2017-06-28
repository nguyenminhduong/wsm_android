package com.framgia.wsm.screen.profile.branch;

import com.framgia.wsm.data.model.Branch;

/**
 * Created by ASUS on 28/06/2017.
 */

public class ItemBranchViewModel {
    private final Branch mBranch;

    public ItemBranchViewModel(Branch branch) {
        mBranch = branch;
    }

    public String getBranchName() {
        if (mBranch != null) {
            return mBranch.getBranchName();
        }
        return "";
    }
}
