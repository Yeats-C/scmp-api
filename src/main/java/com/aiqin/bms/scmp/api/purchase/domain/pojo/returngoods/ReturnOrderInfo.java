package com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@ApiModel("退货订单主表")
@Data
public class ReturnOrderInfo {
    @ApiModelProperty(value="id")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="退货订单编码")
    @JsonProperty("return_order_code")
    private String returnOrderCode;

    @ApiModelProperty(value="订单编码(订单号)")
    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_date")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    @ApiModelProperty(value="订单类型编码 1.直送 2.配送 3.辅采")
    @JsonProperty("order_type")
    private Integer orderType;

    @ApiModelProperty(value="订单产品类型 0.B2B 1.B2C")
    @JsonProperty("order_product_type")
    private Integer orderProductType;

    @ApiModelProperty(value="退货类型 1.客户取消 2.缺货取消 3.划单取消 4.售后退货")
    @JsonProperty("return_order_type")
    private Integer returnOrderType;

    @ApiModelProperty(value="订单类别")
    @JsonProperty("order_category")
    private Integer orderCategory;

    @ApiModelProperty(value="业务形式 0门店退货 1批发退货")
    @JsonProperty("business_form")
    private String businessForm;

    @ApiModelProperty(value="平台类型 0.爱亲(新系统)，1.DL")
    @JsonProperty("platform_type")
    private Integer platformType;

    @ApiModelProperty(value="门店编码")
    @JsonProperty("store_code")
    private String storeCode;

    @ApiModelProperty(value="门店名称")
    @JsonProperty("store_name")
    private String storeName;

    @ApiModelProperty(value="加盟商编码")
    @JsonProperty("franchisee_code")
    private String franchiseeCode;

    @ApiModelProperty(value="加盟商名称")
    @JsonProperty("franchisee_name")
    private String franchiseeName;

    @ApiModelProperty(value="合伙人编码")
    @JsonProperty("partner_code")
    private String partnerCode;

    @ApiModelProperty(value="合伙人名称")
    @JsonProperty("partner_name")
    private String partnerName;

    @ApiModelProperty(value="支付状态 0已支付 1未支付")
    @JsonProperty("payment_status")
    private Integer paymentStatus;

    @ApiModelProperty(value="是否锁定(0是 1否）")
    @JsonProperty("be_lock")
    private Integer beLock;

    @ApiModelProperty(value="锁定原因")
    @JsonProperty("lock_reason")
    private String lockReason;

    @ApiModelProperty(value="是否异常订单(0是 1否）")
    @JsonProperty("be_exception")
    private Integer beException;

    @ApiModelProperty(value="异常原因")
    @JsonProperty("exception_reason")
    private String exceptionReason;

    @ApiModelProperty(value="是否删除(0是 1否)")
    @JsonProperty("be_delete")
    private Integer beDelete;

    @ApiModelProperty(value="物流中心名称")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty(value="物流中心编码")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty(value="仓库名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty(value="仓库编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty(value="供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty(value="供应商编码")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value="客户名称")
    @JsonProperty("customer_name")
    private String customerName;

    @ApiModelProperty(value="客户编码")
    @JsonProperty("customer_code")
    private String customerCode;

    @ApiModelProperty(value="发货人")
    @JsonProperty("consignee")
    private String consignee;

    @ApiModelProperty(value="发货人手机号")
    @JsonProperty("consignee_phone")
    private String consigneePhone;

    @ApiModelProperty(value="发货人电话")
    @JsonProperty("consignee_mobile")
    private String consigneeMobile;

    @ApiModelProperty(value="")
    @JsonProperty("address")
    private String address;

    @ApiModelProperty(value="省编码")
    @JsonProperty("province_code")
    private String provinceCode;

    @ApiModelProperty(value="省名称")
    @JsonProperty("province_name")
    private String provinceName;

    @ApiModelProperty(value="市编码")
    @JsonProperty("city_code")
    private String cityCode;

    @ApiModelProperty(value="市名称")
    @JsonProperty("city_name")
    private String cityName;

    @ApiModelProperty(value="区编码")
    @JsonProperty("district_code")
    private String districtCode;

    @ApiModelProperty(value="区名称")
    @JsonProperty("district_name")
    private String districtName;

    @ApiModelProperty(value="")
    @JsonProperty("detail_address")
    private String detailAddress;

    @ApiModelProperty(value="邮编")
    @JsonProperty("zip_code")
    private String zipCode;

    @ApiModelProperty(value="配送方式")
    @JsonProperty("distribution_mode")
    private String distributionMode;

    @ApiModelProperty(value="配送方式编码")
    @JsonProperty("distribution_mode_code")
    private String distributionModeCode;

    @ApiModelProperty(value="订单状态 1.待审核 2.审核通过 3.退单同步中 4.等待退货验货 5.等待退货入库 11.退货完成 12.退款完成 97.退货异常终止 98.审核不通过 99.已取消")
    @JsonProperty("order_status")
    private Integer orderStatus;

    @ApiModelProperty(value="支付方式")
    @JsonProperty("payment_type")
    private String paymentType;

    @ApiModelProperty(value="支付方式编码")
    @JsonProperty("payment_type_code")
    private String paymentTypeCode;

    @ApiModelProperty(value="运费")
    @JsonProperty("deliver_amount")
    private BigDecimal deliverAmount;

    @ApiModelProperty(value="商品数量")
    @JsonProperty("product_count")
    private Long productCount;

    @ApiModelProperty(value="商品分销总金额")
    @JsonProperty("product_total_amount")
    private BigDecimal productTotalAmount;

    @ApiModelProperty(value="退货金额（分销）")
    @JsonProperty("return_order_amount")
    private BigDecimal returnOrderAmount;

    @ApiModelProperty(value="重量")
    @JsonProperty("weight")
    private Long weight;

    @ApiModelProperty(value="发货时间")
    @JsonProperty("delivery_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deliveryTime;

    @ApiModelProperty(value="收货时间")
    @JsonProperty("receiving_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date receivingTime;

    @ApiModelProperty(value="验货备注")
    @JsonProperty("inspection_remark")
    private String inspectionRemark;

    @ApiModelProperty(value="公司编码")
    @JsonProperty("company_code")
    private String companyCode;

    @ApiModelProperty(value="公司名称")
    @JsonProperty("company_name")
    private String companyName;

    @ApiModelProperty(value="运输公司编码")
    @JsonProperty("transport_company_code")
    private String transportCompanyCode;

    @ApiModelProperty(value="运输公司")
    @JsonProperty("transport_company")
    private String transportCompany;

    @ApiModelProperty(value="运输单号")
    @JsonProperty("transport_number")
    private String transportNumber;

    @ApiModelProperty(value="退货原因编码")
    @JsonProperty("return_reason_code")
    private String returnReasonCode;

    @ApiModelProperty(value="退货原因描述")
    @JsonProperty("return_reason_content")
    private String returnReasonContent;

    @ApiModelProperty(value="备注")
    @JsonProperty("remake")
    private String remake;

    @ApiModelProperty(value="处理办法（1退货退款2仅退货）")
    @JsonProperty("treatment_method")
    private Integer treatmentMethod;

    @ApiModelProperty(value="来源名称")
    @JsonProperty("order_original_name")
    private String orderOriginalName;

    @ApiModelProperty(value="来源编码")
    @JsonProperty("order_original")
    private String orderOriginal;

    @ApiModelProperty(value="创建人编码")
    @JsonProperty("create_by_id")
    private String createById;

    @ApiModelProperty(value="创建人名称")
    @JsonProperty("create_by_name")
    private String createByName;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value="修改人编码")
    @JsonProperty("update_by_id")
    private String updateById;

    @ApiModelProperty(value="修改人名称")
    @JsonProperty("update_by_name")
    private String updateByName;

    @ApiModelProperty(value="修改时间")
    @JsonProperty("update_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty(value="体积")
    @JsonProperty("volume")
    private Long volume;

    @ApiModelProperty(value="实际重量")
    @JsonProperty("actual_weight")
    private Long actualWeight;

    @ApiModelProperty(value="实际退货数量")
    @JsonProperty("actual_product_count")
    private Long actualProductCount;

    @ApiModelProperty(value="实际体积")
    @JsonProperty("actual_volume")
    private Long actualVolume;

    @ApiModelProperty(value="实际商品分销总金额")
    @JsonProperty("actual_product_total_amount")
    private BigDecimal actualProductTotalAmount;

    @ApiModelProperty(value="实际分销退货金额")
    @JsonProperty("actual_return_order_amount")
    private BigDecimal actualReturnOrderAmount;

    @ApiModelProperty(value="渠道总金额")
    @JsonProperty("product_channel_total_amount")
    private BigDecimal productChannelTotalAmount;

    @ApiModelProperty(value="实际渠道总金额")
    @JsonProperty("actual_product_channel_total_amount")
    private BigDecimal actualProductChannelTotalAmount;

    @ApiModelProperty(value="sap同步依据时间")
    @JsonProperty("synchr_time")
    private Date synchrTime;

    @ApiModelProperty(value="0:未同步,1已同步")
    @JsonProperty("synchr_status")
    private Integer synchrStatus;

    public ReturnOrderInfo() {
    }

    public ReturnOrderInfo(String returnOrderCode, String returnReasonContent, String updateByName) {
        this.returnOrderCode = returnOrderCode;
        this.returnReasonContent = returnReasonContent;
        this.updateByName = updateByName;
    }
}