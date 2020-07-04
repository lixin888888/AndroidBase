package com.lixin.xinu.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static final String DEFAULT = "http://192.168.0.222:8080";

    public static OkHttpClient getDefaultClient() {
        return getClient(null);
    }

    public static OkHttpClient getClient(Interceptor... interceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (null != interceptor && interceptor.length > 0) {
            for (int i = 0; i < interceptor.length; i++) {
                builder.addInterceptor(interceptor[i]);
            }
        }
        return builder.build();
    }

    public static Retrofit getRetrofit() {
        return getRetrofit(DEFAULT);
    }

    public static Retrofit getRetrofit(String url) {
        return getRetrofit(url, getDefaultClient());
    }

    public static Retrofit getRetrofit(OkHttpClient client) {
        return getRetrofit(DEFAULT, client);
    }

    public static Retrofit getRetrofit(String url, OkHttpClient client) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync());

        if (null == client) {
            builder.client(getDefaultClient());
        } else {
            builder.client(client);
        }
        // TODO: 16/7/14
        if (null == url || url.length() == 0) {
            builder.baseUrl(DEFAULT);
        } else {
            builder.baseUrl(url);
        }
        return builder.build();
    }

    public static <T> T createService(Class<T> serviceClass) {
        return getRetrofit().create(serviceClass);
    }
}
