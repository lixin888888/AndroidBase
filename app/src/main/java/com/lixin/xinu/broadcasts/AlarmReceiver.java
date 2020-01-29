package com.lixin.xinu.broadcasts;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;

import com.lixin.xinu.functions.notification.NotifacationUtil;


public class AlarmReceiver extends BroadcastReceiver {
    String TAG = "AlarmReceiver";
    Vibrator validator = null;
    @SuppressLint("ServiceCast")
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "onReceive: " );
        validator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        NotifacationUtil.setNotifaction(context);
        //startVibrator();
    }

    /**
     * 震动
     */
    private void startVibrator() {
        /**
         * 想设置震动大小可以通过改变pattern来设定，如果开启时间太短，震动效果可能感觉不到
         *
         */
        long[] pattern = {500, 1000, 500, 1000}; // 停止 开启 停止 开启
        validator.vibrate(pattern, 0);
    }







}
