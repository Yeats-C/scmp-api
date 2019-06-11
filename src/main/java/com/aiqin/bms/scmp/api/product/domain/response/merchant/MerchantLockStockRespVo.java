package com.aiqin.bms.scmp.api.product.domain.response.merchant;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author knight.xie
 * @version 1.0
 * @className MerchantLockStockRespVo
 * @date 2019/1/16 22:16
 * @description 门店库存锁定返回VO
 */
@ApiModel("门店库存锁定返回VO")
@Data
public class MerchantLockStockRespVo implements Serializable {

    @ApiModelProperty("物流中心编码")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("物流中心名称")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty("库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty("sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("库存数量")
    @JsonProperty("lock_num")
    private Long lockNum;

    @ApiModelProperty("商品类型，1-正常品，0-效期品")
    @JsonProperty("product_type")
    private Integer productType;

    @ApiModelProperty("商品行号")
    @JsonProperty("line_num")
    private String lineNum;
}
