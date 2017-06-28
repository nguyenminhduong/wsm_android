package com.framgia.wsm.screen.profile.branch;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.Branch;
import com.framgia.wsm.databinding.ItemListBranchBinding;
import com.framgia.wsm.screen.BaseRecyclerViewAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 28/06/2017.
 */

public class BranchAdapter extends BaseRecyclerViewAdapter<BranchAdapter.ItemBranchViewHolder> {

    private final List<Branch> mBranches;

    public BranchAdapter(@NonNull Context context) {
        super(context);
        mBranches = new ArrayList<>();
    }

    @Override
    public ItemBranchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemListBranchBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_list_branch, parent, false);
        return new ItemBranchViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ItemBranchViewHolder holder, int position) {
        holder.bind(mBranches.get(position));
    }

    @Override
    public int getItemCount() {
        return mBranches.size();
    }

    public void updateDataBranch(List<Branch> branches) {
        if (branches == null) {
            return;
        }
        mBranches.clear();
        mBranches.addAll(branches);
        notifyDataSetChanged();
    }

    static class ItemBranchViewHolder extends RecyclerView.ViewHolder {

        private final ItemListBranchBinding mBinding;

        ItemBranchViewHolder(ItemListBranchBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        void bind(Branch branch) {
            mBinding.setViewModel(new ItemBranchViewModel(branch));
            mBinding.executePendingBindings();
        }
    }
}
