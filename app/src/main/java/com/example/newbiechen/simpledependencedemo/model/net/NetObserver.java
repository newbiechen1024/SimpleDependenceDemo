package com.example.newbiechen.simpledependencedemo.model.net;

import android.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.example.newbiechen.simpledependencedemo.App;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by newbiechen on 17-4-3.
 */

public abstract class NetObserver <T> implements Observer<T> {
    private static final String TAG = "NetObserver";
    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onError(Throwable e) {
        Toast.makeText(App.getContext(),"网络连接失败:"+e.getMessage(),Toast.LENGTH_SHORT)
                .show();
        Log.d(TAG, "onError: "+e.getMessage());
    }
}
