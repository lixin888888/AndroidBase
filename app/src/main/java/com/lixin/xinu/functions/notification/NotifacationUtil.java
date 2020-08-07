package com.lixin.xinu.functions.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import android.text.TextUtils;

import com.lixin.xinu.R;

import static android.app.Notification.VISIBILITY_SECRET;
import static android.app.NotificationManager.IMPORTANCE_DEFAULT;

public class NotifacationUtil {


    public static void setNotifaction(Context context){
        Notification.Builder builder = new Notification.Builder(context);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Bitmap LargeBitmap = BitmapFactory.decodeResource(Resources.getSystem(), R.mipmap.kuaiyu);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String title = "犀牛";
            String channelId = "1";
            NotificationChannel notificationChannel =
                    new NotificationChannel(channelId, TextUtils.isEmpty(title) ? "通知" : title, IMPORTANCE_DEFAULT);
            notificationChannel.setVibrationPattern(new long[]{100, 100, 200});//设置震动模式
            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            notificationChannel.setLockscreenVisibility(VISIBILITY_SECRET);//锁屏显示通知
            notificationChannel.enableLights(true);//闪光灯
            notificationChannel.setShowBadge(true);
            notificationChannel.enableVibration(true);//是否允许震动
            manager.createNotificationChannel(notificationChannel);
            Notification notification = new NotificationCompat.Builder(context,channelId)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentText("休息一下吧")
                    .setSound(soundUri)
                    .setWhen(System.currentTimeMillis())
                    .build();
            manager.notify(1,notification);

        }else {
            builder.setContentTitle("北极熊也有微笑")  //标题
                    .setContentText("北极熊是本人认为最棒的动物，没有之一")   //内容
                    .setSubText("--北极熊很Happy～")     //内容下面的一小段文字
                    .setTicker("收到北极熊也有微笑发来的的消息～")      //收到信息后状态栏显示的文字信息
                    .setWhen(System.currentTimeMillis())    //系统显示时间
                    .setSmallIcon(R.mipmap.ic_launcher)     //收到信息后状态栏显示的小图标
                    .setLargeIcon(LargeBitmap)//大图标
                    .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)    //设置默认的三色灯与振动器
                    .setDefaults(Notification.DEFAULT_SOUND)    //设置系统的提示音
                    .setAutoCancel(true);       //设置点击后取消Notification
            //uilder.setContentIntent(pendingIntent);    //绑定PendingIntent对象
            manager.notify(1, builder.build());
        }
    }
}
