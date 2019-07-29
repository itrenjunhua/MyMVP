package com.renj.found.mode.bean.response;

import com.renj.common.mode.bean.data.GeneralListBean;
import com.renj.common.mode.bean.response.BaseResponseBean;
import com.renj.found.mode.bean.data.BannerBean;

import java.util.List;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-07-08   11:12
 * <p>
 * 描述：发现页响应数据
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class FoundRPB extends BaseResponseBean<FoundRPB> {

    public List<BannerBean> banners;
    public List<GeneralListBean> beanList;
}
