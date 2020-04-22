package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel
@Data
public class InboundBatch {
    @ApiModelProperty(value="")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="入库单号")
    @JsonProperty("inbound_oder_code")
    private String inboundOderCode;

    @ApiModelProperty(value="批次号/wms批次号")
    @JsonProperty("batch_code")
    private String batchCode;

    @ApiModelProperty(value="批次编号")
    @JsonProperty("batch_info_code")
    private String batchInfoCode;

    @ApiModelProperty(value="sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value="SKU名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty(value="供应商编码")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value="供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty(value="批次备注")
    @JsonProperty("batch_remark")
    private String batchRemark;

    @ApiModelProperty(value="生产日期")
    @JsonProperty("product_time")
    private String productTime;

    @ApiModelProperty(value="过期日期")
    @JsonProperty("be_overdue_data")
    private String beOverdueData;

    @ApiModelProperty(value="最小单位数量")
    @JsonProperty("total_count")
    private Long totalCount;

    @ApiModelProperty(value="实际最小单位数量")
    @JsonProperty("actual_total_count")
    private Long actualTotalCount;

    @ApiModelProperty(value="库位号")
    @JsonProperty("location_code")
    private String locationCode;

    @ApiModelProperty(value="行号")
    @JsonProperty("line_code")
    private Integer lineCode;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(value="修改时间")
    @JsonProperty("update_time")
    private Date updateTime;

    @ApiModelProperty(value="创建人编码")
    @JsonProperty("create_by_id")
    private String createById;

    @ApiModelProperty(value="创建人编码")
    @JsonProperty("update_by_id")
    private String updateById;

    @ApiModelProperty(value="修改人名称")
    @JsonProperty("create_by_name")
    private String createByName;

    @ApiModelProperty(value="修改人名称")
    @JsonProperty("update_by_name")
    private String updateByName;

}