package com.example.newbiechen.simpledependencedemo.model.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by newbiechen on 17-4-3.
 */

public class NetWorkHelper {
    private static NetWorkHelper sInstance;
    private Retrofit mRetrofit;

    private NetWorkHelper(){
        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(URLManager.BASE_URL)
                .build();
    }

    public static synchronized NetWorkHelper getInstance(){
        if (sInstance == null){
            sInstance = new NetWorkHelper();
        }
        return sInstance;
    }

    public Retrofit getRetrofit(){
        return mRetrofit;
    }
}

