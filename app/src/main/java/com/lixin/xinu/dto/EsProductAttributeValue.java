package com.lixin.xinu.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EsProductAttributeValue {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long productAttributeId;
    private String value;
    //属性参数：0->规格；1->参数
    private Integer type;
    //属性名称
    private String name;
}
