package com.aiqin.bms.scmp.api.product.domain.request.variableprice;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("价格管理")
public class PriceManagementReqVo extends PageReq {
   @ApiModelProperty("价格类型")
   private String priceTypeCode;
   @ApiModelProperty("sku编号")
   private String skuCode;
    @ApiModelProperty("sku名称")
    private String skuName;
    @ApiModelProperty("金额起始区间")
    private Long amountSta;
    @ApiModelProperty("金额结束区间")
    private Long amountEnd;
    @ApiModelProperty("品类")
   private String productCategoryCode;
    @ApiModelProperty("品牌")
   private String productBrandCode;
    @ApiModelProperty("商品属性")
    private String productPropertyCode;
    @ApiModelProperty("采购组")
    private String procurementSectionCode;
    @ApiModelProperty("生效开始时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Date takeEffectTimeSta;
    @ApiModelProperty("生效结束时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private  Date takeEffectTimeEnd;
    @ApiModelProperty("失效开始时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Date failureTimeSta;
    @ApiModelProperty("失效结束时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private  Date failureTimeEnd;
    @ApiModelProperty("供应商编码")
    private String supplierCode;

}
