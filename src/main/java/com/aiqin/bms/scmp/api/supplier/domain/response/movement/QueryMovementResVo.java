package com.aiqin.bms.scmp.api.supplier.domain.response.movement;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Classname: QueryMovementResVo
 * 描述:
 * @Author: Kt.w
 * @Date: 2019/4/1
 * @Version 1.0
 * @Since 1.0
 */
@Data
@ApiModel("调拨单列表展示返回实体")
public class QueryMovementResVo {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("移库编码")
    private String movementCode;

    @ApiModelProperty("所属仓库编码")
    private String logisticsCenterCode;

    @ApiModelProperty("所属仓库名称")
    private String logisticsCenterName;

    @ApiModelProperty("调出库房编码")
    private String calloutWarehouseCode;

    @ApiModelProperty("调出库房名称")
    private String calloutWarehouseName;

    @ApiModelProperty("调入库房编码")
    private String callinWarehouseCode;

    @ApiModelProperty("调入库房名称")
    private String callinWarehouseName;

    @ApiModelProperty("采购组编码")
    private String purchaseGroupCode;

    @ApiModelProperty("采购组名称")
    private String purchaseGroupName;

    @ApiModelProperty("负责人")
    private String principal;

    @ApiModelProperty("数量")
    private Long quantity;

    @ApiModelProperty("含税库存成本")
    private Long taxInventoryCost;

    @ApiModelProperty("出库单号")
    private String outboundOderCode;

    @ApiModelProperty("入库单号")
    private String inboundOderCode;

    @ApiModelProperty("移库状态编码")
    private Byte movementStatusCode;

    @ApiModelProperty("移库状态名称")
    private String movementStatusName;
}
