package com.diogorborges.themoviedbsample.presentation.main;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class EndlessScrollListener extends RecyclerView.OnScrollListener {
    private LinearLayoutManager linearLayoutManager;
    private ScrollToBottomListener listener;

    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 3;
    private int firstVisibleItem, visibleItemCount, totalItemCount;

    EndlessScrollListener(LinearLayoutManager linearLayoutManager,
                          ScrollToBottomListener listener) {
        this.linearLayoutManager = linearLayoutManager;
        this.listener = listener;
    }

    void onRefresh() {
        previousTotal = 0;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = linearLayoutManager.getItemCount();
        firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold)) {
            this.listener.onScrollToBottom();
            loading = true;
        }
    }

    interface ScrollToBottomListener {

        void onScrollToBottom();

    }
}
