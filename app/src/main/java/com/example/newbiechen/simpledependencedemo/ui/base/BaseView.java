package com.example.newbiechen.simpledependencedemo.ui.base;

/**
 * Created by newbiechen on 17-3-24.
 */

public interface BaseView<T extends BasePresenter> {
    void setPresenter(T presenter);
}
