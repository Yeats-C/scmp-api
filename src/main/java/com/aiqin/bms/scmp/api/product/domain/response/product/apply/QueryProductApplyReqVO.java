package com.aiqin.bms.scmp.api.product.domain.response.product.apply;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-14
 * @time: 17:09
 */
@Data
@ApiModel("商品申请请求vo")
public class QueryProductApplyReqVO extends PageReq {
    @ApiModelProperty("创建时间从")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeStart;

    @ApiModelProperty("创建时间到")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeEnd;

    @ApiModelProperty("审批类型 1:商品 2.配置 3.区域")
    @NotEmpty(message = "审批类型不能为空")
    private Integer approvalType;

    @ApiModelProperty("申请状态")
    private Integer applyStatus;

    @ApiModelProperty("申请编码")
    private String code;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("申请单号")
    private String formNo;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("备注")
    private String approvalRemark;

    @ApiModelProperty("审批名称")
    private String approvalName;
}
