package com.lixin.xinu.netServices;


import com.lixin.xinu.dto.SearchGoodsQueryParam;
import com.lixin.xinu.dto.SearchRecommandParam;
import com.lixin.xinu.entities.CommonPage;
import com.lixin.xinu.entities.CommonResult;
import com.lixin.xinu.entities.Goods;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GoodsService {

    /**
     * 添加goods
     */
    Integer addGoods(Goods goods);

    /**
     * 查询goods 分类
     */
    @GET("product/list")
    Call<CommonResult<CommonPage<Goods>>> searchGoods(
            @Query("publishStatus") Integer publishStatus,
            @Query("pageNum") int pageNum,
            @Query("pageSize") int pageSize
    );

    @GET("product/list")
    CommonResult<CommonPage<Goods>> searchGoodsByEs(      @Query("keyword") String keyword,
                                                          @Query("pageNum") int pageNum,
                                                          @Query("pageSize") int pageSize);

}
