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


    @ApiModelProperty("销售区域编码")
    private String code;

    @ApiModelProperty("销售区域名称")
    private String name;

    @ApiModelProperty("是否禁用(0禁用1启用)")
    private Integer beDisable;

    @ApiModelProperty("允许区域名称")
    private String allowAreaName;

    @ApiModelProperty("允许门店名称")
    private String allowStoreName;


    private String personId;

    @ApiModelProperty("公司编码")
    private String companyCode;
}
