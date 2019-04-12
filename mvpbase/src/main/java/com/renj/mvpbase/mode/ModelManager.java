package com.renj.mvpbase.mode;


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

    public ModelManager(IHttpHelper httpHelper, IDBHelper dbHelper, IFileHelper fileHelper) {
        this.mIHttpHelper = httpHelper;
        this.mIdbHelper = dbHelper;
        this.mIFileHelper = fileHelper;
    }

    //=========================================== 分割线 ===========================================//
    public <T extends IHttpHelper> T getHttpHelper() {
        return (T) mIHttpHelper;
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
