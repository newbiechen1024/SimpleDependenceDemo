# 项目目的

学习这些框架API的使用。

# 项目进度(4月3日)

## 要求

1. 创建四个Fragment，并使用TabLayout+ViewPager链接起来 (完成)
2. 设置 StatusBar + NavigationBar的颜色 (做了一点)
3. 根据返回的json设计JavaBean (做了Recommend)
4. 完成数据展示

## 今天完成的进度

## 之后需要改变的问题

1. 检查命名是否有问题  (完成)
2. 修改成员变量和方法的位置  (完成)
3. 对xml中的命名进行组合 (完成)

# 进度(4月4日)

## 要求

1. 制作Recommend的数据展示及数据库的存储 (数据展示完成，差数据的存储)
2. 制作其他Fragment的数据展示
3. 优化界面

## 过程中调整的问题

1. 将Retrofit的api接口修改了一下，讲Response改成了 Response<T>。T为Result的实际内容

## 过程中需要调整的问题

#进度(4月5日)

## 要求

1. Recommend的数据存储 (完成)
2. 其他Fragment的制作

### Welfare的制作要点

1. 自制的RecyclerView 自动加载
2. 分页加载的问题
3. 存储到数据库,提取的问题(缓存中存储的是第几页的数据)
4. 瀑布流的问题
5. JavaBean是否应该按照继承的形式加载,不应该在一个JavaBean上操作
6. 数据从网络加载还是从数据中加载的逻辑问题
7. 图片懒加载的问题