package com.renj.mvp.utils;

import android.content.SharedPreferences;

import com.renj.mvp.app.MyApplication;

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
 * 可使用 <code>SPUtils.newInstance(new SPUtils.SPConfig.Builder().spMode(Context.MODE_PRIVATE).spName("filiName").build())</code>
 * 的创建方式设置 SharedPreferences 的名字和模式。<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * <b>注意：使用单例模式，只有初次配置时生效。</b>
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class SPUtils {
    private static SPUtils INSTANCE;
    private static SharedPreferences mSharedPreferences;

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
            public Builder spName(String spName) {
                this.spName = spName;
                return this;
            }

            /**
             * 修改 SharedPreferences 的模式
             *
             * @param spMode 模式
             * @return
             */
            public Builder spMode(int spMode) {
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

    /**
     * 创建 {@link SPUtils} 实例对象
     *
     * @return SPUtils 实例
     */
    public static SPUtils newInstance() {
        return newInstance(new SPConfig.Builder().build());
    }

    /**
     * 指定配置创建 {@link SPUtils} 实例对象
     *
     * @param spConfig 配置类 {@link SPConfig} 对象，使用 <code>new SPUtils.SPConfig.Builder().build()</code>方式创建
     * @return SPUtils 实例
     */
    public static SPUtils newInstance(SPConfig spConfig) {
        if (null == mSharedPreferences) {
            synchronized (SPUtils.class) {
                if (null == mSharedPreferences) {
                    INSTANCE = new SPUtils();
                    mSharedPreferences = MyApplication.applicationComponent
                            .getApplication()
                            .getSharedPreferences(spConfig.SP_NAME, spConfig.SP_MODE);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 保存 boolean 类型的值
     *
     * @param key   键名
     * @param value 值
     * @return 是否成功，true 成功
     */
    public boolean putBoolean(String key, boolean value) {
        return mSharedPreferences.edit().putBoolean(key, value).commit();
    }

    /**
     * 保存 String 类型的值
     *
     * @param key   键名
     * @param value 值
     * @return 是否成功，true 成功
     */
    public boolean putString(String key, String value) {
        return mSharedPreferences.edit().putString(key, value).commit();
    }

    /**
     * 保存 int 类型的值
     *
     * @param key   键名
     * @param value 值
     * @return 是否成功，true 成功
     */
    public boolean putInt(String key, int value) {
        return mSharedPreferences.edit().putInt(key, value).commit();
    }

    /**
     * 保存 float 类型的值
     *
     * @param key   键名
     * @param value 值
     * @return 是否成功，true 成功
     */
    public boolean putFloat(String key, float value) {
        return mSharedPreferences.edit().putFloat(key, value).commit();
    }

    /**
     * 保存 long 类型的值
     *
     * @param key   键名
     * @param value 值
     * @return 是否成功，true 成功
     */
    public boolean putLong(String key, long value) {
        return mSharedPreferences.edit().putLong(key, value).commit();
    }

    /**
     * 保存 Set<String> 类型的值
     *
     * @param key    键名
     * @param values 值
     * @return 是否成功，true 成功
     */
    public boolean putStringSet(String key, Set<String> values) {
        return mSharedPreferences.edit().putStringSet(key, values).commit();
    }

    /**
     * 获取 boolean 类型的值
     *
     * @param key          键名
     * @param defaultVaule 默认值，当没有获取到值时返回默认值
     * @return 保存的值，没有获取到时返回默认值
     */
    public boolean getBoolean(String key, boolean defaultVaule) {
        return mSharedPreferences.getBoolean(key, defaultVaule);
    }

    /**
     * 获取 String 类型的值
     *
     * @param key          键名
     * @param defaultVaule 默认值，当没有获取到值时返回默认值
     * @return 保存的值，没有获取到时返回默认值
     */
    public String getString(String key, String defaultVaule) {
        return mSharedPreferences.getString(key, defaultVaule);
    }

    /**
     * 获取 int 类型的值
     *
     * @param key          键名
     * @param defaultVaule 默认值，当没有获取到值时返回默认值
     * @return 保存的值，没有获取到时返回默认值
     */
    public int getInt(String key, int defaultVaule) {
        return mSharedPreferences.getInt(key, defaultVaule);
    }

    /**
     * 获取 float 类型的值
     *
     * @param key          键名
     * @param defaultVaule 默认值，当没有获取到值时返回默认值
     * @return 保存的值，没有获取到时返回默认值
     */
    public float getFloat(String key, float defaultVaule) {
        return mSharedPreferences.getFloat(key, defaultVaule);
    }

    /**
     * 获取 long 类型的值
     *
     * @param key          键名
     * @param defaultVaule 默认值，当没有获取到值时返回默认值
     * @return 保存的值，没有获取到时返回默认值
     */
    public long getLong(String key, long defaultVaule) {
        return mSharedPreferences.getLong(key, defaultVaule);
    }

    /**
     * 获取 Set<String> 类型的值
     *
     * @param key          键名
     * @param defaultVaule 默认值，当没有获取到值时返回默认值
     * @return 保存的值，没有获取到时返回默认值
     */
    public Set<String> getStringSet(String key, Set<String> defaultVaule) {
        return mSharedPreferences.getStringSet(key, defaultVaule);
    }
}
