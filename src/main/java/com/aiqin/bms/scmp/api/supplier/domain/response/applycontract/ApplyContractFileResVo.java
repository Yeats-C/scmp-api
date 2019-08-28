package com.aiqin.bms.scmp.api.supplier.domain.response.applycontract;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author knight.xie
 * @version 1.0
 * @className ContractFileResVo
 * @date 2019/4/23 19:44

 */
@ApiModel("合同文件返回信息")
@Data
public class ApplyContractFileResVo {

    @ApiModelProperty("id 编号")
    private Long id;

    @ApiModelProperty("申请合同编号")
    private String appContractCode;

    @ApiModelProperty("文件路径")
    private String filePath;

    @ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("文件编号")
    private String fileCode;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

}
