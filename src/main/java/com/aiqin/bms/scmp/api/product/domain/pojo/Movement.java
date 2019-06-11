package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("移库管理主表")
@Data
public class Movement extends CommonBean {
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

    @ApiModelProperty("删除标记，0未删除 1已删除")
    private Byte delFlag;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("审批流水编号")
    private String formNo;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;


}