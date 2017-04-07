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
 * Created by newbiechen on 17-4-7.
 */

public class AndroidView extends RelativeLayout implements IAdapter<ArticleBean>{
    private View mView;
    private ImageView mIvShow;
    private TextView mTvTitle;
    private TextView mTvAuthor;
    public AndroidView(Context context) {
        this(context,null);
    }

    public AndroidView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AndroidView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mView = LayoutInflater.from(getContext())
                .inflate(R.layout.adapter_android,this,false);
        addView(mView);

        initView();
    }

    private void initView(){
        mIvShow = getViewById(R.id.android_iv_show);
        mTvTitle = getViewById(R.id.android_tv_title);
        mTvAuthor = getViewById(R.id.android_tv_author);
    }

    @Override
    public void onBind(ArticleBean value, int pos) {
        if (value.getImagesUrlList() != null){
            mIvShow.setVisibility(VISIBLE);
            Glide.with(getContext())
                    .load(value.getImagesUrlList().get(0))
                    .asBitmap()
                    .error(R.mipmap.ic_launcher)
                    .placeholder(R.color.gray)
                    .into(mIvShow);
        }
        else {
            mIvShow.setVisibility(GONE);
        }
        mTvTitle.setText(value.getDesc());
        mTvAuthor.setText(value.getWho());
    }

    private <T> T getViewById(int id){
        return (T) mView.findViewById(id);
    }
}
