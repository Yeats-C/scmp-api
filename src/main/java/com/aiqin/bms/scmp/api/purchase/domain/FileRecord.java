package com.aiqin.bms.scmp.api.purchase.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel
@Data
public class FileRecord {
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="关联文件id")
    @JsonProperty("file_id")
    private String fileId;

    @ApiModelProperty(value="文件编号")
    @JsonProperty("file_code")
    private String fileCode;

    @ApiModelProperty(value="文件名称")
    @JsonProperty("file_name")
    private String fileName;

    @ApiModelProperty(value="0. 禁用  1.启用")
    @JsonProperty("file_status")
    private Integer fileStatus;

    @ApiModelProperty(value="上传时间")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(value="创建人")
    @JsonProperty("create_by")
    private String createBy;

    @ApiModelProperty(value="修改时间")
    @JsonProperty("update_time")
    private Date updateTime;

    @ApiModelProperty(value="修改人")
    @JsonProperty("update_by")
    private String updateBy;
}