package com.example.newbiechen.simpledependencedemo.presenter;

import com.example.newbiechen.simpledependencedemo.model.bean.ArticleBean;
import com.example.newbiechen.simpledependencedemo.model.bean.RecommendBean;
import com.example.newbiechen.simpledependencedemo.model.data.DBRepository;
import com.example.newbiechen.simpledependencedemo.model.net.NetObserver;
import com.example.newbiechen.simpledependencedemo.model.net.NetWorkRepository;
import com.example.newbiechen.simpledependencedemo.presenter.contract.CommonContract;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by newbiechen on 17-4-3.
 */

public class RecommendPresenter implements CommonContract.Presenter {

    private static final String TAG = "RecommendPresenter";
    private CommonContract.View mRecommendView;

    public RecommendPresenter (CommonContract.View view){
        mRecommendView = view;
        mRecommendView.setPresenter(this);
    }

    @Override
    public void subscribe() {

        //首先从数据库中找
        refreshFromDB();
        //open
        mRecommendView.startRefreshAnim();
        //然后从网络中找
        refreshFromNet();
    }

    private void refreshFromDB(){
        DBRepository.getInstance()
                .getRecommend()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((articleList)->{
                    mRecommendView.finishRefresh(articleList);
                });
    }

    private void refreshFromNet(){
        //之后再从网络中找
        NetWorkRepository.getInstance()
                .getRecommendBean(2017,03,21)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetObserver<RecommendBean>() {
                    @Override
                    public void onNext(RecommendBean value) {
                        if (value !=null){
                            //存储到Recommend中
                            mRecommendView.finishRefresh(value.getAllArticle());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRecommendView.finishRefreshAnim();

                    }

                    @Override
                    public void onComplete() {
                        mRecommendView.finishRefreshAnim();
                    }
                });
    }

    @Override
    public void refreshArticle() {
        refreshFromNet();
    }

    @Override
    public void saveArticleToDB(List<ArticleBean> articleList) {
        DBRepository.getInstance()
                .refreshRecommend(articleList);
    }

    @Override
    public void unSubscribe() {

    }
}
