package com.aiqin.bms.scmp.api.product.domain.request.price;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-30
 * @time: 10:27
 */
@Data
@ApiModel("价格列表查询请求vo")
public class QueryProductSkuPriceInfoReqVO extends PageReq {

    @ApiModelProperty("价格类型")
    private String priceTypeCode;

    @ApiModelProperty("价格项目编码")
    private String priceItemCode;

    @ApiModelProperty("价格区间从")
    private Long priceFrom;

    @ApiModelProperty("价格区间到")
    private Long priceTo;

    @ApiModelProperty("开始生效时间从")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date effectiveTimeStartFrom;

    @ApiModelProperty("开始生效时间到")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date effectiveTimeStartTo;

    @ApiModelProperty("结束生效时间从")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date effectiveTimeEndFrom;

    @ApiModelProperty("结束生效时间到")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date effectiveTimeEndTo;

    @ApiModelProperty("商品状态")
    private Integer productStatus;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("供应商编码")
    private String supplierCode;

    @ApiModelProperty("采购组编码")
    private String purchaseGroupCode;

    @ApiModelProperty("品类")
    private String productCategoryCode;

    @ApiModelProperty("品牌")
    private String productBrandCode;

    @ApiModelProperty("商品属性")
    private String productPropertyCode;

    @ApiModelProperty("价格属性编码")
    private String priceAttributeCode;
}
