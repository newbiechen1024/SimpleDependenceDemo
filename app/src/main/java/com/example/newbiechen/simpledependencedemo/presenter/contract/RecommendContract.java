package com.example.newbiechen.simpledependencedemo.presenter.contract;

import com.example.newbiechen.simpledependencedemo.model.bean.ArticleBean;
import com.example.newbiechen.simpledependencedemo.model.bean.RecommendBean;
import com.example.newbiechen.simpledependencedemo.ui.base.BasePresenter;
import com.example.newbiechen.simpledependencedemo.ui.base.BaseView;

import java.util.List;

/**
 * Created by newbiechen on 17-4-3.
 */

public interface RecommendContract {

    interface View extends BaseView<Presenter>{
        void startRefreshAnim();
        void finishRefreshAnim();
        void finishRefresh(List<ArticleBean> articleList);

    }

    interface Presenter extends BasePresenter{
        void refreshArticle();
        void saveArticleToDB(List<ArticleBean> articleList);
    }
}
