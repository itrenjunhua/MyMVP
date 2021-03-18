package com.renj.mvp.mode.bean.data;

import com.renj.mvp.view.cell.RecyclerItemType;
import com.renj.view.recyclerview.adapter.MultiItemEntity;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-07-08   13:29
 * <p>
 * 描述：公告数据bean
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class NoticeBean implements MultiItemEntity {
    /**
     * id : 1
     * title : Dagger 2.11及以上的简便用法
     * url : https://blog.csdn.net/ITRenj/article/details/85267381
     */

    public int id;
    public String title;
    public String url;

    @Override
    public int getItemType() {
        return RecyclerItemType.NOTICE_CELL_TYPE;
    }
}
