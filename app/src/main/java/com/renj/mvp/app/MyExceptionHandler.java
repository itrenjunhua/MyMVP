package com.renj.mvp.app;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-11   17:03
 * <p>
 * 描述：定义全局的异常处理类，使用单例设计模式<br/>
 * 如果发现异常，就会将版本号、设配信息和异常信息收集保存到本地然后发送到服务器。<br/>
 * <b>发送到服务器的代码未完成</b>
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class MyExceptionHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "MyExceptionHandler";
    private volatile static MyExceptionHandler MY_EXCEPTION_HANDLER = new MyExceptionHandler();

    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultUncaughtExceptionHandler;
    private HashMap<String, String> mParamsMap = new HashMap<>();

    private MyExceptionHandler() {
    }

    /**
     * 获取MyExceptionHandler实例
     *
     * @return
     */
    @org.jetbrains.annotations.Contract(pure = true)
    public static MyExceptionHandler newInstance() {
        return MY_EXCEPTION_HANDLER;
    }

    /**
     * 初始化异常处理器
     */
   public void initMyExceptionHandler(Context context) {
        this.mContext = context;
        //获取系统默认的UncaughtException处理器
        mDefaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置程序的默认UncaughtException处理器为自定义的处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (!handleException(e) && mDefaultUncaughtExceptionHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultUncaughtExceptionHandler.uncaughtException(t, e);
        } else {
            // 延时3秒关闭程序
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Log.e(TAG, "error : ", ex);
            }
            //退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 处理异常
     *
     * @param throwable
     * @return true表示处理了，false表示未处理
     */
    @org.jetbrains.annotations.Contract("null -> false")
    private boolean handleException(Throwable throwable) {
        if (null == throwable)
            return false;
        // 添加自定义信息
        addCustomInfo();
        // 收集设备信息
        collectDeviceInfo(mContext);
        // 保存日志文件(自定义信息、设配信息、错误原因等保存)
        String filePath = saveExceptionInfo2File(throwable);
        if (null == filePath) {
            return false;
        } else {
            // 发送到服务器
            sendExceptionInfo2Server(filePath);
        }
        return true;
    }

    /**
     * 将信息上传到服务器
     *
     * @param filePath 文件路径
     */
    private void sendExceptionInfo2Server(String filePath) {

    }

    /**
     * 添加自定义信息
     */
    private void addCustomInfo() {
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ").format(new Date());
        mParamsMap.put("time", time);
    }

    /**
     * 收集设备参数信息
     *
     * @param context
     */
    public void collectDeviceInfo(Context context) {
        try {
            // 获取versionName,versionCode
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                mParamsMap.put("versionName", versionName);
                mParamsMap.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "获取程序版本信息失败...", e);
        }
        // 获取所有系统信息
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                mParamsMap.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                Log.e(TAG, "获取系统信息失败...", e);
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param throwable
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    @Nullable
    private String saveExceptionInfo2File(Throwable throwable) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : mParamsMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        throwable.printStackTrace(printWriter);
        Throwable cause = throwable.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            long timestamp = System.currentTimeMillis();
            String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(timestamp));
            String fileName = "exlog-" + time + "-" + timestamp + ".log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/exinfo/";
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
            }
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "将异常信息保存到本地失败...", e);
            return null;
        }
    }
}
