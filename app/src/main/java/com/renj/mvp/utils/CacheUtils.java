package com.renj.mvp.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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
    private static final long SECOND = 1000;
    private static final long MINUTE = SECOND * 60;
    private static final long HOUR = MINUTE * 60;
    private static final long DAY = HOUR * 24;
    /**
     * 指定缓存大小 默认大小12M
     */
    private static final long CACHE_SIZE = 1024 * 1024 * 12;

    private static CacheUtils instance;
    private static File cachePath;

    private CacheUtils(Context context, String fileName) {
        cachePath = new File(context.getCacheDir(), fileName);
        if (!cachePath.exists() || !cachePath.isDirectory())
            cachePath.mkdir();

        long totalSpace = cachePath.getTotalSpace();

        Log.i("CacheUtils", "----- " + (totalSpace > CACHE_SIZE));
    }

    public static void initCacheUtil(Context context) {
        initCacheUtil(context, "RCache");
    }

    public static void initCacheUtil(Context context, String fileName) {
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

    public void putString(String key, String value) {
        File file = new File(cachePath, key + ".cache");
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(value);
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null)
                    bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getString(String key) {
        File file = new File(cachePath, key + ".cache");
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (bufferedReader != null)
                    bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
