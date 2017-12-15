package com.renj.mvp.utils.cache;

import java.io.File;
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
 * 创建时间：2017-12-15   17:52
 * <p>
 * 描述：缓存大小控制线程，用于控制缓存大小，当超过指定大小时，就删除老的文件
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class RCacheSizeControl extends Thread {
    // 用于临时保存所有的缓存文件对象
    private List<File> cacheFiles = Collections.synchronizedList(new LinkedList<File>());
    // 缓存占用的大小
    private AtomicLong cacheSize = new AtomicLong();

    @Override
    public void run() {
        cacheFiles.clear();
        cacheSize.set(0);
        handlerCacheSize();
    }

    private void handlerCacheSize() {
        cacheSize();

        if (cacheSize.get() > RCacheConfig.CACHE_SIZE)
            deleteFileToCacheSize();
    }

    private void cacheSize() {
        if (RCacheConfig.CACHE_PATH == null || !RCacheConfig.CACHE_PATH.exists() || !RCacheConfig.CACHE_PATH.isDirectory()) return;

        File[] listFiles = RCacheConfig.CACHE_PATH.listFiles();
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
            if (temp <= RCacheConfig.CACHE_SIZE)
                break;
        }

        for (File deleteFile : deleteFiles) {
            RCacheManage.deleteFile(deleteFile);
            cacheFiles.remove(deleteFile);
        }
    }
}
