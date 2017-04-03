package com.example.newbiechen.simpledependencedemo.presenter;

import com.example.newbiechen.simpledependencedemo.model.bean.RecommendBean;
import com.example.newbiechen.simpledependencedemo.model.net.NetObserver;
import com.example.newbiechen.simpledependencedemo.model.net.NetWorkRepository;
import com.example.newbiechen.simpledependencedemo.presenter.contract.RecommendContract;

import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import rx.internal.util.ObserverSubscriber;

/**
 * Created by newbiechen on 17-4-3.
 */

public class RecommendPresenter implements RecommendContract.Presenter {
    private RecommendContract.View mRecommendView;

    public RecommendPresenter (RecommendContract.View view){
        mRecommendView = view;
        mRecommendView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        //open
        mRecommendView.startRefreshAnim();

        NetWorkRepository.getInstance()
                .getTodayRecommendBean()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetObserver<RecommendBean>() {
                    @Override
                    public void onNext(RecommendBean value) {
                        if (value == null){
                            //不做
                        }
                        else {

                        }
                    }

                    @Override
                    public void onComplete() {
                        mRecommendView.finishRefreshAnim();
                    }
                });
    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void refreshArticle() {

    }

}
