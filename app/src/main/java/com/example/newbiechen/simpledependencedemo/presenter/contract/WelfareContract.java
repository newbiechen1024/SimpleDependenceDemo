package com.example.newbiechen.simpledependencedemo.presenter.contract;

import com.example.newbiechen.simpledependencedemo.model.bean.ArticleBean;

import java.util.List;

/**
 * Created by newbiechen on 17-4-6.
 */

public interface WelfareContract {

    interface View extends CommonContract.View<Presenter>{
        void finishLoad(List<ArticleBean> articles);
        void canLoading(boolean loading);
    }

    interface Presenter extends CommonContract.Presenter{
        void loadArticle();
    }
}
