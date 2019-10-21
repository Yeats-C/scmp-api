package com.aiqin.bms.scmp.api.supplier.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className ApprovalFileInfo
 * @date 2019/10/18 17:06
 */
@ApiModel("审批附件")
@Data
public class ApprovalFileInfo  extends CommonBean {

    @ApiModelProperty("审批类型")
    private Byte approvalType;

    @ApiModelProperty("申请编号")
    private String applyCode;

    @ApiModelProperty("文件路径")
    private String filePath;

    @ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("表单编号")
    private String formNo;

}
