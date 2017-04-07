package com.example.newbiechen.simpledependencedemo.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

/**
 * Created by newbiechen on 17-4-6.
 */

public class StaggerRefreshRecyclerView extends RefreshRecyclerView{
    public StaggerRefreshRecyclerView(Context context) {
        super(context);
    }

    public StaggerRefreshRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StaggerRefreshRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected boolean checkLayoutIsRight() {
        if (getLayoutManager() instanceof StaggeredGridLayoutManager){
            return  true;
        }
        return false;
    }

    @Override
    protected int getLastVisibleItem() {
        StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) getLayoutManager();
        int spanCount = manager.getSpanCount();
        int [] into = new int[spanCount];
        manager.findLastVisibleItemPositions(into);
        int lastItemPos = 0;
        for (int last : into){
            if (last > lastItemPos){
                lastItemPos = last;
            }
        }
        return lastItemPos;
    }
}
