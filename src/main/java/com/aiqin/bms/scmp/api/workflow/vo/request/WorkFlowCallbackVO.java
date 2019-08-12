package com.aiqin.bms.scmp.api.workflow.vo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Description:
 *
 * @author: zth
 * @date: 2019-01-15
 * @time: 16:38
 */
@Data
@ApiModel("审核回调请求vo")
public class WorkFlowCallbackVO {

    @ApiModelProperty("表单编码（申请编号")
    private String formNo;

    @ApiModelProperty("审核人")
    private String auditorBy;

    @ApiModelProperty("审核时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditorTime;

    @ApiModelProperty("状态")
    private Byte applyStatus;

    @ApiModelProperty("审核状态")
    private Integer updateFormStatus;

    @ApiModelProperty("按钮选项")
    private String optBtn;

    @ApiModelProperty("备注")
    private String remake;
    /**审批人职位*/
    private String approvalPositionCode;
    /**审批人编号*/
    private String approvalUserCode;
    /**审批人姓名*/
    private String approvalUserName;
    /**审批意见*/
    private String approvalOpinion;
}
