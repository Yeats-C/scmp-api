package com.aiqin.bms.scmp.api.supplier.domain.request;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class OperationLogBean extends CommonBean {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("对象id")
    private String objectId;

    @ApiModelProperty("处理类型(0:新增,1:修改2:删除3：上传，4:下载)")
    private Byte handleType;

    @ApiModelProperty("处理描述")
    private String handleName;

    @ApiModelProperty("对象类型(供应商，合同等)")
    private Byte objectType;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("备注")
    private String remark;

    public OperationLogBean(String objectId, Byte handleType, Byte objectType, String content,String handleName) {
        this.objectId = objectId;
        this.handleType = handleType;
        this.objectType = objectType;
        this.content = content;
        this.handleName = handleName;
    }

    public OperationLogBean() {
    }
}

