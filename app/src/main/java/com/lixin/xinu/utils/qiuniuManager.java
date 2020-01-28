package com.lixin.xinu.utils;

import android.util.Log;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class qiuniuManager {

    private static Configuration configuration = new Configuration.Builder()
            .chunkSize(512 * 1024) // 分片上传时，每片的大小。 默认256K
            .putThreshhold(1024 * 1024) // 启用分片上传阀值。默认512K
            .connectTimeout(10)
            .useHttps(true)
            .responseTimeout(60)
            .build();
   private static UploadManager uploadManager = new UploadManager(configuration);

   public void upload(ArrayList<File> datas , final ArrayList<String> keys , String token){
//       注意闭包的存在
       int count = datas.size();
       for (int i = 0; i <count; i++) {
           uploadManager.put(datas.get(i),keys.get(i) , token, new UpCompletionHandler() {
               @Override
               public void complete(String key, ResponseInfo info, JSONObject response) {
                   if(info.isOK()) {
                       Log.i("qiniu", "Upload Success");
                   } else {
                       Log.i("qiniu", "Upload Fail");
                       //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                   }
                   Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + response);
               }
           },null);
       }
   }
}