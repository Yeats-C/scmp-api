package com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/3/1 0001 10:46
 */
@Data
@ApiModel("供应商文件下载记录请求")
public class DownSupplierFileReq {
    @ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("供应商文件路径")
    private String filePath;

    @ApiModelProperty("供应商code")
    private String supplierCode;
}
