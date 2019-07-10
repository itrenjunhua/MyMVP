package com.renj.mvp.mode.db.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：itrenjunhua@163.com
 * <p>
 * 创建时间：2019-07-10   13:24
 * <p>
 * 描述：数据库对应实体
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
@Entity
public class ListSeeAndCollectionDB {
    @Id(autoincrement = true)
    private Long id;

    private int pid; // 分类id

    private int dataId; // 数据id

    private String title;

    private String content;

    private String url;

    private String images;

    private byte collection;

    private int seeCount;

    @Generated(hash = 1929720936)
    public ListSeeAndCollectionDB(Long id, int pid, int dataId, String title,
            String content, String url, String images, byte collection,
            int seeCount) {
        this.id = id;
        this.pid = pid;
        this.dataId = dataId;
        this.title = title;
        this.content = content;
        this.url = url;
        this.images = images;
        this.collection = collection;
        this.seeCount = seeCount;
    }

    @Generated(hash = 1373035211)
    public ListSeeAndCollectionDB() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPid() {
        return this.pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getDataId() {
        return this.dataId;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImages() {
        return this.images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public byte getCollection() {
        return this.collection;
    }

    public void setCollection(byte collection) {
        this.collection = collection;
    }

    public int getSeeCount() {
        return this.seeCount;
    }

    public void setSeeCount(int seeCount) {
        this.seeCount = seeCount;
    }
}
