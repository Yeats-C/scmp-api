package com.aiqin.bms.scmp.api.product.domain.request.merchant;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className MerchantLockStockReqVo
 * @date 2019/1/16 22:11
 * @description 门店库存锁定请求Vo
 */
@ApiModel("门店库存锁定请求Vo")
@Data
public class MerchantLockStockReqVo implements Serializable {

    @ApiModelProperty("省Id")
    @JsonProperty("province_id")
    private String provinceId;

    @ApiModelProperty("市Id")
    @JsonProperty("city_id")
    private String cityId;

    @ApiModelProperty("公司编码")
    @JsonProperty("company_code")
    private String companyCode;

    @ApiModelProperty("订单编号")
    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty("skuList")
    @JsonProperty("sku_list")
    private List<MerchantLockStockItemReqVo> skuList;
}
