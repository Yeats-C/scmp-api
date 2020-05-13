package com.aiqin.bms.scmp.api.product.domain.response.allocation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-07-05
 * @time: 20:47
 */
@Data
@ApiModel("调拨导入商品批次返回列表")
public class SkuBatchRespVO {

    @ApiModelProperty("sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("sku编码")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("仓库批次号编码")
    @JsonProperty("batch_code")
    private String warehouseBatchNumber;

    @ApiModelProperty("仓库编码")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("仓库名称")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty("库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty("批次备注")
    @JsonProperty("batch_remark")
    private String batchRemark;

    @ApiModelProperty("可用库存")
    @JsonProperty("available_num")
    private Long availableNum;

    @ApiModelProperty("生产日期")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @JsonProperty("production_date")
    private Date productTime;

    @ApiModelProperty("供应商code")
    @JsonProperty(value = "supplier_code")
    private String supplierCode;

    @ApiModelProperty("税率")
    @JsonProperty("tax_rate")
    private BigDecimal taxRate;

    @ApiModelProperty("成本")
    @JsonProperty("tax_cost")
    private BigDecimal taxCost;

}
