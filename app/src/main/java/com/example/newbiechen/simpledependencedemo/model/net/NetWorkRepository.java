package com.example.newbiechen.simpledependencedemo.model.net;

import android.support.annotation.StringRes;
import android.util.Log;

import com.example.newbiechen.simpledependencedemo.App;
import com.example.newbiechen.simpledependencedemo.R;
import com.example.newbiechen.simpledependencedemo.model.bean.ArticleBean;
import com.example.newbiechen.simpledependencedemo.model.bean.RecommendBean;
import com.example.newbiechen.simpledependencedemo.model.bean.ResponseBean;
import com.example.newbiechen.simpledependencedemo.model.net.api.ArticleApi;

import java.util.Calendar;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * Created by newbiechen on 17-4-3.
 */

public class NetWorkRepository {
    private static final String TAG = "NetWorkRepository";

    private static NetWorkRepository sInstance;
    private Retrofit mRetrofit;
    private ArticleApi mArticleApi;

    private NetWorkRepository(){
        mRetrofit = NetWorkHelper.getInstance()
                .getRetrofit();

        mArticleApi = mRetrofit.create(ArticleApi.class);
    }

    public static synchronized NetWorkRepository getInstance(){
        if (sInstance == null){
            sInstance = new NetWorkRepository();
        }
        return sInstance;
    }


    /***Recommend**/
    public Observable<RecommendBean> getTodayRecommendBean(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.WEEK_OF_MONTH);

        Log.d(TAG, "getTodayRecommendBean: "+year+" "+month+" "+day);
        return getRecommendBean(year,month,day);
    }


    public Observable<RecommendBean> getRecommendBean(int year, int month, int day){
        Observable<ResponseBean<RecommendBean>> observable = mArticleApi.getRecommendBean(year+"",month+"",day+"");
        return toObservable(observable);
    }

    public Observable<List<ArticleBean>> getWelfareArticle(int page){
        String type = getString(R.string.nb_resource_type_welfare);
        Observable<ResponseBean<List<ArticleBean>>> observable = mArticleApi.getArticleList(type,page+"");
        return toObservable(observable);
    }

    public Observable<List<ArticleBean>> getAndroidArticle(int page){
        String type = getString(R.string.nb_fragment_title_android);
        Observable<ResponseBean<List<ArticleBean>>> observable = mArticleApi.getArticleList(type,page+"");
        return toObservable(observable);
    }



    private <T> Observable<T> toObservable(Observable<ResponseBean<T>> observable){
        return observable.map(
                responseBean -> {
                    //这里有点问题，返回null比较不友好。但是不知道怎么改
                    if (responseBean.isError()){
                        return null;
                    }
                    return responseBean.getResults();
                }
        );
    }

    private String getString(@StringRes int res){
        return App.getContext()
                .getResources().getString(res);
    }
}
