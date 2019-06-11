package com.aiqin.bms.scmp.api.supplier.domain.response.product.apply;

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

    @ApiModelProperty("审批类型")
    private Integer approvalType;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("审核人")
    private String auditorBy;

    @ApiModelProperty("审核时间")
    private Date auditorTime;

    @ApiModelProperty("申请状态")
    private Integer applyStatus;
}
