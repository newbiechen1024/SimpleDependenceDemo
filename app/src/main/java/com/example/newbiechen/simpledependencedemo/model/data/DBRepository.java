package com.example.newbiechen.simpledependencedemo.model.data;

import android.support.annotation.StringRes;
import android.util.Log;

import com.example.newbiechen.simpledependencedemo.App;
import com.example.newbiechen.simpledependencedemo.R;
import com.example.newbiechen.simpledependencedemo.model.bean.ArticleBean;
import com.example.newbiechen.simpledependencedemo.model.gen.ArticleBeanDao;
import com.example.newbiechen.simpledependencedemo.model.gen.DaoSession;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by newbiechen on 17-4-5.
 */

public class DBRepository {
    private static final String TAG = "DBRepository";
    private static DBRepository sInstance;
    private DaoSession mSession;
    private DBRepository(){
        mSession = DaoHelper.getInstance()
                .getSession();
    }

    public static synchronized DBRepository getInstance(){
        if (sInstance == null){
            sInstance = new DBRepository();
        }
        return sInstance;
    }

    public Observable<List<ArticleBean>> getRecommend(){
        return Observable.create(new ObservableOnSubscribe<List<ArticleBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ArticleBean>> e) throws Exception {
                String property = getString(R.string.nb_fragment_title_recommend);
                List<ArticleBean> list = getArticleList(property);
                e.onNext(list);
                e.onComplete();
            }
        });
    }

    private List<ArticleBean> getArticleList(String key){
        List<ArticleBean> list = mSession.getArticleBeanDao()
                .queryBuilder()
                .where(ArticleBeanDao.Properties.Property.eq(key))
                .build()
                .list();
        if (list != null){
            return list;
        }
        return new ArrayList<>();
    }

    public void saveRecommend(List<ArticleBean> newList){
        if (newList == null || newList.size() == 0) return;

        deleteRecommendAll();
        String name = getString(R.string.nb_fragment_title_recommend);
        for (ArticleBean bean : newList){
            bean.setProperty(name);
            mSession.getArticleBeanDao()
                    .insert(bean);
        }
    }

    public void deleteRecommendAll(){
        String property = getString(R.string.nb_fragment_title_recommend);
        List<ArticleBean> list = getArticleList(property);
        mSession.getArticleBeanDao()
                .deleteInTx(()-> {
                            return list.iterator();
                        }
                );
    }

    private String getString(@StringRes int res){
        return App.getContext()
                .getResources().getString(res);
    }
}
