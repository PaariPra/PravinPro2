package com.chettapps.invitationmaker.photomaker.adapter;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chettapps.invitationmaker.photomaker.listener.OnLoadMoreListener;


public class RecyclerViewLoadMoreScroll extends RecyclerView.OnScrollListener {
    private boolean isLoading;
    private int lastVisibleItem;
    private RecyclerView.LayoutManager mLayoutManager;
    private OnLoadMoreListener mOnLoadMoreListener;
    private int totalItemCount;
    private int visibleThreshold;

    public void setLoaded() {
        this.isLoading = false;
    }

    public boolean getLoaded() {
        return this.isLoading;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.mOnLoadMoreListener = onLoadMoreListener;
    }

    public RecyclerViewLoadMoreScroll(LinearLayoutManager linearLayoutManager) {
        this.visibleThreshold = 5;
        this.mLayoutManager = linearLayoutManager;
    }

    public RecyclerViewLoadMoreScroll(GridLayoutManager gridLayoutManager) {
        this.visibleThreshold = 5;
        this.mLayoutManager = gridLayoutManager;
        this.visibleThreshold = gridLayoutManager.getSpanCount() * 5;
    }

    public RecyclerViewLoadMoreScroll(StaggeredGridLayoutManager staggeredGridLayoutManager) {
        this.visibleThreshold = 5;
        this.mLayoutManager = staggeredGridLayoutManager;
        this.visibleThreshold = staggeredGridLayoutManager.getSpanCount() * 5;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
    public void onScrolled(RecyclerView recyclerView, int i, int i2) {
        super.onScrolled(recyclerView, i, i2);
        if (i2 > 0) {
            this.totalItemCount = this.mLayoutManager.getItemCount();
            RecyclerView.LayoutManager layoutManager = this.mLayoutManager;
            if (layoutManager instanceof StaggeredGridLayoutManager) {
                this.lastVisibleItem = getLastVisibleItem(((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(null));
            } else if (layoutManager instanceof GridLayoutManager) {
                this.lastVisibleItem = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof LinearLayoutManager) {
                this.lastVisibleItem = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            }
            if (!this.isLoading && this.totalItemCount <= this.lastVisibleItem + this.visibleThreshold) {
                OnLoadMoreListener onLoadMoreListener = this.mOnLoadMoreListener;
                if (onLoadMoreListener != null) {
                    onLoadMoreListener.onLoadMore();
                }
                this.isLoading = true;
            }
        }
    }

    public int getLastVisibleItem(int[] iArr) {
        int i = 0;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            if (i2 == 0) {
                i = iArr[i2];
            } else if (iArr[i2] > i) {
                i = iArr[i2];
            }
        }
        return i;
    }
}
