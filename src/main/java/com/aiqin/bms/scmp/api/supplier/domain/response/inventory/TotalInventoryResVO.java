package com.aiqin.bms.scmp.api.supplier.domain.response.inventory;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("总库存返回实体")
@Data
public class TotalInventoryResVO {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("sku号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("品类编码")
    private String productCategoryId;

    @ApiModelProperty("品类名称")
    private String productCategoryName;

    @ApiModelProperty("规格编码")
    private String specCode;

    @ApiModelProperty("规格名称")
    private String specName;

    @ApiModelProperty("单位编码")
    private String unitCode;

    @ApiModelProperty("单位名称")
    private String unitName;

    @ApiModelProperty("包装")
    private String pack;

    @ApiModelProperty("进货状态")
    private Byte purchaseStatus;

    @ApiModelProperty("销售状态")
    private Byte saleStatus;

    @ApiModelProperty("正品数量")
    private Long authenticNum;

    @ApiModelProperty("可用库存数")
    private Long availableNum;

    @ApiModelProperty("锁定库存数")
    private Long lockNum;

    @ApiModelProperty("备品库存数")
    private Long spareNum;

    @ApiModelProperty("库存数")
    private Long inventoryNum;

    @ApiModelProperty("在途数")
    private Long wayNum;

    @ApiModelProperty("采购在途数")
    private Long purchaseWayNum;

    @ApiModelProperty("库存含税金额")
    private Long taxAmount;

    @ApiModelProperty("最新供货单位")
    private String newDelivery;

    @ApiModelProperty("最新采购价")
    private Long newPurchasePrice;

}
