package com.aiqin.bms.scmp.api.supplier.domain.request.applycontract.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author knight.xie
 * @version 1.0
 * @className ApplyContractFileReqVo
 * @date 2019/4/23 17:24

 */

@Data
@ApiModel("新增申请合同文件信息")
public class ApplyContractFileReqVo {

    @ApiModelProperty("文件路径")
    @NotNull(message = "文件路径不能为空")
    private String filePath;

    @ApiModelProperty("文件名称")
    @NotNull(message = "文件名称不能为空")
    private String fileName;

    @ApiModelProperty("文件编号")
    @NotNull(message = "文件编号不能为空")
    private String fileCode;
}
