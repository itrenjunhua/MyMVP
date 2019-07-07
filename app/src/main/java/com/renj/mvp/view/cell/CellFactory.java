package com.renj.mvp.view.cell;

import com.renj.mvp.mode.bean.BannerAndNoticeRPB;
import com.renj.mvp.mode.bean.GeneralListRPB;

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

    public static BannerCell createBannerCell(List<BannerAndNoticeRPB.BannersEntity> data) {
        return new BannerCell(data);
    }

    public static NoticeCell createNoticeCell(List<BannerAndNoticeRPB.NoticesEntity> data) {
        return new NoticeCell(data);
    }

    public static GeneralListCell createGeneralListCell(GeneralListRPB.ListEntity data) {
        return new GeneralListCell(data);
    }

    public static List<GeneralListCell> createGeneralListCell(List<GeneralListRPB.ListEntity> dataList) {
        List<GeneralListCell> cells = new ArrayList<>();
        for (GeneralListRPB.ListEntity data : dataList) {
            cells.add(createGeneralListCell(data));
        }
        return cells;
    }

    public static NoMoreCell createNoMoreCell() {
        return new NoMoreCell(null);
    }
}
