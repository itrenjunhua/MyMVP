# MyMVP
将MVP框架基本代码和常用代码抽取成一个个独立的库，方便引入并快速搭建MVP项目框架；同时方便替换底层实现。

# 抽取的各个单独库功能说明

## mvpBase 库
MVP 框架的各个基类，定义MVP基本框架

## httpLibrary 库
网络通讯框架，使用 Retrofit 框架搭建。

其他封装的框架：
 [OkHttp 框架的二次封装](https://github.com/itrenjunhua/MyOkHttp "OkHttp 框架的二次封装")

 [Volley 框架的二次封装](https://github.com/itrenjunhua/RVolleyTest "Volley 框架的二次封装")

## androidCommon 库
Android常用工具类及控件。[查看这里](https://github.com/itrenjunhua/AndroidUtils "AndroidCommon")

## RPageStatusController 库
Android 页面状态控制框架 [查看这里](https://github.com/itrenjunhua/RPageStatusController "AndroidCommon")

## imageLoaderLibrary 库和 glideLibrary 库
Android 图片加载库，其中 imageLoaderLibrary 库定义图片加载的接口，glideLibrary 库为imageLoaderLibrary的一个实现库。[查看这里](https://github.com/itrenjunhua/ImageLoader "图片加载库封装")

## loadingDialog 库
加载中进度框的封装

## RecyclerSupport 库
RecyclerView Adapter 的封装以及 分割线的绘制类封装。[查看这里](https://github.com/itrenjunhua/RRecyclerView "RRecyclerView")

## cacheLibrary 库
缓存库，可以缓存各种类型数据。[查看这里](https://github.com/itrenjunhua/CacheUtils "缓存库封装")

## rxSupport 库
需要使用 RxJava时，引入 mvpBase 中的各个基类有对应的 RxJava 实现类。

## daggerSupport 库
如果项目中使用了 dagger 时，可以引入该库，该库在 rxSupport 的基础上对 各个基类提供了 dagger 的支持。然后结合 app 的 `com.renj.mvp.dagger`包下的代码实现项目的dagger的使用。
