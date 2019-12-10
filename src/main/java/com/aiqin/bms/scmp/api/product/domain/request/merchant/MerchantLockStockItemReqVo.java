package com.aiqin.bms.scmp.api.product.domain.request.merchant;

import com.aiqin.bms.scmp.api.product.domain.response.stock.StockTransportResponse;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.LogisticsCenter;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className MerchantLockStockItemReqVo
 * @date 2019/1/16 22:30
 * @description 门店库存锁定Sku请求Vo
 */
@ApiModel("门店库存锁定Sku请求Vo")
@Data
public class MerchantLockStockItemReqVo implements Serializable {

    @ApiModelProperty("sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("数量")
    private Long num;

    @ApiModelProperty("商品类型，1-正常品，0-效期品")
    @JsonProperty("product_type")
    private Integer productType;

    @ApiModelProperty("商品行号")
    @JsonProperty("line_num")
    private String lineNum;

    @ApiModelProperty("仓库集合")
    @JsonProperty("transport_center_list")
    private List<StockTransportResponse> transportCenterList;

}
