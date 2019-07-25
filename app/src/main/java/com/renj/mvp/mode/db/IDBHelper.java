package com.renj.mvp.mode.db;

import android.support.annotation.NonNull;

import com.renj.common.mode.db.bean.ListSeeAndCollectionRDB;
import com.renj.mvp.mode.bean.data.GeneralListBean;
import com.renj.mvpbase.mode.IMvpDBHelper;

import io.reactivex.Flowable;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2018-03-20   10:44
 * <p>
 * 描述：APP 操作数据库的帮助类接口，将所有的数据库相关操作方法定义在此接口中
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public interface IDBHelper extends IMvpDBHelper {
    /**
     * 增加一条数据
     */
    Flowable<Long> addData(@NonNull GeneralListBean generalListBean);

    /**
     * 改变收藏状态
     */
    Flowable<Boolean> changeCollectionStatus(int pid, int id, boolean collectionStatus);

    /**
     * 增加查看次数，如果没有这条数据就增加数据并增加查看次数
     */
    Flowable<Long> addSeeCount(@NonNull GeneralListBean generalListBean);

    /**
     * 增加查看次数
     */
    Flowable<Long> addSeeCount(int pid, int id);

    /**
     * 获取收藏状态
     */
    Flowable<Boolean> getCollectionStatus(int pid, int id);

    /**
     * 获取查看次数
     */
    Flowable<Integer> getSeeCount(int pid, int id);

    /**
     * 获取收藏列表
     */
    Flowable<ListSeeAndCollectionRDB> getCollectionList(int pageNo, int pageSize);

    /**
     * 获取查看列表
     */
    Flowable<ListSeeAndCollectionRDB> getSeeList(int pageNo, int pageSize);
}
