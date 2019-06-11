package com.aiqin.bms.scmp.api.supplier.domain.response.logisticscenter;

import com.aiqin.bms.scmp.api.supplier.domain.response.warehouse.WarehouseByLogisticsCenterCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 描述:
 *
 * @Author: Kt.w
 * @Date: 2019/1/17
 * @Version 1.0
 * @since 1.0
 */
@Data
@ApiModel("通过区域范围查询物流中心跟库房返回实体")
public class LogisticsCenterListByArea {

    @ApiModelProperty("物流中心编码")
    private String logisticsCenterCode;

    @ApiModelProperty("物流中心名称")
    private String logisticsCenterName;

    @ApiModelProperty("物流中心对应库房")
    List<WarehouseByLogisticsCenterCode> warehouseList ;
}
