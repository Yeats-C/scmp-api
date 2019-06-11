package com.aiqin.bms.scmp.api.supplier.domain.response.skuconfig;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/18 0018 16:52
 */
@Data
@ApiModel("sku配置详情")
public class SkuConfigDetailRespVO {
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("物流中心名称")
    private String transportCenterName;

    @ApiModelProperty("物流中心编码")
    private String transportCenterCode;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty("进货状态")
    private String purchaseStatusName;

    @ApiModelProperty("销售状态")
    private String saleStatusName;

    @ApiModelProperty("进货状态值")
    private String purchaseStatus;

    @ApiModelProperty("销售状态值")
    private String saleStatus;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("商品编码")
    private String productCode;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("商品类别名称")
    private String productSortName;

    @ApiModelProperty("商品品类名称")
    private String productCategoryName;

    @ApiModelProperty("流程编码")
    private String formNo;
}
