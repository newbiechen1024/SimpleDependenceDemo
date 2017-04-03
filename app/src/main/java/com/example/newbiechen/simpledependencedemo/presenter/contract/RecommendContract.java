package com.example.newbiechen.simpledependencedemo.presenter.contract;

import com.example.newbiechen.simpledependencedemo.ui.base.BasePresenter;
import com.example.newbiechen.simpledependencedemo.ui.base.BaseView;

/**
 * Created by newbiechen on 17-4-3.
 */

public interface RecommendContract {

    interface View extends BaseView<Presenter>{
        void startRefreshAnim();
        void finishRefreshAnim();
        void finishRefresh();
    }

    interface Presenter extends BasePresenter{
        void refreshArticle();
    }
}
