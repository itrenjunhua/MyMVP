package com.renj.mvp.app;

import android.app.Application;

import com.renj.common.app.BaseApplication;
import com.renj.common.app.IApplication;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-11   16:53
 * <p>
 * 描述：继承至Application的类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class MainApplication extends BaseApplication implements IApplication {

    @Override
    public void init(Application application) {
        initModuleApplication(application);
    }

    private void initModuleApplication(Application application) {
        for (String moduleApplication : ModuleConfig.MODULE_APPLICATION_CLASS) {
            try {
                Class<?> moduleApplicationClass = Class.forName(moduleApplication);
                Object newInstance = moduleApplicationClass.newInstance();
                if (newInstance instanceof IApplication) {
                    ((IApplication) newInstance).init(application);
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
