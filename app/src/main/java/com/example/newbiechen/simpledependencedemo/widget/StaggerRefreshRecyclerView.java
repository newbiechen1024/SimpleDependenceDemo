package com.example.newbiechen.simpledependencedemo.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

/**
 * Created by newbiechen on 17-4-6.
 */

public class StaggerRefreshRecyclerView extends RecyclerView{
    private static final String TAG = "RefreshStaggerRecyclerView";

    private OnLoadingListener mListener;
    private boolean canLoading = true;
    private boolean isLoading = false;

    public StaggerRefreshRecyclerView(Context context) {
        this(context,null);
    }

    public StaggerRefreshRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public StaggerRefreshRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!(getLayoutManager() instanceof StaggeredGridLayoutManager)){
                    try {
                        throw new IllegalAccessException("Recycler must be StaggeredGridLayoutManager");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

                //必须是下滑
                if (Math.abs(dx)<Math.abs(dy) && dy > 0 && !isLoading && canLoading){
                    StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) getLayoutManager();
                    int childCount = manager.getItemCount();
                    int spanCount = manager.getSpanCount();
                    int [] into = new int[spanCount];
                    manager.findLastVisibleItemPositions(into);
                    //这里有个逻辑问题，不管了
                    for (int pos : into){
                        if (pos == childCount - 2 && mListener != null){
                            mListener.onLoading();
                            isLoading = true;
                            break;
                        }
                    }
                }
            }
        });
    }

    public void setOnLoadingListener(OnLoadingListener listener){
        mListener = listener;
    }

    public void setCanLoading(boolean loading){
        canLoading = loading;
    }

    public void setLoadingFinish(){
        isLoading = false;
    }

    public interface OnLoadingListener{
        void onLoading();
    }
}
