package com.renj.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.Set;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2017-06-27   10:22
 * <p>
 * 描述：操作 SharedPreferences 的工具类，使用单例模式。<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * 可在MyApplication中调用 <code>SPUtils.initConfig(new SPUtils.SPConfig.Builder().spMode(Context.MODE_PRIVATE).spName("filiName").build())</code>
 * 方法设置 SharedPreferences 的名字和访问模式。<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * 不设置表示使用默认值 name：config_sp mode：Context.MODE_PRIVATE.
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class SPUtils {
    private static volatile SPUtils instance;
    private static SharedPreferences mSharedPreferences;
    private static SPConfig mSpConfig;
    private static String DEFAULT_FILENAME = "config_sp";
    private static int DEFAULT_MODE = Context.MODE_PRIVATE;

    /**
     * 初始化 SharedPreferences 的配置，在MyApplication中调用即可
     *
     * @param spConfig 配置类 {@link SPConfig} 对象，使用 <code>new SPUtils.SPConfig.Builder().spName(指定文件名).spMode(指定访问模式).build()</code>方式创建
     */
    public static void initConfig(@NonNull SPConfig spConfig) {
        if (null != spConfig) {
            mSpConfig = spConfig;
            if (TextUtils.isEmpty(spConfig.SP_NAME)) {
                mSpConfig.SP_NAME = DEFAULT_FILENAME;
                mSpConfig.SP_MODE = DEFAULT_MODE;
                Logger.v("SPConfig 没有指定文件名，使用默认值。name：config_sp mode：Context.MODE_PRIVATE");
            } else {
                mSpConfig.SP_NAME = spConfig.SP_NAME;
                mSpConfig.SP_MODE = spConfig.SP_MODE;
            }
        } else {
            defaultConfig(); // 创建默认配置
            Logger.v("SPConfig 为null，使用默认值。name：config_sp mode：Context.MODE_PRIVATE");
        }
    }

    /**
     * 创建 {@link SPUtils} 实例对象
     *
     * @return SPUtils 实例
     */
    public static SPUtils newInstance() {
        if (null == instance) {
            synchronized (SPUtils.class) {
                if (null == instance) {
                    if (null == mSpConfig) // 没有调用initConfig()方法
                        defaultConfig();

                    instance = new SPUtils();
                    mSharedPreferences = UIUtils.getContext()
                            .getSharedPreferences(mSpConfig.SP_NAME, mSpConfig.SP_MODE);
                }
            }
        }
        return instance;
    }

    /**
     * 创建默认配置
     */
    private static void defaultConfig() {
        mSpConfig = new SPConfig.Builder()
                .spName(DEFAULT_FILENAME)
                .spMode(DEFAULT_MODE)
                .build();
    }

    /**
     * 保存 boolean 类型的值
     *
     * @param key   键名
     * @param value 值
     * @return 是否成功，true 成功
     */
    public boolean putBoolean(@NonNull String key, @NonNull boolean value) {
        return mSharedPreferences.edit().putBoolean(key, value).commit();
    }

    /**
     * 保存 String 类型的值
     *
     * @param key   键名
     * @param value 值
     * @return 是否成功，true 成功
     */
    public boolean putString(@NonNull String key, @NonNull String value) {
        return mSharedPreferences.edit().putString(key, value).commit();
    }

    /**
     * 保存 int 类型的值
     *
     * @param key   键名
     * @param value 值
     * @return 是否成功，true 成功
     */
    public boolean putInt(@NonNull String key, @NonNull int value) {
        return mSharedPreferences.edit().putInt(key, value).commit();
    }

    /**
     * 保存 float 类型的值
     *
     * @param key   键名
     * @param value 值
     * @return 是否成功，true 成功
     */
    public boolean putFloat(@NonNull String key, @NonNull float value) {
        return mSharedPreferences.edit().putFloat(key, value).commit();
    }

    /**
     * 保存 long 类型的值
     *
     * @param key   键名
     * @param value 值
     * @return 是否成功，true 成功
     */
    public boolean putLong(@NonNull String key, @NonNull long value) {
        return mSharedPreferences.edit().putLong(key, value).commit();
    }

    /**
     * 保存 Set<String> 类型的值
     *
     * @param key    键名
     * @param values 值
     * @return 是否成功，true 成功
     */
    public boolean putStringSet(@NonNull String key, @NonNull Set<String> values) {
        return mSharedPreferences.edit().putStringSet(key, values).commit();
    }

    /**
     * 获取 boolean 类型的值
     *
     * @param key          键名
     * @param defaultValue 默认值，当没有获取到值时返回默认值
     * @return 保存的值，没有获取到时返回默认值
     */
    public boolean getBoolean(@NonNull String key, @NonNull boolean defaultValue) {
        return mSharedPreferences.getBoolean(key, defaultValue);
    }

    /**
     * 获取 String 类型的值
     *
     * @param key          键名
     * @param defaultValue 默认值，当没有获取到值时返回默认值
     * @return 保存的值，没有获取到时返回默认值
     */
    public String getString(@NonNull String key, @NonNull String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }

    /**
     * 获取 int 类型的值
     *
     * @param key          键名
     * @param defaultValue 默认值，当没有获取到值时返回默认值
     * @return 保存的值，没有获取到时返回默认值
     */
    public int getInt(@NonNull String key, @NonNull int defaultValue) {
        return mSharedPreferences.getInt(key, defaultValue);
    }

    /**
     * 获取 float 类型的值
     *
     * @param key          键名
     * @param defaultValue 默认值，当没有获取到值时返回默认值
     * @return 保存的值，没有获取到时返回默认值
     */
    public float getFloat(@NonNull String key, @NonNull float defaultValue) {
        return mSharedPreferences.getFloat(key, defaultValue);
    }

    /**
     * 获取 long 类型的值
     *
     * @param key          键名
     * @param defaultValue 默认值，当没有获取到值时返回默认值
     * @return 保存的值，没有获取到时返回默认值
     */
    public long getLong(@NonNull String key, @NonNull long defaultValue) {
        return mSharedPreferences.getLong(key, defaultValue);
    }

    /**
     * 获取 Set<String> 类型的值
     *
     * @param key          键名
     * @param defaultValue 默认值，当没有获取到值时返回默认值
     * @return 保存的值，没有获取到时返回默认值
     */
    public Set<String> getStringSet(@NonNull String key, @NonNull Set<String> defaultValue) {
        return mSharedPreferences.getStringSet(key, defaultValue);
    }

    /**
     * 根据键名移除指定的数据
     *
     * @param key 键名
     */
    public void remove(@NonNull String key) {
        mSharedPreferences.edit().remove(key).commit();
    }

    /**
     * 清除 {@link SharedPreferences} 中所有的数据
     */
    public void clear() {
        mSharedPreferences.edit().clear().commit();
    }

    /**
     * 用于配置 SharedPreferences 名字和模式的配置类，<br/>
     * 使用 <code>SPConfig spConfig = new SPUtils.SPConfig.Builder().build()</code>
     * 的方式创建对象
     */
    public static class SPConfig {
        private String SP_NAME;
        private int SP_MODE;

        private SPConfig(String spName, int spMode) {
            this.SP_NAME = spName;
            this.SP_MODE = spMode;
        }

        /**
         * 用于构建 {@link SPConfig} 类的 Builder 类
         */
        public static class Builder {
            private String spName;
            private int spMode;

            /**
             * 修改 SharedPreferences 的名字
             *
             * @param spName 名字
             * @return
             */
            public Builder spName(@NonNull String spName) {
                this.spName = spName;
                return this;
            }

            /**
             * 修改 SharedPreferences 的模式
             *
             * @param spMode 模式
             * @return
             */
            public Builder spMode(@NonNull int spMode) {
                this.spMode = spMode;
                return this;
            }

            /**
             * 构建 {@link SPConfig} 对象
             *
             * @return SpConfig 对象
             */
            public SPConfig build() {
                return new SPConfig(spName, spMode);
            }
        }
    }
}
