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



