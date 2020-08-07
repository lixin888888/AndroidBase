package com.lixin.xinu.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class AccessPermissions {
     private Context mContext;
     private ArrayList<String> Permissions;
     private static AccessPermissions ass;

    private  AccessPermissions(Context mContext) {
        this.mContext = mContext;
    }

    public static AccessPermissions  getInstance(Context context){
        if (ass!=null){
            return ass;
        }
         ass = new AccessPermissions(context);
         return ass;
     }
     public AccessPermissions addPermission(String permission){
         Permissions.add(permission);
         return ass;
     }
     public void   DynamicAccessPermissions(){
          // 查看版本信息
          if(Build.VERSION.SDK_INT>=23){
              int i = ContextCompat.checkSelfPermission(mContext, Permissions.get(0));
              // 检查是否授权
              if(i!=PackageManager.PERMISSION_GRANTED){
                  ToRequestPermission();
              }
          }

        }

    public void ToRequestPermission() {
        new AlertDialog.Builder(mContext)
                .setTitle("南阳同城需要获取您的位置,为该动态标识位置;\n否则,将无法正常工作")
                .setPositiveButton("开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //开启权限  的方法
                        startRequestPermission();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转到应用设置界面
                        return;
                        //  getActivity().finish();
                    }

                }).setCancelable(false).show();
    }
    public void startRequestPermission() {
        for (int i = 0; i < Permissions.size(); i++) {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Permissions.get(i)},i);
        }
    }
}
