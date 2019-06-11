package com.aiqin.bms.scmp.api.supplier.domain.response.logisticscenter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 描述:物流中心服务范围查看详情返回实体
 *
 * @Author: Kt.w
 * @Date: 2018/12/26
 * @Version 1.0
 * @since 1.0
 */
@Data
@ApiModel("物流中心服务范围查看详情返回实体")
public class LogisticsCenterAreaResVo {

    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("物流中心编码")
    private String logisticsCenterCode;

    @ApiModelProperty("省编码")
    private String provinceCode;

    @ApiModelProperty("省名称")
    private String provinceName;

    @ApiModelProperty("市编码")
    private String cityCode;

    @ApiModelProperty("市名称")
    private String cityName;

    @ApiModelProperty("启用禁用状态")
    private Byte enable;
}
