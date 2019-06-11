package com.aiqin.bms.scmp.api.supplier.domain.pojo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("文件信息")
@Data
public class ProductSkuFileDraft {
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("文件编码")
    private String fileCode;

    @ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("文件路径")
    private String filePath;

}