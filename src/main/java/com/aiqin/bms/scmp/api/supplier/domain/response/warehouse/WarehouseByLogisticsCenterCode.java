package com.aiqin.bms.scmp.api.supplier.domain.response.warehouse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 描述:返回实体只含有库房名称跟编码
 *
 * @Author: Kt.w
 * @Date: 2019/1/17
 * @Version 1.0
 * @since 1.0
 */
@Data
@ApiModel("返回实体只含有库房名称跟编码")
public class WarehouseByLogisticsCenterCode {

    @ApiModelProperty("库房编码")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    private String warehouseName;
}
