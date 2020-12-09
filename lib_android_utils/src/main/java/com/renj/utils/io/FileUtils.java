package com.renj.utils.io;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;

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

    /**
     * 保存字符串到缓存目录中
     *
     * @param filePath 文件路径
     * @param fileName 文件名
     * @param content  保存内容
     */
    public static void saveStringToFile(String filePath, String fileName, String content) {
        BufferedWriter bufferedWriter = null;
        try {
            File file = new File(filePath, fileName);
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(content);
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(bufferedWriter);
        }
    }

    /**
     * 从缓存目录中获取字符串
     *
     * @param filePath 文件路径
     * @param fileName 文件名
     * @return 返回内容
     */
    public static String getStringFormFile(String filePath, String fileName) {
        BufferedReader bufferedReader = null;
        try {
            File file = new File(filePath, fileName);
            if (!checkFileExists(file.getPath())) {
                return "";
            }
            bufferedReader = new BufferedReader(new FileReader(file));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String tResult = stringBuilder.toString();
            return tResult;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        } finally {
            IOUtils.close(bufferedReader);
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param absPath 决定路径
     * @return true：存在
     */
    public static boolean checkFileExists(String absPath) {
        if (TextUtils.isEmpty(absPath)) {
            return false;
        }
        File file = new File(absPath);
        return file.isFile() && file.exists() && file.length() > 0;
    }

    /**
     * 删除文件夹
     *
     * @param dirFile 需要删除的文件夹
     */
    public static void deleteDirectory(File dirFile) {
        if (dirFile == null) {
            return;
        }
        File[] files = dirFile.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                deleteDirectory(file);
            } else {
                file.delete();
            }
        }
    }

    /**
     * 删除文件夹，并指定过滤掉部分文件
     *
     * @param dirFile     需要删除的文件夹
     * @param filterFiles 需要过滤掉的文件，或者文件夹名称
     */
    public static void deleteDirectory(File dirFile, String... filterFiles) {
        if (dirFile == null) {
            return;
        }
        File[] files = dirFile.listFiles();
        if (files == null) {
            return;
        }
        boolean isContinue;
        for (File file : files) {
            isContinue = false;
            if (filterFiles != null) {
                for (String filterFile : filterFiles) {
                    if (filterFile.equals(file.getName())) {
                        isContinue = true;
                        break;
                    }
                }
            }
            if (isContinue)
                continue;

            if (file.isDirectory()) {
                deleteDirectory(file, filterFiles);
            } else {
                file.delete();
            }
        }
    }
}
