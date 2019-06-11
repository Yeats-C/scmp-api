package com.aiqin.bms.scmp.api.supplier.domain.request.logisticscenter.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 描述:物流中心请求修改实体
 *
 * @Author: Kt.w
 * @Date: 2018/12/26
 * @Version 1.0
 * @since 1.0
 */
@Data
@ApiModel("物流中心请求修改实体")
public class UpdateLogisticsCenterReqVo {
    @ApiModelProperty("主键")
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty("物流中心编码")
    @NotEmpty(message = "物流中心编码不能为空")
    private String logisticsCenterCode;

    @ApiModelProperty("物流中心名称")
    @NotEmpty(message = "物流中心名称不能为空")
    private String logisticsCenterName;

    @ApiModelProperty("启用禁用状态")
    @NotNull(message = "启用禁用状态能为空")
    private Byte enable;

    @ApiModelProperty("服务区范围")
    @Valid
    private List<UpdateLogisticsCenterAreaReqVo> logisticsCenterAreaReqVoList;
}
