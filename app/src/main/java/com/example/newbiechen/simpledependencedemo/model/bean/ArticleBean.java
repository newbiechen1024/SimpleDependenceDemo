package com.example.newbiechen.simpledependencedemo.model.bean;

import android.test.FlakyTest;

import com.example.newbiechen.simpledependencedemo.model.gen.convert.StringConvert;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.List;

/**
 * Created by newbiechen on 17-4-3.
 */
@Entity
public class ArticleBean {
    /**
     * _id : 58d08d4b421aa90f033451aa
     * createdAt : 2017-03-21T10:17:47.778Z
     * desc : 独立的 TouchBar 模拟器，方便开发
     * images : ["http://img.gank.io/0fe4bb8f-7b1f-4c0e-a594-6cd1f9e91a81"]
     * publishedAt : 2017-03-21T12:19:46.895Z
     * source : chrome
     * type : iOS
     * url : https://github.com/sindresorhus/touch-bar-simulator
     * used : true
     * who : 带马甲
     */

    @SerializedName("_id")
    @Id private String strId;
    //属性:用来确定是哪个版块的数据
    private String property;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;

    @SerializedName("images")
    @Convert(converter = StringConvert.class,columnType = String.class)
    private List<String> imagesUrlList;

    @Generated(hash = 608046980)
    public ArticleBean(String strId, String property, String createdAt,
            String desc, String publishedAt, String source, String type,
            String url, boolean used, String who, List<String> imagesUrlList) {
        this.strId = strId;
        this.property = property;
        this.createdAt = createdAt;
        this.desc = desc;
        this.publishedAt = publishedAt;
        this.source = source;
        this.type = type;
        this.url = url;
        this.used = used;
        this.who = who;
        this.imagesUrlList = imagesUrlList;
    }

    @Generated(hash = 392728754)
    public ArticleBean() {
    }

    public String getStrId() {
        return strId;
    }

    public void setStrId(String id) {
        this.strId = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public List<String> getImagesUrlList() {
        return imagesUrlList;
    }

    public void setImagesUrlList(List<String> imagesUrlList) {
        this.imagesUrlList = imagesUrlList;
    }


    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public boolean getUsed() {
        return this.used;
    }

    @Override
    public String toString() {
        return "ArticleBean{" +
                "strId='" + strId + '\'' +
                ", property='" + property + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", desc='" + desc + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", source='" + source + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", used=" + used +
                ", who='" + who + '\'' +
                ", imagesUrlList=" + imagesUrlList +
                '}';
    }
}
