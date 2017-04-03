package com.example.newbiechen.simpledependencedemo.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.ViewStub;

import com.example.newbiechen.simpledependencedemo.App;
import com.example.newbiechen.simpledependencedemo.R;
import com.example.newbiechen.simpledependencedemo.ui.base.BaseFragment;

/**
 * Created by newbiechen on 17-4-3.
 */

public class AndroidFragment extends BaseFragment {
    private static final String TAG = "AndroidFragment";

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private ViewStub mEmptyView;

    @Override
    protected int createView() {
        return R.layout.fragment_android;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mRefreshLayout = getViewById(R.id.swipe_refresh);
        mRecyclerView = getViewById(R.id.recycler_view);
        mEmptyView = getViewById(R.id.empty_view);

    }



    @Override
    public String getName() {
        return App.getContext().
                getString(R.string.nb_fragment_title_android);
    }
}
