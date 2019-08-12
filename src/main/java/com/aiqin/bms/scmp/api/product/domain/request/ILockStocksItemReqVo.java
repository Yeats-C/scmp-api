package com.aiqin.bms.scmp.api.product.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className ILockStockItemReqVo
 * @date 2019/1/9 10:46
 * @description 库存锁定明细请求VO
 */
@ApiModel("库存锁定明细请求VO")
@Data
public class ILockStocksItemReqVo {

    @ApiModelProperty("sku编号")
    @JsonProperty(value = "sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty(value = "sku_name")
    private String skuName;

    @ApiModelProperty("图片地址")
    @JsonProperty(value = "picture_url")
    private String pictureUrl;

    @ApiModelProperty("规格")
    @JsonProperty(value = "norms")
    private String norms;

    @ApiModelProperty("单位编号")
    @JsonProperty(value = "unit_code")
    private String unitCode;

    @ApiModelProperty("单位名称")
    @JsonProperty(value = "unit_name")
    private String unitName;

    @ApiModelProperty("颜色名称")
    @JsonProperty(value = "color_name")
    private String colorName;

    @ApiModelProperty("颜色编码")
    @JsonProperty(value = "color_code")
    private String colorCode;

    @ApiModelProperty("进货规格")
    @JsonProperty(value = "purchase_norms")
    private String purchaseNorms;

    @ApiModelProperty("预计出库数量")
    @JsonProperty(value = "pre_outbound_num")
    private Long preOutboundNum;

    @ApiModelProperty("预计出库主数量")
    @JsonProperty(value = "pre_outbound_main_num")
    private Long preOutboundMainNum;

    @ApiModelProperty("预计含税进价")
    @JsonProperty(value = "pre_tax_purchase_amount")
    private Long preTaxPurchaseAmount;

    @ApiModelProperty("预计含税总价")
    @JsonProperty(value = "pre_tax_amount")
    private Long preTaxAmount;

    @ApiModelProperty("数量")
    @JsonProperty(value = "num")
    private Long num;

    @ApiModelProperty("操作人")
    @JsonProperty(value = "operator")
    private String operator;

    @ApiModelProperty("单据类型")
    @JsonProperty(value = "document_type")
    private Integer documentType;

    @ApiModelProperty("单据号")
    @JsonProperty(value = "document_num")
    private String documentNum;

    @ApiModelProperty("来源单据类型")
    @JsonProperty(value = "source_document_type")
    private Integer sourceDocumentType;

    @ApiModelProperty("来源单据号")
    @JsonProperty(value = "source_document_num")
    private String sourceDocumentNum;

    @ApiModelProperty("商品备注")
    @JsonProperty(value = "remark")
    private String remark;
}
