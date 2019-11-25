package com.aiqin.bms.scmp.api.product.domain.request.price;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-17
 * @time: 10:30
 */
@ApiModel("sku价格保存")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class SkuPriceDraftReqVO {

    public SkuPriceDraftReqVO(String priceItemCode, String priceItemName, String priceTypeCode, String priceTypeName, String priceAttributeCode, String priceAttributeName) {
        this.priceItemCode = priceItemCode;
        this.priceItemName = priceItemName;
        this.priceTypeCode = priceTypeCode;
        this.priceTypeName = priceTypeName;
        this.priceAttributeCode = priceAttributeCode;
        this.priceAttributeName = priceAttributeName;
    }
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
    private BigDecimal priceTax;

    @ApiModelProperty(value = "未税金额",hidden = true)
    private BigDecimal priceNoTax;

    @ApiModelProperty(value = "税率",hidden = true)
    private BigDecimal tax;

    @ApiModelProperty("开始生效时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
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
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "修改人",hidden = true)
    private String updateBy;

    @ApiModelProperty(value = "修改时间",hidden = true)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty(value = "公司名称",hidden = true)
    private String companyCode;

    @ApiModelProperty(value = "公司编码",hidden = true)
    private String companyName;

}
