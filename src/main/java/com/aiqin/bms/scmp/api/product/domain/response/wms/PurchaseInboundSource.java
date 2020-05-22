package com.aiqin.bms.scmp.api.product.domain.response.wms;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel(value = "采购入库推送源数据")
@Data
public class PurchaseInboundSource implements Serializable{

    @NotBlank(message = "入库单号不能为空")
    @ApiModelProperty(value = "入库单号")
    @JsonProperty("inbound_oder_code")
    private String inboundOderCode;

    @NotBlank(message = "库房编码不能为空")
    @ApiModelProperty(value = "库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty(value = "库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty(value = "合同号")
    @JsonProperty("contract_code")
    private String contractCode;

    @ApiModelProperty(value = "合同编号")
    @JsonProperty("contract_num")
    private String contractNum;

    @NotBlank(message = "供应商编码不能为空")
    @ApiModelProperty(value = "供应商编码")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value = "供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty(value = "采购单创建人编号")
    @JsonProperty("operuser_code")
    private String operuserCode;

    @ApiModelProperty(value = "采购单创建人姓名")
    @JsonProperty("operuser_name")
    private String operuserName;

    @NotNull(message = "出库单创建日期不能为空")
    @ApiModelProperty(value="出库单创建日期 (yyyy-MM-dd HH:mm:ss)", example = "2001-01-01 01:01:01")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("operuser_date")
    private Date operuserDate;

    @NotNull(message = "预计到货日期不能为空")
    @ApiModelProperty(value = "预计到货日期")
    @JsonProperty("pre_arrival_time")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date preArrivalTime;

    @ApiModelProperty(value = "备注")
    @JsonProperty("remark")
    private String remark;

    @ApiModelProperty(value = "采购单号")
    @JsonProperty("purchase_order_code")
    private String purchaseOrderCode;

    @Valid
    @ApiModelProperty(value = "采购入库推送目标数据明细")
    @JsonProperty("purchase_inbound_detail_source")
    @NotEmpty(message = "采购入库推送目标数据明细不能为空")
    private List<PurchaseInboundDetailSource> purchaseInboundDetailSource;

    @ApiModelProperty(value="批次信息明细")
    @JsonProperty("batch_info")
    private List<BatchInfo> batchInfo;
}
