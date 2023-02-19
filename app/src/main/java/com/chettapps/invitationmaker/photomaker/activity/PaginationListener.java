package com.chettapps.invitationmaker.photomaker.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public abstract class PaginationListener extends RecyclerView.OnScrollListener {
    private static final int PAGE_SIZE = 10;
    public static final int PAGE_START = 1;
    private LinearLayoutManager layoutManager;

    public abstract boolean isLastPage();

    public abstract boolean isLoading();

    protected abstract void loadMoreItems();

    public PaginationListener(LinearLayoutManager linearLayoutManager) {
        this.layoutManager = linearLayoutManager;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
    public void onScrolled(RecyclerView recyclerView, int i, int i2) {
        super.onScrolled(recyclerView, i, i2);
        int childCount = this.layoutManager.getChildCount();
        int itemCount = this.layoutManager.getItemCount();
        int findFirstVisibleItemPosition = this.layoutManager.findFirstVisibleItemPosition();
        if (!isLoading() && !isLastPage() && childCount + findFirstVisibleItemPosition >= itemCount && findFirstVisibleItemPosition >= 0 && itemCount >= 10) {
            loadMoreItems();
        }
    }
}
