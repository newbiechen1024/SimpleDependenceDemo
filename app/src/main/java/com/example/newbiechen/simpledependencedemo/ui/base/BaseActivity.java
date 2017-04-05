package com.example.newbiechen.simpledependencedemo.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by PC on 2016/9/8.
 */
public abstract class BaseActivity extends AppCompatActivity {
    /****************************abstract area*************************************/
    protected abstract void onCreateView(Bundle savedInstanceState);

    /************************init area************************************/

    /**
     * 初始化零件
     */
    protected void initWidget(Bundle savedInstanceState) {

    }
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

    /*************************lifecycle area*****************************************************/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(savedInstanceState);
        initWidget(savedInstanceState);
        initClick();
        processLogic(savedInstanceState);
    }

    /**************************used method area*******************************************/

    protected void startActivity(Class<? extends AppCompatActivity> activity){
        Intent intent = new Intent(this,activity);
        startActivity(intent);
    }

    protected  <VT> VT getViewById(int id){
        return (VT) findViewById(id);
    }

    protected ActionBar supportActionBar(Toolbar toolbar){
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        return actionBar;
    }

}
