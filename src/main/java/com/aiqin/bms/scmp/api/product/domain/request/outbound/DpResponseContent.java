package com.aiqin.bms.scmp.api.product.domain.request.outbound;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author:ch
 * @Date:2020/7/15
 * @Content:
 */
@Data
@ApiModel(value = "波次(wms回传 发运波次  保存发运单)")
public class DpResponseContent {

    @ApiModelProperty("德邦业务编号")
    @JsonProperty("bill_type")
    private String billType;
    //仓库编号
    @ApiModelProperty("库房编号")
    @JsonProperty("warehouse_code")
    private String warehouseCode;
    //波次
    @ApiModelProperty("波次号")
    @JsonProperty("picking_code")
    private String pickingCode;
    //打包数量
    @ApiModelProperty("打包数量")
    @JsonProperty("package_amount")
    private Integer packageAmount;
    //出库单标识 渠道号
    @ApiModelProperty("耘链订单号 组合的,隔开的")
    @JsonProperty("order_ids")
    private String orderIds;


    //出库单标识 渠道号 耘链
    @ApiModelProperty("耘链订单号")
    @JsonProperty("order_ids_list")
    private List<String> orderIdsList;
}
