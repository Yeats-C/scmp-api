package com.aiqin.bms.scmp.api.product.domain.response.sku.config;

import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class SkuConfigsWmsRepsVo {


    @ApiModelProperty("物流中心(仓库)编码")
    @JsonProperty("transportCenter_code")
    private String transportCenterCode;

    @ApiModelProperty("物流中心(仓库)名称")
    @JsonProperty("transportCenter_name")
    private String transportCenterName;

    @ApiModelProperty("配置状态(0:再用 1:停止进货 2:停止配送 3:停止销售)")
    @JsonProperty("config_status")
    private Byte configStatus;


}
