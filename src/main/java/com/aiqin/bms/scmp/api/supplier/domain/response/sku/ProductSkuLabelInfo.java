package com.aiqin.bms.scmp.api.supplier.domain.response.sku;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/7 0007 15:12
 */
@Data
@ApiModel("商品sku标签信息")
public class ProductSkuLabelInfo {
    @JsonProperty("product_label_code")
    @ApiModelProperty("商品标签code")
    private String productLabelCode;

    @JsonProperty("product_label_name")
    @ApiModelProperty("商品标签名称")
    private String productLabelName;
}
