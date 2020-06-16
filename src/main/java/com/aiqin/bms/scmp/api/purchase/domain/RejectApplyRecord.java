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
public class RejectApplyRecord {
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="退货申请单号")
    @JsonProperty("reject_apply_record_code")
    private String rejectApplyRecordCode;

    @ApiModelProperty(value="退供申请名称")
    @JsonProperty("reject_apply_record_name")
    private String rejectApplyRecordName;

    @ApiModelProperty(value="采购组编码")
    @JsonProperty("purchase_group_code")
    private String purchaseGroupCode;

    @ApiModelProperty(value="采购组名称")
    @JsonProperty("purchase_group_name")
    private String purchaseGroupName;

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

    @ApiModelProperty(value="公司")
    @JsonProperty("company_code")
    private String companyCode;

    @ApiModelProperty(value="公司名称")
    @JsonProperty("company_name")
    private String companyName;

    @ApiModelProperty(value="商品数量")
    @JsonProperty("product_count")
    private Long productCount;

    @ApiModelProperty(value="实物返数量")
    @JsonProperty("return_count")
    private Long returnCount;

    @ApiModelProperty(value="赠品数量")
    @JsonProperty("gift_count")
    private Long giftCount;

    @ApiModelProperty(value="最小单位数量")
    @JsonProperty("total_count")
    private Long totalCount;

    @ApiModelProperty(value="(普通)商品含税金额")
    @JsonProperty("product_tax_amount")
    private BigDecimal productTaxAmount;

    @ApiModelProperty(value="赠品含税金额")
    @JsonProperty("gift_tax_amount")
    private BigDecimal giftTaxAmount;

    @ApiModelProperty(value="实物返商品含税金额")
    @JsonProperty("return_tax_amount")
    private BigDecimal returnTaxAmount;

    @ApiModelProperty(value="第一个商品品牌编码")
    @JsonProperty("brand_id")
    private String brandId;

    @ApiModelProperty(value="第一个商品品牌名称")
    @JsonProperty("brand_name")
    private String brandName;

    @ApiModelProperty(value="审批岗位编码")
    @JsonProperty("position_code")
    private String positionCode;

    @ApiModelProperty(value="审批岗位名称")
    @JsonProperty("position_name")
    private String positionName;

    @ApiModelProperty(value="直属上级编码")
    @JsonProperty("direct_supervisor_code")
    private String directSupervisorCode;

    @ApiModelProperty(value="直属上级名称")
    @JsonProperty("direct_supervisor_name")
    private String directSupervisorName;

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

    @ApiModelProperty(value="负责人")
    @JsonProperty("charge_person")
    private String chargePerson;

    @ApiModelProperty(value="联系人")
    @JsonProperty("supplier_person")
    private String supplierPerson;

    @ApiModelProperty(value="联系电话")
    @JsonProperty("supplier_mobile")
    private String supplierMobile;

    @ApiModelProperty(value="省编码")
    @JsonProperty("province_id")
    private String provinceId;

    @ApiModelProperty(value="省名称")
    @JsonProperty("province_name")
    private String provinceName;

    @ApiModelProperty(value="市编码")
    @JsonProperty("city_id")
    private String cityId;

    @ApiModelProperty(value="市名称")
    @JsonProperty("city_name")
    private String cityName;

    @ApiModelProperty(value="区县编码")
    @JsonProperty("district_id")
    private String districtId;

    @ApiModelProperty(value="区县名称")
    @JsonProperty("district_name")
    private String districtName;

    @ApiModelProperty(value="详细地址")
    @JsonProperty("detail_address")
    private String detailAddress;

    @ApiModelProperty(value="备注")
    @JsonProperty("remark")
    private String remark;

    @ApiModelProperty(value="退供申请单状态: 0  待提交 1 待审核 2.审核中 3.审核通过 4.审核不通过 5. 撤销")
    @JsonProperty("apply_record_status")
    private Integer applyRecordStatus;

    @ApiModelProperty(value="申请单类型: 0 手动 1自动")
    @JsonProperty("apply_type")
    private Integer applyType;

    @ApiModelProperty(value="是否删除 0.否 1.是")
    @JsonProperty("use_status")
    private Integer useStatus;

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
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(value="修改时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("update_time")
    private Date updateTime;

    @ApiModelProperty(value="供应商集团编码")
    @JsonProperty("supplier_company_code")
    private String supplierCompanyCode;

    @ApiModelProperty(value="供应商集团名称")
    @JsonProperty("supplier_company_name")
    private String supplierCompanyName;

}