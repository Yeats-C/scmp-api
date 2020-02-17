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
public class PurchaseApply {
    @ApiModelProperty(value="")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="采购申请id")
    @JsonProperty("purchase_apply_id")
    private String purchaseApplyId;

    @ApiModelProperty(value="采购申请单")
    @JsonProperty("purchase_apply_code")
    private String purchaseApplyCode;

    @ApiModelProperty(value="采购申请单名称（审批名称）")
    @JsonProperty("purchase_apply_name")
    private String purchaseApplyName;

    @ApiModelProperty(value="供应商编码")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value="供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty(value="结算方式编码")
    @JsonProperty("settlement_method_code")
    private String settlementMethodCode;

    @ApiModelProperty(value="结算方式名称")
    @JsonProperty("settlement_method_name")
    private String settlementMethodName;

    @ApiModelProperty(value="商品数量")
    @JsonProperty("product_count")
    private Long productCount;

    @ApiModelProperty(value="实物返数量")
    @JsonProperty("return_count")
    private Long returnCount;

    @ApiModelProperty(value="赠品数量")
    @JsonProperty("gift_count")
    private Long giftCount;

    @ApiModelProperty(value="采购申请类型   0 手动 1自动")
    @JsonProperty("apply_type")
    private Integer applyType;

    @ApiModelProperty(value="采购申请状态0. 待提交 1.已完成  2.待审核 3.审核中 4.审核通过 5.审核不通过 6.撤销")
    @JsonProperty("apply_status")
    private Integer applyStatus;

    @ApiModelProperty(value="采购组编码")
    @JsonProperty("purchase_group_code")
    private String purchaseGroupCode;

    @ApiModelProperty(value="采购组名称")
    @JsonProperty("purchase_group_name")
    private String purchaseGroupName;

    @ApiModelProperty(value="公司编码")
    @JsonProperty("company_code")
    private String companyCode;

    @ApiModelProperty(value="公司名称")
    @JsonProperty("company_name")
    private String companyName;

    @ApiModelProperty(value="是否删除 0 否 1是")
    @JsonProperty("status")
    private Integer status;

    @ApiModelProperty(value="最小单位数量")
    @JsonProperty("total_count")
    private Long totalCount;

    @ApiModelProperty(value="商品含税金额")
    @JsonProperty("product_tax_amount")
    private BigDecimal productTaxAmount;

    @ApiModelProperty(value="实物返含税金额")
    @JsonProperty("return_tax_amount")
    private BigDecimal returnTaxAmount;

    @ApiModelProperty(value="赠品含税金额")
    @JsonProperty("gift_tax_amount")
    private BigDecimal giftTaxAmount;

    @ApiModelProperty(value="采购价来源 0.读取 1.录入")
    @JsonProperty("purchase_source")
    private Integer purchaseSource;

    @ApiModelProperty(value="预采购类型 0 普通采购 1.预采购")
    @JsonProperty("pre_purchase_type")
    private Integer prePurchaseType;

    @ApiModelProperty(value="预采购单号")
    @JsonProperty("pre_purchase_code")
    private String prePurchaseCode;

    @ApiModelProperty(value="账户编码")
    @JsonProperty("account_code")
    private String accountCode;

    @ApiModelProperty(value="账户名称")
    @JsonProperty("account_name")
    private String accountName;

    @ApiModelProperty(value="合同编码")
    @JsonProperty("contract_code")
    private String contractCode;

    @ApiModelProperty(value="合同名称")
    @JsonProperty("contract_name")
    private String contractName;

    @ApiModelProperty(value="负责人编码（登录人名称）")
    @JsonProperty("charge_person_code")
    private String chargePersonCode;

    @ApiModelProperty(value="负责人名称")
    @JsonProperty("charge_person_name")
    private String chargePersonName;

    @ApiModelProperty(value="联系人（供应商的联系人）")
    @JsonProperty("supplier_person")
    private String supplierPerson;

    @ApiModelProperty(value="供应商电话")
    @JsonProperty("supplier_mobile")
    private String supplierMobile;

    @ApiModelProperty(value="第一个商品品牌编码")
    @JsonProperty("brand_id")
    private String brandId;

    @ApiModelProperty(value="第一个商品品牌名称")
    @JsonProperty("brand_name")
    private String brandName;

    @ApiModelProperty(value="备注")
    @JsonProperty("remark")
    private String remark;

    @ApiModelProperty(value="岗位编码")
    @JsonProperty("position_code")
    private String positionCode;

    @ApiModelProperty(value="岗位名称")
    @JsonProperty("position_name")
    private String positionName;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value="修改时间")
    @JsonProperty("update_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty(value="创建人id")
    @JsonProperty("create_by_id")
    private String createById;

    @ApiModelProperty(value="修改人id")
    @JsonProperty("update_by_id")
    private String updateById;

    @ApiModelProperty(value="创建人名称")
    @JsonProperty("create_by_name")
    private String createByName;

    @ApiModelProperty(value="修改人名称")
    @JsonProperty("update_by_name")
    private String updateByName;

}