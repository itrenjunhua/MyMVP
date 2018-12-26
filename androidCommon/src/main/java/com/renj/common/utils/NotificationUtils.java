package com.renj.common.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2018-06-21   18:03
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
    private static String CHANNEL_ID = "custom_id";
    private static String CHANNEL_NAME = "custom_name";

    private NotificationUtils() {
    }

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

    /**
     * 设置 CHANNEL_ID 和 CHANNEL_NAME<br/><br/>
     * Android O (Build.VERSION.SDK_INT >= 26)引入了 通知渠道（Notification Channels），以提供统一的系统来帮助用户管理通知，
     * 如果是针对 android O 为目标平台时，必须实现一个或者多个通知渠道，以向用户显示通知。
     * 若并不以 Android O 为目标平台，当应用运行在 android O 设备上时，其行为将与运行在 Android 7.0 上时相同。
     *
     * @param channelId   通道标识
     * @param channelName 通道名字
     * @return
     */
    public NotificationUtils setChannelValue(String channelId, String channelName) {
        NotificationUtils.CHANNEL_ID = channelId;
        NotificationUtils.CHANNEL_NAME = channelName;
        return instance;
    }

    /**
     * 根据参数显示一个通知
     *
     * @param context        上下文
     * @param requestCode    请求码
     * @param iconId         显示图标
     * @param notificationId 通知id
     * @param ticker         通知时在状态栏显示的通知内容
     * @param title          标题
     * @param content        内容
     * @param intent         延迟意图
     */
    public void showNotification(@NonNull Context context, int requestCode, @DrawableRes int iconId,
                                 int notificationId, @NonNull String ticker, @NonNull String title,
                                 @NonNull String content, Intent intent) {

        // RemoteViews contentView = new RemoteViews(packageName, R.layout.message_notification);// 远程视图
        // 设置远程视图中的控件内容
        // contentView.setImageViewResource(R.CHANNEL_ID.iv_notification_message_priority, R.drawable.icon);
        // contentView.setTextViewText(R.CHANNEL_ID.tv_notification_message_time, DateUtils.getCurrentDate("d日 HH:mm"));
        // contentView.setTextViewText(R.CHANNEL_ID.tv_notification_message_title, "通知标题");
        // contentView.setTextViewText(R.CHANNEL_ID.tv_notification_message_content, "通知内容内容内容");

        // 直接使用系统的消息类型
        if (Build.VERSION.SDK_INT >= 26)
            createNotificationChannel();

        Notification notification = getNotification(context, requestCode, iconId, ticker, title, content, intent);
        notificationManager.notify(notificationId, notification);
    }

    /**
     * 根据参数返回一个通知 {@link Notification} 对象
     *
     * @param context     上下文
     * @param requestCode 请求码
     * @param iconId      显示图标
     * @param ticker      通知时在状态栏显示的通知内容
     * @param title       标题
     * @param content     内容
     * @param intent      延迟意图
     * @return {@link Notification} 对象
     */
    public Notification getNotification(@NonNull Context context, int requestCode, @DrawableRes int iconId,
                                        @NonNull String ticker, @NonNull String title, @NonNull String content, Intent intent) {
        PendingIntent pendingIntent = null;
        if (intent != null)
            pendingIntent = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= 26) {
            Notification.Builder builder = getChannelNotification(context)
                    .setSmallIcon(iconId)
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
                    .setSmallIcon(iconId)
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
