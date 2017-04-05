package com.example.newbiechen.simpledependencedemo.model.net.api;


import com.example.newbiechen.simpledependencedemo.model.bean.ArticleBean;
import com.example.newbiechen.simpledependencedemo.model.bean.RecommendBean;
import com.example.newbiechen.simpledependencedemo.model.bean.ResponseBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by newbiechen on 17-4-3.
 */

public interface ArticleApi {

    @GET("day/{year}/{month}/{day}")
    Observable<ResponseBean<RecommendBean>> getRecommendBean(@Path("year")String year, @Path("month")String month,
                                                             @Path("day")String day);

    @GET("data/{type}/10/{page}")
    Observable<ResponseBean<List<ArticleBean>>> getArticleList(@Path("type")String type,@Path("page") String page);
}
