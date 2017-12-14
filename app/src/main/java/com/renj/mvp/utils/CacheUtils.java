package com.renj.mvp.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

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
    private static String EXTEND_NAME = ".cache";

    private static RCacheSizeManageThread rCacheSizeManageThread;

    private CacheUtils(Context context, String fileName) {
        cachePath = new File(context.getCacheDir(), fileName);
        if (!cachePath.exists() || !cachePath.isDirectory())
            cachePath.mkdir();

        rCacheSizeManageThread = new RCacheSizeManageThread();
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

    public void putString(@NonNull String key, @NonNull String value, long outtime) {
        putString(key, RCacheManage.addDateInfo(value, outtime * SECOND));
    }

    public void putString(@NonNull String key, @NonNull String value) {
        File file = RCacheManage.spliceFile(key);
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

    public String getString(@NonNull String key) {
        File file = RCacheManage.spliceFile(key);
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return RCacheManage.clearDateInfo(stringBuilder.toString());
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

    /**
     * 缓存管理类
     */
    static class RCacheManage {
        // 时间和具体内容之间的分隔符，尽量避免具体内容中可能出现的值
        private static String splitValue = "&-=SPLIT_char_VALUE=-&";

        /**
         * 基于缓存路径 {@link #cachePath} 统一拼接文件扩展名
         *
         * @param fileName 文件名
         * @return 带扩展名的 File 对象
         */
        @NonNull
        public static File spliceFile(@NonNull String fileName) {
            return new File(cachePath, fileName.hashCode() + EXTEND_NAME);
        }

        /**
         * 增加有效期
         *
         * @param value   保存内容
         * @param outtime 有效时间
         * @return 按照特殊格式增加了有效时间的内容(增加的时间表示最终有效期)
         */
        public static String addDateInfo(@NonNull String value, long outtime) {
            return (System.currentTimeMillis() + outtime) + splitValue + value;
        }

        /**
         * 清除时间信息
         *
         * @param value
         * @return 返回清除过期时间之后的内容
         */
        public static String clearDateInfo(@NonNull String value) {
            if (value != null) {
                if (value.contains(splitValue)) {
                    String[] strings = value.split(splitValue);
                    if (strings.length == 2) {
                        if (System.currentTimeMillis() <= Long.parseLong(strings[0]))
                            return strings[1];
                    }
                } else {
                    return value;
                }
            }
            return "";
        }

        /**
         * 计算文件大小，如果是文件夹返回0
         *
         * @param listFile
         * @return 文件大小，如果是文件夹返回 0
         */
        public static long calculateFileSize(@NonNull File listFile) {
            if (listFile != null && listFile.exists() && listFile.isFile())
                return listFile.length();
            return 0;
        }

        /**
         * 删除文件
         *
         * @param file
         * @return 删除的文件长度
         */
        public static long deleteFile(@NonNull File file) {
            long length = file.length();
            file.delete();
            return length;
        }

        /**
         * 检查缓存文件大小
         */
        public static void checkCacheSize() {
            if (rCacheSizeManageThread != null && !rCacheSizeManageThread.isAlive()) {
                synchronized (CacheUtils.class) {
                    if (rCacheSizeManageThread != null && !rCacheSizeManageThread.isAlive())
                        rCacheSizeManageThread.start();
                }
            }
        }
    }

    /**
     * 缓存大小控制线程，用于控制缓存大小，当超过指定大小时，就删除老的文件
     */
    class RCacheSizeManageThread extends Thread {
        // 用于临时保存所有的缓存文件对象
        private List<File> cacheFiles = Collections.synchronizedList(new LinkedList<File>());
        // 缓存占用的大小
        private AtomicLong cacheSize = new AtomicLong();

        @Override
        public void run() {
            handlerCacheSize();
        }

        private void handlerCacheSize() {
            cacheSize();

            if (cacheSize.get() > CACHE_SIZE)
                deleteFileToCacheSize();
        }

        private void cacheSize() {
            if (cachePath == null || !cachePath.exists() || !cachePath.isDirectory()) return;

            File[] listFiles = cachePath.listFiles();
            for (File listFile : listFiles) {
                long fileSize = RCacheManage.calculateFileSize(listFile);
                cacheFiles.add(listFile);
                cacheSize.addAndGet(fileSize);
            }
        }

        private void deleteFileToCacheSize() {
            // 按修改时间进行排序
            Collections.sort(cacheFiles, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    long l = o1.lastModified() - o2.lastModified();
                    return l > 0 ? 1 : -1;
                }
            });

            List<File> deleteFiles = new LinkedList<>();
            for (File cacheFile : cacheFiles) {
                long temp = cacheSize.addAndGet(-RCacheManage.calculateFileSize(cacheFile));
                deleteFiles.add(cacheFile);
                if (temp <= CACHE_SIZE)
                    break;
            }

            for (File deleteFile : deleteFiles) {
                RCacheManage.deleteFile(deleteFile);
                cacheFiles.remove(deleteFile);
            }
        }
    }
}
