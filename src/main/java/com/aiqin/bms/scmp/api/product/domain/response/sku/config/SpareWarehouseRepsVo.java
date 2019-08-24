package com.aiqin.bms.scmp.api.product.domain.response.sku.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className SpareWarehouseReqVo
 * @date 2019/6/4 16:40
 */
@Data
@ApiModel("备用仓库返回Vo")
public class SpareWarehouseRepsVo {

    @ApiModelProperty("商品配置编码")
    private String configCode;

    @ApiModelProperty("物流中心(仓库)编码")
    private String transportCenterCode;

    @ApiModelProperty("物流中心(仓库)名称")
    private String transportCenterName;

    @ApiModelProperty("使用顺序")
    private Integer useOrder;
}
