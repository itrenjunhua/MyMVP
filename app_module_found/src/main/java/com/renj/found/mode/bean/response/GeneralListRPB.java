package com.renj.found.mode.bean.response;


import com.renj.common.mode.bean.data.GeneralListBean;
import com.renj.common.mode.bean.response.BaseResponseBean;

import java.util.List;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-04-16   16:40
 * <p>
 * 描述：首页数据
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class GeneralListRPB extends BaseResponseBean<GeneralListRPB> {

    /**
     * total : 54
     * page : 54
     * list : [{"pid":1,"id":54,"title":"Java中Collection和Map体系(Java容器)","content":"Java中Collection和Map体系(Java容器) Java常用容器类继承关系图解 Java容器类简介 Java中容器类主要分为四中体系：List、Set、Queue、Map。","url":"https://blog.csdn.net/ITRenj/article/details/94363815","images":[]}]
     */

    public long total;
    public int page;
    public List<GeneralListBean> list;
}
