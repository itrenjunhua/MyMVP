package com.renj.utils.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.renj.utils.res.StringUtils;

import java.util.Set;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2017-06-27   10:22
 * <p>
 * 描述：操作 SharedPreferences 的工具类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class SPUtils {

    private static String mCurrentSharedPreferencesName;
    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor mSharedPreferencesEdit;

    /**
     * 根据name创建 SharedPreferences，默认模式 {@link Context#MODE_PRIVATE}
     *
     * @param name SharedPreferences 名称
     * @return
     */
    public static SPUtils createSharedPreferences(String name) {
        return createSharedPreferences(name, Context.MODE_PRIVATE);
    }

    /**
     * 根据name和mode创建 SharedPreferences
     *
     * @param name SharedPreferences 名称
     * @param mode SharedPreferences mode
     * @return
     */
    public static SPUtils createSharedPreferences(String name, int mode) {
        return new SPUtils(name, mode);
    }


    private SPUtils(String name, int mode) {
        if (mSharedPreferences == null || !StringUtils.isEquals(mCurrentSharedPreferencesName, name)) {
            mSharedPreferences = UIUtils.getContext().getSharedPreferences(name, mode);
            mSharedPreferencesEdit = mSharedPreferences.edit();
            mCurrentSharedPreferencesName = name;
        }
    }


    // ------------------- 每次保存一条数据方法 ------------------- //

    /**
     * 保存 boolean 类型的值。<b>保存当前数据直接commit，如果需要连续保存多条时，建议使用 addXxx() 系列方法。</b>
     *
     * @param key   键名
     * @param value 值
     * @return 是否成功，true 成功
     */
    public boolean putBoolean(@NonNull String key, @NonNull boolean value) {
        return mSharedPreferencesEdit.putBoolean(key, value).commit();
    }

    /**
     * 保存 String 类型的值。<b>保存当前数据直接commit，如果需要连续保存多条时，建议使用 addXxx() 系列方法。</b>
     *
     * @param key   键名
     * @param value 值
     * @return 是否成功，true 成功
     */
    public boolean putString(@NonNull String key, @NonNull String value) {
        return mSharedPreferencesEdit.putString(key, value).commit();
    }

    /**
     * 保存 int 类型的值。<b>保存当前数据直接commit，如果需要连续保存多条时，建议使用 addXxx() 系列方法。</b>
     *
     * @param key   键名
     * @param value 值
     * @return 是否成功，true 成功
     */
    public boolean putInt(@NonNull String key, @NonNull int value) {
        return mSharedPreferencesEdit.putInt(key, value).commit();
    }

    /**
     * 保存 float 类型的值。<b>保存当前数据直接commit，如果需要连续保存多条时，建议使用 addXxx() 系列方法。</b>
     *
     * @param key   键名
     * @param value 值
     * @return 是否成功，true 成功
     */
    public boolean putFloat(@NonNull String key, @NonNull float value) {
        return mSharedPreferencesEdit.putFloat(key, value).commit();
    }

    /**
     * 保存 long 类型的值。<b>保存当前数据直接commit，如果需要连续保存多条时，建议使用 addXxx() 系列方法。</b>
     *
     * @param key   键名
     * @param value 值
     * @return 是否成功，true 成功
     */
    public boolean putLong(@NonNull String key, @NonNull long value) {
        return mSharedPreferencesEdit.putLong(key, value).commit();
    }

    /**
     * 保存 Set<String> 类型的值。<b>保存当前数据直接commit，如果需要连续保存多条时，建议使用 addXxx() 系列方法。</b>
     *
     * @param key    键名
     * @param values 值
     * @return 是否成功，true 成功
     */
    public boolean putStringSet(@NonNull String key, @NonNull Set<String> values) {
        return mSharedPreferencesEdit.putStringSet(key, values).commit();
    }


    // ------------------- 每次增加一条数据方法 ------------------- //

    /**
     * 增加保存 boolean 类型的值。<b>注意：增加完所有要保存的数据之后，一定需要调用 {@link #commit()} 或者 {@link #apply()} 提交保存。</b>
     *
     * @param key   键名
     * @param value 值
     * @return
     */
    public SPUtils addBoolean(@NonNull String key, @NonNull boolean value) {
        mSharedPreferencesEdit.putBoolean(key, value);
        return this;
    }

    /**
     * 增加保存 String 类型的值。<b>注意：增加完所有要保存的数据之后，一定需要调用 {@link #commit()} 或者 {@link #apply()} 提交保存。</b>
     *
     * @param key   键名
     * @param value 值
     * @return
     */
    public SPUtils addString(@NonNull String key, @NonNull String value) {
        mSharedPreferencesEdit.putString(key, value);
        return this;
    }

    /**
     * 增加保存 int 类型的值。<b>注意：增加完所有要保存的数据之后，一定需要调用 {@link #commit()} 或者 {@link #apply()} 提交保存。</b>
     *
     * @param key   键名
     * @param value 值
     * @return
     */
    public SPUtils addInt(@NonNull String key, @NonNull int value) {
        mSharedPreferencesEdit.putInt(key, value);
        return this;
    }

    /**
     * 增加保存 float 类型的值。<b>注意：增加完所有要保存的数据之后，一定需要调用 {@link #commit()} 或者 {@link #apply()} 提交保存。</b>
     *
     * @param key   键名
     * @param value 值
     * @return
     */
    public SPUtils addFloat(@NonNull String key, @NonNull float value) {
        mSharedPreferencesEdit.putFloat(key, value);
        return this;
    }

    /**
     * 增加保存 long 类型的值。<b>注意：增加完所有要保存的数据之后，一定需要调用 {@link #commit()} 或者 {@link #apply()} 提交保存。</b>
     *
     * @param key   键名
     * @param value 值
     * @return
     */
    public SPUtils addLong(@NonNull String key, @NonNull long value) {
        mSharedPreferencesEdit.putLong(key, value);
        return this;
    }

    /**
     * 增加保存 Set<String> 类型的值。<b>注意：增加完所有要保存的数据之后，一定需要调用 {@link #commit()} 或者 {@link #apply()} 提交保存。</b>
     *
     * @param key    键名
     * @param values 值
     * @return
     */
    public SPUtils addStringSet(@NonNull String key, @NonNull Set<String> values) {
        mSharedPreferencesEdit.putStringSet(key, values);
        return this;
    }

    /**
     * 提交 addXxx 系列方法增加的数据
     */
    public void commit() {
        mSharedPreferencesEdit.commit();
    }

    /**
     * 提交 addXxx 系列方法增加的数据
     */
    public void apply() {
        mSharedPreferencesEdit.apply();
    }

    // ------------------- 获取数据数据方法 ------------------- //

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

    // ------------------- 移除和清除数据方法 ------------------- //

    /**
     * 根据键名移除指定的数据
     *
     * @param key 键名
     */
    public void remove(@NonNull String key) {
        mSharedPreferencesEdit.remove(key).commit();
    }

    /**
     * 清除 {@link SharedPreferences} 中所有的数据
     */
    public void clear() {
        mSharedPreferencesEdit.clear().commit();
    }
}
