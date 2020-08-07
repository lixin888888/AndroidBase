package com.lixin.xinu.netServices;

import com.lixin.xinu.R;
import com.lixin.xinu.dto.SearchGoodsReq;
import com.lixin.xinu.dto.EsGoods;
import com.lixin.xinu.entities.CommonPage;
import com.lixin.xinu.entities.CommonResult;
import com.lixin.xinu.entities.Goods;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoodsServiceTest {

    @Test
    public void addGoods() {
    }

    @Test
    public void searchGoods() throws InterruptedException {

        GoodsService goodsService = RetrofitManager.createService(GoodsService.class,NetServicePrefixAddress.ADMIN);

        Call<CommonResult<CommonPage<Goods>>> commonPageCommonResult = goodsService.searchGoods(1,1,10);
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

    @Test
    public void searchGoodsES() throws InterruptedException {
        SearchGoodsReq req = new SearchGoodsReq();
        req.setKeyword("小米");
        GoodsService goodsService = RetrofitManager.createService(GoodsService.class,NetServicePrefixAddress.ES);
        Call<CommonResult<CommonPage<EsGoods>>> goodsByEs = goodsService.searchGoodsByES(req.getBrandId(), req.getKeyword(), req.getProductCategoryId(), req.getSort(), req.getPageNum(), req.getPageSize());
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        goodsByEs.enqueue(new Callback<CommonResult<CommonPage<EsGoods>>>() {
            @Override
            public void onResponse(Call<CommonResult<CommonPage<EsGoods>>> call, Response<CommonResult<CommonPage<EsGoods>>> response) {
                System.out.println(response.body());
                countDownLatch.countDown();
            }

            @Override
            public void onFailure(Call<CommonResult<CommonPage<EsGoods>>> call, Throwable t) {
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
    }

}