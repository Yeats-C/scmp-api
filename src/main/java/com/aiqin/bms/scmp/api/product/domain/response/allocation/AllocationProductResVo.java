package com.aiqin.bms.scmp.api.product.domain.response.allocation;

/**
 * 描述:订购单sku返回详情
 *
 * @Author: Kt.w
 * @Date: 2019/1/10
 * @Version 1.0
 * @since 1.0
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("sku返回详情")
public class AllocationProductResVo {

    @ApiModelProperty("sku编号")
//    @JsonProperty(value = "sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
//    @JsonProperty(value = "sku_code")
    private String skuName;

    @ApiModelProperty("品类")
//    @JsonProperty(value = "sku_code")
    private String category;

    @ApiModelProperty("品牌")
//    @JsonProperty(value = "sku_code")
    private String brand;

    @ApiModelProperty("颜色")
//    @JsonProperty(value = "sku_code")
    private String color;

    @ApiModelProperty("规格")
//    @JsonProperty(value = "sku_code")
    private String specification;

    @ApiModelProperty("型号")
//    @JsonProperty(value = "sku_code")
    private String model;

    @ApiModelProperty("单位(销售单位)")
//    @JsonProperty(value = "sku_code")
    private String unit;

    @ApiModelProperty("类别")
//    @JsonProperty(value = "sku_code")
    private String classes;

    @ApiModelProperty("类型")
//    @JsonProperty(value = "sku_code")
    private String type;

    @ApiModelProperty("库存单位")
//    @JsonProperty(value = "sku_code")
    private String inventoryUnit;

    @ApiModelProperty("库存")
//    @JsonProperty(value = "sku_code")
    private Long inventory;

    @ApiModelProperty("税率")
//    @JsonProperty(value = "sku_code")
    private BigDecimal tax;

    @ApiModelProperty("含税单价")
//    @JsonProperty(value = "sku_code")
    private BigDecimal taxPrice;

    @ApiModelProperty("数量")
//    @JsonProperty(value = "sku_code")
    private Long quantity;

    @ApiModelProperty("出库数量")
//    @JsonProperty(value = "sku_code")
    private Long callOutQuantity;

    @ApiModelProperty("入库数量")
//    @JsonProperty(value = "sku_code")
    private Long  callInQuantity;

    @ApiModelProperty("含税总价")
//    @JsonProperty(value = "sku_code")
    private BigDecimal taxAmount;

    @ApiModelProperty("出库含税总成本")
//    @JsonProperty(value = "call_out_tax_amount")
    private BigDecimal callOutTaxAmount;

    @ApiModelProperty("入库含税总成本")
//    @JsonProperty(value = "call_in_tax_amount")
    private BigDecimal callInTaxAmount;

    @ApiModelProperty("删除标记，0未删除 1已删除")
//    @JsonProperty(value = "del_flag")
    private Byte delFlag;

    @ApiModelProperty("创建时间")
//    @JsonProperty(value = "create_time")
    private Date createTime;

    @ApiModelProperty("创建人")
//    @JsonProperty(value = "create_by")
    private String createBy;

    @ApiModelProperty("更新时间")
//    @JsonProperty(value = "update_time")
    private Date updateTime;

    @ApiModelProperty("更新人")
//    @JsonProperty(value = "update_by")
    private String updateBy;

    @ApiModelProperty("图片地址")
//    @JsonProperty(value = "picture_url")
    private String pictureUrl;

    @ApiModelProperty("行号")
//    @JsonProperty(value = "line_num")
    private Long lineNum;
}
