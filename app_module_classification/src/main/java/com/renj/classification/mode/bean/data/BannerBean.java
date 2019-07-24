package com.renj.classification.mode.bean.data;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-07-08   13:27
 * <p>
 * 描述：banner 数据bean
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class BannerBean {
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

    public BannerBean(int id, String title, String image, String url) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.url = url;
    }
}
