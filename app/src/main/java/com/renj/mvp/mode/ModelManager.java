package com.renj.mvp.mode;

import com.renj.mvp.mode.db.DBHelper;
import com.renj.mvp.mode.db.IDBHelper;
import com.renj.mvp.mode.file.FileHelper;
import com.renj.mvp.mode.file.IFileHelper;
import com.renj.mvp.mode.http.HttpHelper;
import com.renj.mvp.mode.http.IHttpHelper;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2018-08-17   10:46
 * <p>
 * 描述：Model 层管理类，所有对 Model 层的操作都通过 {@link ModelManager} 类实现
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ModelManager implements IHttpHelper, IDBHelper, IFileHelper {

    private IHttpHelper mIHttpHelper;
    private IDBHelper mIdbHelper;
    private IFileHelper mIFileHelper;

    @Inject
    public ModelManager(HttpHelper httpHelper, DBHelper dbHelper, FileHelper fileHelper) {
        this.mIHttpHelper = httpHelper;
        this.mIdbHelper = dbHelper;
        this.mIFileHelper = fileHelper;
    }


    /****************************** 【start】 网络接口部分  【start】****************************/


    @Override
    public Flowable<String> getWeather(String text, Map<String,String> queryMap){
        return mIHttpHelper.getWeather(text,queryMap);
    }

    /****************************** 【end】 网络接口部分  【end】****************************/

    //=========================================== 分割线 ===========================================//

    /****************************** 【start】 文件处理部分  【start】****************************/

    /****************************** 【end】 文件处理部分  【end】****************************/

    //=========================================== 分割线 ===========================================//

    /****************************** 【start】 数据库操作部分  【start】****************************/

    /****************************** 【end】 数据库操作部分  【end】****************************/
}
