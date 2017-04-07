package com.example.newbiechen.simpledependencedemo.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewStub;

import com.example.newbiechen.simpledependencedemo.App;
import com.example.newbiechen.simpledependencedemo.R;
import com.example.newbiechen.simpledependencedemo.model.bean.ArticleBean;
import com.example.newbiechen.simpledependencedemo.presenter.WelfarePresenter;
import com.example.newbiechen.simpledependencedemo.presenter.contract.WelfareContract;
import com.example.newbiechen.simpledependencedemo.ui.adapter.WelfareAdapter;
import com.example.newbiechen.simpledependencedemo.ui.base.BaseFragment;
import com.example.newbiechen.simpledependencedemo.widget.StaggerRefreshRecyclerView;

import java.util.List;

/**
 * Created by newbiechen on 17-4-3.
 */

public class WelfareFragment extends BaseFragment implements WelfareContract.View{
    private static final String TAG = "WelfareFragment";
    private SwipeRefreshLayout mRefreshLayout;
    private StaggerRefreshRecyclerView mRecyclerView;
    private ViewStub mEmptyView;

    private final Handler mHandler = new Handler();
    private WelfareAdapter mAdapter;
    private WelfareContract.Presenter mPresenter;
    @Override
    protected int createView() {
        return R.layout.fragment_welfare;
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
        setAdapter();
    }

    private void setAdapter(){
        mAdapter = new WelfareAdapter();
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        //不允许自动加载
        mRecyclerView.setCanLoading(false);
    }

    @Override
    protected void initClick() {
        super.initClick();
        mRefreshLayout.setOnRefreshListener(
                ()->mPresenter.refreshArticle());

        mRecyclerView.setOnLoadingListener(
                ()->mPresenter.loadArticle());
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        super.processLogic(savedInstanceState);
        new WelfarePresenter(this).subscribe();
    }

    @Override
    public String getName() {
        return App.getContext().
                getString(R.string.nb_fragment_title_welfare);
    }

    @Override
    public void startRefreshAnim() {
        mHandler.post(()->mRefreshLayout.setRefreshing(true));
    }

    @Override
    public void finishRefreshAnim() {
        mHandler.post(()->{
                    if (mRefreshLayout.isRefreshing()){
                        mRefreshLayout.setRefreshing(false);
                    }
                });
    }

    @Override
    public void finishRefresh(List<ArticleBean> articleList) {
        mAdapter.refreshItems(articleList);
    }

    @Override
    public void finishLoad(List<ArticleBean> articles) {
        mAdapter.addItems(articles);
        mRecyclerView.setLoadingFinish();
    }

    @Override
    public void canLoading(boolean loading) {
        mRecyclerView.setCanLoading(loading);
    }

    @Override
    public void setPresenter(WelfareContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.saveArticleToDB(mAdapter.getItems());
    }
}
