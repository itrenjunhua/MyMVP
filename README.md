**当前分支为：RxJava+Retrofit实现快速搭建组件化MVP框架**

# MyMVP
将MVP框架基本代码和常用代码抽取成一个个独立的库，方便引入并快速搭建MVP项目框架；
同时方便替换底层实现。

[使用 RxJava+Retrofit实现快速搭建MVP框架](https://github.com/itrenjunhua/MyMVP/tree/master)

[使用 RxJava+Retrofit+Dagger实现快速搭建MVP框架](https://github.com/itrenjunhua/MyMVP/tree/dagger)

[使用 RxJava+Retrofit实现快速搭建组件化MVP框架](https://github.com/itrenjunhua/MyMVP/tree/master_component)

# 抽取的各个单独库功能说明

## mvp_mvpbase 库
MVP 框架的各个基类，定义MVP基本框架

## mvp_rxsupport 库
mvp_mvpbase 中各个基类的对应 RxJava 实现类。

## APP 项目 相关库/子module
各个子module作为 library 或者 application 的切换控制在  gradle.properties 文件的 isBuildAsLibrary 属性

* app：APP 主项目，壳工程
* app_lib_common：当前APP的公共类、资源、工具类以及ARouter路由工具类
* app_module_home：APP 项目的首页模块
* app_module_found：APP 项目的发现模块
* app_module_my：APP 项目的我的模块

## lib_http 库
网络通讯框架，使用 Retrofit 框架搭建。

其他封装的框架：

 [OkHttp 框架的二次封装](https://github.com/itrenjunhua/MyOkHttp "OkHttp 框架的二次封装")

 [Volley 框架的二次封装](https://github.com/itrenjunhua/RVolleyTest "Volley 框架的二次封装")

## lib_android_utils 库
Android常用工具类。[查看这里](https://github.com/itrenjunhua/AndroidUtils "AndroidUtils")

## lib_rpagestatuscontroller 库
Android 页面状态控制框架 [查看这里](https://github.com/itrenjunhua/RPageStatusController "RPageStatusController")

## lib_imageLoader_interface 库和 lib_imageLoader_glide 库
Android 图片加载库: [查看这里](https://github.com/itrenjunhua/ImageLoader "图片加载库封装")

 * lib_imageLoader_interface 库定义图片加载的接口
 * lib_imageLoader_glide 库为lib_imageLoader_interface的一个实现库。

## lib_loading_dialog 库
加载进度框的封装

## lib_android_view 库
Android常用控件封装。[查看这里](https://github.com/itrenjunhua/AndroidView "AndroidView")

RecyclerView Adapter 的以及 分割线的绘制类封装。[查看这里](https://github.com/itrenjunhua/RRecyclerView "RRecyclerView")

## lib_android_cache 库
缓存库，可以缓存各种类型数据。[查看这里](https://github.com/itrenjunhua/CacheUtils "缓存库封装")





