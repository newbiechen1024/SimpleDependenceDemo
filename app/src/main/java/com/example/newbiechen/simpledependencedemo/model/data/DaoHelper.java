package com.example.newbiechen.simpledependencedemo.model.data;

import android.database.sqlite.SQLiteDatabase;

import com.example.newbiechen.simpledependencedemo.App;
import com.example.newbiechen.simpledependencedemo.model.gen.DaoMaster;
import com.example.newbiechen.simpledependencedemo.model.gen.DaoSession;

/**
 * Created by newbiechen on 17-4-5.
 */

public class DaoHelper {
    private static final String DB_NAME = "Article_DB";

    private static DaoHelper sInstance;
    private DaoMaster mDaoMaster;
    private DaoSession mSession;

    private DaoHelper(){
        //封装数据库的创建、更新、删除
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(App.getContext(),DB_NAME,null);
        //获取数据库
        SQLiteDatabase db = openHelper.getWritableDatabase();
        //封装数据库中表的创建、更新、删除
        mDaoMaster = new DaoMaster(db);  //合起来就是对数据库的操作
        //对表操作的对象。
        mSession = mDaoMaster.newSession(); //可以认为是对数据的操作
    }

    public static synchronized DaoHelper getInstance(){
        if (sInstance == null){
            sInstance = new DaoHelper();
        }
        return sInstance;
    }

    public DaoSession getSession(){
        return mSession;
    }

    public DaoSession getNewSession(){
        return mDaoMaster.newSession();
    }
}
