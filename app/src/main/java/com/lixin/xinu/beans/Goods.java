package com.lixin.xinu.beans;

import java.util.List;

public class Goods {

    private String Id;
    private String goodId; // 商品索引
    private String goodsName; //商品名称
    private String goodsDetail; // 商品的简短介绍
    private int salesCount; //商品的月销售量
    private int goodsPrices; // 商品的价格
    private List<String> goodsImages; //商品的图片详情
    private String goodscategory; // 商品的分类
    private List<PingLun> goodPinglun; // 商品的评论
    private String goodShopId; //该商品属于那个商铺
    private String goodsSize; // 商品规格

    public String getGoodsSize() {
        return goodsSize;
    }

    public void setGoodsSize(String goodsSize) {
        this.goodsSize = goodsSize;
    }

    public Goods() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(String goodsDetail) {
        this.goodsDetail = goodsDetail;
    }

    public int getSalesCount() {
        return salesCount;
    }

    public void setSalesCount(int salesCount) {
        this.salesCount = salesCount;
    }

    public int getGoodsPrices() {
        return goodsPrices;
    }

    public void setGoodsPrices(int goodsPrices) {
        this.goodsPrices = goodsPrices;
    }

    public List<String> getGoodsImages() {
        return goodsImages;
    }

    public void setGoodsImages(List<String> goodsImages) {
        this.goodsImages = goodsImages;
    }

    public String getGoodscategory() {
        return goodscategory;
    }

    public void setGoodscategory(String goodscategory) {
        this.goodscategory = goodscategory;
    }

    public List<PingLun> getGoodPinglun() {
        return goodPinglun;
    }

    public void setGoodPinglun(List<PingLun> goodPinglun) {
        this.goodPinglun = goodPinglun;
    }

    public String getGoodShopId() {
        return goodShopId;
    }

    public void setGoodShopId(String goodShopId) {
        this.goodShopId = goodShopId;
    }
}

