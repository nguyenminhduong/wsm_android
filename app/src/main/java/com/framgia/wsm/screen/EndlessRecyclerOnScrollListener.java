package com.framgia.wsm.screen;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by minhd on 7/25/2017.
 */

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    private final LinearLayoutManager mLayoutManager;

    protected EndlessRecyclerOnScrollListener(LinearLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }

    protected abstract void onLoadMore();

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        int threshold = 5;
        int visibleItemCount = mLayoutManager.getChildCount();
        int totalItemCount = mLayoutManager.getItemCount();
        int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

        if (newState == RecyclerView.SCROLL_STATE_IDLE
                && visibleItemCount < totalItemCount
                && firstVisibleItemPosition + visibleItemCount + threshold >= totalItemCount) {
            onLoadMore();
        }
    }
}

