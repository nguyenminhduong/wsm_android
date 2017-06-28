package com.framgia.wsm.screen.profile.group;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.Group;
import com.framgia.wsm.databinding.ItemListGroupBinding;
import com.framgia.wsm.screen.BaseRecyclerViewAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 28/06/2017.
 */

public class GroupAdapter extends BaseRecyclerViewAdapter<GroupAdapter.ItemGroupViewHolder> {

    private final List<Group> mGroups;

    public GroupAdapter(@NonNull Context context) {
        super(context);
        mGroups = new ArrayList<>();
    }

    @Override
    public GroupAdapter.ItemGroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemListGroupBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_list_group, parent, false);
        return new GroupAdapter.ItemGroupViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(GroupAdapter.ItemGroupViewHolder holder, int position) {
        holder.bind(mGroups.get(position));
    }

    @Override
    public int getItemCount() {
        return mGroups.size();
    }

    public void updateDataGroup(List<Group> groups) {
        if (groups == null) {
            return;
        }
        mGroups.clear();
        mGroups.addAll(groups);
        notifyDataSetChanged();
    }

    static class ItemGroupViewHolder extends RecyclerView.ViewHolder {

        private final ItemListGroupBinding mBinding;

        ItemGroupViewHolder(ItemListGroupBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        void bind(Group group) {
            mBinding.setViewModel(new ItemGroupViewModel(group));
            mBinding.executePendingBindings();
        }
    }
}
