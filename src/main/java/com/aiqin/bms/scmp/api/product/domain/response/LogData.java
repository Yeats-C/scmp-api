package com.aiqin.bms.scmp.api.product.domain.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("返回日志")
@Data
public class LogData {
    @ApiModelProperty("对象id")
    private String objectId;

    @ApiModelProperty("处理类型(0:新增,1:修改2:删除3：上传，4:下载)")
    private Byte handleType;

    @ApiModelProperty("内容")
    private String handleName;

    @ApiModelProperty("对象类型(供应商，合同等)")
    private Byte objectType;

    @ApiModelProperty("json数据")
    private String content;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("状态")
    private String status;
}
