package com.lixin.xinu.utils;


import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.lixin.xinu.beans.Token;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UploadThread extends Thread {
    private ArrayList<File> datas;
    private ArrayList<String> targets;
    private ArrayList<String> keys;
    private Gson gson = new Gson();
    public UploadThread(ArrayList<File> datas, ArrayList<String> target, ArrayList<String> keys){
        this.targets=target;
        this.datas=datas;
        this.keys = keys;
    }
    @Override
    public void run() {
         super.run();
//        先获得Token
         OkHttpClient oc = new OkHttpClient();
         MediaType json = MediaType.parse("application/json; charset=utf-8");
         final Token to = new Token("hello");
         String jsTr = gson.toJson(to,Token.class);
         RequestBody body = RequestBody.create(json,jsTr);
         final Request request = new Request.Builder()
                .url(targets.get(0))
                .post(body)
                .build();
         oc.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                Log.i("test",response.body().string());
                Token tk = gson.fromJson(response.body().string(), Token.class);
                qiuniuManager manager = new qiuniuManager();
                manager.upload(datas,keys,tk.getToken());
            }
        });
    }

}