package com.aiqin.bms.scmp.api.product.domain.response.wms;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "批次信息")
@Data
public class BatchInfo{

    @ApiModelProperty(value = "业务id")
    @JsonProperty("id")
    private String id;

    @ApiModelProperty(value = "业务号")
    //@JsonProperty("batch_id")
    private String batchId;

    @ApiModelProperty(value = "批次号")
    //@JsonProperty("batch_code")
    private String batchCode;

    @ApiModelProperty(value = "批次编号")
    //@JsonProperty("batch_info_code")
    private String batchInfoCode;

    @ApiModelProperty(value = "wms批次编码")
    //@JsonProperty("wms_batch_code")
    private String wmsBatchCode;

    @ApiModelProperty(value = "sku编码")
    //@JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value = "sku名称")
    //@JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty(value = "生产日期")
    //@JsonProperty("product_date")
    private String productDate;

    @ApiModelProperty(value = "批次备注")
    //@JsonProperty("batch_remark")
    private String batchRemark;

    @ApiModelProperty(value = "供应商编码")
    //@JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value = "供应商名称")
    //@JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty(value = "最小单位数量")
   // @JsonProperty("total_count")
    private Long totalCount;

    @ApiModelProperty(value = "实际最小单位数量")
    //@JsonProperty("actual_total_count")
    private Long actualTotalCount;

    @ApiModelProperty(value = "库位号")
    //@JsonProperty("location_code")
    private String locationCode;

    @ApiModelProperty(value = "行号")
    //@JsonProperty("line_code")
    private Integer lineCode;

    @ApiModelProperty(value = "过期日期")
    //@JsonProperty("be_overdue_date")
    private String beOverdueDate;

    @ApiModelProperty(value = "创建时间(yyyy-MM-dd HH:mm:ss)", example = "2001-01-01 01:01:01")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    //@JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(value = "修改时间(yyyy-MM-dd HH:mm:ss)", example = "2001-01-01 01:01:01")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
   // @JsonProperty("update_time")
    private Date updateTime;

    @ApiModelProperty(value = "创建人id")
   // @JsonProperty("create_by_id")
    private String createById;

    @ApiModelProperty(value = "修改人id")
    //@JsonProperty("update_by_id")
    private String updateById;

    @ApiModelProperty(value = "创建人名称")
    //@JsonProperty("create_by_name")
    private String createByName;

    @ApiModelProperty(value = "修改人名称")
    //@JsonProperty("update_by_name")
    private String updateByName;
}
