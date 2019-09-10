package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("sku渠道管理临时信息")
@Data
public class ProductSkuChannelDraft extends CommonBean {
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("价格渠道编码")
    private String priceChannelCode;

    @ApiModelProperty("价格渠道名称")
    private String priceChannelName;

}