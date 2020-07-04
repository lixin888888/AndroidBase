package com.lixin.xinu.netServices;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lixin.xinu.dto.SearchGoodsQueryParam;
import com.lixin.xinu.entities.CommonPage;
import com.lixin.xinu.entities.CommonResult;
import com.lixin.xinu.entities.Goods;
import com.lixin.xinu.utils.RetrofitManager;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GoodsServiceTest {

    @Test
    public void addGoods() {
    }

    @Test
    public void searchGoods() throws InterruptedException {

        GoodsService goodsService = RetrofitManager.createService(GoodsService.class);
        SearchGoodsQueryParam searchGoodsQueryParam = new SearchGoodsQueryParam();
        searchGoodsQueryParam.setClassify1((short) 2);
        searchGoodsQueryParam.setClassify2((short) 5);
        Call<CommonResult<CommonPage<Goods>>> commonPageCommonResult = goodsService.searchGoods(searchGoodsQueryParam);
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        commonPageCommonResult.enqueue(new Callback<CommonResult<CommonPage<Goods>>>() {
            @Override
            public void onResponse(Call<CommonResult<CommonPage<Goods>>> call, Response<CommonResult<CommonPage<Goods>>> response) {
                System.out.println(response.body().getData().getList().toString());
                countDownLatch.countDown();
            }

            @Override
            public void onFailure(Call<CommonResult<CommonPage<Goods>>> call, Throwable t) {
                System.out.println(t.getCause());
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
    }

}