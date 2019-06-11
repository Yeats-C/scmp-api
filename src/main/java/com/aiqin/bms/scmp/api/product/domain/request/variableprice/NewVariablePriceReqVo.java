package com.aiqin.bms.scmp.api.product.domain.request.variableprice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@ApiModel("excal导入")
public class NewVariablePriceReqVo {
    @NotEmpty(message = "sku编码不能为空")
    @ApiModelProperty("sku编码")
    private String skuCode;
    @NotEmpty(message = "sku名称不能为空")
    @ApiModelProperty("sku名称")
    private String skuName;
    @ApiModelProperty("品类code")
    private String productCategoryCode;
    @ApiModelProperty("品类名称")
    private String productCategoryName;
    @NotEmpty(message = "价格类型code不能为空")
    @ApiModelProperty("价格类型code")
    private String priceTypeCode;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("价格类型")
    private String priceTypeName;

    @ApiModelProperty("最新采购价")
    private Long newPurchasingPrice;

    @ApiModelProperty("供应商code")
    private String supplierCode;

    @ApiModelProperty("0:否 1：是")
    private Byte isDefault;

    @ApiModelProperty("原含税采购价")
    private Long originalTaxPurchasePrice;

    @ApiModelProperty("生效时间")
    private Date takeEffectTime;

    @ApiModelProperty("新含税采购价")
    private Long newTaxedPurchasingPrice;

    @ApiModelProperty("原会员价")
    private Long originalMembershipPrice;

    @ApiModelProperty("新含税会员价")
    private Long newTaxedMembershipPrice;

    @ApiModelProperty("调价原因")
    private String updatePriceReason;

    @ApiModelProperty("变价原因关联字典code")
    private String updatePriceReasonCode;

    @ApiModelProperty("失效时间")
    private Date failureTime;

    @ApiModelProperty("临时含税售价")
    private Long temporaryTaxedPrice;

    @ApiModelProperty("变价标识(0:新建草稿 1:确认新建)")
    private Byte variablePriceStatus;

    @ApiModelProperty("变价名称")
    private String variablePriceName;

    @ApiModelProperty("采购组编码")
    private String procurementSectionCode;

    @ApiModelProperty("采购组名称")
    private String procurementSectionName;

    @ApiModelProperty("备注")
    private String remark;
    //保存后状态是待提交，提交后状态是带审核
    @ApiModelProperty("(0:保存->待提交,1:提交->待审核，2：审核通过3:审核不通过)")
    private Byte status;
}
