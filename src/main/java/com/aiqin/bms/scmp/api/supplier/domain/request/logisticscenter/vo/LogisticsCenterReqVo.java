package com.aiqin.bms.scmp.api.supplier.domain.request.logisticscenter.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 描述: 物流中心请求保存实体
 *
 * @Author: Kt.w
 * @Date: 2018/12/25
 * @Version 1.0
 * @since 1.0
 */
@Data
@ApiModel("物流中心请求保存实体")
public class LogisticsCenterReqVo {

    @ApiModelProperty("物流中心名称")
    @NotEmpty(message = "物流中心名称不能为空")
    private String logisticsCenterName;

    @ApiModelProperty("服务范围")
    @Valid
    private List<LogisticsCenterAreaReqVo> logisticsCenterAreaReqVos;
}
