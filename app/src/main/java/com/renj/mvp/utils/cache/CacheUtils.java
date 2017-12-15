package com.renj.mvp.utils.cache;

import android.content.Context;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2017-12-14   14:42
 * <p>
 * 描述：缓存工具类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class CacheUtils {
    private static CacheUtils instance;

    private CacheUtils(Context context, String fileName) {
        RCacheConfig.CACHE_PATH = new File(context.getCacheDir(), fileName);
        if (!RCacheConfig.CACHE_PATH.exists() || !RCacheConfig.CACHE_PATH.isDirectory())
            RCacheConfig.CACHE_PATH.mkdir();

        RCacheConfig.RCACHE_SIZE_CONTROL = new RCacheSizeControl();
    }

    public static void initCacheUtil(@NonNull Context context) {
        initCacheUtil(context, "RCache");
    }

    public static void initCacheUtil(@NonNull Context context, @NonNull String fileName) {
        if (instance == null) {
            synchronized (CacheUtils.class) {
                if (instance == null) {
                    instance = new CacheUtils(context, fileName);
                }
            }
        }
    }

    public static CacheUtils newInstance() {
        if (instance == null)
            throw new IllegalStateException("没有对 CacheUtils 进行初始化，需要先调用 initCacheUtil(Context) " +
                    "或 initCacheUtil(Context，String) 方法。建议在 Application 中调用。");
        return instance;
    }

    public void put(@NonNull String key, @NonNull JSONObject jsonObject) {
        put(key, jsonObject.toString());
    }

    public void put(@NonNull String key, @NonNull JSONObject jsonObject, @NonNull long outtime) {
        put(key, RCacheManage.addDateInfo(jsonObject.toString(), outtime));
    }

    public JSONObject getAsJsonObjct(@NonNull String key) {
        try {
            return new JSONObject(getAsString(key));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void put(@NonNull String key, @NonNull JSONArray jsonArray) {
        put(key, jsonArray.toString());
    }

    public void put(@NonNull String key, @NonNull JSONArray jsonArray, @NonNull long outtime) {
        put(key, RCacheManage.addDateInfo(jsonArray.toString(), outtime));
    }

    public JSONArray getAsJsonArray(@NonNull String key) {
        try {
            return new JSONArray(getAsString(key));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void put(@NonNull String key, @NonNull String value, @NonNull long outtime) {
        put(key, RCacheManage.addDateInfo(value, outtime * RCacheConfig.SECOND));
    }

    public void put(@NonNull String key, @NonNull String value) {
        File file = RCacheManage.spliceFile(key);
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(value);
            bufferedWriter.flush();

            // 检查缓存大小
            RCacheManage.checkCacheSize();
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

    public String getAsString(@NonNull String key) {
        File file = RCacheManage.spliceFile(key);
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
            if (RCacheManage.isTimeLimit(tResult)) {
                String resultVaule = RCacheManage.clearDateInfo(tResult);
                // 判断是否过期，如果已过期就删除该文件
                if (RCacheConfig.OUT_TIME_FLAG.equals(resultVaule)) {
                    RCacheManage.deleteFile(file);
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

    public void put(@NonNull String key, @NonNull byte[] value) {
        File file = RCacheManage.spliceFile(key);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(value);
            fileOutputStream.flush();

            // 检查缓存大小
            RCacheManage.checkCacheSize();
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

    public byte[] getAsBinary(@NonNull String key) {
        File file = RCacheManage.spliceFile(key);
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
            if (RCacheManage.isTimeLimit(readResult)) {
                byte[] resultValue = RCacheManage.clearDateInfo(readResult);
                // 获取分隔符的字节形式
                byte[] outTimeBytes = RCacheManage.toBytes(RCacheConfig.OUT_TIME_FLAG);
                // 判断是否过期，如果已过期就删除该文件
                if (RCacheManage.equalsBytes(outTimeBytes, resultValue)) {
                    RCacheManage.deleteFile(file);
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
}
