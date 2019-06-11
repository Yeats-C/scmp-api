package com.aiqin.bms.scmp.api.supplier.domain.response.logisticscenter;

import com.aiqin.bms.scmp.api.supplier.domain.response.warehouse.WarehouseApiResVo;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Classname: LogisticsCenterApiResVo
 * 描述:
 * @Author: Kt.w
 * @Date: 2019/3/2
 * @Version 1.0
 * @Since 1.0
 */
@Data
@ApiModel("物流中心外部接口")
public class LogisticsCenterApiResVo {
    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("物流中心编码")
    @JsonProperty("logistics_center_code")
    private String logisticsCenterCode;

    @ApiModelProperty("物流中心名称")
    @JsonProperty("logistics_center_name")
    private String logisticsCenterName;

    @ApiModelProperty("库房列表")
    private List<WarehouseApiResVo>list;
}
