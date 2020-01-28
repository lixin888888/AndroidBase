package com.lixin.xinu.utils;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class OkNet {
    static MediaType json = MediaType.parse("application/json; charset=utf-8");
    public static Request getRequest(Object data,String url){
        Gson gson = new Gson();
        String jStr = gson.toJson(data,data.getClass());
        RequestBody requestBody = RequestBody.create(json,jStr);
        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        return request;
    }
}
