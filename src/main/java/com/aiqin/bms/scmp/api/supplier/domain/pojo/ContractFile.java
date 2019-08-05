package com.aiqin.bms.scmp.api.supplier.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel("合同文件")
@Data
public class ContractFile extends CommonBean {
    @ApiModelProperty("主键Id")
    private Long id;

    @ApiModelProperty("合同编号")
    private String contractCode;

    @ApiModelProperty("文件路径")
    private String filePath;

    @ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("文件编号")
    private String fileCode;

}