package com.renj.mvp.mode.db;

import android.support.annotation.NonNull;

import com.renj.common.app.BaseApplication;
import com.renj.common.mode.db.bean.DaoSession;
import com.renj.common.mode.db.bean.ListSeeAndCollectionDB;
import com.renj.common.mode.db.bean.ListSeeAndCollectionDBDao;
import com.renj.common.mode.db.bean.ListSeeAndCollectionRDB;
import com.renj.mvp.mode.bean.data.GeneralListBean;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

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

    public DBHelper() {
    }

    /**
     * 增加一条数据
     *
     * @param generalListBean
     */
    @Override
    public Flowable<Long> addData(@NonNull GeneralListBean generalListBean) {
        return addData(generalListBean, 0);
    }

    /**
     * 改变收藏状态
     */
    @Override
    public Flowable<Boolean> changeCollectionStatus(int pid, int id, boolean collectionStatus) {
        ListSeeAndCollectionDB collectionDB = getData(pid, id);
        if (collectionDB == null)
            return Flowable.create(e -> e.onNext(false), BackpressureStrategy.BUFFER);

        ListSeeAndCollectionDB updateCollectionDBDao = new ListSeeAndCollectionDB();
        updateCollectionDBDao.setId(collectionDB.getId());
        updateCollectionDBDao.setPid(collectionDB.getPid());
        updateCollectionDBDao.setDataId(collectionDB.getDataId());
        updateCollectionDBDao.setTitle(collectionDB.getTitle());
        updateCollectionDBDao.setContent(collectionDB.getTitle());
        updateCollectionDBDao.setUrl(collectionDB.getUrl());
        updateCollectionDBDao.setSeeCount(collectionDB.getSeeCount());
        updateCollectionDBDao.setCollection((byte) (collectionStatus == true ? 1 : 0));
        updateCollectionDBDao.setImages(collectionDB.getImages());
        daoSession.update(updateCollectionDBDao);
        return Flowable.create(e -> e.onNext(true), BackpressureStrategy.BUFFER);
    }

    /**
     * 增加查看次数，如果没有这条数据就增加数据并增加查看次数
     */
    @Override
    public Flowable<Long> addSeeCount(@NonNull GeneralListBean generalListBean) {
        ListSeeAndCollectionDB collectionDB = getData(generalListBean.pid, generalListBean.id);
        if (collectionDB == null) {
            addData(generalListBean, 1);
            return Flowable.create(e -> e.onNext(Long.valueOf(1)), BackpressureStrategy.BUFFER);
        } else {
            ListSeeAndCollectionDB updateCollectionDBDao = new ListSeeAndCollectionDB();
            updateCollectionDBDao.setId(collectionDB.getId());
            updateCollectionDBDao.setPid(collectionDB.getPid());
            updateCollectionDBDao.setDataId(collectionDB.getDataId());
            updateCollectionDBDao.setTitle(collectionDB.getTitle());
            updateCollectionDBDao.setContent(collectionDB.getTitle());
            updateCollectionDBDao.setUrl(collectionDB.getUrl());
            updateCollectionDBDao.setSeeCount(collectionDB.getSeeCount() + 1);
            updateCollectionDBDao.setCollection(collectionDB.getCollection());
            updateCollectionDBDao.setImages(collectionDB.getImages());
            daoSession.update(updateCollectionDBDao);
            return Flowable.create(e -> e.onNext(Long.valueOf(collectionDB.getSeeCount() + 1)), BackpressureStrategy.BUFFER);
        }
    }

    private Flowable<Long> addData(GeneralListBean generalListBean, int seeCount) {
        ListSeeAndCollectionDB addCollectionDBDao = new ListSeeAndCollectionDB();
        addCollectionDBDao.setPid(generalListBean.pid);
        addCollectionDBDao.setDataId(generalListBean.id);
        addCollectionDBDao.setTitle(generalListBean.title);
        addCollectionDBDao.setContent(generalListBean.content);
        addCollectionDBDao.setUrl(generalListBean.url);
        addCollectionDBDao.setSeeCount(seeCount);
        addCollectionDBDao.setCollection((byte) 0);
        addCollectionDBDao.setImages(generalListBean.images.toString().replace("[", "").replace("]", ""));
        long insertOrReplace = daoSession.insertOrReplace(addCollectionDBDao);

        return Flowable.create(e -> e.onNext(insertOrReplace), BackpressureStrategy.BUFFER);
    }


    /**
     * 增加查看次数
     */
    @Override
    public Flowable<Long> addSeeCount(int pid, int id) {
        ListSeeAndCollectionDB collectionDB = getData(pid, id);
        if (collectionDB == null)
            return Flowable.create(e -> e.onNext(0L), BackpressureStrategy.BUFFER);

        ListSeeAndCollectionDB updateCollectionDBDao = new ListSeeAndCollectionDB();
        updateCollectionDBDao.setId(collectionDB.getId());
        updateCollectionDBDao.setPid(collectionDB.getPid());
        updateCollectionDBDao.setDataId(collectionDB.getDataId());
        updateCollectionDBDao.setTitle(collectionDB.getTitle());
        updateCollectionDBDao.setContent(collectionDB.getTitle());
        updateCollectionDBDao.setUrl(collectionDB.getUrl());
        updateCollectionDBDao.setSeeCount(collectionDB.getSeeCount() + 1);
        updateCollectionDBDao.setCollection(updateCollectionDBDao.getCollection());
        updateCollectionDBDao.setImages(collectionDB.getImages());
        daoSession.update(updateCollectionDBDao);
        return Flowable.create(e -> e.onNext(collectionDB.getId()), BackpressureStrategy.BUFFER);
    }

    /**
     * 获取收藏状态
     *
     * @param pid
     * @param id
     */
    @Override
    public Flowable<Boolean> getCollectionStatus(int pid, int id) {
        ListSeeAndCollectionDB collectionDB = getData(pid, id);
        if (collectionDB == null)
            return Flowable.create(e -> e.onNext(false), BackpressureStrategy.BUFFER);

        return Flowable.create(e -> e.onNext(collectionDB.getCollection() == 1), BackpressureStrategy.BUFFER);
    }

    /**
     * 获取查看次数
     *
     * @param pid
     * @param id
     */
    @Override
    public Flowable<Integer> getSeeCount(int pid, int id) {
        ListSeeAndCollectionDB collectionDB = getData(pid, id);
        if (collectionDB == null)
            return Flowable.create(e -> e.onNext(0), BackpressureStrategy.BUFFER);

        return Flowable.create(e -> e.onNext(collectionDB.getSeeCount()), BackpressureStrategy.BUFFER);
    }

    /**
     * 获取收藏列表
     *
     * @param pageNo
     * @param pageSize
     */
    @Override
    public Flowable<ListSeeAndCollectionRDB> getCollectionList(int pageNo, int pageSize) {
        ListSeeAndCollectionRDB result = new ListSeeAndCollectionRDB();

        QueryBuilder<ListSeeAndCollectionDB> dbQueryBuilder = daoSession.queryBuilder(ListSeeAndCollectionDB.class);
        QueryBuilder<ListSeeAndCollectionDB> queryBuilder = dbQueryBuilder
                .where(ListSeeAndCollectionDBDao.Properties.Collection.eq(1))
                .limit(pageSize)
                .offset((pageNo - 1) * pageSize);
        long total = queryBuilder.count();
        List<ListSeeAndCollectionDB> queryList = queryBuilder.list();

        result.total = total;
        if (total % pageSize == 0) {
            result.page = (int) (total / pageSize);
        } else {
            result.page = (int) (total / pageSize + 1);
        }
        result.list = queryList;

        return Flowable.create(e -> e.onNext(result), BackpressureStrategy.BUFFER);
    }

    /**
     * 获取查看列表
     *
     * @param pageNo
     * @param pageSize
     */
    @Override
    public Flowable<ListSeeAndCollectionRDB> getSeeList(int pageNo, int pageSize) {
        ListSeeAndCollectionRDB result = new ListSeeAndCollectionRDB();

        QueryBuilder<ListSeeAndCollectionDB> dbQueryBuilder = daoSession.queryBuilder(ListSeeAndCollectionDB.class);
        QueryBuilder<ListSeeAndCollectionDB> queryBuilder = dbQueryBuilder
                .orderDesc(ListSeeAndCollectionDBDao.Properties.SeeCount)
                .limit(pageSize)
                .offset((pageNo - 1) * pageSize);

        long total = queryBuilder.count();
        result.total = total;
        if (total % pageSize == 0) {
            result.page = (int) (total / pageSize);
        } else {
            result.page = (int) (total / pageSize + 1);
        }
        result.list = queryBuilder.list();
        return Flowable.create(e -> e.onNext(result), BackpressureStrategy.BUFFER);
    }


    private ListSeeAndCollectionDB getData(int pid, int id) {
        QueryBuilder<ListSeeAndCollectionDB> queryBuilder = daoSession.queryBuilder(ListSeeAndCollectionDB.class);
        List<ListSeeAndCollectionDB> collectionDBS = queryBuilder.where(queryBuilder
                .and(ListSeeAndCollectionDBDao.Properties.Pid.eq(pid),
                        ListSeeAndCollectionDBDao.Properties.DataId.eq(id)))
                .list();
        if (collectionDBS != null && collectionDBS.size() > 0)
            return collectionDBS.get(0);
        return null;
    }
}
