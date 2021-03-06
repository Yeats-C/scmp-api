package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import com.aiqin.bms.scmp.api.product.domain.request.sku.config.SpareWarehouseReqVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("商品配置临时表")
@Data
public class ProductSkuConfigDraft extends CommonBean {
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("是否显示此条记录(0:显示 1:不显示)")
    private Byte applyShow;

    @ApiModelProperty("申请类型")
    private Byte applyType;

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

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("备用仓库")
    private List<SpareWarehouseReqVo> spareWarehouses;
}