package com.renj.mvp.mode.bean;

import java.util.List;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2019-07-07   19:34
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class BannerAndNoticeRPB extends BaseResponseBean<BannerAndNoticeRPB> {

    public List<BannersEntity> banners;
    public List<NoticesEntity> notices;

    public static class BannersEntity {
        /**
         * id : 1
         * title : Java中Collection和Map体系(Java容器)
         * image : http://img07.mifile.cn/v1/MI_55950AFBBEDCB/T1t.V_BQJT1RXrhCrK.jpg
         * url : https://blog.csdn.net/ITRenj/article/details/94363815
         */

        public int id;
        public String title;
        public String image;
        public String url;
    }

    public static class NoticesEntity {
        /**
         * id : 1
         * title : Dagger 2.11及以上的简便用法
         * url : https://blog.csdn.net/ITRenj/article/details/85267381
         */

        public int id;
        public String title;
        public String url;
    }
}
