package com.lixin.xinu.net_services;

import com.lixin.xinu.dto.SearchGoodsQueryParam;
import com.lixin.xinu.entities.Goods;
import com.lixin.xinu.utils.RetrofitManager;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class GoodsServiceTest {

    @Test
    public void addGoods() {
    }

    @Test
    public void searchGoods() {

        GoodsService goodsService = RetrofitManager.createService(GoodsService.class);
        SearchGoodsQueryParam searchGoodsQueryParam = new SearchGoodsQueryParam();
        searchGoodsQueryParam.setClassify1((short) 2);
        searchGoodsQueryParam.setClassify2((short) 5);
        List<Goods> goods = goodsService.searchGoods(searchGoodsQueryParam);
        System.out.println(goods.size());

    }
}