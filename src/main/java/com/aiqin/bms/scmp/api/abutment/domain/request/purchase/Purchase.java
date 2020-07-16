package com.aiqin.bms.scmp.api.abutment.domain.request.purchase;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author sunx
 * @description scmp采购单
 * @date 2019-08-03
 */
@Data
@ApiModel("scmp采购单")
public class Purchase {

    @JsonProperty("order_id")
    @ApiModelProperty("供应链业务单据id")
    @NotBlank(message = "供应链业务单据id不能为空")
    private String orderId;

    @JsonProperty("order_code")
    @ApiModelProperty("采购或退供单号")
    @NotBlank(message = "采购或退供单号不能为空")
    private String orderCode;

    @JsonProperty("parent_order_id")
    @ApiModelProperty("关联单据id")
    private String parentOrderId;

    @JsonProperty("parent_order_code")
    @ApiModelProperty("关联单编码")
    private String parentOrderCode;

    @JsonProperty("supplier_code")
    @ApiModelProperty("供应商编码")
    private String supplierCode;

    @JsonProperty("supplier_name")
    @ApiModelProperty("供应商名称")
    private String supplierName;

    @JsonProperty("order_type")
    @ApiModelProperty("业务单类型 0 采购 5 退供")
    @NotNull(message = "业务单类型不能为空")
    private String orderType;

    @JsonProperty("order_type_desc")
    @ApiModelProperty("单据类型描述")
    private String orderTypeDesc;

    @JsonProperty("transport_code")
    @ApiModelProperty("仓库编码")
    private String transportCode;

    @JsonProperty("transport_name")
    @ApiModelProperty("仓库名称")
    private String transportName;

    @JsonProperty("warehouse_code")
    @ApiModelProperty("库房编码")
    private String warehouseCode;

    @JsonProperty("warehouse_name")
    @ApiModelProperty("库房名称")
    private String warehouseName;

    @JsonProperty("sku_count")
    @ApiModelProperty("预计数量")
    private Long skuCount;

    @ApiModelProperty(value = "预计金额", hidden = true)
    private BigDecimal amount;

    @JsonProperty("group_code")
    @ApiModelProperty("采购组编码")
    @NotBlank(message = "采购组编码不能为空")
    private String groupCode;

    @JsonProperty("group_name")
    @ApiModelProperty("采购组描述")
    private String groupName;

    @JsonProperty("company_code")
    @ApiModelProperty(value = "公司编码", hidden = true)
    private String companyCode;

    @JsonProperty("company_name")
    @ApiModelProperty(value = "公司名", hidden = true)
    private String companyName;

    @JsonProperty("contract_no")
    @ApiModelProperty("合同编码")
    private String contractNo;

    @JsonProperty("settlement_type")
    @ApiModelProperty("结算方式编码")
    private String settlementType;

    @JsonProperty("settlement_type_desc")
    @ApiModelProperty("结算方式")
    private String settlementTypeDesc;

    @JsonProperty("account_name")
    @ApiModelProperty("账户名称")
    private String accountName;

    @JsonProperty("pay_type")
    @ApiModelProperty("付款方式编码")
    @NotBlank(message = "付款方式编码不能为空")
    private String payType;

    @JsonProperty("pay_type_desc")
    @ApiModelProperty("付款方式名称")
    private String payTypeDesc;

    @JsonProperty("pay_date")
    @ApiModelProperty("付款期")
    @NotBlank(message = "付款期不能为空")
    private Integer payDate;

    @JsonProperty("sync_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("同步时间")
    @NotNull(message = "时间不能为空")
    private Date createTime;

    @JsonProperty("user_id")
    @ApiModelProperty("最终操作人id")
    private String createById;

    @JsonProperty("user_name")
    @ApiModelProperty("最终操作人")
    @NotBlank(message = "最终操作人不能为空")
    private String createByName;

    @JsonProperty("details")
    @ApiModelProperty("单据明细信息")
    private List<PurchaseDetail> details;
}
