package com.renj.classification.mode.db;

import com.renj.common.app.BaseApplication;
import com.renj.common.mode.bean.dp.DaoSession;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2018-03-20   10:45
 * <p>
 * 描述：APP 操作数据库的帮助类，实现 {@link IDBHelper} 接口
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class DBHelper implements IDBHelper {
    protected DaoSession daoSession = BaseApplication.getDaoSession();

}
