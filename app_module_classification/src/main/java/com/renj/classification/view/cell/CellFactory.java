package com.renj.classification.view.cell;

import com.renj.classification.mode.bean.data.GeneralListBean;
import com.renj.classification.mode.bean.response.ClassificationRPB;
import com.renj.common.mode.bean.dp.ListSeeAndCollectionDB;
import com.renj.common.view.cell.NoMoreCell;

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

    public static GeneralListCell createGeneralListCell(GeneralListBean data) {
        return new GeneralListCell(data);
    }

    public static List<GeneralListCell> createGeneralListCell(List<GeneralListBean> dataList) {
        List<GeneralListCell> cells = new ArrayList<>();
        for (GeneralListBean data : dataList) {
            cells.add(createGeneralListCell(data));
        }
        return cells;
    }

    public static ClassificationCell createClassificationCell(ClassificationRPB data) {
        return new ClassificationCell(data);
    }

    public static List<ClassificationCell> createClassificationCell(List<ClassificationRPB> dataList) {
        List<ClassificationCell> cells = new ArrayList<>();
        for (ClassificationRPB data : dataList) {
            cells.add(createClassificationCell(data));
        }
        return cells;
    }

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

    public static NoMoreCell createNoMoreCell() {
        return new NoMoreCell(null);
    }
}
