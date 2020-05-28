package com.aiqin.bms.scmp.api.purchase.domain.pojo.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("订单商品批次表")
@Data
public class OrderInfoItemProductBatch {
    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("订单编码")
    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty("库位号")
    @JsonProperty("location_code")
    private Long locationCode;

    @ApiModelProperty("商品行号")
    @JsonProperty("line_code")
    private Long lineCode;

    @ApiModelProperty("原行号")
    @JsonProperty("original_line_code")
    private Long originalLineCode;

    @ApiModelProperty(value = "sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value = "sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("数量")
    @JsonProperty("total_count")
    private Long totalCount;

    @ApiModelProperty("实发数量")
    @JsonProperty("actual_total_count")
    private Long actualTotalCount;

    @ApiModelProperty("退货数量")
    @JsonProperty("return_total_count")
    private Long returnTotalCount;

    @ApiModelProperty("生产日期")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("product_date")
    private Date productDate;

    @ApiModelProperty("过期日期")
    @JsonProperty("be_overdue_date")
    private String beOverdueDate;

    @ApiModelProperty("批次备注")
    @JsonProperty("batch_remark")
    private String batchRemark;

    @ApiModelProperty("批次业务id")
    @JsonProperty("batch_id")
    private String batchId;

    @ApiModelProperty("批次号")
    @JsonProperty("batch_code")
    private String batchCode;

    @ApiModelProperty("批次编码")
    @JsonProperty("batch_info_code")
    private String batchInfoCode;

    @ApiModelProperty("锁定类型")
    @JsonProperty("lock_type")
    private Integer lockType;

    @ApiModelProperty("供应商code")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "create_time")
    private Date createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "update_time")
    private Date updateTime;

    @ApiModelProperty("创建人id")
    @JsonProperty(value = "create_by_id")
    private String createById;

    @ApiModelProperty("更新人id")
    @JsonProperty(value = "update_by_id")
    private String updateById;

    @ApiModelProperty("创建人名称")
    @JsonProperty(value = "create_by_name")
    private String createByName;

    @ApiModelProperty("更新人名称")
    @JsonProperty(value = "update_by_name")
    private String updateByName;

}