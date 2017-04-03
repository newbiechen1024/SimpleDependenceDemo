package com.example.newbiechen.simpledependencedemo.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by newbiechen on 17-4-3.
 */

public class RecommendBean {


    @SerializedName("Android")
    private List<ArticleBean> androidList;
    @SerializedName("iOS")
    private List<ArticleBean> iosList;
    @SerializedName("休息视频")
    private List<ArticleBean> relaxVideoList;
    @SerializedName("拓展资源")
    private List<ArticleBean> extraResourceList;
    @SerializedName("瞎推荐")
    private List<ArticleBean> randomRecommendList;
    @SerializedName("福利")
    private List<ArticleBean> welfareList;

    public List<ArticleBean> getAndroidList() {
        return androidList;
    }

    public void setAndroidList(List<ArticleBean> androidList) {
        this.androidList = androidList;
    }

    public List<ArticleBean> getIosList() {
        return iosList;
    }

    public void setIosList(List<ArticleBean> iosList) {
        this.iosList = iosList;
    }

    public List<ArticleBean> getRelaxVideoList() {
        return relaxVideoList;
    }

    public void setRelaxVideoList(List<ArticleBean> relaxVideoList) {
        this.relaxVideoList = relaxVideoList;
    }

    public List<ArticleBean> getExtraResourceList() {
        return extraResourceList;
    }

    public void setExtraResourceList(List<ArticleBean> extraResourceList) {
        this.extraResourceList = extraResourceList;
    }

    public List<ArticleBean> getRandomRecommendList() {
        return randomRecommendList;
    }

    public void setRandomRecommendList(List<ArticleBean> randomRecommendList) {
        this.randomRecommendList = randomRecommendList;
    }

    public List<ArticleBean> getWelfareList() {
        return welfareList;
    }

    public void setWelfareList(List<ArticleBean> welfareList) {
        this.welfareList = welfareList;
    }
}
