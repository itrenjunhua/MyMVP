package com.renj.utils.system;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.renj.utils.common.UIUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：itrenjunhua@163.com
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
    private List<ChannelGroupInfo> channelGroupInfoList = new ArrayList<>();
    private List<ChannelInfo> channelInfoList = new ArrayList<>();

    private NotificationUtils() {
    }

    public static NotificationUtils getInstance() {
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
     * 增加通道信息组，需要在 {@link #showNotification(Context, int, ChannelInfo, int, int, String, String, String, Intent)} 方法之前调用才生效.<br/><br/>
     * Android O (Build.VERSION.SDK_INT >= 26)引入了 通知渠道（Notification Channels），以提供统一的系统来帮助用户管理通知，
     * 如果是针对 android O 为目标平台时，必须实现一个或者多个通知渠道，以向用户显示通知。
     * 若并不以 Android O 为目标平台，当应用运行在 android O 设备上时，其行为将与运行在 Android 7.0 上时相同。
     *
     * @param channelGroupInfo 通道信息组
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationUtils addChannelGroups(@NonNull ChannelGroupInfo channelGroupInfo) {
        addChannelGroups(channelGroupInfo, false);
        return instance;
    }

    /**
     * 增加通道信息组，需要在 {@link #showNotification(Context, int, ChannelInfo, int, int, String, String, String, Intent)} 方法之前调用才生效.<br/><br/>
     * Android O (Build.VERSION.SDK_INT >= 26)引入了 通知渠道（Notification Channels），以提供统一的系统来帮助用户管理通知，
     * 如果是针对 android O 为目标平台时，必须实现一个或者多个通知渠道，以向用户显示通知。
     * 若并不以 Android O 为目标平台，当应用运行在 android O 设备上时，其行为将与运行在 Android 7.0 上时相同。
     *
     * @param channelGroupInfo 通道信息组
     * @param isClear          是否清楚原来的通道组信息
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationUtils addChannelGroups(@NonNull ChannelGroupInfo channelGroupInfo, boolean isClear) {
        if (isClear)
            channelGroupInfoList.clear();
        channelGroupInfoList.add(channelGroupInfo);
        return instance;
    }

    /**
     * 增加通道信息，需要在 {@link #showNotification(Context, int, ChannelInfo, int, int, String, String, String, Intent)} 方法之前调用才生效.<br/><br/>
     * Android O (Build.VERSION.SDK_INT >= 26)引入了 通知渠道（Notification Channels），以提供统一的系统来帮助用户管理通知，
     * 如果是针对 android O 为目标平台时，必须实现一个或者多个通知渠道，以向用户显示通知。
     * 若并不以 Android O 为目标平台，当应用运行在 android O 设备上时，其行为将与运行在 Android 7.0 上时相同。
     *
     * @param channelInfo 通道信息
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationUtils addChannel(@NonNull ChannelInfo channelInfo) {
        addChannel(channelInfo, false);
        return instance;
    }

    /**
     * 增加通道信息，需要在 {@link #showNotification(Context, int, ChannelInfo, int, int, String, String, String, Intent)} 方法之前调用才生效.<br/><br/>
     * Android O (Build.VERSION.SDK_INT >= 26)引入了 通知渠道（Notification Channels），以提供统一的系统来帮助用户管理通知，
     * 如果是针对 android O 为目标平台时，必须实现一个或者多个通知渠道，以向用户显示通知。
     * 若并不以 Android O 为目标平台，当应用运行在 android O 设备上时，其行为将与运行在 Android 7.0 上时相同。
     *
     * @param channelInfo 通道信息
     * @param isClear     是否清除原来的通道信息
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationUtils addChannel(@NonNull ChannelInfo channelInfo, boolean isClear) {
        if (isClear)
            channelInfoList.clear();
        channelInfoList.add(channelInfo);
        return instance;
    }

    /**
     * 删除 通道信息组
     *
     * @param channelGroupInfo
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationUtils deleteNotificationChannelGroup(@NonNull ChannelGroupInfo channelGroupInfo) {
        channelGroupInfoList.remove(channelGroupInfo);
        notificationManager.deleteNotificationChannelGroup(channelGroupInfo.channelGroupId);
        return instance;
    }

    /**
     * 删除 通道信息
     *
     * @param channelInfo
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationUtils deleteNotificationChannel(@NonNull ChannelInfo channelInfo) {
        channelInfoList.remove(channelInfo);
        notificationManager.deleteNotificationChannel(channelInfo.channelId);
        return instance;
    }

    /**
     * 根据参数显示一个通知，<b>注意：Android O(Build.VERSION.SDK_INT >= 26) 以上必须先调用方法增加通道信息</b><br/>
     *
     * @param context        上下文
     * @param requestCode    请求码
     * @param channelInfo    通道信息，表示该条消息进入到哪个通道。Android O(Build.VERSION.SDK_INT >= 26) 以上需要传递，否则可以传递 {@code null}
     *                       通道和通道组可以通过{@link #addChannel(ChannelInfo)}、{@link #addChannel(ChannelInfo, boolean)}
     *                       {@link #addChannelGroups(ChannelGroupInfo)}、{@link #addChannelGroups(ChannelGroupInfo, boolean)}添加
     * @param iconId         显示图标
     * @param notificationId 通知id
     * @param ticker         通知时在状态栏显示的通知内容
     * @param title          标题
     * @param content        内容
     * @param intent         延迟意图
     * @see #addChannel(ChannelInfo)
     * @see #addChannel(ChannelInfo, boolean)
     * @see #addChannelGroups(ChannelGroupInfo)
     * @see #addChannelGroups(ChannelGroupInfo, boolean)
     */
    public void showNotification(@NonNull Context context, int requestCode, @Nullable ChannelInfo channelInfo, @DrawableRes int iconId,
                                 int notificationId, @NonNull String ticker, @NonNull String title,
                                 @NonNull String content, Intent intent) {
        if (Build.VERSION.SDK_INT >= 26) {
            createNotificationChannel();
        }

        Notification notification = getNotification(context, requestCode, channelInfo, iconId, ticker, title, content, intent);
        notificationManager.notify(notificationId, notification);
    }

    /**
     * 根据参数返回一个通知 {@link Notification} 对象，<b>注意：Android O(Build.VERSION.SDK_INT >= 26) 以上必须先调用方法增加通道信息</b><br/>
     *
     * @param context     上下文
     * @param requestCode 请求码
     * @param channelInfo 通道信息，表示该条消息进入到哪个通道。Android O(Build.VERSION.SDK_INT >= 26) 以上需要传递，否则可以传递 {@code null}
     *                    通道和通道组可以通过{@link #addChannel(ChannelInfo)}、{@link #addChannel(ChannelInfo, boolean)}
     *                    {@link #addChannelGroups(ChannelGroupInfo)}、{@link #addChannelGroups(ChannelGroupInfo, boolean)}添加
     * @param iconId      显示图标
     * @param ticker      通知时在状态栏显示的通知内容
     * @param title       标题
     * @param content     内容
     * @param intent      延迟意图
     * @return {@link Notification} 对象
     * @see #addChannel(ChannelInfo)
     * @see #addChannel(ChannelInfo, boolean)
     * @see #addChannelGroups(ChannelGroupInfo)
     * @see #addChannelGroups(ChannelGroupInfo, boolean)
     */
    public Notification getNotification(@NonNull Context context, int requestCode, @Nullable ChannelInfo channelInfo, @DrawableRes int iconId,
                                        @NonNull String ticker, @NonNull String title, @NonNull String content, Intent intent) {
        PendingIntent pendingIntent = null;
        if (intent != null)
            pendingIntent = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= 26) {
            if (channelInfo == null)
                throw new IllegalArgumentException("Android O 以上 ChannelInfo 不能为 null.");

            Notification.Builder builder = getChannelNotification(context, channelInfo.channelId)
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
        if (channelInfoList.size() <= 0)
            throw new IllegalStateException("Android O 以上需要设置 Channel 信息，调用 addChannel() 等方法。");

        List<NotificationChannelGroup> channelGroups = new ArrayList<>();
        List<NotificationChannel> channels = new ArrayList<>();

        if (channelGroupInfoList.size() > 0) {
            for (ChannelGroupInfo channelGroupInfo : channelGroupInfoList) {
                NotificationChannelGroup channelGroup = new NotificationChannelGroup(channelGroupInfo.channelGroupId, channelGroupInfo.channelGroupValue);
                channelGroups.add(channelGroup);
            }
            notificationManager.createNotificationChannelGroups(channelGroups);
        }

        for (ChannelInfo channelInfo : channelInfoList) {
            NotificationChannel channel = new NotificationChannel(channelInfo.channelId, channelInfo.channelValue, NotificationManager.IMPORTANCE_HIGH);
            channel.setGroup(channelInfo.channelGroupId);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            channels.add(channel);
        }

        notificationManager.createNotificationChannels(channels);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Notification.Builder getChannelNotification(@NonNull Context context, String channelId) {
        return new Notification.Builder(context, channelId);
    }

    private NotificationCompat.Builder getNotification_25(@NonNull Context context) {
        return new NotificationCompat.Builder(context);
    }


    @TargetApi(Build.VERSION_CODES.O)
    static class ChannelGroupInfo {
        String channelGroupId;
        String channelGroupValue;

        public ChannelGroupInfo(@NonNull String channelGroupId, @NonNull String channelGroupValue) {
            this.channelGroupId = channelGroupId;
            this.channelGroupValue = channelGroupValue;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ChannelGroupInfo that = (ChannelGroupInfo) o;
            return Objects.equals(channelGroupId, that.channelGroupId) &&
                    Objects.equals(channelGroupValue, that.channelGroupValue);
        }

        @Override
        public int hashCode() {
            return Objects.hash(channelGroupId, channelGroupValue);
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    public static class ChannelInfo {
        String channelId;
        String channelGroupId;
        String channelValue;

        public ChannelInfo(@NonNull String channelId, @NonNull String channelValue) {
            this.channelId = channelId;
            this.channelValue = channelValue;
        }

        public ChannelInfo(@NonNull String channelId, @NonNull String channelGroupId, @NonNull String channelValue) {
            this.channelId = channelId;
            this.channelGroupId = channelGroupId;
            this.channelValue = channelValue;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ChannelInfo that = (ChannelInfo) o;
            return Objects.equals(channelId, that.channelId) &&
                    Objects.equals(channelGroupId, that.channelGroupId) &&
                    Objects.equals(channelValue, that.channelValue);
        }

        @Override
        public int hashCode() {
            return Objects.hash(channelId, channelGroupId, channelValue);
        }
    }
}
