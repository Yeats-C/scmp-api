package com.aiqin.bms.scmp.api.supplier.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("操作日志")
@Data
public class SupplierOperationLog extends CommonBean {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("对象id")
    private String objectId;

    @ApiModelProperty("处理类型(0:新增,1:修改2:删除3：上传，4:下载)")
    private Byte handleType;

    @ApiModelProperty("对象类型(供应商，合同等)")
    private Byte objectType;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateBy;

    @ApiModelProperty("删除标记(0:正常 1:删除)")
    private Byte delFlag;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("处理描述")
    private String handleName;

    @ApiModelProperty("备注")
    private String remark;

}