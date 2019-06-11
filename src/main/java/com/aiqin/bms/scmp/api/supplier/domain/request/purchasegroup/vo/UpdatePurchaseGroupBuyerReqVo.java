package com.aiqin.bms.scmp.api.supplier.domain.request.purchasegroup.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 描述:采购组专员更新请求实体
 *
 * @Author: Kt.w
 * @Date: 2018/12/25
 * @Version 1.0
 * @since 1.0
 */
@Data
@ApiModel("采购组专员更新请求实体")
public class UpdatePurchaseGroupBuyerReqVo {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("采购组编码")
    private String purchaseGroupCode;

    @ApiModelProperty("采购人员名称")
    private String buyerName;

    @ApiModelProperty("采购人员编码")
    private String buyerCode;

    @ApiModelProperty("采购专员性别")
    private Byte buyerGender;

    @ApiModelProperty("职位编码")
    private String positionCode;

    @ApiModelProperty("职位名称")
    private String positionName;

    @ApiModelProperty("职位级别编码")
    private String positionLevelCode;

    @ApiModelProperty("职位级别名称")
    private String positionLevelName;

    @ApiModelProperty("禁用启用状态，0是禁用，1是未禁用")
    private Byte enable;
}
