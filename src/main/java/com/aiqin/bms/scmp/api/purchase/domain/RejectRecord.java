package com.aiqin.bms.scmp.api.purchase.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel
@Data
public class RejectRecord {

    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="退供申请单编码")
    @JsonProperty("reject_apply_record_code")
    private String rejectApplyRecordCode;

    @ApiModelProperty(value="业务编码")
    @JsonProperty("reject_record_id")
    private String rejectRecordId;

    @ApiModelProperty(value="退供单号")
    @JsonProperty("reject_record_code")
    private String rejectRecordCode;

    @ApiModelProperty(value="退供单名称")
    @JsonProperty("reject_record_name")
    private String rejectRecordName;

    @ApiModelProperty(value="公司")
    @JsonProperty("company_code")
    private String companyCode;

    @ApiModelProperty(value="公司名称")
    @JsonProperty("company_name")
    private String companyName;

    @ApiModelProperty(value="供应商编码")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value="供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty(value="商品结算方式")
    @JsonProperty("settlement_method_code")
    private String settlementMethodCode;

    @ApiModelProperty(value="商品 结算方式名称")
    @JsonProperty("settlement_method_name")
    private String settlementMethodName;

    @ApiModelProperty(value="采购组 编码")
    @JsonProperty("purchase_group_code")
    private String purchaseGroupCode;

    @ApiModelProperty(value="采购组名称")
    @JsonProperty("purchase_group_name")
    private String purchaseGroupName;

    @ApiModelProperty(value="仓编码(物流中心编码)")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty(value="仓名称(物流中心名称)")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty(value="库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty(value="库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

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

    @ApiModelProperty(value="普通商品含税金额")
    @JsonProperty("product_tax_amount")
    private BigDecimal productTaxAmount;

    @ApiModelProperty(value="实物返含税金额")
    @JsonProperty("return_tax_amount")
    private BigDecimal returnTaxAmount;

    @ApiModelProperty(value="赠品含税金额")
    @JsonProperty("gift_tax_amount")
    private BigDecimal giftTaxAmount;

    @ApiModelProperty(value="实际商品数量")
    @JsonProperty("actual_product_count")
    private Long actualProductCount;

    @ApiModelProperty(value="实际实物返数量")
    @JsonProperty("actual_return_count")
    private Long actualReturnCount;

    @ApiModelProperty(value="实际赠品数量")
    @JsonProperty("actual_gift_count")
    private Long actualGiftCount;

    @ApiModelProperty(value="实际最小单位数量")
    @JsonProperty("actual_total_count")
    private Long actualTotalCount;

    @ApiModelProperty(value="实际普通商品含税金额")
    @JsonProperty("actual_product_tax_amount")
    private BigDecimal actualProductTaxAmount;

    @ApiModelProperty(value="实际实物返金额")
    @JsonProperty("actual_return_tax_amount")
    private BigDecimal actualReturnTaxAmount;

    @ApiModelProperty(value="实际赠品含税金额")
    @JsonProperty("actual_gift_tax_amount")
    private BigDecimal actualGiftTaxAmount;

    @ApiModelProperty(value="有效期")
    @JsonProperty("valid_time")
    private Date validTime;

    @ApiModelProperty(value="预计发货时间")
    @JsonProperty("pre_deliver_time")
    private Date preDeliverTime;

    @ApiModelProperty(value="负责人")
    @JsonProperty("charge_person")
    private String chargePerson;

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

    @ApiModelProperty(value="联系人")
    @JsonProperty("supplier_person")
    private String supplierPerson;

    @ApiModelProperty(value="联系人电话")
    @JsonProperty("supplier_mobile")
    private String supplierMobile;

    @ApiModelProperty(value="收货区域 :省")
    @JsonProperty("province_id")
    private String provinceId;

    @ApiModelProperty(value="省名称")
    @JsonProperty("province_name")
    private String provinceName;

    @ApiModelProperty(value="市")
    @JsonProperty("city_id")
    private String cityId;

    @ApiModelProperty(value="市名称")
    @JsonProperty("city_name")
    private String cityName;

    @ApiModelProperty(value="县")
    @JsonProperty("district_id")
    private String districtId;

    @ApiModelProperty(value="区县名称")
    @JsonProperty("district_name")
    private String districtName;

    @ApiModelProperty(value="收货地址")
    @JsonProperty("detail_address")
    private String detailAddress;

    @ApiModelProperty(value="供应商评分编号")
    @JsonProperty("score_code")
    private String scoreCode;

    @ApiModelProperty(value="确认时间")
    @JsonProperty("confirm_time")
    private Date confirmTime;

    @ApiModelProperty(value="出库时间")
    @JsonProperty("out_stock_time")
    private Date outStockTime;

    @ApiModelProperty(value="来源类型 0.退供申请 1.退货单")
    @JsonProperty("source_type")
    private Integer sourceType;

    @ApiModelProperty(value="来源单号")
    @JsonProperty("source_code")
    private String sourceCode;

    @ApiModelProperty(value="备注")
    @JsonProperty("remark")
    private String remark;

    @ApiModelProperty(value="关联审批")
    @JsonProperty("approval_code")
    private String approvalCode;

    @ApiModelProperty(value="退供单状态: 0 查看 1 确认  2 撤销 3 重发  ")
    @JsonProperty("reject_status")
    private Integer rejectStatus;

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

    @ApiModelProperty(value="0:未同步,1已同步")
    @JsonProperty("synchr_status")
    private Integer synchrStatus;
}