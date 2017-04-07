package com.example.newbiechen.simpledependencedemo.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

/**
 * Created by newbiechen on 17-4-7.
 */

public class LinearRefreshRecyclerView extends RefreshRecyclerView {
    public LinearRefreshRecyclerView(Context context) {
        super(context);
    }

    public LinearRefreshRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LinearRefreshRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected boolean checkLayoutIsRight() {
        return getLayoutManager() instanceof LinearLayoutManager ? true : false;
    }

    @Override
    protected int getLastVisibleItem() {
        LinearLayoutManager manager = (LinearLayoutManager) getLayoutManager();
        int lastPos = manager.findLastVisibleItemPosition();
        return lastPos;
    }
}
