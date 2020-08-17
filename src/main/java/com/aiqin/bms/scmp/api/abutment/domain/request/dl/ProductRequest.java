package com.aiqin.bms.scmp.api.abutment.domain.request.dl;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel("对接DL供应商主表")
public class ProductRequest {

    @NotNull(message = "行号不能为空")
    @ApiModelProperty(value="行号")
    @JsonProperty("line_code")
    private Integer lineCode;

    @ApiModelProperty(value="退货销售单行号")
    @JsonProperty("order_line_code")
    private Long orderLineCode;

    @NotNull(message = "SKU不能为空")
    @ApiModelProperty(value="sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value="sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @NotNull(message = "商品数量不能为空")
    @ApiModelProperty(value="最小单位数量")
    @JsonProperty("total_count")
    private Long totalCount;

    @ApiModelProperty(value="库存单位编码")
    @JsonProperty("unit_code")
    private String unitCode;

    @ApiModelProperty(value="库存单位名称")
    @JsonProperty("unit_name")
    private String unitName;

    @ApiModelProperty(value="颜色")
    @JsonProperty("color_name")
    private String colorName;

    @ApiModelProperty(value="型号")
    @JsonProperty("model_number")
    private String modelNumber;

    @ApiModelProperty(value="商品类型 0商品 1赠品 2实物返回  销售（0商品（本品） 1赠品）")
    @JsonProperty("product_type")
    private Integer productType;

    @NotNull(message = "分销单价不能为空")
    @ApiModelProperty(value="含税单价/分销单价")
    @JsonProperty("product_amount")
    private BigDecimal productAmount;

    @ApiModelProperty(value="未税金额")
    @JsonProperty("not_product_amount")
    private BigDecimal notProductAmount;

    @ApiModelProperty(value="税率")
    @JsonProperty("tax_rate")
    private BigDecimal taxRate;

    @ApiModelProperty(value="实际最小单位数量：回传用到")
    @JsonProperty("actual_total_count")
    private Long actualTotalCount;

    @ApiModelProperty(value="库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty(value="渠道单价：只有销售、退货用")
    @JsonProperty("channel_amount")
    private BigDecimal channelAmount;

    @ApiModelProperty(value="渠道分摊：只有销售用")
    @JsonProperty("activity_apportionment")
    private BigDecimal activityApportionment;

    @ApiModelProperty(value="优惠分摊：只有销售用")
    @JsonProperty("preferential_allocation")
    private BigDecimal preferentialAllocation;

    @ApiModelProperty(value="渠道总价：只有退货用")
    @JsonProperty("channel_total_amount")
    private BigDecimal channelTotalAmount;

    @ApiModelProperty(value="分销总价：只有退货用")
    @JsonProperty("product_total_amount")
    private BigDecimal productTotalAmount;

    @ApiModelProperty(value="仓库发货的日期")
    @JsonProperty("product_date")
    private String productDate;

    @ApiModelProperty(value="wms库房类型")
    @JsonProperty("wms_warehouse_type")
    private Integer wmsWarehouseType;

    @ApiModelProperty(value="退货回传类型 1.正品 2.次品")
    @JsonProperty("return_type")
    private Integer returnType;

    @ApiModelProperty(value="拆零系数")
    @JsonProperty("zero_disassembly_coefficient")
    private Long zeroDisassemblyCoefficient;

    @ApiModelProperty(value="批次信息")
    @JsonProperty("batch_list")
    private List<BatchRequest> batchList;

}
