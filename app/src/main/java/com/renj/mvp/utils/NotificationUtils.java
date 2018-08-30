package com.renj.mvp.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.renj.mvp.R;


/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2018-08-30   18:03
 * <p>
 * 描述：系统通知工具类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class NotificationUtils {
    private static NotificationUtils instance;
    private static NotificationManager notificationManager;
    public static final String CHANNEL_ID = "anlovek_nursing_id";
    public static final String CHANNEL_NAME = "anlovek_nursing_name";

    public static NotificationUtils newInstance() {
        if (instance == null) {
            synchronized (NotificationUtils.class) {
                if (instance == null) {
                    instance = new NotificationUtils();
                    notificationManager = (NotificationManager) UIUtils.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                }
            }
        }
        return instance;
    }

    public void showNotification(@NonNull Context context, int requestCode ,int notificationId, @NonNull String ticker, @NonNull String title, @NonNull String content, Intent intent) {
        // 直接使用系统的消息类型
        // RemoteViews contentView = new RemoteViews(AppConstants.packageName, R.layout.message_notification);//远程视图
        // 设置远程视图中的控件内容
        // contentView.setImageViewResource(R.CHANNEL_ID.iv_notification_message_priority, R.drawable.shape_oval_priority2);
        // contentView.setTextViewText(R.CHANNEL_ID.tv_notification_message_time, DateUtils.getCurrentDate("d日 HH:mm"));
        // contentView.setTextViewText(R.CHANNEL_ID.tv_notification_message_title, "通知标题");
        // contentView.setTextViewText(R.CHANNEL_ID.tv_notification_message_address, "通知内容内容内容，老人地址");

        Notification notification = getNotification(context, requestCode,ticker, title, content, intent);
        notificationManager.notify(notificationId, notification);
    }

    public Notification getNotification(@NonNull Context context,int requestCode, @NonNull String ticker, @NonNull String title, @NonNull String content, Intent intent) {
        PendingIntent pendingIntent = null;
        if (intent != null)
            pendingIntent = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= 26) {
            createNotificationChannel();
            Notification.Builder builder = getChannelNotification(context)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true)// 点击通知头自动取消
                    .setTicker(ticker)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setFullScreenIntent(null, true);// 不设置此项不会悬挂,false 不会出现悬挂

            if (pendingIntent != null)
                builder.setContentIntent(pendingIntent);

            return builder.build();

        } else {
            NotificationCompat.Builder builder = getNotification_25(context)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true)// 点击通知头自动取消
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setTicker(ticker)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setVisibility(Notification.VISIBILITY_PUBLIC) // 锁屏显示
                    .setDefaults(NotificationCompat.DEFAULT_ALL)// 设置铃声及震动效果等
                    .setFullScreenIntent(null, true); // 不设置此项不会悬挂,false 不会出现悬挂

            if (pendingIntent != null)
                builder.setContentIntent(pendingIntent);

            return builder.build();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        notificationManager.createNotificationChannel(channel);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Notification.Builder getChannelNotification(@NonNull Context context) {
        return new Notification.Builder(context, CHANNEL_ID);
    }

    private NotificationCompat.Builder getNotification_25(@NonNull Context context) {
        return new NotificationCompat.Builder(context);
    }
}
