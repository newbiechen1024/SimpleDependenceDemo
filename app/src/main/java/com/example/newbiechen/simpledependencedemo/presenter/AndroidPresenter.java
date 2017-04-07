package com.example.newbiechen.simpledependencedemo.presenter;

import android.util.Log;

import com.example.newbiechen.simpledependencedemo.model.bean.ArticleBean;
import com.example.newbiechen.simpledependencedemo.model.data.DBRepository;
import com.example.newbiechen.simpledependencedemo.model.net.NetObserver;
import com.example.newbiechen.simpledependencedemo.model.net.NetWorkRepository;
import com.example.newbiechen.simpledependencedemo.presenter.contract.AndroidContract;
import com.example.newbiechen.simpledependencedemo.presenter.contract.CommonContract;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by newbiechen on 17-4-7.
 */

public class AndroidPresenter implements AndroidContract.Presenter {

    private static final String TAG = "AndroidPresenter";
    private AndroidContract.View mView;

    private int mPage = 0;
    public AndroidPresenter(AndroidContract.View view){
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        mView.startRefreshAnim();
        refreshFromDB();
        refreshFromNet();
    }

    private void refreshFromDB(){
        DBRepository.getInstance()
                .getAndroid()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        articleBeans-> mView.finishRefresh(articleBeans)
                );
    }

    private void refreshFromNet(){
        mPage = 0;
        NetWorkRepository.getInstance()
                .getAndroidArticle(mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetObserver<List<ArticleBean>>() {
                    @Override
                    public void onNext(List<ArticleBean> value) {
                        mView.finishRefresh(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.finishRefreshAnim();
                    }

                    @Override
                    public void onComplete() {
                        mView.finishRefreshAnim();
                        mView.canLoading(true);
                        mPage = 1;
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
                .updateAndroid(articleList);
    }

    @Override
    public void loadArticle() {
        NetWorkRepository.getInstance()
                .getAndroidArticle(mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetObserver<List<ArticleBean>>() {
                    @Override
                    public void onNext(List<ArticleBean> value) {
                        mView.finishLoad(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        mPage += 1;
                    }
                });
    }
}
