package com.aiqin.bms.scmp.api.purchase.domain.response.returngoods;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-19
 * @time: 19:33
 */
@Data
@ApiModel("退货单批次商品信息")
public class ReturnOrderInboundBatchResponse {

    @ApiModelProperty("sku编号")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty(value = "图片地址")
    @JsonProperty("picture_url")
    private String pictureUrl;

    @ApiModelProperty(value = "规格")
    private String spec;

    @ApiModelProperty(value = "颜色名称")
    @JsonProperty("color_name")
    private String colorName;

    @ApiModelProperty(value = "型号")
    private String model;

    @ApiModelProperty(value="销售包装")
    @JsonProperty("box_gauge")
    private String boxGauge;

    @ApiModelProperty("商品状态1新品2残品")
    @JsonProperty("product_status")
    private Integer productStatus;

    @ApiModelProperty(value = "数量")
    @JsonProperty("product_count")
    private Long productCount;

    @ApiModelProperty(value = "批次号")
    @JsonProperty("batch_code")
    private String batchCode;

    @ApiModelProperty(value = "批次编号")
    @JsonProperty("batch_info_code")
    private String batchInfoCode;

    @ApiModelProperty(value = "生产日期")
    @JsonProperty("product_date")
    private String productDate;

    @ApiModelProperty(value = "批次备注")
    @JsonProperty("batch_remark")
    private String batchRemark;

    @ApiModelProperty(value = "供应商编码")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value = "供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

}
