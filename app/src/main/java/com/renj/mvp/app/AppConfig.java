package com.renj.mvp.app;

import com.renj.common.CommonUtils;
import com.renj.common.utils.PackageUtils;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2017-12-12   17:13
 * <p>
 * 描述：APP全局配置类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public interface AppConfig {
    /**
     * 是否 debug 版本，true 是调试版本；false 是正式版本
     */
    boolean IS_DEBUG = CommonUtils.isDebug();
    /**
     * APP访问网络token
     */
    String APP_TOKEN_KEY = "token";
    String APP_TOKEN_VALUE = "Renj_1994_(token)@20190705014318@(TOKEN)_Android";
    /**
     * 数据库名字
     */
    String DATABASE_NAME = "mvp.db";
    /**
     * APP 版本名
     */
    String versionName = PackageUtils.getVersionName();
    /**
     * APP 版本号
     */
    int versionCode = PackageUtils.getVersionCode();
    /**
     * APP的包名
     */
    String packageName = PackageUtils.getPackageName();
}
