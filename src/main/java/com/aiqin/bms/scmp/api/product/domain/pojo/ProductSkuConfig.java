package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("商品配置表")
@Data
public class ProductSkuConfig extends CommonBean {

    @ApiModelProperty("主键Id")
    private Long id;

    @ApiModelProperty("商品配置编号")
    private String configCode;

    @ApiModelProperty("商品名称")
    private String productCode;

    @ApiModelProperty("商品编码")
    private String productName;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("物流中心(仓库)编码")
    private String transportCenterCode;

    @ApiModelProperty("物流中心(仓库)名称")
    private String transportCenterName;

    @ApiModelProperty("配置状态(0:再用 1:停止进货 2:停止配送 3:停止销售)")
    private Byte configStatus;

    @ApiModelProperty("到货周期")
    private Integer arrivalCycle;

    @ApiModelProperty("订货周期")
    private Integer orderCycle;

    @ApiModelProperty("基本库存天数")
    private Integer basicInventoryDay;

    @ApiModelProperty("大库存预警天数")
    private Integer largeInventoryWarnDay;

    @ApiModelProperty("大效期预警天数")
    private Integer bigEffectPeriodWarnDay;

    @ApiModelProperty("到货后周转期")
    private Integer turnoverPeriodAfterArrival;

    @ApiModelProperty("申请编号")
    private String applyCode;

    @ApiModelProperty("申请状态(0:待审 1:审核中 2:审核通过 3:审核未通过 4:已撤销)")
    private Byte applyStatus;

    @ApiModelProperty("审核人")
    private String auditorBy;

    @ApiModelProperty("审核时间")
    private Date auditorTime;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

}