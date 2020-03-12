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


    @ApiModelProperty("允许区域（省）")
    private String allowAreaProvince;

    @ApiModelProperty("允许区域（市）")
    private String allowAreaCity;

    @ApiModelProperty("允许门店编号")
    private String allowStoreCode;
    @ApiModelProperty("允许门店名称")
    private String allowStoreName;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司编码")
    private String purchaseGroupCode;

    @ApiModelProperty("公司名称")
    private String purchaseGroupName;

    @ApiModelProperty("人员编码")
    private String personId;
}

