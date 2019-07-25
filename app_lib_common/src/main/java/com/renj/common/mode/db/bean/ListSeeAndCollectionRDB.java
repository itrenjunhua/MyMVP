package com.renj.common.mode.db.bean;

import java.util.List;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2019-07-10   21:44
 * <p>
 * 描述：数据库列表响应封装数据
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ListSeeAndCollectionRDB {
    public long total;
    public int page;
    public List<ListSeeAndCollectionDB> list;
}
