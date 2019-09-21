package com.aiqin.bms.scmp.api.product.domain.response.sku.config;

import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className SkuConfigsRepsVo
 * @date 2019/5/25 10:18
 */
@ApiModel("SKU配置信息列表返回")
@Data
public class SkuConfigsRepsVo {

    @ApiModelProperty("主键ID")
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

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("备用仓库名称")
    private String spareWarehouse;

    @ApiModelProperty("备用仓库名称")
    private String spareWarehouse2;

    @ApiModelProperty("采购组编码")
    private String purchasingGroupCode;

    @ApiModelProperty("采购组名称")
    private String purchasingGroupName;

    @ApiModelProperty("备用仓库")
    private List<SpareWarehouseRepsVo> spareWarehouses;

    public String getSpareWarehouse() {
        List<String> stringList = new ArrayList<>();
        if(CollectionUtils.isNotEmptyCollection(this.spareWarehouses)) {
            this.spareWarehouses.stream().forEach(item->stringList.add(item.getTransportCenterName()));
        }
        this.spareWarehouse = String.join(",",stringList);
        return spareWarehouse;
    }
}
