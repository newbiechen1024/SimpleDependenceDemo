# 问题

## BaseActivity的设计

我只将onCreate和initView设置为了抽象方法。其他都设置为默认方法。

我觉的这种方式挺好的，想用的时候再用，而不是继承方法又不用，浪费了空间

## Fragment的设计

如何设置Fragment的别名。

我的见解:就是在BaseFragment设置一个getName()，之后的Fragment继承这个方法。其他需要Fragment别名的类直接使用就可以了。

过程中遇到了一个问题，对于ViewPager来说，她调用getPageTitle()方法在调用getItem()方法前。这就在Fragment重写
getName()的时候出了点问题。直接调用getString(R.string.xxx);会报错。原因是getString()需要上一个Activity的context,
然而因为Fragment还没有被加入到Activity，所以会报错。

解决办法:
使用Application全局的Context，代替Activity的Context。

## Fragment的内存溢出
```java
public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    int resId = createView();
    //没有设置false导致的内存溢出，之后需要分析一下，产生的原因。
    root = inflater.inflate(resId,container,false);
    return root;
}
```

原因:不知道 - -

## 处理json都会返回error，result然后进行封装的问题。

第一次封装，处理error的问题。
```java
private <T> Observable<T> toObservable(Observable<ResponseBean> observable,Class<T> type){
    return observable.map(
            responseBean -> {
                //这里有点问题，返回null比较不友好。但是不知道怎么改
                if (responseBean.isError()){
                    return null;
                }
                return new Gson().fromJson(responseBean.getResults(),type);
            }
    ).cast(type);
}
```
使用这种方法将ResponseBean转换成其子类型。
再创建一个Observer，简化使用。虽然没有简化多少，但是有点用处。
```java
public abstract class NetObserver <T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onError(Throwable e) {
        Toast.makeText(App.getContext(),"网络连接失败:"+e.getMessage(),Toast.LENGTH_SHORT)
                .show();
    }
}
```
# 如何使用Gson的问题

1. 通过使用GsonFormat插件，将Json转换成JavaBean
2. 如何让成员变量与javaBean的变量名匹配

   使用@SerializedName("xx");

3. 如何将一个String数据填充到一个javaBean中。

   new Gson().from(String data, Class<T> type);

## 网络连接工具的命名

NetHelper:网络交互的工具
NetRepository:网络交互的接口

## Retrofit的使用

1. 如何创建一个Retrofit。
   ```java
   mRetrofit = new Retrofit.Builder()
           .addConverterFactory(GsonConverterFactory.create())//Gson解析
           .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//RxJava
           .baseUrl(URLManager.BASE_URL)
           .build();
   ```

2. 如何创建网络交互的方法

   ```java
   public interface ArticleApi {

       @GET("day/{year}/{month}/{day}")
       Call<ResponseBean> getResponse(@Path("year")int year, @Path("month")int month,
                                            @Path("day")int day);
   }
   ```

   使用:
   ```java
   call.enqueue() -> okHttp的用法
   ```

## Retrofit + RxJava

1. 两个如何结合使用
2. 如何封装
   1. 首先是Response的封装
   2. onFailure()和onComplete的封装

   ```java
   public interface ArticleApi {

       @GET("day/{year}/{month}/{day}")
       Observable<ResponseBean> getResponse(@Path("year")int year, @Path("month")int month,
                                            @Path("day")int day);
   }
   ```

## 在Retrofit中Observable<List<String>>什么意思

## GreenDao的使用

## String is not a entity

1. 利用GreenDao实现一对多和一对一的关系。(知道@ToMany @ToOne @joinEntity)
2. 如何在GreenDao中添加List<String> 需要知道转换其Convert的使用。基础类型是无法使用List的。
3. GreenDao类中能否存在list,或者中
```java
@Entity
public class RecommendBean {

    @Id(autoincrement = true)
    private int id;
    @ToMany(referencedJoinProperty = "")
    @SerializedName("Android")
    private List<ArticleBean> androidList;
    @ToMany
    @SerializedName("iOS")
    private List<ArticleBean> iosList;
    @ToMany
    @SerializedName("休息视频")
    private List<ArticleBean> relaxVideoList;
    @ToMany
    @SerializedName("拓展资源")
    private List<ArticleBean> extraResourceList;
    @ToMany
    @SerializedName("瞎推荐")
    private List<ArticleBean> randomRecommendList;
}
```
其中的List表示什么意思。 我的理解是List并不是Bean存储在数据库中的字段，而是在生成的时候获取的字段。即存储的时候只保存了id
但是在生成的时候通过一对一或者一对多的关系，生成这个List。

所以说这个RecommendBean并不能加入到数据库中，应该是我们从数据库中获取数据然后生成的东西。应该是要在每个Article中加上一个
类型名，叫做property。名字为"每日推荐"，然后通过搜索这样的字段进行整合数据而已。

问题:N:M是什么关系？

# 4月5日

1. 有必要Helper 和 Repository都进行单例吗
2. GreenDao的原理。比如说其创建过程，Session的作用。
3. 创建GreenDao一直有问题- -，后来才发现是插件版本的问题
4. GreenDao save无法使用但是insert就可以使用
5. 学习RxJava2.0的新调用

# 4月6日

1. RecyclerView中的StaggeredLayoutManager的使用。(关键代码是随机改变ViewHolder的高度)
2. 关于获取最新数据的问题～～。数据加载的逻辑问题0 0，在弱网环境下，新数据与旧数据结合的问题。
3. 如何创建触发器，当数据超过100条的时候自动删除前一条。
4. 按照最新数据排序数据。

解决方法:
1. 默认存储到数据库20条数据。
2. 在显示数据库的数据下，无法更新。必须刷新成功一次才能自动加载。

## 问题
如何只保留20条数据。
1. 首先存储所有数据，然后提取数据的总数判断是否超过20条，如果超过则删除多出来数目。

当没有网络的情况下之允许浏览数据库存储的数据。
