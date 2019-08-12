package com.aiqin.bms.scmp.api.product.domain.request.salearea;

import com.aiqin.bms.scmp.api.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-10
 * @time: 14:00
 */
@Data
@ApiModel("销售区域查询请求vo")
public class QueryProductSaleAreaReqVO extends PageReq {

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("是否禁用(0禁用1启用)")
    private Integer beDisable;

    @ApiModelProperty("供货渠道类别编码")
    private String categoriesSupplyChannelsCode;

    @ApiModelProperty("直送供应商名称")
    private String directDeliverySupplierCode;

    @ApiModelProperty("公司编码")
    private String companyCode;
}
