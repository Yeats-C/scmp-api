package com.aiqin.bms.scmp.api.supplier.domain.request.purchasegroup.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;


/**
 * 描述: 采购管理组人员添加请求实体
 *
 * @Author: Kt.w
 * @Date: 2018/12/25
 * @Version 1.0
 * @since 1.0
 */
@Data
@ApiModel("采购管理组人员添加请求实体")
public class PurchaseGroupBuyerReqVo {

    @ApiModelProperty("采购人员名称")
    @NotEmpty(message = "采购人员名称不能为空")
    private String buyerName;

    @ApiModelProperty("采购人员编码")
    @NotEmpty(message = "采购人员编码不能为空")
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


}
