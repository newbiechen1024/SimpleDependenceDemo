package com.example.newbiechen.simpledependencedemo.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewStub;

import com.example.newbiechen.simpledependencedemo.App;
import com.example.newbiechen.simpledependencedemo.R;
import com.example.newbiechen.simpledependencedemo.model.bean.ArticleBean;
import com.example.newbiechen.simpledependencedemo.presenter.RecommendPresenter;
import com.example.newbiechen.simpledependencedemo.presenter.contract.CommonContract;
import com.example.newbiechen.simpledependencedemo.ui.adapter.RecommendAdapter;
import com.example.newbiechen.simpledependencedemo.ui.base.BaseFragment;

import java.util.List;

/**
 * Created by newbiechen on 17-3-31.
 */

public class RecommendFragment extends BaseFragment implements CommonContract.View<CommonContract.Presenter> {
    private static final String TAG = "RecommendFragment";

    /*ui statement*/
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private ViewStub mEmptyView;

    /*common statement*/
    private RecommendAdapter mAdapter;
    private CommonContract.Presenter mPresenter;
    private final Handler mHandler = new Handler();

    /************************************init area*********************************************/
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
        setAdapter();
    }

    private void setAdapter(){
        mAdapter = new RecommendAdapter();
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initClick() {
        super.initClick();
        mRefreshLayout.setOnRefreshListener(
                ()->{
                    mPresenter.refreshArticle();
                }
        );
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        super.processLogic(savedInstanceState);

        new RecommendPresenter(this).subscribe();
    }

    /*******************************rewrite area**********************************************/

    @Override
    public String getName() {
        return App.getContext()
                .getString(R.string.nb_fragment_title_recommend);
    }

    @Override
    public void setPresenter(CommonContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void startRefreshAnim() {
        mHandler.post(()-> mRefreshLayout.setRefreshing(true));
    }

    @Override
    public void finishRefreshAnim() {
        if (mRefreshLayout.isRefreshing()){
            mHandler.post(()-> mRefreshLayout.setRefreshing(false));
        }
    }

    @Override
    public void finishRefresh(List<ArticleBean> articleList){
        mAdapter.refreshItems(articleList);
    }
    /***************************lifecycle area***************************************/
    @Override
    public void onPause() {
        super.onPause();
        mPresenter.saveArticleToDB(mAdapter.getItems());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
    }
}
