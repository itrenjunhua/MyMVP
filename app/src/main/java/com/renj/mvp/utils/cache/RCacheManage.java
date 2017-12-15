package com.renj.mvp.utils.cache;

import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2017-12-15   17:54
 * <p>
 * 描述：缓存管理工具类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class RCacheManage {
    /**
     * 基于缓存路径 {@link RCacheConfig#CACHE_PATH} 统一拼接文件扩展名
     *
     * @param fileName 文件名
     * @return 带扩展名的 File 对象
     */
    @NonNull
    static File spliceFile(@NonNull String fileName) {
        File file = new File(RCacheConfig.CACHE_PATH, fileName.hashCode() + RCacheConfig.EXTEND_NAME);
        return file;
    }

    /**
     * 增加有效期
     *
     * @param value   保存内容
     * @param outtime 有效时间
     * @return 按照特殊格式增加了有效时间的内容(增加的时间表示最终有效期)
     */
    static String addDateInfo(@NonNull String value, long outtime) {
        return createDateInfo(outtime) + value;
    }

    /**
     * 增加有效期
     *
     * @param value   保存内容
     * @param outtime 有效时间
     * @return 按照特殊格式增加了有效时间的内容(增加的时间表示最终有效期)
     */
    static byte[] addDateInfo(@NonNull byte[] value, long outtime) {
        if (value == null) return null;

        try {
            String dateInfo = createDateInfo(outtime);
            byte[] timeOutBytes = dateInfoToBytes();
            byte[] newBytes = new byte[timeOutBytes.length + value.length];
            System.arraycopy(timeOutBytes, 0, newBytes, 0, timeOutBytes.length);
            System.arraycopy(value, 0, newBytes, timeOutBytes.length, value.length);
            return newBytes;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 清除时间信息
     *
     * @param value
     * @return 返回清除过期时间之后的内容
     */
    static String clearDateInfo(@NonNull String value) {
        if (value != null) {
            String[] strings = value.split(RCacheConfig.SPLITVALUE);
            if (strings.length == 2) {
                if (currentTimeMillis() <= Long.parseLong(strings[0]))
                    return strings[1];
                else return RCacheConfig.OUT_TIME_FLAG;
            }
            return value;
        }
        return "";
    }

    /**
     * 是否有有效期限制
     *
     * @param value
     * @return
     */
    static boolean isTimeLimit(@NonNull String value) {
        return value.contains(RCacheConfig.SPLITVALUE);
    }

    /**
     * 计算文件大小，如果是文件夹返回0
     *
     * @param listFile
     * @return 文件大小，如果是文件夹返回 0
     */
    static long calculateFileSize(@NonNull File listFile) {
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
    static long deleteFile(@NonNull File file) {
        long length = file.length();
        file.delete();
        return length;
    }

    /**
     * 清除文件中的内容
     *
     * @param file
     */
    static void clearContent(@NonNull File file) {
        FileWriter fileWriter = null;
        try {
            if (file.length() > 0) {
                fileWriter = new FileWriter(file);
                fileWriter.write("");
                fileWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 检查缓存文件大小
     */
    static void checkCacheSize() {
        if (RCacheConfig.RCACHE_SIZE_CONTROL != null && !RCacheConfig.RCACHE_SIZE_CONTROL.isAlive()) {
            synchronized (CacheUtils.class) {
                if (RCacheConfig.RCACHE_SIZE_CONTROL != null && !RCacheConfig.RCACHE_SIZE_CONTROL.isAlive())
                    RCacheConfig.RCACHE_SIZE_CONTROL.start();
            }
        }
    }

    /**
     * 创建时间信息字符串，过期时间+分割内容 {@link RCacheConfig#SPLITVALUE}
     *
     * @param outtime
     * @return
     */
    @NonNull
    static String createDateInfo(long outtime) {
        return (currentTimeMillis() + outtime) + RCacheConfig.SPLITVALUE;
    }

    /**
     * 获取13位的时间
     *
     * @return
     */
    static long currentTimeMillis() {
        String currentTimeMillis = System.currentTimeMillis() + "";
        while (currentTimeMillis.length() < 13) {
            currentTimeMillis = currentTimeMillis + "0";
        }
        return Long.parseLong(currentTimeMillis);
    }

    /**
     * 将分隔符 utf-8 编码为字节数组
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    static byte[] dateInfoToBytes() throws UnsupportedEncodingException {
        return RCacheConfig.SPLITVALUE.getBytes("utf-8");
    }
}
