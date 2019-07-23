package com.renj.utils.io;

import android.os.Environment;
import android.support.annotation.NonNull;

import java.io.File;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：itrenjunhua@163.com
 * <p>
 * 创建时间：2018-04-03   13:27
 * <p>
 * 描述：文件相关工具类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class FileUtils {
    /**
     * 判断是否有SD卡
     *
     * @return true：有 false：没有
     */
    public static boolean isHasSDCard() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获取外置内存卡的路径 ，注意需要先调用 {@link #isHasSDCard()} 方法判断 SD卡是否挂载
     *
     * @return 外置内存卡的路径
     */
    @NonNull
    public static String getExternalStoragePath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * 在子线程中创建文件夹
     */
    public static void mkdirsInThread(@NonNull final File file) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mkdirs(file);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    /**
     * 创建文件夹
     *
     * @param file 目录名
     * @return true：创建成功 false：目录已存在，没有创建
     */
    public static boolean mkdirs(@NonNull File file) {
        return (!file.exists() || !file.isDirectory()) && file.mkdirs();
    }

    /**
     * 将 long 类型的大小转换为 B、KB、MB、G形式
     *
     * @param length
     * @return
     */
    public static String getFileLength(long length) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        String fileSizeString;
        if (length < 1024) {
            fileSizeString = df.format(length) + "B";
        } else if (length < 1048576) {
            fileSizeString = df.format(length / 1024) + "KB";
        } else if (length < 1073741824) {
            fileSizeString = df.format(length / 1048576) + "MB";
        } else {
            fileSizeString = df.format(length / 1073741824) + "G";
        }
        return fileSizeString;

        //return Formatter.formatFileSize(UIUtils.getContext(),length);
    }
}
