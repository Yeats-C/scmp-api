package com.aiqin.bms.scmp.api.supplier.domain.response.logisticscenter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 描述:物流中心查看返回实体
 *
 * @Author: Kt.w
 * @Date: 2018/12/26
 * @Version 1.0
 * @since 1.0
 */
@Data
@ApiModel("物流中心查看返回实体")
public class LogisticsCenterResVo {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("物流中心编码")
    private String logisticsCenterCode;

    @ApiModelProperty("物流中心名称")
    private String logisticsCenterName;

    @ApiModelProperty("启用禁用状态")
    private Byte enable;

    @ApiModelProperty("物流中心服务范围")
    List<LogisticsCenterAreaResVo> logisticsCenterAreaResVos;
}
