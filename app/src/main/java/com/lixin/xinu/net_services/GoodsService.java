package com.lixin.xinu.net_services;


import com.lixin.xinu.dto.SearchGoodsQueryParam;
import com.lixin.xinu.entities.Goods;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GoodsService {

    /**
     * 添加goods
     */
    Integer addGoods(Goods goods);

    /**
     * 查询goods 分类
     */
    @POST("/searchGoods")
    List<Goods> searchGoods(@Body SearchGoodsQueryParam searchGoodsQueryParam);

}
