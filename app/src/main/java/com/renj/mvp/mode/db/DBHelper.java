package com.renj.mvp.mode.db;

import android.support.annotation.NonNull;

import com.renj.mvp.app.MyApplication;
import com.renj.mvp.mode.bean.data.GeneralListBean;
import com.renj.mvp.mode.db.bean.DaoSession;
import com.renj.mvp.mode.db.bean.ListSeeAndCollectionDB;
import com.renj.mvp.mode.db.bean.ListSeeAndCollectionDBDao;
import com.renj.mvp.mode.db.bean.ListSeeAndCollectionRDB;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

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
    protected DaoSession daoSession = MyApplication.getDaoSession();

    public DBHelper() {
    }

    /**
     * 增加一条数据
     *
     * @param generalListBean
     */
    @Override
    public void addData(@NonNull GeneralListBean generalListBean) {
        addData(generalListBean, 0);
    }

    /**
     * 改变收藏状态
     */
    @Override
    public boolean changeCollectionStatus(int pid, int id, boolean collectionStatus) {
        ListSeeAndCollectionDB collectionDB = getData(pid, id);
        if (collectionDB == null) return false;

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
        return true;
    }

    /**
     * 增加查看次数，如果没有这条数据就增加数据并增加查看次数
     */
    @Override
    public void addSeeCount(@NonNull GeneralListBean generalListBean) {
        ListSeeAndCollectionDB collectionDB = getData(generalListBean.pid, generalListBean.id);
        if (collectionDB == null) {
            addData(generalListBean, 1);
        } else {
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
        }
    }

    private void addData(GeneralListBean generalListBean, int seeCount) {
        ListSeeAndCollectionDB addCollectionDBDao = new ListSeeAndCollectionDB();
        addCollectionDBDao.setPid(generalListBean.pid);
        addCollectionDBDao.setDataId(generalListBean.id);
        addCollectionDBDao.setTitle(generalListBean.title);
        addCollectionDBDao.setContent(generalListBean.content);
        addCollectionDBDao.setUrl(generalListBean.url);
        addCollectionDBDao.setSeeCount(seeCount);
        addCollectionDBDao.setCollection((byte) 0);
        addCollectionDBDao.setImages(generalListBean.images.toString().replace("[", "").replace("]", ""));
        daoSession.insertOrReplace(addCollectionDBDao);
    }


    /**
     * 增加查看次数
     */
    @Override
    public void addSeeCount(int pid, int id) {
        ListSeeAndCollectionDB collectionDB = getData(pid, id);
        if (collectionDB == null) return;

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
    }

    /**
     * 获取收藏状态
     *
     * @param pid
     * @param id
     */
    @Override
    public boolean getCollectionStatus(int pid, int id) {
        ListSeeAndCollectionDB collectionDB = getData(pid, id);
        if (collectionDB == null) return false;

        return collectionDB.getCollection() == 1;
    }

    /**
     * 获取查看次数
     *
     * @param pid
     * @param id
     */
    @Override
    public int getSeeCount(int pid, int id) {
        ListSeeAndCollectionDB collectionDB = getData(pid, id);
        if (collectionDB == null) return 0;

        return collectionDB.getSeeCount();
    }

    /**
     * 获取收藏列表
     *
     * @param pageNo
     * @param pageSize
     */
    @Override
    public ListSeeAndCollectionRDB getCollectionList(int pageNo, int pageSize) {
        ListSeeAndCollectionRDB result = new ListSeeAndCollectionRDB();
        long total = getTotal();
        result.total = total;
        if (total % pageSize == 0) {
            result.page = (int) (total / pageSize);
        } else {
            result.page = (int) (total / pageSize + 1);
        }

        QueryBuilder<ListSeeAndCollectionDB> dbQueryBuilder = daoSession.queryBuilder(ListSeeAndCollectionDB.class);
        List<ListSeeAndCollectionDB> queryList = dbQueryBuilder
                .where(ListSeeAndCollectionDBDao.Properties.Collection.eq(1))
                .limit(10)
                .offset((pageNo - 1) * pageSize)
                .list();
        result.list = queryList;
        return result;
    }

    /**
     * 获取查看列表
     *
     * @param pageNo
     * @param pageSize
     */
    @Override
    public ListSeeAndCollectionRDB getSeeList(int pageNo, int pageSize) {
        ListSeeAndCollectionRDB result = new ListSeeAndCollectionRDB();
        long total = getTotal();
        result.total = total;
        if (total % pageSize == 0) {
            result.page = (int) (total / pageSize);
        } else {
            result.page = (int) (total / pageSize + 1);
        }

        QueryBuilder<ListSeeAndCollectionDB> dbQueryBuilder = daoSession.queryBuilder(ListSeeAndCollectionDB.class);
        List<ListSeeAndCollectionDB> queryList = dbQueryBuilder
                .orderAsc(ListSeeAndCollectionDBDao.Properties.SeeCount)
                .limit(10)
                .offset((pageNo - 1) * pageSize)
                .list();


        result.list = queryList;
        return result;
    }

    private long getTotal() {
        return daoSession.getListSeeAndCollectionDBDao().count();
    }

    private ListSeeAndCollectionDB getData(int pid, int id) {
        QueryBuilder<ListSeeAndCollectionDB> queryBuilder = daoSession.queryBuilder(ListSeeAndCollectionDB.class);
        queryBuilder.and(ListSeeAndCollectionDBDao.Properties.Pid.eq(pid), ListSeeAndCollectionDBDao.Properties.DataId.eq(id));
        List<ListSeeAndCollectionDB> collectionDBS = queryBuilder.list();
        if (collectionDBS != null && collectionDBS.size() > 0)
            return collectionDBS.get(0);
        return null;
    }
}
