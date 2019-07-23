package com.renj.arouter;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：itrenjunhua@163.com
 * <p>
 * 创建时间：2019-07-23   15:59
 * <p>
 * 描述：路由path
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public interface ARouterPath {
    /**
     * 首页包含的Activity/Fragment组
     */
    String GROUP_MAIN = "main";
    /**
     * 我的相关(我的收藏、我的查看等)组
     */
    String GROUP_MY = "my";
    /**
     * 公共部分(splash、公共WebActivity、关于项目)组
     */
    String GROUP_COMMON = "common";
    /**
     * 我的博客组
     */
    String GROUP_MY_BLOG = "my_blog";
    /**
     * 分类查看组
     */
    String GROUP_CLASSIFICATION = "classification";

    String PATH_ACTIVITY_MAIN = "/activity/main";
    String PATH_ACTIVITY_CLASSIFICATION = "/activity/classification";
    String PATH_ACTIVITY_CLASSIFICATION_LIST = "/activity/classification/list";
    String PATH_ACTIVITY_COLLECTION_LIST = "/activity/collection/list";
    String PATH_ACTIVITY_SEE_LIST = "/activity/see/list";
    String PATH_ACTIVITY_WEB = "/activity/web";

    String PATH_FRAGMENT_HOME = "/fragment/main/home";
    String PATH_FRAGMENT_FOUND = "/fragment/main/found";
    String PATH_FRAGMENT_MY = "/fragment/main/my";
    String PATH_FRAGMENT_MY_CSDN = "/fragment/my/csdn";
    String PATH_FRAGMENT_MY_GITHUB = "/fragment/my/github";
}
