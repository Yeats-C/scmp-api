package com.aiqin.bms.scmp.api.supplier.domain.request.applycontract.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author knight.xie
 * @version 1.0
 * @className UpdateApplyContractFileReqVo
 * @date 2019/4/23 17:55

 */


@Data
@ApiModel("修改申请合同文件信息")
public class UpdateApplyContractFileReqVo {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("文件路径")
    @NotNull(message = "文件路径不能为空")
    private String filePath;

    @ApiModelProperty("文件名称")
    @NotNull(message = "文件名称不能为空")
    private String fileName;

    @ApiModelProperty("文件编码")
    @NotNull(message = "文件编码不能为空")
    private String fileCode;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;
}
