package com.lixin.xinu.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 *
 */
public class AlarmUtil {
    /**
     * 获取AlarmManager的实例
     */
    private static AlarmManager manager;

    public  static  AlarmManager   getAlarmManager (Context context){
        if (manager == null){
            manager =  (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        }
        return manager;
    }

    /**
     * 开启周期任务
     */
    public static void sendRepeatAlarmBroadCast(Context context,int requestCode,int type, long  startTime, long cycleTime,Class cls){
        AlarmManager alarmManager = getAlarmManager(context);
        Intent intent = new Intent(context,cls);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,requestCode,intent,0);
        alarmManager.setRepeating(type,startTime,cycleTime,pendingIntent);
    }





}
