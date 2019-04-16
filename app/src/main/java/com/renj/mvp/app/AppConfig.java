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
     * 阿凡达数据 APP_KEY
     */
    String APP_KEY = "23860680610142ada1684c5aac79a2f5";
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
