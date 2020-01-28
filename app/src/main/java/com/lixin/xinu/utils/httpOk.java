package com.lixin.xinu.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public  class httpOk {
//    使用弱引用 防止内存泄漏
    private WeakReference<Handler> handlerWeakReference;
//    该对象

    private String url;

    private Object data;

    private int identification;

    public httpOk() {

    }
    public  httpOk addHandler(WeakReference<Handler> handlerWeakReference) {
        this.handlerWeakReference = handlerWeakReference;
        return this;
    }
    public httpOk addUrl (String url){
        this.url=url;
        return this ;
    }

    public httpOk addData (Object obj){
        this.data = obj;
        return this;
    }

    public httpOk addIdentification(int identification){
        this.identification = identification;
        return this;
    }

    public void postJson(){
        OkHttpClient client = new OkHttpClient();//创建client
        MediaType json = MediaType.parse("application/json; charset=utf-8");
        Gson gson = new Gson();
        String jStr = gson.toJson(data,data.getClass());
        RequestBody requestBody = RequestBody.create(json,jStr);
        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("testjosn","no ok");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                Log.i("testjosn",""+response.body().string());
                Handler handler = handlerWeakReference.get();
                if (handler==null){
//                    nothing to Do

                }else{
                    Message message = Message.obtain();
                    message.what = identification;
                    Bundle b = new Bundle();
                    b.putString("hello",response.body().string());
                    message.setData(b);
                    handler.sendMessage(message);
                }
            }
        });
    }

    public void postJsonHaving(){
        OkHttpClient client = new OkHttpClient();//创建client
        MediaType json = MediaType.parse("application/json; charset=utf-8");
        Gson gson = new Gson();
        String jStr = gson.toJson(data,data.getClass());
        RequestBody requestBody = RequestBody.create(json,jStr);
        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("testjosn","no ok");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("testjosn",""+response.body().string());

            }
        });
    }

    public Request HalfAutoPost(){
        MediaType json = MediaType.parse("application/json; charset=utf-8");
        Gson gson = new Gson();
        String jStr = gson.toJson(data,data.getClass());
        RequestBody requestBody = RequestBody.create(json,jStr);
        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        return  request;
    }
}

