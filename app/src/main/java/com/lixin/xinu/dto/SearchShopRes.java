package com.lixin.xinu.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchShopRes {
    private static final long serialVersionUID = -1L;
    private Long id;
    private String productSn;
    private Long brandId;
    private String brandName;
    private Long productCategoryId;
    private String productCategoryName;
    private String pic;
    private String name;
    private String subTitle;
    private String keywords;
    private BigDecimal price;
    private Integer sale;
    private Integer newStatus;
    private Integer recommandStatus;
    private Integer stock;
    private Integer promotionType;
    private Integer sort;
    private List<EsProductAttributeValue> attrValueList;
    //  新增店铺的位置和名字查询
    private String   shopName; //店铺名字
    private String   shopDesc; //店铺简介
    private String   shopAddress; // 店铺位置简介
    private String   location; //构建位置查询的地理初始化字段
    private String   logo; //搜索显示的店铺的图片
    private BigDecimal originPrice ;//搜索显示的商品原始价格
}
