package com.example.newbiechen.simpledependencedemo.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewStub;

import com.example.newbiechen.simpledependencedemo.App;
import com.example.newbiechen.simpledependencedemo.R;
import com.example.newbiechen.simpledependencedemo.model.bean.ArticleBean;
import com.example.newbiechen.simpledependencedemo.presenter.AndroidPresenter;
import com.example.newbiechen.simpledependencedemo.presenter.contract.AndroidContract;
import com.example.newbiechen.simpledependencedemo.presenter.contract.CommonContract;
import com.example.newbiechen.simpledependencedemo.ui.adapter.AndroidAdapter;
import com.example.newbiechen.simpledependencedemo.ui.base.BaseFragment;
import com.example.newbiechen.simpledependencedemo.widget.RefreshRecyclerView;

import java.util.List;

/**
 * Created by newbiechen on 17-4-3.
 */

public class AndroidFragment extends BaseFragment implements AndroidContract.View{
    private static final String TAG = "AndroidFragment";

    private SwipeRefreshLayout mRefreshLayout;
    private RefreshRecyclerView mRecyclerView;
    private ViewStub mEmptyView;

    private final Handler mHandler = new Handler();
    private AndroidAdapter mAdapter;
    private AndroidContract.Presenter mPresenter;
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
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        setAdapter();
    }

    private void setAdapter(){
        mAdapter = new AndroidAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        //默认禁止加载
        mRecyclerView.setCanLoading(false);
    }

    @Override
    protected void initClick() {
        super.initClick();
        mRefreshLayout.setOnRefreshListener(
                () -> mPresenter.refreshArticle());

        mRecyclerView.setOnLoadingListener(
                () -> mPresenter.loadArticle());

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        super.processLogic(savedInstanceState);
        new AndroidPresenter(this).subscribe();
    }

    @Override
    public String getName() {
        return App.getContext().
                getString(R.string.nb_fragment_title_android);
    }

    @Override
    public void startRefreshAnim() {
        mHandler.post(
                ()->mRefreshLayout.setRefreshing(true)
        );
    }

    @Override
    public void finishRefreshAnim() {
        mHandler.post(
                ()->{
                    if (mRefreshLayout.isRefreshing()){
                        mRefreshLayout.setRefreshing(false);
                        Log.d(TAG, "finishRefreshAnim: ");
                    }
                }
        );
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
    public void setPresenter(AndroidContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.saveArticleToDB(mAdapter.getItems());
    }
}
