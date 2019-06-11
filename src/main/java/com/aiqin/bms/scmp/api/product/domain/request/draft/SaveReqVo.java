package com.aiqin.bms.scmp.api.product.domain.request.draft;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * @author knight.xie
 * @version 1.0
 * @className SaveReqVo
 * @date 2019/5/16 14:28
 * @description TODO
 */
@ApiModel("商品申请单提交请求Vo")
@Data
public class SaveReqVo<T> {

    @ApiModelProperty("审批类型,1:商品,2:商品配置,3:销售区域")
    @NotEmpty(message = "直属上级名称不能为空！")
    private Integer approvalType;

    @ApiModelProperty("是否使用生效时间(0:是1:否)")
    private Byte selectionEffectiveTime;

    @ApiModelProperty("生效开始时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date selectionEffectiveStartTime;

    @ApiModelProperty("直属上级编码")
    @NotEmpty(message = "直属上级编码不能为空！")
    private String directSupervisorCode;

    @ApiModelProperty("直属上级名称")
    @NotEmpty(message = "直属上级名称不能为空！")
    private String directSupervisorName;

    @ApiModelProperty("提交的数据")
    @NotEmpty(message = "提交的数据不能为空！")
    private T data;

}
