package com.aiqin.bms.scmp.api.product.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
@Getter
@Setter
public class ProductSku {
    private Long id;

    private String skuId;

    private String skuCode;

    private String spuCode;

    private String spec;

    private String unit;

    private String productId;

    private String productCode;

    private String productName;

    private Boolean skuStatus;

    private String logo;

    private String images;

    private String itroImages;

    private BigDecimal price;

    private Date createTime;

    private Date updateTime;

    private String createBy;

    private String updateBy;

    private String procurementSectionCode;

    private String procurementSectionName;


}