package com.aiqin.bms.scmp.api.product.domain.request.allocation;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author:ch
 * @Date:2020/5/6
 * @Content:
 */
@Data
@ApiModel(value = "移库推送Wms商品子表")
public class MovementWmsProductReqVo implements Serializable {

    @ApiModelProperty(value="行号")
    @JsonProperty("line_code")
    private String lineCode;

    @ApiModelProperty(value="SKU编号")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value="最小单位数量")
    @JsonProperty("actual_min_num")
    private String actualMinNum;

    @ApiModelProperty(value="移库备注")
    @JsonProperty("transfer_remark")
    private String transferRemark;

    /******德邦新增******/
    @ApiModelProperty(value="SKU名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty(value="SKU条码")
    @JsonProperty("sku_bar_code")
    private String skuBarCode;

    @ApiModelProperty(value="包装单位")
    @JsonProperty("package_name")
    private String packageName;
    /******德邦新增******/

}
