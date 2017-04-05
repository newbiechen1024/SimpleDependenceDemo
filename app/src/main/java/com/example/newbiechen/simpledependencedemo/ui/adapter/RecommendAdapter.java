package com.example.newbiechen.simpledependencedemo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.newbiechen.simpledependencedemo.model.bean.ArticleBean;
import com.example.newbiechen.simpledependencedemo.ui.adapter.view.CardView;
import com.example.newbiechen.simpledependencedemo.ui.base.BaseListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by newbiechen on 17-4-3.
 */

public class RecommendAdapter extends BaseListAdapter<ArticleBean>{
    @Override
    protected View createView(Context context, int viewType) {
        CardView cardView = new CardView(context);
        cardView.setTagVisible(true);
        return cardView;
    }
}
