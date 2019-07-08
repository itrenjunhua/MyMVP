package com.renj.mvp.mode.bean.response;

import com.renj.mvp.mode.bean.data.BannerBean;
import com.renj.mvp.mode.bean.data.NoticeBean;

import java.util.List;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2019-07-07   19:34
 * <p>
 * 描述：banner以及notice数据接口返回数据响应
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class BannerAndNoticeRPB extends BaseResponseBean<BannerAndNoticeRPB> {

    public List<BannerBean> banners;
    public List<NoticeBean> notices;
}
