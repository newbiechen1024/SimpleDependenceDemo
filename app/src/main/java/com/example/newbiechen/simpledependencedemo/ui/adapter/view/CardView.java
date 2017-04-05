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
 * Created by newbiechen on 17-4-4.
 */

public class CardView extends RelativeLayout implements IAdapter<ArticleBean>{
    private static final String TYPE_IMAGE = "福利";
    private static final String TYPE_ANDROID = "Android";
    private static final String TYPE_IOS = "iOS";
    private static final String TYPE_RELAX = "休息视频";
    private static final String TYPE_RANDOM = "瞎推荐";
    private static final String TYPE_EXPLOIT = "拓展资源";

    private View mView;

    private ImageView mIvShow;
    private TextView mTvTitle;
    private TextView mTvDesc;
    private TextView mTvTag;
    public CardView(Context context) {
        this(context,null);
    }

    public CardView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.view_article_card,this,false);
        mView = view;
        addView(view);

        initView();
    }

    private void initView(){
        mIvShow = getViewById(R.id.card_iv_show);
        mTvTitle = getViewById(R.id.card_tv_title);
        mTvDesc = getViewById(R.id.card_tv_desc);
        mTvTag = getViewById(R.id.card_tv_tag);
    }




    private <T> T getViewById(int id){
        return (T) mView.findViewById(id);
    }

    @Override
    public void onBind(ArticleBean value, int pos) {

        String type = value.getType();
        String imageUrl = "";

        if (value.getImagesUrlList() != null){
            imageUrl = value.getImagesUrlList().get(0);
        }
        else if (type.equals(TYPE_IMAGE)){
            imageUrl = value.getUrl();
        }

        Glide.with(getContext())
                .load(imageUrl)
                .error(R.mipmap.ic_launcher)
                .placeholder(getResources().getColor(R.color.gray))
                .into(mIvShow);

        mTvTitle.setText(value.getDesc());
        mTvDesc.setText(value.getWho());
        mTvTag.setText(changeName(value.getType()));
    }

    private String changeName(String type){
        String name = "";
        switch (type){
            case TYPE_ANDROID:
                name = getResources().getString(R.string.nb_resource_type_android);
                break;
            case TYPE_IOS:
                name = getResources().getString(R.string.nb_resource_type_ios);
                break;
            case TYPE_IMAGE:
                name = getResources().getString(R.string.nb_resource_type_welfare);
                break;
            case TYPE_EXPLOIT:
                name = getResources().getString(R.string.nb_resource_type_exploit);
                break;
            case TYPE_RANDOM:
                name = getResources().getString(R.string.nb_resource_type_random);
                break;
            case TYPE_RELAX:
                name = getResources().getString(R.string.nb_resource_type_video);
                break;
        }
        return name;
    }

    public void setTagVisible(boolean isVisible){
        if (isVisible){
            mTvTag.setVisibility(VISIBLE);
        }
        else {
            mTvTag.setVisibility(GONE);
        }
    }
}
