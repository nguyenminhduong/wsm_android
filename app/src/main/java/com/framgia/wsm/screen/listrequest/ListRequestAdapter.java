package com.framgia.wsm.screen.listrequest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.framgia.wsm.data.model.Request;
import com.framgia.wsm.screen.BaseRecyclerViewAdapter;
import com.framgia.wsm.utils.RequestTypeAnim;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 12/06/2017.
 */

public class ListRequestAdapter extends BaseRecyclerViewAdapter<RecyclerView.ViewHolder> {

    private OnRecyclerViewItemClickListener<Object> mItemClickListener;
    private List<Request> mRequests;
    private int mRequestType;

    ListRequestAdapter(@NonNull Context context, @RequestTypeAnim int requestType) {
        super(context);
        mRequestType = requestType;
        mRequests = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mRequests.size();
    }

    void setItemClickListener(OnRecyclerViewItemClickListener<Object> itemClickListener) {
        mItemClickListener = itemClickListener;
    }
}
