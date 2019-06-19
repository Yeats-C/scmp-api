package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("调拨商品表")
@Data
public class AllocationProduct extends CommonBean {
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

    @ApiModelProperty("税率")
    private Long tax;

    @ApiModelProperty("含税成本")
    private Long taxPrice;

    @ApiModelProperty("数量")
    private Long quantity;

    @ApiModelProperty("含税总成本")
    private Long taxAmount;

    @ApiModelProperty("图片地址")
    private String pictureUrl;

    @ApiModelProperty("行号")
    private Long lineNum;

}