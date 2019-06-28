package com.renj.mvp.mode.bean;

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
public class HomeListRPB extends BaseResponseBean<HomeListRPB> {

    public List<BannerBean> banner;
    public List<ListBean> list;
    public List<NoticeBean> notice;

    public static class BannerBean {
        /**
         * title : Banner 标题6
         * image : http:image6
         * clickable : 1
         * clickResult : http:banner/url
         */

        public String title;
        public String image;
        public int clickable;
        public String clickResult;
    }

    public static class ListBean {
        /**
         * title : 列表 标题3
         * content : 列表内容CCCCCCCCCCCCCCCCCCCCCCC
         * url : http:list/url
         * time : 1561712055526
         * src : 来源e
         * images : ["http:image7"]
         */

        public String title;
        public String content;
        public String url;
        public long time;
        public String src;
        public List<String> images;
    }

    public static class NoticeBean {
        /**
         * title : 公告内容6
         * url : http:notice/url
         */

        public String title;
        public String url;
    }
}
