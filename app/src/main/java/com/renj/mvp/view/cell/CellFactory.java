package com.renj.mvp.view.cell;

import com.renj.mvp.mode.bean.NewsListRPB;

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
    public static NewsListCell createNewsListCell(NewsListRPB newsListRPB) {
        return new NewsListCell(newsListRPB);
    }

    public static List<NewsListCell> createNewsListCell(List<NewsListRPB> newsListRPBs) {
        List<NewsListCell> cells = new ArrayList<>();
        for (NewsListRPB newsListRPB : newsListRPBs) {
            cells.add(createNewsListCell(newsListRPB));
        }
        return cells;
    }

    public static HomeListCell createHomeListCell(String data) {
        return new HomeListCell(data);
    }

    public static List<HomeListCell> createHomeListCell(List<String> dataList) {
        List<HomeListCell> cells = new ArrayList<>();
        for (String data : dataList) {
            cells.add(createHomeListCell(data));
        }
        return cells;
    }
}
