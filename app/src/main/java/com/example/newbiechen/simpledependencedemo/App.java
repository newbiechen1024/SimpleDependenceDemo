package com.example.newbiechen.simpledependencedemo;

import android.app.Application;
import android.content.Context;

/**
 * Created by newbiechen on 17-4-3.
 */

public class App extends Application {
    private static App INSTANCE;


    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }

    public static Context getContext(){
        return INSTANCE.getApplicationContext();
    }
}
