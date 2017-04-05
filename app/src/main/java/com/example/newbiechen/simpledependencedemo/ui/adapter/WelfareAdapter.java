package com.example.newbiechen.simpledependencedemo.ui.adapter;

import android.content.Context;
import android.view.View;

import com.example.newbiechen.simpledependencedemo.model.bean.ArticleBean;
import com.example.newbiechen.simpledependencedemo.ui.adapter.view.WelfareView;
import com.example.newbiechen.simpledependencedemo.ui.base.BaseListAdapter;

/**
 * Created by newbiechen on 17-4-5.
 */

public class WelfareAdapter extends BaseListAdapter<ArticleBean> {
    @Override
    protected View createView(Context context, int viewType) {
        return new WelfareView(context);
    }
}
