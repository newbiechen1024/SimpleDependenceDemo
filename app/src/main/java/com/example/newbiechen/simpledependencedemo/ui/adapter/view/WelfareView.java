package com.example.newbiechen.simpledependencedemo.ui.adapter.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.newbiechen.simpledependencedemo.R;
import com.example.newbiechen.simpledependencedemo.model.bean.ArticleBean;
import com.example.newbiechen.simpledependencedemo.ui.base.IAdapter;

/**
 * Created by newbiechen on 17-4-5.
 */

public class WelfareView extends RelativeLayout implements IAdapter<ArticleBean>{
    private View mView;
    private ImageView mIvShow;
    private TextView mTvName;

    public WelfareView(Context context) {
        this(context,null);
    }

    public WelfareView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WelfareView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mView = LayoutInflater.from(getContext())
                .inflate(R.layout.adapter_welfare,this,false);
        mView.getLayoutParams()
                .height = (int)(300+Math.random()*400);
        addView(mView);

        mIvShow = getViewById(R.id.welfare_iv_show);
        mTvName = getViewById(R.id.welfare_tv_name);
    }




    private <T> T getViewById(int id){
        return (T) mView.findViewById(id);
    }

    @Override
    public void onBind(ArticleBean value, int pos) {
        Glide.with(getContext())
                .load(value.getUrl())
                .error(R.mipmap.ic_launcher)
                .placeholder(R.color.gray)
                .into(mIvShow);

        mTvName.setText(value.getWho());
    }
}
