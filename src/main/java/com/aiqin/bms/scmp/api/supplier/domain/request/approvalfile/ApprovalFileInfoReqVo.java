package com.aiqin.bms.scmp.api.supplier.domain.request.approvalfile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className ApprovalFileInfoReqVo
 * @date 2019/10/18 15:27
 */
@ApiModel("审批附件请求信息")
@Data
public class ApprovalFileInfoReqVo {

    @ApiModelProperty("文件路径")
    private String filePath;

    @ApiModelProperty("文件编码")
    private String fileName;
}
