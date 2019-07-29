**当前分支为：RxJava+Retrofit+Dagger实现快速搭建MVP框架**

# MyMVP
将MVP框架基本代码和常用代码抽取成一个个独立的库，方便引入并快速搭建MVP项目框架；
同时方便替换底层实现。

## 查看分支

[使用 RxJava+Retrofit实现快速搭建MVP框架](https://github.com/itrenjunhua/MyMVP/tree/master)

[使用 RxJava+Retrofit+Dagger实现快速搭建MVP框架](https://github.com/itrenjunhua/MyMVP/tree/dagger)

[使用 RxJava+Retrofit实现快速搭建组件化MVP框架](https://github.com/itrenjunhua/MyMVP/tree/master_component)

##  APP框架库
* mvp_mvpbase：MVP 框架的各个基类，定义MVP基本框架
* mvp_rxsupport：mvp_mvpbase 中各个基类的对应 RxJava 实现类。
* lib_daggersupport：mvp_rxsupport 的基础上对 各个基类提供了 dagger 的支持。(然后结合 app 的 `com.renj.mvp.dagger`包下的代码实现项目的dagger的使用。)

## 工具库/支持库
* lib_http网络通讯框架，使用 Retrofit 框架搭建。
* 其他封装的框架：
    * [OkHttp 框架的二次封装](https://github.com/itrenjunhua/MyOkHttp "OkHttp 框架的二次封装")
    * [Volley 框架的二次封装](https://github.com/itrenjunhua/RVolleyTest "Volley 框架的二次封装")
* lib_android_utils：Android常用工具类。[查看这里](https://github.com/itrenjunhua/AndroidUtils "AndroidUtils")
* lib_rpagestatuscontroller：Android 页面状态控制框架 [查看这里](https://github.com/itrenjunhua/RPageStatusController "RPageStatusController")
* lib_imageLoader_interface、lib_imageLoader_glide：Android 图片加载库: [查看这里](https://github.com/itrenjunhua/ImageLoader "图片加载库封装")
    * lib_imageLoader_interface 库定义图片加载的接口
    * lib_imageLoader_glide 库为lib_imageLoader_interface的一个实现库。
* lib_loading_dialog：加载进度框的封装
* lib_android_view：Android常用控件封装。[查看这里](https://github.com/itrenjunhua/AndroidView "AndroidView")
    * RecyclerView 基本使用、Adapter 的以及 分割线的绘制类封装。[查看这里](https://github.com/itrenjunhua/RRecyclerView "RRecyclerView")
* lib_android_cache：缓存库，可以缓存各种类型数据。[查看这里](https://github.com/itrenjunhua/CacheUtils "缓存库封装")

