package com.renj.my.view.cell;

import com.renj.common.mode.bean.dp.ListSeeAndCollectionDB;

import java.util.ArrayList;
import java.util.List;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-06-14   14:22
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class CellFactory {

    public static SeeAndCollectionListCell createSeeAndCollectionListCell(ListSeeAndCollectionDB data, boolean isSeeList) {
        return new SeeAndCollectionListCell(data, isSeeList);
    }

    public static List<SeeAndCollectionListCell> createSeeAndCollectionListCell(List<ListSeeAndCollectionDB> dataList, boolean isSeeList) {
        List<SeeAndCollectionListCell> cells = new ArrayList<>();
        for (ListSeeAndCollectionDB data : dataList) {
            cells.add(createSeeAndCollectionListCell(data, isSeeList));
        }
        return cells;
    }
}
