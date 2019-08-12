package com.aiqin.bms.scmp.api.bireport.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@ApiModel("供应商退货(退供)respVo")
@Data
public class SupplierReturnRespVo {

    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("供应商编号")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty("仓库编码")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("仓库名称")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty("库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("库房编码名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty("退货单号")
    @JsonProperty("reject_record_code")
    private String rejectRecordCode;

    @ApiModelProperty("退货数量")
    @JsonProperty("sum_count")
    private Long sumCount;

    @ApiModelProperty("退货金额")
    @JsonProperty("sum_amount")
    private Long sumAmount;

    @ApiModelProperty("退货单日期")
    @JsonProperty("return_goods_create_time")
    private String returnGoodsCreateTime;

    @ApiModelProperty("实际发货日期")
    @JsonProperty("out_stock_time")
    private String outStockTime;

    @ApiModelProperty("商品退货状态 0 待审核 1 审核中  2 待供应商确认 3 待出库  4 出库开始 5 已出库 6 已发运 7 完成 8 取消 9 审核不通过")
    @JsonProperty("reject_status")
    private Integer rejectStatus;

    @ApiModelProperty("采购组编码")
    @JsonProperty("purchase_group_code")
    private String purchaseGroupCode;

    @ApiModelProperty("采购组")
    @JsonProperty("purchase_group_name")
    private String purchaseGroupName;

    @ApiModelProperty("采购组负责人编号")
    @JsonProperty("responsible_person_code")
    private String responsiblePersonCode;

    @ApiModelProperty("采购组负责人")
    @JsonProperty("responsible_person_name")
    private String responsiblePersonName;

    @ApiModelProperty("计算时间")
    @JsonProperty("create_time")
    private String createTime;

    @ApiModelProperty("退货数量合计")
    @JsonProperty("sum_counts")
    private Long sumCounts;

    @ApiModelProperty("退货金额合计")
    @JsonProperty("sum_amounts")
    private Long sumAmounts;

    @ApiModelProperty("返回列名")
    @JsonProperty("column_list")
    private List<Map> columnList;
}
