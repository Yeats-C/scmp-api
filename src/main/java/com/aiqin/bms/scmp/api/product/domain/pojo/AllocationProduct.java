package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import com.aiqin.bms.scmp.api.product.domain.response.allocation.SkuBatchRespVO;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@ApiModel("调拨商品表")
@Data
public class AllocationProduct extends CommonBean {
    @ApiModelProperty("主键")
    @JsonProperty(value = "id")
    private Long id;

    @ApiModelProperty("调拨单编码")
    @JsonProperty(value = "allocation_code")
    private String allocationCode;

    @ApiModelProperty("sku编号")
    @JsonProperty(value = "sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty(value = "sku_name")
    private String skuName;

    @ApiModelProperty("品类")
    @JsonProperty(value = "category")
    private String category;

    @ApiModelProperty("品牌")
    @JsonProperty(value = "brand")
    private String brand;

    @ApiModelProperty("颜色")
    @JsonProperty(value = "color")
    private String color;

    @ApiModelProperty("规格")
    @JsonProperty(value = "specification")
    private String specification;

    @ApiModelProperty("型号")
    @JsonProperty(value = "model")
    private String model;

    @ApiModelProperty("单位(销售单位)")
    @JsonProperty(value = "unit")
    private String unit;

    @ApiModelProperty("类别")
    @JsonProperty(value = "classes")
    private String classes;

    @ApiModelProperty("类型")
    @JsonProperty(value = "type")
    private String type;

    @ApiModelProperty("税率")
    @JsonProperty(value = "tax")
    private BigDecimal tax;

    @ApiModelProperty("含税成本")
    @JsonProperty(value = "tax_price")
    private BigDecimal taxPrice;

    @ApiModelProperty("数量")
    @JsonProperty(value = "quantity")
    private Long quantity;

    @ApiModelProperty("含税总成本")
    @JsonProperty(value = "tax_amount")
    private BigDecimal taxAmount;

    @ApiModelProperty("图片地址")
    @JsonProperty(value = "picture_url")
    private String pictureUrl;

    @ApiModelProperty("行号")
    @JsonProperty(value = "line_num")
    private Long lineNum;

    @ApiModelProperty("实际调拨出数量")
    @JsonProperty(value = "callout_actual_total_count")
    private Long calloutActualTotalCount;

    @ApiModelProperty("实际调拨入的数量")
    @JsonProperty(value = "callin_actual_total_count")
    private Long callinActualTotalCount;

    @ApiModelProperty("批次列表")
    @JsonProperty("sku_batch")
    private List<SkuBatchRespVO> skuBatchRespVOS;
}