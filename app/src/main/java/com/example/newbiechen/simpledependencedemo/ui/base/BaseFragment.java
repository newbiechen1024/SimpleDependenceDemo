package com.example.newbiechen.simpledependencedemo.ui.base;

import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by newbiechen on 17-3-31.
 */

public abstract class BaseFragment extends Fragment{
    private View root = null;

    @LayoutRes
    protected abstract int createView();

    /*******************************init area*********************************/
    /**
     * 初始化View
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 初始化点击事件
     */
    protected void initClick(){
    }

    /**
     * 逻辑使用区
     */
    protected void processLogic(Bundle savedInstanceState){
    }

    /**
     * 初始化零件
     */
    protected void initWidget(Bundle savedInstanceState){
    }

    /******************************lifecycle area*****************************************/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int resId = createView();
        root = inflater.inflate(resId,container,false);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(savedInstanceState);
        initWidget(savedInstanceState);
        initClick();
        processLogic(savedInstanceState);
    }

    /**************************公共类*******************************************/
    public String getName(){
        return getClass().getName();
    }

    protected <VT> VT getViewById(int id){
        if (root == null){
            return  null;
        }
        return (VT) root.findViewById(id);
    }
}


