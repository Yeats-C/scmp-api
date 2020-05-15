package com.aiqin.bms.scmp.api.purchase.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel
@Data
public class RejectRecordDetail {
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="退供单商品id")
    @JsonProperty("reject_record_detail_id")
    private String rejectRecordDetailId;

    @ApiModelProperty(value="退供单id")
    @JsonProperty("reject_record_id")
    private String rejectRecordId;

    @ApiModelProperty(value="退供单号")
    @JsonProperty("reject_record_code")
    private String rejectRecordCode;

    @ApiModelProperty(value="sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value="SKU名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty(value="品类编码")
    @JsonProperty("category_id")
    private String categoryId;

    @ApiModelProperty(value="品类名称")
    @JsonProperty("category_name")
    private String categoryName;

    @ApiModelProperty(value="品牌编码")
    @JsonProperty("brand_id")
    private String brandId;

    @ApiModelProperty(value="品类名称")
    @JsonProperty("brand_name")
    private String brandName;

    @ApiModelProperty(value="商品类型 0商品  1赠品 2实物返回")
    @JsonProperty("product_type")
    private Integer productType;

    @ApiModelProperty(value="规格")
    @JsonProperty("product_spec")
    private String productSpec;

    @ApiModelProperty(value="颜色")
    @JsonProperty("color_code")
    private String colorCode;

    @ApiModelProperty(value="颜色")
    @JsonProperty("color_name")
    private String colorName;

    @ApiModelProperty(value="型号")
    @JsonProperty("model_number")
    private String modelNumber;

    @ApiModelProperty(value="单位")
    @JsonProperty("unit_code")
    private String unitCode;

    @ApiModelProperty(value="单位")
    @JsonProperty("unit_name")
    private String unitName;

    @ApiModelProperty(value="商品数量")
    @JsonProperty("product_count")
    private Long productCount;

    @ApiModelProperty(value="最小单位数量")
    @JsonProperty("total_count")
    private Long totalCount;

    @ApiModelProperty(value="厂商SKU编码")
    @JsonProperty("factory_sku_code")
    private String factorySkuCode;

    @ApiModelProperty(value="税率")
    @JsonProperty("tax_rate")
    private BigDecimal taxRate;

    @ApiModelProperty(value="条形码")
    @JsonProperty("barcode")
    private String barcode;

    @ApiModelProperty(value="含税单价")
    @JsonProperty("product_amount")
    private BigDecimal productAmount;

    @ApiModelProperty(value="含税总价")
    @JsonProperty("product_total_amount")
    private BigDecimal productTotalAmount;

    @ApiModelProperty(value="实际最小单位数量")
    @JsonProperty("actual_total_count")
    private Long actualTotalCount;

    @ApiModelProperty(value="实际含税总价")
    @JsonProperty("actual_product_total_amount")
    private BigDecimal actualProductTotalAmount;

    @ApiModelProperty(value="库存含税成本")
    @JsonProperty("product_cost")
    private BigDecimal productCost;

    @ApiModelProperty(value="行号")
    @JsonProperty("line_code")
    private Integer lineCode;

    @ApiModelProperty(value="创建人编码")
    @JsonProperty("create_by_id")
    private String createById;

    @ApiModelProperty(value="创建人名称")
    @JsonProperty("create_by_name")
    private String createByName;

    @ApiModelProperty(value="修改人编码")
    @JsonProperty("update_by_id")
    private String updateById;

    @ApiModelProperty(value="修改人名称")
    @JsonProperty("update_by_name")
    private String updateByName;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(value="修改时间")
    @JsonProperty("update_time")
    private Date updateTime;
}