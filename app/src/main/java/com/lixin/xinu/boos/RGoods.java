package com.lixin.xinu.boos;

import android.net.Uri;

public class RGoods {
    String goodsName;
    Uri goodsImage;

    public RGoods(String goodsName, Uri goodsImage) {
        this.goodsName = goodsName;
        this.goodsImage = goodsImage;
    }

    public RGoods() {
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Uri getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(Uri goodsImage) {
        this.goodsImage = goodsImage;
    }
}
