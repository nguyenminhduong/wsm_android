package com.framgia.wsm.screen.listrequest;

import android.databinding.BaseObservable;
import com.framgia.wsm.data.model.Request;
import com.framgia.wsm.screen.BaseRecyclerViewAdapter;

/**
 * Created by ASUS on 13/06/2017.
 */

public class ItemListRequestViewModel extends BaseObservable {
    private final Request mRequest;
    private final BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener<Request>
            mItemClickListener;

    public ItemListRequestViewModel(Request request,
            BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener<Request> itemClickListener) {
        mRequest = request;
        mItemClickListener = itemClickListener;
    }

    public void onItemClicked() {
        if (mItemClickListener == null) {
            return;
        }
        mItemClickListener.onItemRecyclerViewClick(mRequest);
    }
}
