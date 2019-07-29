package com.renj.common.view.cell;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-07-29   13:44
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class CommonCellFactory {
    public static SegmentationCell createSegmentationCell(String segmentationName) {
        return new SegmentationCell(segmentationName);
    }
    public static NoMoreCell createNoMoreCell() {
        return new NoMoreCell(null);
    }
}
