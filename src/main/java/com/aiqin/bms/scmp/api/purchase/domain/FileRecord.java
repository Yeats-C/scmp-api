package com.aiqin.bms.scmp.api.purchase.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
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

    @Size(max = 15,message = "文件名称长度小于15")
    @ApiModelProperty(value="文件名称")
    @JsonProperty("file_name")
    private String fileName;

    @ApiModelProperty(value="文件连接")
    @JsonProperty("file_path")
    private String filePath;

    @ApiModelProperty(value="0. 禁用  1.启用")
    @JsonProperty("file_status")
    private Integer fileStatus;

    @ApiModelProperty(value="上传时间")
    @JsonProperty("create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value="创建人")
    @JsonProperty("create_by_id")
    private String createById;

    @ApiModelProperty(value="创建人")
    @JsonProperty("create_by_name")
    private String createByName;

    @ApiModelProperty(value="修改时间")
    @JsonProperty("update_time")
    private Date updateTime;

    @ApiModelProperty(value="修改人")
    @JsonProperty("update_by_id")
    private String updateById;

    @ApiModelProperty(value="修改人")
    @JsonProperty("update_by_name")
    private String updateByName;
}