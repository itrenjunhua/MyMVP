package com.renj.mvp.utils.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.renj.mvp.utils.MyLogger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2017-12-14   14:42
 * <p>
 * 描述：缓存管理工具类，提供缓存和获取 字符串、字节数组、Drawable 和 Bitmap 内容的方法，同时支持指定缓存时间，缓存时间单位为 秒(s)
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class CacheManageUtils {
    /**
     * 缓存路径
     */
    static File CACHE_PATH;
    /**
     * 缓存大小检查和删除文件线程
     */
    static RCacheSizeControl RCACHE_SIZE_CONTROL;
    /**
     * 每秒的毫秒数
     */
    private static final long SECOND = 1000;
    /**
     * CacheManageUtils 实例对象
     */
    private static CacheManageUtils instance;

    private CacheManageUtils(Context context, String fileName) {
        CACHE_PATH = new File(context.getCacheDir(), fileName);
        if (!CACHE_PATH.exists() || !CACHE_PATH.isDirectory())
            CACHE_PATH.mkdir();

        RCACHE_SIZE_CONTROL = new RCacheSizeControl();
    }

    /**
     * 初始化缓存管理工具类，在使用该缓存管理工具类前必须先调用 {@link #initCacheUtil(Context)} 方法(使用默认缓存目录名'ACache')
     * 或者 {@link #initCacheUtil(Context, String)} 方法(可以指定缓存目录名)进行初始化，建议在 Application 中调用。
     *
     * @param context 上下文
     */
    public static void initCacheUtil(@NonNull Context context) {
        initCacheUtil(context, "RCache");
    }

    /**
     * 初始化缓存管理工具类，在使用该缓存管理工具类前必须先调用 {@link #initCacheUtil(Context)} 方法(使用默认缓存目录名'ACache')
     * 或者 {@link #initCacheUtil(Context, String)} 方法(可以指定缓存目录名)进行初始化，建议在 Application 中调用。
     *
     * @param context  上下文
     * @param fileName 缓存目录的名称
     */
    public static void initCacheUtil(@NonNull Context context, @NonNull String fileName) {
        if (instance == null) {
            synchronized (CacheManageUtils.class) {
                if (instance == null) {
                    instance = new CacheManageUtils(context, fileName);
                }
            }
        }
    }

    /**
     * 获取 {@link CacheManageUtils} 实例对象，在调用该方法前，必须先调用 {@link #initCacheUtil(Context)} 方法(使用默认缓存目录名'ACache')
     * 或者 {@link #initCacheUtil(Context, String)} 方法(可以指定缓存目录名)进行初始化，建议在 Application 中调用。
     *
     * @return {@link CacheManageUtils} 实例对象
     * @throws IllegalStateException 在调用该方法前没有调用 {@link #initCacheUtil(Context)} 方法(使用默认缓存目录名'ACache')
     *                               或者 {@link #initCacheUtil(Context, String)} 方法(可以指定缓存目录名)进行初始化时抛出。
     */
    public static CacheManageUtils newInstance() {
        if (instance == null)
            throw new IllegalStateException("没有对 CacheManageUtils 进行初始化，需要先调用 CacheManageUtils.initCacheUtil(Context) " +
                    "或 CacheManageUtils.initCacheUtil(Context，String) 方法。建议在 Application 中调用。");
        return instance;
    }

    /**
     * 缓存字符串({@link String})内容，指定缓存时间，<b>注意：缓存时间单位为 秒(S)</b>
     *
     * @param key     缓存键名称，同时用于获取缓存
     * @param value   需要缓存的字符串({@link String})内容
     * @param outtime 有效时间，<b>注意：缓存时间单位为 秒(S)</b>
     */
    public void put(@NonNull String key, @NonNull String value, @NonNull long outtime) {
        put(key, RCacheOperatorUtils.addDateInfo(value, outtime * SECOND));
    }

    /**
     * 缓存 {@link JSONObject} 对象
     *
     * @param key        缓存键名称，同时用于获取缓存
     * @param jsonObject 需要缓存的 {@link JSONObject} 对象
     */
    public void put(@NonNull String key, @NonNull JSONObject jsonObject) {
        put(key, jsonObject.toString());
    }

    /**
     * 缓存 {@link JSONObject} 对象，指定缓存时间，<b>注意：缓存时间单位为 秒(S)</b>
     *
     * @param key        缓存键名称，同时用于获取缓存
     * @param jsonObject 需要缓存的  {@link JSONObject} 对象
     * @param outtime    有效时间，<b>注意：缓存时间单位为 秒(S)</b>
     */
    public void put(@NonNull String key, @NonNull JSONObject jsonObject, @NonNull long outtime) {
        put(key, RCacheOperatorUtils.addDateInfo(jsonObject.toString(), outtime * SECOND));
    }

    /**
     * 缓存 {@link JSONArray} 对象
     *
     * @param key       缓存键名称，同时用于获取缓存
     * @param jsonArray 需要缓存的  {@link JSONArray} 对象
     */
    public void put(@NonNull String key, @NonNull JSONArray jsonArray) {
        put(key, jsonArray.toString());
    }

    /**
     * 缓存 {@link JSONArray} 对象，指定缓存时间，<b>注意：缓存时间单位为 秒(S)</b>
     *
     * @param key       缓存键名称，同时用于获取缓存
     * @param jsonArray 需要缓存的  {@link JSONArray} 对象
     * @param outtime   有效时间，<b>注意：缓存时间单位为 秒(S)</b>
     */
    public void put(@NonNull String key, @NonNull JSONArray jsonArray, @NonNull long outtime) {
        put(key, RCacheOperatorUtils.addDateInfo(jsonArray.toString(), outtime * SECOND));
    }

    /**
     * 缓存字节数组(byte[])，指定缓存时间，<b>注意：缓存时间单位为 秒(S)</b>
     *
     * @param key     缓存键名称，同时用于获取缓存
     * @param bytes   需要缓存的字节数组(byte[])
     * @param outtime 有效时间，<b>注意：缓存时间单位为 秒(S)</b>
     */
    public void put(@NonNull String key, @NonNull byte[] bytes, @NonNull long outtime) {
        put(key, RCacheOperatorUtils.addDateInfo(bytes, outtime * SECOND));
    }

    /**
     * 缓存 {@link Bitmap} 对象
     *
     * @param key    缓存键名称，同时用于获取缓存
     * @param bitmap 需要缓存的  {@link Bitmap} 对象
     */
    public void put(@NonNull String key, @NonNull Bitmap bitmap) {
        put(key, RCacheOperatorUtils.bitmapToBytes(bitmap));
    }

    /**
     * 缓存 {@link Bitmap} 对象，指定缓存时间，<b>注意：缓存时间单位为 秒(S)</b>
     *
     * @param key     缓存键名称，同时用于获取缓存
     * @param bitmap  需要缓存的 {@link Bitmap} 对象
     * @param outtime 有效时间，<b>注意：缓存时间单位为 秒(S)</b>
     */
    public void put(@NonNull String key, @NonNull Bitmap bitmap, @NonNull long outtime) {
        put(key, RCacheOperatorUtils.addDateInfo(RCacheOperatorUtils.bitmapToBytes(bitmap), outtime * SECOND));
    }

    /**
     * 缓存 {@link Drawable} 对象
     *
     * @param key      缓存键名称，同时用于获取缓存
     * @param drawable 需要缓存的  {@link Drawable} 对象
     */
    public void put(@NonNull String key, @NonNull Drawable drawable) {
        put(key, RCacheOperatorUtils.drawableToBitmap(drawable));
    }

    /**
     * 缓存 {@link Drawable} 对象，指定缓存时间，<b>注意：缓存时间单位为 秒(S)</b>
     *
     * @param key      缓存键名称，同时用于获取缓存
     * @param drawable 需要缓存的 {@link Drawable} 对象
     * @param outtime  有效时间，<b>注意：缓存时间单位为 秒(S)</b>
     */
    public void put(@NonNull String key, @NonNull Drawable drawable, @NonNull long outtime) {
        put(key, RCacheOperatorUtils.drawableToBitmap(drawable), outtime);
    }

    /**
     * 缓存 {@link Serializable} 对象
     *
     * @param key   缓存键名称，同时用于获取缓存
     * @param value 需要缓存的  {@link Serializable} 对象
     */
    public void put(@NonNull String key, @NonNull Serializable value) {
        put(key, value, -1);
    }

    /**
     * 缓存 {@link Serializable} 对象，指定缓存时间，<b>注意：缓存时间单位为 秒(S)</b>
     *
     * @param key     缓存键名称，同时用于获取缓存
     * @param value   需要缓存的 {@link Serializable} 对象
     * @param outtime 有效时间，<b>注意：缓存时间单位为 秒(S)</b>
     */
    public void put(@NonNull String key, @NonNull Serializable value, @NonNull long outtime) {
        if (value == null) return;

        ByteArrayOutputStream byteArrayOutputStream = null;
        ObjectOutputStream objectOutputStream = null;

        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(value);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            if (outtime == -1) {
                put(key, bytes);
            } else {
                put(key, bytes, outtime);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 缓存字符串({@link String})内容
     *
     * @param key   缓存键名称，同时用于获取缓存
     * @param value 需要缓存的字符串内容({@link String})
     */
    public void put(@NonNull String key, @NonNull String value) {
        if (TextUtils.isEmpty(value)) return;

        File file = RCacheOperatorUtils.spliceFile(key);
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(value);
            bufferedWriter.flush();

            // 检查缓存大小
            RCacheOperatorUtils.checkCacheSize();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 缓存字节数组(byte[])
     *
     * @param key   缓存键名称，同时用于获取缓存
     * @param bytes 需要缓存的字节数组(byte[])
     */
    public void put(@NonNull String key, @NonNull byte[] bytes) {
        if (bytes == null || bytes.length == 0) return;

        File file = RCacheOperatorUtils.spliceFile(key);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes);
            fileOutputStream.flush();

            // 检查缓存大小
            RCacheOperatorUtils.checkCacheSize();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void putOnNewThread(@NonNull String key, @NonNull String value) {
        putOnNewThread(key, value, -1);
    }

    public void putOnNewThread(@NonNull final String key, @NonNull final String value, @NonNull final long outtime) {
        CacheThreadResult.<String>create().runOnNewThread(new CacheThreadResult.CacheCallBack<String>() {
            @Override
            public String execute() {
                MyLogger.i("Put Data Threa => " + Thread.currentThread());
                if (outtime == -1) put(key, value);
                else put(key, RCacheOperatorUtils.addDateInfo(value, outtime));
                return null;
            }
        });
    }

    /**
     * 获取缓存的 {@link JSONObject} 对象，没有则返回 {@code null}
     *
     * @param key 缓存时的键名称
     * @return 缓存的 {@link JSONObject} 对象，没有则返回 {@code null}
     */
    public JSONObject getAsJsonObjct(@NonNull String key) {
        try {
            return new JSONObject(getAsString(key));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取缓存的 {@link JSONArray} 对象，没有则返回 {@code null}
     *
     * @param key 缓存时的键名称
     * @return 缓存的 {@link JSONArray} 对象，没有则返回 {@code null}
     */
    public JSONArray getAsJsonArray(@NonNull String key) {
        try {
            return new JSONArray(getAsString(key));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取缓存的 {@link Bitmap} 对象，没有则返回 {@code null}
     *
     * @param key 缓存时的键名称
     * @return 缓存的 {@link Bitmap} 对象，没有则返回 {@code null}
     */
    public Bitmap getAsBitmap(@NonNull String key) {
        byte[] bytes = getAsBinary(key);
        return RCacheOperatorUtils.bytesToBitmap(bytes);
    }

    /**
     * 获取缓存的 {@link Drawable} 对象，没有则返回 {@code null}
     *
     * @param key 缓存时的键名称
     * @return 缓存的 {@link Drawable} 对象，没有则返回 {@code null}
     */
    public Drawable getAsDrawable(@NonNull String key) {
        Bitmap bitmap = getAsBitmap(key);
        return RCacheOperatorUtils.bitmapToDrawable(bitmap);
    }

    /**
     * 获取缓存的 {@link Serializable} 对象，没有则返回 {@code null}
     *
     * @param key 缓存时的键名称
     * @return 缓存的 {@link Serializable} 对象，没有则返回 {@code null}
     */
    public Object getAsObject(@NonNull String key) {
        byte[] bytes = getAsBinary(key);
        if (bytes == null || bytes.length == 0) return null;

        ByteArrayInputStream byteArrayInputStream = null;
        ObjectInputStream objectInputStream = null;

        try {
            byteArrayInputStream = new ByteArrayInputStream(bytes);
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取缓存的字符串内容({@link String})，没有则返回 {@code ""}
     *
     * @param key 缓存时的键名称
     * @return 缓存的字符串内容({@link String})，没有则返回 {@code ""}
     */
    public String getAsString(@NonNull String key) {
        File file = RCacheOperatorUtils.spliceFile(key);
        if (!file.exists()) return "";

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String tResult = stringBuilder.toString();
            // 是否包含有效期
            if (RCacheOperatorUtils.isTimeLimit(tResult)) {
                String resultVaule = RCacheOperatorUtils.clearDateInfo(tResult);
                // 判断是否过期，如果已过期就删除该文件
                if (RCacheConfig.OUT_TIME_FLAG.equals(resultVaule)) {
                    RCacheOperatorUtils.deleteFile(file);
                    return "";
                } else {
                    return resultVaule;
                }
            }

            return tResult;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取缓存的字节数组(byte[])，没有则返回 {@code null}
     *
     * @param key 缓存时的键名称
     * @return 缓存的字节数组(byte[])，没有则返回 {@code null}
     */
    public byte[] getAsBinary(@NonNull String key) {
        File file = RCacheOperatorUtils.spliceFile(key);
        if (!file.exists()) return null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] bys = new byte[1024 * 5];
            int len = 0;
            while ((len = fileInputStream.read(bys)) != -1) {
                outputStream.write(bys, 0, len);
            }
            byte[] readResult = outputStream.toByteArray();
            // 是否包含有效期
            if (RCacheOperatorUtils.isTimeLimit(readResult)) {
                byte[] resultValue = RCacheOperatorUtils.clearDateInfo(readResult);
                // 获取分隔符的字节形式
                byte[] outTimeBytes = RCacheOperatorUtils.toBytes(RCacheConfig.OUT_TIME_FLAG);
                // 判断是否过期，如果已过期就删除该文件
                if (RCacheOperatorUtils.equalsBytes(outTimeBytes, resultValue)) {
                    RCacheOperatorUtils.deleteFile(file);
                    return null;
                } else {
                    return resultValue;
                }
            }
            return readResult;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public CacheThreadResult<String> getAsStringOnNewThread(@NonNull final String key) {
        return CacheThreadResult.<String>create().runOnNewThread(new CacheThreadResult.CacheCallBack<String>() {
            @Override
            public String execute() {
                MyLogger.i("Get Data Threa => " + Thread.currentThread());
                return getAsString(key);
            }
        });
    }
}
