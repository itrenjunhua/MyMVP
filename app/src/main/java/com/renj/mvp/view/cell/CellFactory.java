package com.renj.mvp.view.cell;

import com.renj.mvp.mode.bean.HomeListRPB;

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

    public static BannerCell createBannerCell(List<HomeListRPB.BannerBean> data) {
        return new BannerCell(data);
    }

    public static NoticeCell createNoticeCell(List<HomeListRPB.NoticeBean> data) {
        return new NoticeCell(data);
    }

    public static GeneralListCell createGeneralListCell(HomeListRPB.ListBean data) {
        return new GeneralListCell(data);
    }

    public static List<GeneralListCell> createGeneralListCell(List<HomeListRPB.ListBean> dataList) {
        List<GeneralListCell> cells = new ArrayList<>();
        for (HomeListRPB.ListBean data : dataList) {
            cells.add(createGeneralListCell(data));
        }
        return cells;
    }

    public static NoMoreCell createNoMoreCell() {
        return new NoMoreCell(null);
    }
}
