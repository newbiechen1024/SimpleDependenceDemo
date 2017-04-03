package com.example.newbiechen.simpledependencedemo.ui.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by newbiechen on 17-3-21.
 */

public abstract class BaseListAdapter <T> extends RecyclerView.Adapter {
    private static final String TAG = "BaseListAdapter";

    private final List<T> mList = new ArrayList<T>();

    private OnItemClickListener mListener;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = createView(parent.getContext(),viewType);
        //初始化
        RecyclerView.ViewHolder holder = new RecyclerView.ViewHolder(view) {};
        //设置点击事件
        holder.itemView.setOnClickListener((v)->{
            if (mListener != null){
                int pos = holder.getAdapterPosition();
                if (pos != Adapter.NO_SELECTION){
                    mListener.onItemClick(v,pos);
                }
            }
        });
        return holder;
    }

    protected abstract View createView(Context context, int viewType);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (! (holder.itemView instanceof IAdapter))
            return;
        IAdapter<T> view = (IAdapter<T>)holder.itemView;
        view.onBind(mList.get(position),position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setOnItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    public void addItem(T value){
        mList.add(value);
        notifyDataSetChanged();
    }

    public void addItems(List<T> values){
        mList.addAll(values);
        notifyDataSetChanged();
    }

    public T getItem(int position){
        return mList.get(position);
    }

    public List<T> getItems(){
        return Collections.unmodifiableList(mList);
    }

    public void refreshItems(List<T> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int pos);
    }
}
