package com.example.newbiechen.simpledependencedemo.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.ViewStub;

import com.example.newbiechen.simpledependencedemo.App;
import com.example.newbiechen.simpledependencedemo.R;
import com.example.newbiechen.simpledependencedemo.presenter.contract.RecommendContract;
import com.example.newbiechen.simpledependencedemo.ui.base.BaseFragment;

/**
 * Created by newbiechen on 17-3-31.
 */

public class RecommendFragment extends BaseFragment implements RecommendContract.View {
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private ViewStub mEmptyView;

    private RecommendContract.Presenter mPresenter;
    private final Handler mHandler = new Handler();
    @Override
    protected int createView() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mRefreshLayout = getViewById(R.id.swipe_refresh);
        mRecyclerView = getViewById(R.id.recycler_view);
        mEmptyView = getViewById(R.id.empty_view);
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
    }

    @Override
    protected void initClick() {
        super.initClick();

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        super.processLogic(savedInstanceState);
    }

    @Override
    public String getName() {
        return App.getContext()
                .getString(R.string.nb_fragment_title_recommend);
    }

    @Override
    public void setPresenter(RecommendContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void startRefreshAnim() {
        mHandler.post(()-> mRefreshLayout.setRefreshing(true));
    }

    @Override
    public void finishRefreshAnim() {
        if (mRefreshLayout.isRefreshing()){
            mRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void finishRefresh() {

    }


}
