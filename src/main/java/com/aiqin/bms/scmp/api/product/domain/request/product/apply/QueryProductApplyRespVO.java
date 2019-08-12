package com.aiqin.bms.scmp.api.product.domain.request.product.apply;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-14
 * @time: 17:08
 */
@Data
@ApiModel("商品申请列表返回vo")
public class QueryProductApplyRespVO {

    @ApiModelProperty("申请编码")
    private String code;

    @ApiModelProperty("流程编号")
    private String formNo;

    @ApiModelProperty("审批类型")
    private Integer approvalType;

    @ApiModelProperty("申请类型")
    private Integer applyType;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("审核人")
    private String auditorBy;

    @ApiModelProperty("审核时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditorTime;

    @ApiModelProperty("申请状态")
    private Integer applyStatus;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("修改时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
