package com.framgia.wsm.data.model;

/**
 * Created by tri on 31/05/2017.
 */

public class Branch extends BaseModel {
    private String mBranchId;
    private String mBranchName;

    public Branch() {
    }

    public Branch(String branchId, String branchName) {
        mBranchId = branchId;
        mBranchName = branchName;
    }

    public String getBranchId() {
        return mBranchId;
    }

    public void setBranchId(String branchId) {
        mBranchId = branchId;
    }

    public String getBranchName() {
        return mBranchName;
    }

    public void setBranchName(String branchName) {
        mBranchName = branchName;
    }
}
