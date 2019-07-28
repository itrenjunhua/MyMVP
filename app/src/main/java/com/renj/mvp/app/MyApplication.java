package com.renj.mvp.app;

import android.app.Application;

import com.renj.common.app.BaseApplication;
import com.renj.common.app.IApplication;
import com.renj.httplibrary.RetrofitUtil;
import com.renj.mvp.mode.db.DBHelper;
import com.renj.mvp.mode.file.FileHelper;
import com.renj.mvp.mode.http.ApiServer;
import com.renj.mvp.mode.http.HttpHelper;
import com.renj.mvpbase.mode.ModelManager;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-11   16:53
 * <p>
 * 描述：继承至Application的类，设置默认全局的异常处理，初始化全局的相关变量等操作
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class MyApplication extends BaseApplication implements IApplication {

    @Override
    public void init(Application application) {

        // 初始化 Retrofit
        RetrofitUtil.newInstance()
                .addApiServerClass(ApiServer.class);

        // 初始化 ModelManager，注意 需要先 初始化 Retrofit
        ModelManager.newInstance()
                .addDBHelper(new DBHelper())
                .addFileHelper(new FileHelper())
                .addHttpHelper(new HttpHelper());

        initModuleApplication();
    }

    private void initModuleApplication() {
        for (String moduleApplication : ModuleConfig.MODULE_APPLICATION_CLASS) {
            try {
                Class<?> moduleApplicationClass = Class.forName(moduleApplication);
                Object newInstance = moduleApplicationClass.newInstance();
                if (newInstance instanceof IApplication) {
                    ((IApplication) newInstance).init(getInstance());
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
}
