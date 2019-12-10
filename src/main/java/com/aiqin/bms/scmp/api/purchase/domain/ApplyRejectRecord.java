package com.aiqin.bms.scmp.api.purchase.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel
@Data
public class ApplyRejectRecord {
    @ApiModelProperty(value="")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="")
    @JsonProperty("reject_record_id")
    private String rejectRecordId;

    @ApiModelProperty(value="")
    @JsonProperty("reject_record_code")
    private String rejectRecordCode;

    @ApiModelProperty(value="关联审批号")
    @JsonProperty("approval_code")
    private String approvalCode;

    @ApiModelProperty(value="负责人")
    @JsonProperty("duty_person")
    private String dutyPerson;

    @ApiModelProperty(value="联系人")
    @JsonProperty("contacts_person")
    private String contactsPerson;

    @ApiModelProperty(value="联系人电话")
    @JsonProperty("contacts_person_phone")
    private String contactsPersonPhone;

    @ApiModelProperty(value="公司")
    @JsonProperty("company_code")
    private String companyCode;

    @ApiModelProperty(value="公司名称")
    @JsonProperty("company_name")
    private String companyName;

    @ApiModelProperty(value="收货区域 :省")
    @JsonProperty("province_id")
    private String provinceId;

    @ApiModelProperty(value="")
    @JsonProperty("province_name")
    private String provinceName;

    @ApiModelProperty(value="市")
    @JsonProperty("city_id")
    private String cityId;

    @ApiModelProperty(value="")
    @JsonProperty("city_name")
    private String cityName;

    @ApiModelProperty(value="县")
    @JsonProperty("district_id")
    private String districtId;

    @ApiModelProperty(value="")
    @JsonProperty("district_name")
    private String districtName;

    @ApiModelProperty(value="收货地址")
    @JsonProperty("address")
    private String address;

    @ApiModelProperty(value="预计配送时间")
    @JsonProperty("expect_time")
    private Date expectTime;

    @ApiModelProperty(value="有效期")
    @JsonProperty("valid_day")
    private Date validDay;

    @ApiModelProperty(value="直属上级id (取字典表数据)")
    @JsonProperty("dictionary_id")
    private String dictionaryId;

    @ApiModelProperty(value="")
    @JsonProperty("dictionary_name")
    private String dictionaryName;

    @ApiModelProperty(value="供应商code")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value="商品 结算方式")
    @JsonProperty("settlement_method_code")
    private String settlementMethodCode;

    @ApiModelProperty(value="商品 结算方式")
    @JsonProperty("settlement_method_name")
    private String settlementMethodName;

    @ApiModelProperty(value="")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty(value="采购组 code")
    @JsonProperty("purchase_group_code")
    private String purchaseGroupCode;

    @ApiModelProperty(value="")
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

    @ApiModelProperty(value="退供单状态: 0 待审核 1 审核中  2 待供应商确认 3 待出库  4 出库开始 5 已出库 6 已发运 7 完成 8 取消 9 审核不通过")
    @JsonProperty("reject_status")
    private Integer rejectStatus;

    @ApiModelProperty(value="供应商评分编号")
    @JsonProperty("score_code")
    private String scoreCode;

    @ApiModelProperty(value="")
    @JsonProperty("remark")
    private String remark;

    @ApiModelProperty(value="")
    @JsonProperty("transport_url")
    private String transportUrl;

    @ApiModelProperty(value="运输信息说明")
    @JsonProperty("transport_remark")
    private String transportRemark;

    @ApiModelProperty(value="")
    @JsonProperty("out_stock_time")
    private Date outStockTime;

    @ApiModelProperty(value="普通商品数量")
    @JsonProperty("product_count")
    private Integer productCount;

    @ApiModelProperty(value="普通商品含税金额")
    @JsonProperty("product_amount")
    private BigDecimal productAmount;

    @ApiModelProperty(value="单品数量")
    @JsonProperty("single_count")
    private Integer singleCount;

    @ApiModelProperty(value="赠品数量")
    @JsonProperty("gift_count")
    private Integer giftCount;

    @ApiModelProperty(value="赠品含税金额")
    @JsonProperty("gift_amount")
    private BigDecimal giftAmount;

    @ApiModelProperty(value="实物返回数量")
    @JsonProperty("return_count")
    private Integer returnCount;

    @ApiModelProperty(value="实物返金额")
    @JsonProperty("return_amount")
    private BigDecimal returnAmount;

    @ApiModelProperty(value="实际普通商品数量")
    @JsonProperty("actual_product_count")
    private Integer actualProductCount;

    @ApiModelProperty(value="实际普通商品含税金额")
    @JsonProperty("actual_product_amount")
    private BigDecimal actualProductAmount;

    @ApiModelProperty(value="实际单品数量")
    @JsonProperty("actual_single_count")
    private Integer actualSingleCount;

    @ApiModelProperty(value="实际赠品数量")
    @JsonProperty("actual_gift_count")
    private Integer actualGiftCount;

    @ApiModelProperty(value="实际赠品含税金额")
    @JsonProperty("actual_gift_amount")
    private BigDecimal actualGiftAmount;

    @ApiModelProperty(value="实际实物返回数量")
    @JsonProperty("actual_return_count")
    private Integer actualReturnCount;

    @ApiModelProperty(value="实际实物返金额")
    @JsonProperty("actual_return_amount")
    private BigDecimal actualReturnAmount;

    @ApiModelProperty(value="未税金额")
    @JsonProperty("untaxed_amount")
    private BigDecimal untaxedAmount;

    @ApiModelProperty(value="")
    @JsonProperty("create_by_id")
    private String createById;

    @ApiModelProperty(value="")
    @JsonProperty("create_by_name")
    private String createByName;

    @ApiModelProperty(value="")
    @JsonProperty("update_by_id")
    private String updateById;

    @ApiModelProperty(value="")
    @JsonProperty("update_by_name")
    private String updateByName;

    @ApiModelProperty(value="")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(value="")
    @JsonProperty("update_time")
    private Date updateTime;

}