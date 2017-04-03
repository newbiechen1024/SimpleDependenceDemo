package com.example.newbiechen.simpledependencedemo.model.net;

import android.util.Log;

import com.example.newbiechen.simpledependencedemo.model.bean.RecommendBean;
import com.example.newbiechen.simpledependencedemo.model.bean.ResponseBean;
import com.example.newbiechen.simpledependencedemo.model.net.api.ArticleApi;
import com.google.gson.Gson;

import java.util.Calendar;

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
        Observable<ResponseBean> observable = mArticleApi.getResponse(year,month,day);
        return toObservable(observable,RecommendBean.class);
    }

    private <T> Observable<T> toObservable(Observable<ResponseBean> observable,Class<T> type){
        return observable.map(
                responseBean -> {
                    //这里有点问题，返回null比较不友好。但是不知道怎么改
                    if (responseBean.isError()){
                        return null;
                    }
                    return new Gson().fromJson(responseBean.getResults(),type);
                }
        ).cast(type);
    }
}
