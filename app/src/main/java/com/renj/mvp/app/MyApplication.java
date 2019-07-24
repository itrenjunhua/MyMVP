package com.renj.mvp.app;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.renj.common.app.AppConfig;
import com.renj.common.app.BaseApplication;
import com.renj.common.app.IApplication;
import com.renj.common.utils.ApplicationManager;
import com.renj.httplibrary.RetrofitUtil;
import com.renj.mvp.dagger.ActivityModule;
import com.renj.mvp.dagger.ApplicationComponent;
import com.renj.mvp.dagger.ApplicationModule;
import com.renj.mvp.dagger.DaggerApplicationComponent;
import com.renj.mvp.dagger.FragmentModule;
import com.renj.mvp.mode.db.DBHelper;
import com.renj.mvp.mode.db.bean.DaoMaster;
import com.renj.mvp.mode.db.bean.DaoSession;
import com.renj.mvp.mode.file.FileHelper;
import com.renj.mvp.mode.http.ApiServer;
import com.renj.mvp.mode.http.HttpHelper;
import com.renj.mvpbase.mode.ModelManager;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

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
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        ApplicationComponent applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule())
                .activityModule(new ActivityModule())
                .fragmentModule(new FragmentModule())
                .build();
        applicationComponent.inject(this);
        return applicationComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init(this);
        ApplicationManager.initApplications(this);
    }

    @Override
    public void init(Application application) {
        // 初始化 Retrofit
        RetrofitUtil.newInstance()
                .addApiServerClass(ApiServer.class);

        // 初始化数据库框架
        initGreenDao(application);

        // 初始化 ModelManager，注意 需要先 初始化 Retrofit
        ModelManager.newInstance()
                .addDBHelper(new DBHelper())
                .addFileHelper(new FileHelper())
                .addHttpHelper(new HttpHelper());
    }

    public static DaoSession daoSession;

    /**
     * 初始化GreenDao,直接在Application中进行初始化操作
     * @param application
     */
    private void initGreenDao(Application application) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(application, AppConfig.DATABASE_NAME);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

}
