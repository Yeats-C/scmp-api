package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("监管仓订单")
@Data
public class SupervisoryWarehouseOrderProduct extends CommonBean {

    @ApiModelProperty("主键Id")
    private Long id;

    @ApiModelProperty("订单编号")
    private String orderCode;

    @ApiModelProperty("订单类型(1:入库 2:出库)")
    private Byte orderType;

    @ApiModelProperty("订单类型名称")
    private String orderTypeName;

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("数量")
    private Integer num;

    @ApiModelProperty("基商品含量")
    private Integer baseProductContent;

    @ApiModelProperty("单品数量")
    private Integer singleCount;

    @ApiModelProperty("生产日期")
    private String productDate;

    @ApiModelProperty("批次号")
    private String skuBatchNumber;

    @ApiModelProperty("税率")
    private Integer taxRate;

    @ApiModelProperty("含税单价")
    private Integer productAmount;

    @ApiModelProperty("含税总价")
    private Integer productTotalAmount;

    @ApiModelProperty("品牌id")
    private String brandId;

    @ApiModelProperty("品牌名称")
    private String brandName;

    @ApiModelProperty("品类id")
    private String categoryId;

    @ApiModelProperty("品类名称")
    private String categoryName;

    @ApiModelProperty("规格")
    private String productSpec;

    @ApiModelProperty("颜色")
    private String colorName;

    @ApiModelProperty("型号")
    private String modelNumber;

    @ApiModelProperty("单位编码")
    private String unitCode;

    @ApiModelProperty("单位名称")
    private String unitName;

    @ApiModelProperty("行号")
    private Integer lineNum;

    @ApiModelProperty("毛重")
    private Long boxGrossWeight;

    @ApiModelProperty("净重")
    private Long netWeight;


}