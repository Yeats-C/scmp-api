package com.aiqin.bms.scmp.api.product.domain.response.movement;

import com.aiqin.bms.scmp.api.product.domain.response.allocation.AllocationProductBatchResVo;
import com.aiqin.bms.scmp.api.product.domain.response.allocation.AllocationProductResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.LogData;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Classname: MovementResVo
 * 描述:
 * @Author: Kt.w
 * @Date: 2019/4/1
 * @Version 1.0
 * @Since 1.0
 */
@Data
@ApiModel("查看详情返回实体")
public class MovementResVo {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("移库编码")
    private String movementCode;

    @ApiModelProperty("所属仓库编码")
    private String logisticsCenterCode;

    @ApiModelProperty("所属仓库名称")
    private String logisticsCenterName;

    @ApiModelProperty("调出库房编码")
    private String callOutWarehouseCode;

    @ApiModelProperty("调出库房名称")
    private String callOutWarehouseName;

    @ApiModelProperty("调入库房编码")
    private String callInWarehouseCode;

    @ApiModelProperty("调入库房名称")
    private String callInWarehouseName;

 //   @ApiModelProperty("采购组编码")
 //   private String purchaseGroupCode;

 //   @ApiModelProperty("采购组名称")
 //   private String purchaseGroupName;

    @ApiModelProperty("负责人")
    private String principal;

    @ApiModelProperty("数量")
    private Long quantity;

    @ApiModelProperty("出库数量")
    private Long callOutQuantity;

    @ApiModelProperty("入库数量")
    private Long  callInQuantity;

    @ApiModelProperty("含税总价")
    private BigDecimal taxAmount;

    @ApiModelProperty("出库含税总成本")
    private BigDecimal callOutTaxAmount;

    @ApiModelProperty("入库含税总成本")
    private BigDecimal callInTaxAmount;

    @ApiModelProperty("出库单号")
    private String outboundOderCode;

    @ApiModelProperty("出库单状态")
    private String outboundOderStatus;

    @ApiModelProperty("入库单号")
    private String inboundOderCode;

    @ApiModelProperty("入库单状态")
    private String inboundOderStatus;

    @ApiModelProperty("移库状态编码")
    private Byte movementStatusCode;

    @ApiModelProperty("移库状态名称")
    private String movementStatusName;

    @ApiModelProperty("删除标记，0未删除 1已删除")
    private Byte delFlag;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("修改时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("审批流水编号")
    private String formNo;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("承担单位编码")
    private String undertakingUnitCode;

    @ApiModelProperty("承担单位名称")
    private String undertakingUnitName;

    @ApiModelProperty(value = "移库模式（1，我方发起 2，wms方发起）")
    private Integer movementPatternType;

    @ApiModelProperty(value = "移库模式")
    private String movementPatternName;

    @ApiModelProperty("sku")
    private List<AllocationProductResVo> skuList;

    @ApiModelProperty("batchSku")
    private List<AllocationProductBatchResVo> batchSkuList;

    @ApiModelProperty("操作日志列表")
    private List<LogData> logDataList;



}
