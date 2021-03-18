package com.renj.mvp.mode.bean.response;

import com.renj.mvp.view.cell.RecyclerItemType;
import com.renj.view.recyclerview.adapter.MultiItemEntity;

import java.util.List;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-07-08   16:59
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ClassificationRPB extends BaseResponseBean<List<ClassificationRPB>> implements MultiItemEntity {
    /**
     * id : 1
     * desc : my_csdn
     * label : 我的CSDN
     * total : 54
     * file : my_csdn/list.data
     */

    public int id;
    public String desc;
    public String label;
    public int total;
    public String file;

    @Override
    public int getItemType() {
        return RecyclerItemType.CLASSIFICATION_TYPE;
    }
}
