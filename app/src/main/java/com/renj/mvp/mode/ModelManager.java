package com.renj.mvp.mode;

import com.renj.mvp.mode.db.DBHelper;
import com.renj.mvp.mode.db.IDBHelper;
import com.renj.mvp.mode.file.FileHelper;
import com.renj.mvp.mode.file.IFileHelper;
import com.renj.mvp.mode.http.HttpHelper;
import com.renj.mvp.mode.http.IHttpHelper;

import javax.inject.Inject;

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

    private IHttpHelper mIHttpHelper;
    private IDBHelper mIdbHelper;
    private IFileHelper mIFileHelper;

    @Inject
    public ModelManager(HttpHelper httpHelper, DBHelper dbHelper, FileHelper fileHelper) {
        this.mIHttpHelper = httpHelper;
        this.mIdbHelper = dbHelper;
        this.mIFileHelper = fileHelper;
    }

    //=========================================== 分割线 ===========================================//
    public IHttpHelper getHttpHelper() {
        return mIHttpHelper;
    }

    //=========================================== 分割线 ===========================================//
    public IDBHelper getDBHelper() {
        return mIdbHelper;
    }

    //=========================================== 分割线 ===========================================//
    public IFileHelper getFileHelper() {
        return mIFileHelper;
    }

}
