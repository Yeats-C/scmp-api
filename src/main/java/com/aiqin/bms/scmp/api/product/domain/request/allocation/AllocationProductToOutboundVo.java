package com.aiqin.bms.scmp.api.product.domain.request.allocation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Classname: AllocationProductToOutboundVo
 * 描述:
 * @Author: Kt.w
 * @Date: 2019/3/18
 * @Version 1.0
 * @Since 1.0
 */
@Data
public class AllocationProductToOutboundVo {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("调拨单编码")
    private String allocationCode;

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("品类")
    private String category;

    @ApiModelProperty("品牌")
    private String brand;

    @ApiModelProperty("颜色")
    private String color;

    @ApiModelProperty("规格")
    private String specification;

    @ApiModelProperty("型号")
    private String model;

    @ApiModelProperty("单位(销售单位)")
    private String unit;

    @ApiModelProperty("类别")
    private String classes;

    @ApiModelProperty("类型")
    private String type;

    @ApiModelProperty("库存单位")
    private String inventoryUnit;

    @ApiModelProperty("库存")
    private Long inventory;

    @ApiModelProperty("税率")
    private BigDecimal tax;

    @ApiModelProperty("含税单价")
    private BigDecimal taxPrice;

    @ApiModelProperty("数量")
    private Long quantity;

    @ApiModelProperty("含税总价")
    private BigDecimal taxAmount;

    @ApiModelProperty("删除标记，0未删除 1已删除")
    private Byte delFlag;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateBy;


    @ApiModelProperty("图片地址")
    private String pictureUrl;

    @ApiModelProperty("行号")
    private Long linenum;

}
