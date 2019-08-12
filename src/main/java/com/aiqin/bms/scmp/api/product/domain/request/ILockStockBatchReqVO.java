package com.aiqin.bms.scmp.api.product.domain.request;

import com.aiqin.bms.scmp.api.common.OutboundTypeEnum;
import com.aiqin.bms.scmp.api.common.StockStatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className ILockStockReqVO
 * @date 2019/1/8 19:35
 * @description 批次库存锁定请求Vo
 */
@ApiModel("批次库存锁定请求Vo")
@Data
public class ILockStockBatchReqVO {

    @ApiModelProperty("来源oder编码")
    @JsonProperty(value = "source_oder_code")
    private String sourceOderCode;

    @ApiModelProperty("出库类型编码")
    @JsonProperty(value = "outbound_type_code")
    private OutboundTypeEnum outboundTypeCode;

    @ApiModelProperty("状态编码")
    @JsonProperty(value = "stock_status_code")
    private StockStatusEnum stockStatusCode;

    @ApiModelProperty("公司编码")
    @JsonProperty(value = "company_code")
    private String companyCode;

    @ApiModelProperty("公司名称")
    @JsonProperty(value = "company_name")
    private String companyName;

    @ApiModelProperty("供应商编码")
    @JsonProperty(value = "supply_code")
    private String supplyCode;

    @ApiModelProperty("供应商名称")
    @JsonProperty(value = "supply_name")
    private String supplyName;

    @ApiModelProperty("仓库code")
    @JsonProperty(value = "transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("仓库名称")
    @JsonProperty(value = "transport_center_name")
    private String transportCenterName;

    @ApiModelProperty("库房code")
    @JsonProperty(value = "warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    @JsonProperty(value = "warehouse_name")
    private String warehouseName;

    @ApiModelProperty("采购组code")
    @JsonProperty(value = "purchase_group_code")
    private String purchaseGroupCode;

    @ApiModelProperty("采购组名称")
    @JsonProperty(value = "purchase_group_name")
    private String purchaseGroupName;

    @ApiModelProperty("出库时间")
    @JsonProperty(value = "outbound_time")
    private Date outboundTime;

    @ApiModelProperty("预到达时间")
    @JsonProperty(value = "pre_arrival_time")
    private Date preArrivalTime;

    @ApiModelProperty("预出库数量")
    @JsonProperty(value = "pre_outbound_num")
    private Long preOutboundNum;

    @ApiModelProperty("税前金额")
    @JsonProperty(value = "pre_tax_amount")
    private Long preTaxAmount;

    @ApiModelProperty("操作类型")
    @JsonProperty(value = "operator")
    private String operator;

    @ApiModelProperty("前主单位数")
    @JsonProperty(value = "pre_main_unit_num")
    private Long preMainUnitNum;

    @ApiModelProperty("预数量")
    @JsonProperty(value = "pre_amount")
    private Long preAmount;

    @ApiModelProperty("锁定库存批次请求")
    @JsonProperty(value = "item_req_vos")
    private List<ILockStockBatchItemReqVo> itemReqVos;


}
