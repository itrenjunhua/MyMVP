package com.renj.mvp.mode.bean;

import java.util.List;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-04-17   14:16
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class NewsListRPB extends BaseResponseBean<List<NewsListRPB>> {

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
