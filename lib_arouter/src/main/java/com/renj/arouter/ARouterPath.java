package com.renj.arouter;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：itrenjunhua@163.com
 * <p>
 * 创建时间：2019-07-23   15:59
 * <p>
 * 描述：路由path，注意：不同module中一级路径不能相同
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public interface ARouterPath {

    String PATH_MAIN_ACTIVITY_MAIN = "/main/activity/main";
    String PATH_COMMON_ACTIVITY_WEB = "/common/activity/web";

    String PATH_HOME_FRAGMENT_HOME = "/home/fragment/main/home";
    String PATH_HOME_FRAGMENT_MY_CSDN = "/home/fragment/my/csdn";
    String PATH_HOME_FRAGMENT_MY_GITHUB = "/home/fragment/my/github";

    String PATH_FOUND_ACTIVITY_CLASSIFICATION = "/found/activity/classification";
    String PATH_FOUND_ACTIVITY_CLASSIFICATION_LIST = "/found/activity/classification/list";
    String PATH_FOUND_FRAGMENT_FOUND = "/found/fragment/main/found";

    String PATH_MY_ACTIVITY_COLLECTION_LIST = "/my/activity/collection/list";
    String PATH_MY_ACTIVITY_SEE_LIST = "/my/activity/see/list";
    String PATH_MY_FRAGMENT_MY = "/my/fragment/main/my";

}
