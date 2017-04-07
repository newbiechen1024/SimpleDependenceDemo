package com.example.newbiechen.simpledependencedemo.model.data;

import android.support.annotation.StringRes;
import android.util.Log;

import com.example.newbiechen.simpledependencedemo.App;
import com.example.newbiechen.simpledependencedemo.R;
import com.example.newbiechen.simpledependencedemo.model.bean.ArticleBean;
import com.example.newbiechen.simpledependencedemo.model.gen.ArticleBeanDao;
import com.example.newbiechen.simpledependencedemo.model.gen.DaoSession;

import org.greenrobot.greendao.Property;

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
    private static final String DELETE_EXTRA_ARTICLE = "delete from "+ArticleBeanDao.TABLENAME
            +" where "+ArticleBeanDao.Properties.PublishedAt.columnName+" in("
            +" select "+ArticleBeanDao.Properties.PublishedAt.columnName+" from "+ArticleBeanDao.TABLENAME+" where "
            + ArticleBeanDao.Properties.Property.columnName +" = "+"?"
            +" order by "+ArticleBeanDao.Properties.PublishedAt.columnName+" asc"
            + " limit ?)";

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

    /**
     * 删除，再添加。保存数据都是最新的
     * @param articleBeans
     */
    public void refreshRecommend(List<ArticleBean> articleBeans){
        if (articleBeans == null || articleBeans.size() == 0) return;

        deleteAllRecommend();
        String property = getString(R.string.nb_fragment_title_recommend);
        for (ArticleBean bean : articleBeans){
            bean.setProperty(property);
            mSession.getArticleBeanDao()
                    .insert(bean);
        }
    }

    /**
     * 更新，只要不相同就可以保存下来。
     * @param articleBeans
     */
    public void updateWelfare(List<ArticleBean> articleBeans){
        String property = getString(R.string.nb_fragment_title_welfare);
        Property daoProperty = ArticleBeanDao.Properties.StrId;
        saveArticle(property,daoProperty,articleBeans);

        //只允许保留20条数据
        int count = getArticleList(property)
                .size();
        if (count > 20){
            int delCount = count - 20;
            DaoHelper.getInstance()
                    .getDatabase()
                    .execSQL(DELETE_EXTRA_ARTICLE,new Object[]{property,delCount});
        }
    }

    public void updateAndroid(List<ArticleBean> articleBeans){
        String property = getString(R.string.nb_fragment_title_android);
        Property daoProperty = ArticleBeanDao.Properties.StrId;
        saveArticle(property,daoProperty,articleBeans);
        //只允许保留20条数据
        int count = getArticleList(property)
                .size();
        if (count > 20){
            int delCount = count - 20;
            DaoHelper.getInstance()
                    .getDatabase()
                    .execSQL(DELETE_EXTRA_ARTICLE,new Object[]{"Android",delCount});
        }
    }


    /**
     * 判断是Update还是insert数据。
     * @param property
     * @param daoProperty
     * @param articleBeans
     */
    private void saveArticle(String property,Property daoProperty,List<ArticleBean> articleBeans){
        if (articleBeans == null || articleBeans.size() == 0) return;

        for (ArticleBean bean : articleBeans){
            bean.setProperty(property);
            if (isArticleExist(daoProperty,bean)){
                mSession.getArticleBeanDao()
                        .update(bean);
            }
            else {
                mSession.getArticleBeanDao()
                        .insertOrReplace(bean);
            }
        }
    }

    /**
     * 判断Bean是否存在与数据库中
     * @param property
     * @param articleBean
     * @return
     */
    private boolean isArticleExist(Property property, ArticleBean articleBean){
        ArticleBean result = mSession.getArticleBeanDao()
                .queryBuilder()
                .where(property.eq(articleBean.getStrId()))
                .build()
                .unique();
        if (result != null){
            return true;
        }
        return false;
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

    public Observable<List<ArticleBean>> getWelfare(){
        return Observable.create(new ObservableOnSubscribe<List<ArticleBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ArticleBean>> e) throws Exception {
                String property = getString(R.string.nb_fragment_title_welfare);

                List<ArticleBean> list = mSession.getArticleBeanDao()
                        .queryBuilder()
                        .where(ArticleBeanDao.Properties.Property.eq(property))
                        .orderDesc(ArticleBeanDao.Properties.PublishedAt)
                        .build()
                        .list();
                if (list == null){
                    list = new ArrayList<ArticleBean>();
                }

                e.onNext(list);
                e.onComplete();
            }
        });
    }

    public Observable<List<ArticleBean>> getAndroid(){
        return Observable.create(new ObservableOnSubscribe<List<ArticleBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ArticleBean>> e) throws Exception {
                String property = getString(R.string.nb_fragment_title_android);

                List<ArticleBean> list = mSession.getArticleBeanDao()
                        .queryBuilder()
                        .where(ArticleBeanDao.Properties.Property.eq(property))
                        .orderDesc(ArticleBeanDao.Properties.PublishedAt)
                        .build()
                        .list();
                if (list == null){
                    list = new ArrayList<ArticleBean>();
                }

                e.onNext(list);
                e.onComplete();
            }
        });
    }

    /**
     * 根据属性获取Article
     * @param key
     * @return
     */
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


    public void deleteAllRecommend(){
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
