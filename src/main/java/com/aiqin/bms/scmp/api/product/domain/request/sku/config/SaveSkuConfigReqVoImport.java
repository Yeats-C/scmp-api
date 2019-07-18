package com.aiqin.bms.scmp.api.product.domain.request.sku.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-07-18
 * @time: 14:30
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("商品配置导入返回vo")
public class SaveSkuConfigReqVoImport extends SaveSkuConfigReqVo {
    @ApiModelProperty("错误原因")
    private String error;
}
