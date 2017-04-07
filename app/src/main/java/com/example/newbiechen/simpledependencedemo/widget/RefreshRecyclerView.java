package com.example.newbiechen.simpledependencedemo.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

/**
 * Created by newbiechen on 17-4-7.
 */

public abstract class RefreshRecyclerView extends RecyclerView{
    private static final String TAG = "RefreshStaggerRecyclerView";

    private OnLoadingListener mListener;
    private boolean canLoading = true;
    private boolean isLoading = false;

    public RefreshRecyclerView(Context context) {
        this(context,null);
    }

    public RefreshRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RefreshRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    protected abstract boolean checkLayoutIsRight();
    protected abstract int getLastVisibleItem();

    private void init(){
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isRight = checkLayoutIsRight();
                if (!isRight){
                    try {
                        throw new IllegalAccessException("LayoutManager Error,Please checkout your LayoutManger");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

                //必须是下滑
                if (Math.abs(dx)<Math.abs(dy) && dy > 0
                        && !isLoading && canLoading){
                    int lastItem = getLastVisibleItem();
                    //这里有个问题，当未显示满一行的时候，会一直加载的问题。
                    if (lastItem == getLayoutManager().getItemCount() - 2
                            && mListener != null){
                        mListener.onLoading();
                        isLoading = true;
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
