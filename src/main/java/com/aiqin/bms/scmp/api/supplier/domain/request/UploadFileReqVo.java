package com.aiqin.bms.scmp.api.supplier.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className UploadFileReqVo
 * @date 2019/10/15 14:39
 */
@ApiModel("文件上传请求VO")
@Data
public class UploadFileReqVo {

//    @ApiModelProperty("编码")
//    private String code;

    @ApiModelProperty("类型")
    private String type;
}
