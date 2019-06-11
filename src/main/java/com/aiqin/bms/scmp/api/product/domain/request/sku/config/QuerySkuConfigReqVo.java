package com.aiqin.bms.scmp.api.product.domain.request.sku.config;

import com.aiqin.bms.scmp.api.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className QuerySkuConfigReqVo
 * @date 2019/5/25 18:16
 * @description TODO
 */
@ApiModel("查询SKU配置信息Vo")
@Data
public class QuerySkuConfigReqVo extends PageReq {

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

    @ApiModelProperty(value = "公司编码",hidden = true)
    private String companyCode;

}
