package com.aiqin.bms.scmp.api.supplier.domain.response.warehouse;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Classname: WarehouseApiResVo
 * 描述:根据地址物流中心返回库房
 * @Author: Kt.w
 * @Date: 2019/3/1
 * @Version 1.0
 * @Since 1.0
 */
@ApiModel("根据地址物流中心返回库房")
@Data
public class WarehouseApiResVo {
    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty("仓库类型编码")
    @JsonProperty("warehouse_type_code")
    private Byte warehouseTypeCode;

}
