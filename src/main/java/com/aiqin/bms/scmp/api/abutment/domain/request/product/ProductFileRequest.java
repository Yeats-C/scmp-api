package com.aiqin.bms.scmp.api.abutment.domain.request.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("DL- 商品推送文件信息")
public class ProductFileRequest {

    @ApiModelProperty(value="文件编号")
    @JsonProperty("file_code")
    private String file_code;

    @ApiModelProperty(value="文件名称")
    @JsonProperty("file_name")
    private String fileName;

    @ApiModelProperty(value="文件路径")
    @JsonProperty("file_path")
    private String filePath;

}
