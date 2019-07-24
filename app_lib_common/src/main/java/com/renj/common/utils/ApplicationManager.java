package com.renj.common.utils;

import android.app.Application;
import android.support.annotation.NonNull;

import com.renj.common.app.IApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2019-07-25   0:42
 * <p>
 * 描述：管理所有的 Application
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ApplicationManager {
    private static List<IApplication> applications = new ArrayList<>();

    public static void registerApplication(@NonNull IApplication application) {
        if (application != null) {
            applications.add(application);
        }
    }

    public static void initApplications(Application application) {
        for (IApplication iApplication : applications) {
            iApplication.init(application);
        }
    }

    public static List<IApplication> getApplications() {
        return applications;
    }
}
