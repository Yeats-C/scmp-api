package com.aiqin.bms.scmp.api.product.domain.request.sku.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className UpdateSkuConfigSupplierReqVo
 * @date 2019/7/4 14:27
 * @description TODO
 */
@ApiModel("修改配置信息和供应商信息请求Vo")
@Data
public class UpdateSkuConfigSupplierReqVo {

    @ApiModelProperty("商品名称")
    private String productCode;

    @ApiModelProperty("商品编码")
    private String productName;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty(value = "sku配置管理",name = "productSkuConfigs")
    List<UpdateSkuConfigReqVo> configs;

    @ApiModelProperty(value = "sku供应商信息")
    @JsonProperty("productSkuSupplyUnits")
    private List<UpdateProductSkuSupplyUnitReqVo> updateProductSkuSupplyUnitReqVos;
}
