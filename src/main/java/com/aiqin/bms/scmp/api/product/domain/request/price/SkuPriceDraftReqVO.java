package com.aiqin.bms.scmp.api.product.domain.request.price;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-17
 * @time: 10:30
 */
@ApiModel("sku价格保存")
@Data
public class SkuPriceDraftReqVO {

    @ApiModelProperty(value = "sku编码",hidden = true)
    private String skuCode;

    @ApiModelProperty(value = "sku名称",hidden = true)
    private String skuName;

    @ApiModelProperty(value = "采购组编码",hidden = true)
    private String purchaseGroupCode;

    @ApiModelProperty(value = "采购组名称",hidden = true)
    private String purchaseGroupName;

    @ApiModelProperty("价格项目编码")
    private String priceItemCode;

    @ApiModelProperty("价格项目名称")
    private String priceItemName;

    @ApiModelProperty("价格类型")
    private String priceTypeCode;

    @ApiModelProperty("价格类型名称")
    private String priceTypeName;

    @ApiModelProperty("价格属性编码")
    private String priceAttributeCode;

    @ApiModelProperty("价格数据名称")
    private String priceAttributeName;

    @ApiModelProperty("含税金额")
    private Long priceTax;

    @ApiModelProperty(value = "未税金额",hidden = true)
    private Long priceNoTax;

    @ApiModelProperty(value = "税率",hidden = true)
    private Long tax;

    @ApiModelProperty("开始生效时间")
    private Date effectiveTimeStart;

    @ApiModelProperty(value = "供应商编码",hidden = true)
    private String supplierCode;

    @ApiModelProperty(value = "供应商名称",hidden = true)
    private String supplierName;

    @ApiModelProperty(value = "是否默认(0否1是)",hidden = true)
    private Integer beDefault;

    @ApiModelProperty(value = "创建人",hidden = true)
    private String createBy;

    @ApiModelProperty(value = "创建时间",hidden =true)
    private Date createTime;

    @ApiModelProperty(value = "修改人",hidden = true)
    private String updateBy;

    @ApiModelProperty(value = "修改时间",hidden = true)
    private Date updateTime;

    @ApiModelProperty(value = "公司名称",hidden = true)
    private String companyCode;

    @ApiModelProperty(value = "公司编码",hidden = true)
    private String companyName;
}
