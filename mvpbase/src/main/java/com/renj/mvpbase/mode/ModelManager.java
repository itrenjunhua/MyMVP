package com.renj.mvpbase.mode;


import android.support.annotation.NonNull;

import com.renj.common.utils.CheckUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2018-08-17   10:46
 * <p>
 * 描述：Model 层管理类，所有对 Model 层的操作都通过 {@link ModelManager} 类实现
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ModelManager {
    private final static ModelManager INSTANCE = new ModelManager();
    // 保存实例
    private static Map<Class, IMvpHttpHelper> iHttpHelperMap = new HashMap<>();
    private static Map<Class, IMvpDBHelper> idbHelperMap = new HashMap<>();
    private static Map<Class, IMvpFileHelper> iFileHelperMap = new HashMap<>();

    private ModelManager() {
    }

    @org.jetbrains.annotations.Contract(pure = true)
    public static ModelManager newInstance() {
        return INSTANCE;
    }

    //=========================================== 分割线 ===========================================//

    /**
     * 增加 网络请求接口实现类 实例
     *
     * @param httpHelper
     * @param <T>        T extends {@link IMvpHttpHelper}
     * @return
     */
    public <T extends IMvpHttpHelper> ModelManager addHttpHelper(@NonNull T httpHelper) {
        if (CheckUtils.isNull(httpHelper)) {
            throw new NullPointerException("参数 httpHelper 不能为 null");
        }
        iHttpHelperMap.put(httpHelper.getClass(), httpHelper);
        return INSTANCE;
    }

    /**
     * 获取实例，但是该实例是通过 {@link #addHttpHelper(IMvpHttpHelper)} 方法增加过的，否则返回 {@code null}
     *
     * @param tClass
     * @param <T>    T extends {@link IMvpHttpHelper}
     * @return
     */
    public <T extends IMvpHttpHelper> T getHttpHelper(Class<T> tClass) {
        return (T) iHttpHelperMap.get(tClass);
    }

    //=========================================== 分割线 ===========================================//

    /**
     * 增加 数据库操作实现类 实例
     *
     * @param dbHelper
     * @param <T>      T extends {@link IMvpDBHelper}
     * @return
     */
    public <T extends IMvpDBHelper> ModelManager addDBHelper(@NonNull T dbHelper) {
        if (CheckUtils.isNull(dbHelper)) {
            throw new NullPointerException("参数 dbHelper 不能为 null");
        }
        idbHelperMap.put(dbHelper.getClass(), dbHelper);
        return INSTANCE;
    }

    /**
     * 获取实例，但是该实例是通过 {@link #addDBHelper(IMvpDBHelper)} 方法增加过的，否则返回 {@code null}
     *
     * @param tClass
     * @param <T>    T extends {@link IMvpDBHelper}
     * @return
     */
    public <T extends IMvpDBHelper> T getDBHelper(Class<T> tClass) {
        return (T) idbHelperMap.get(tClass);
    }

    //=========================================== 分割线 ===========================================//

    /**
     * 增加 文件操作实现类 实例
     *
     * @param fileHelper
     * @param <T>        T extends {@link IMvpFileHelper}
     * @return
     */
    public <T extends IMvpFileHelper> ModelManager addFileHelper(@NonNull T fileHelper) {
        if (CheckUtils.isNull(fileHelper)) {
            throw new NullPointerException("参数 fileHelper 不能为 null");
        }
        iFileHelperMap.put(fileHelper.getClass(), fileHelper);
        return INSTANCE;
    }

    /**
     * 获取实例，但是该实例是通过 {@link #addFileHelper(IMvpFileHelper)} 方法增加过的，否则返回 {@code null}
     *
     * @param tClass
     * @param <T>    T extends {@link IMvpFileHelper}
     * @return
     */
    public <T extends IMvpFileHelper> T getFileHelper(Class<T> tClass) {
        return (T) iFileHelperMap.get(tClass);
    }

}
