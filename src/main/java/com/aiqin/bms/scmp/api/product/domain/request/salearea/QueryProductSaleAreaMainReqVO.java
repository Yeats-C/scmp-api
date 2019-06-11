package com.aiqin.bms.scmp.api.product.domain.request.salearea;

import com.aiqin.bms.scmp.api.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-05
 * @time: 16:06
 */
@Data
@ApiModel("区域列表查询vo")
public class QueryProductSaleAreaMainReqVO extends PageReq {
    @ApiModelProperty("限制区域名称")
    private String code;

    @ApiModelProperty("限制区域名称")
    private String name;

    @ApiModelProperty("是否禁用(0禁用1启用)")
    private Integer beDisable;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("允许区域")
    private String allowArea;

    @ApiModelProperty("禁止区域")
    private String forbiddenArea;

    @ApiModelProperty("允许门店")
    private String allowStore;

    @ApiModelProperty("禁止门店")
    private String forbiddenStore;

    @ApiModelProperty("公司编码")
    private String companyCode;
}

