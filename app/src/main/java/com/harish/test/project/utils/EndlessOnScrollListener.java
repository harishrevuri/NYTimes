package com.harish.test.project.utils;

import android.support.v7.widget.RecyclerView;

public abstract class EndlessOnScrollListener extends RecyclerView.OnScrollListener {

    public static String TAG = EndlessOnScrollListener.class.getSimpleName();

    //private LinearLayoutManager layoutManager;

    public EndlessOnScrollListener() {
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (!recyclerView.canScrollVertically(1)) {
            onScrolledToEnd();
        }
    }

    public abstract void onScrolledToEnd();
}