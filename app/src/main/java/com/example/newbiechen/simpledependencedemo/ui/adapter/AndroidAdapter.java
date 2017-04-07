package com.example.newbiechen.simpledependencedemo.ui.adapter;

import android.content.Context;
import android.view.View;

import com.example.newbiechen.simpledependencedemo.model.bean.ArticleBean;
import com.example.newbiechen.simpledependencedemo.ui.adapter.view.AndroidView;
import com.example.newbiechen.simpledependencedemo.ui.base.BaseListAdapter;

/**
 * Created by newbiechen on 17-4-7.
 */

public class AndroidAdapter extends BaseListAdapter<ArticleBean> {
    @Override
    protected View createView(Context context, int viewType) {
        return new AndroidView(context);
    }
}
