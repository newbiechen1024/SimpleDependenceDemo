package com.example.newbiechen.simpledependencedemo.model.net.api;

import com.example.newbiechen.simpledependencedemo.model.bean.ResponseBean;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by newbiechen on 17-4-3.
 */

public interface ArticleApi {

    @GET("day/{year}/{month}/{day}")
    Observable<ResponseBean> getResponse(@Path("year")int year, @Path("month")int month,
                                         @Path("day")int day);
}
