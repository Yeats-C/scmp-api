package com.aiqin.bms.scmp.api.product.domain.response.sku.store;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/3/2 0002 14:08
 */
@Data
@ApiModel("根据地址物流中心返回库房")
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
