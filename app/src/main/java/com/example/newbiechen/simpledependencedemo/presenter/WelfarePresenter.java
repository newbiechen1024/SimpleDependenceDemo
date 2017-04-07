package com.example.newbiechen.simpledependencedemo.presenter;

import com.example.newbiechen.simpledependencedemo.model.bean.ArticleBean;
import com.example.newbiechen.simpledependencedemo.model.data.DBRepository;
import com.example.newbiechen.simpledependencedemo.model.net.NetObserver;
import com.example.newbiechen.simpledependencedemo.model.net.NetWorkRepository;
import com.example.newbiechen.simpledependencedemo.presenter.contract.WelfareContract;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by newbiechen on 17-4-6.
 */

public class WelfarePresenter implements WelfareContract.Presenter {
    private WelfareContract.View mView;
    private int mPage = 0;
    public WelfarePresenter(WelfareContract.View view){
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        mView.startRefreshAnim();
        //从数据库中加载
        refreshFromDB();
        //从网络中加载
        refreshFromNet();
    }

    private void refreshFromDB(){
        DBRepository.getInstance()
                .getWelfare()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        articleBeans-> mView.finishRefresh(articleBeans)
                );
    }

    private void refreshFromNet(){
        mPage = 0;
        NetWorkRepository.getInstance()
                .getWelfareArticle(mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetObserver<List<ArticleBean>>() {
                    @Override
                    public void onNext(List<ArticleBean> value) {
                        if (value != null){
                            mView.finishRefresh(value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.finishRefreshAnim();
                    }

                    @Override
                    public void onComplete() {
                        mView.finishRefreshAnim();
                        //允许自动加载
                        mView.canLoading(true);

                        mPage += 1;
                    }
                });
    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void refreshArticle() {
        refreshFromNet();
    }

    @Override
    public void saveArticleToDB(List<ArticleBean> articleList) {
        DBRepository.getInstance()
                .updateWelfare(articleList);
    }

    @Override
    public void loadArticle() {
        NetWorkRepository.getInstance()
                .getWelfareArticle(mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetObserver<List<ArticleBean>>() {
                    @Override
                    public void onNext(List<ArticleBean> value) {
                        if (value != null){
                            mView.finishLoad(value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.finishRefreshAnim();
                    }

                    @Override
                    public void onComplete() {
                        mView.finishRefreshAnim();
                        mPage += 1;
                    }
                });
    }
}
