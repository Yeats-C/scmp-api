package com.aiqin.bms.scmp.api.abutment.domain.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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

    /**
     * 供应链业务单据id
     */
    @JsonProperty("order_id")
    @ApiModelProperty("供应链业务单据id")
    @NotBlank(message = "供应链业务单据id不能为空")
    private String orderId;
    /**
     * 采购或退供单号
     */
    @JsonProperty("order_code")
    @ApiModelProperty("采购或退供单号")
    @NotBlank(message = "采购或退供单号不能为空")
    private String orderCode;
    /**
     * 关联单据id
     */
    @JsonProperty("parent_order_id")
    @ApiModelProperty("关联单据id")
    private String parentOrderId;
    /**
     * 关联单编码
     */
    @JsonProperty("parent_order_code")
    @ApiModelProperty("关联单编码")
    private String parentOrderCode;
    /**
     * 供应商编码
     */
    @JsonProperty("supplier_code")
    @ApiModelProperty("供应商编码")
    private String supplierCode;
    /**
     * 供应商名称
     */
    @JsonProperty("supplier_name")
    @ApiModelProperty("供应商名称")
    private String supplierName;
    /**
     * 业务单类型 0 采购单  5 退供单
     */
    @JsonProperty("order_type")
    @ApiModelProperty("业务单类型 0 采购单  5 退供单")
    @NotNull(message = "业务单类型不能为空")
    private Integer orderType;
    /**
     * 单据类型描述
     */
    @JsonProperty("order_type_desc")
    @ApiModelProperty("单据类型描述")
    private String orderTypeDesc;
    /**
     * 仓库编码
     */
    @JsonProperty("transport_code")
    @ApiModelProperty("仓库编码")
    private String transportCode;
    /**
     * 仓库名称
     */
    @JsonProperty("transport_name")
    @ApiModelProperty("仓库名称")
    private String transportName;
    /**
     * 库房编码
     */
    @JsonProperty("warehouse_code")
    @ApiModelProperty("库房编码")
    private String warehouseCode;
    /**
     * 库房名称
     */
    @JsonProperty("warehouse_name")
    @ApiModelProperty("库房名称")
    private String warehouseName;
    /**
     * 预计数量
     */
    @JsonProperty("sku_count")
    @ApiModelProperty("预计数量")
    private Integer skuCount;
    /**
     * 预计金额
     */
    @ApiModelProperty(value = "预计金额",hidden = true)
    private String amount;
    /**
     * 采购组编码
     */
    @JsonProperty("group_code")
    @ApiModelProperty("采购组编码")
    @NotBlank(message = "采购组编码不能为空")
    private String groupCode;
    /**
     * 采购组描述
     */
    @JsonProperty("group_name")
    @ApiModelProperty("采购组描述")
    private String groupName;
    /**
     * 公司编码
     */
    @JsonProperty("company_code")
    @ApiModelProperty(value = "公司编码",hidden = true)
    private String companyCode;
    /**
     * 公司名
     */
    @JsonProperty("company_name")
    @ApiModelProperty(value = "公司名",hidden = true)
    private String companyName;
    /**
     * 合同编码
     */
    @JsonProperty("contract_no")
    @ApiModelProperty("合同编码")
    private String contractNo;
    /**
     * 结算方式编码
     */
    @JsonProperty("settlement_type")
    @ApiModelProperty("结算方式编码")
    private String settlementType;
    /**
     * 结算方式
     */
    @JsonProperty("settlement_type_desc")
    @ApiModelProperty("结算方式")
    private String settlementTypeDesc;
    /**
     * 账户名称
     */
    @JsonProperty("account_name")
    @ApiModelProperty("账户名称")
    private String accountName;
    /**
     * 支付方式编码
     */
    @JsonProperty("pay_type")
    @ApiModelProperty("付款方式编码")
    @NotBlank(message = "付款方式编码不能为空")
    private String payType;
    /**
     * 支付方式名称
     */
    @JsonProperty("pay_type_desc")
    @ApiModelProperty("付款方式名称")
    private String payTypeDesc;
    /**
     * 付款期
     */
    @JsonProperty("pay_date")
    @ApiModelProperty("付款期")
    @NotBlank(message = "付款期不能为空")
    private Integer payDate;

    /**
     * 同步时间
     */
    @JsonProperty("sync_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("同步时间")
    @NotNull(message = "时间不能为空")
    private Date createTime;

    /**
     * 最终操作人id
     */
    @JsonProperty("user_id")
    @ApiModelProperty("最终操作人id")
    private String createById;

    /**
     * 最终操作人
     */
    @JsonProperty("user_name")
    @ApiModelProperty("最终操作人")
    @NotBlank(message = "最终操作人不能为空")
    private String createByName;


    /**
     * 单据明细信息
     */
    @JsonProperty("details")
    @ApiModelProperty("单据明细信息")
    private List<PurchaseDetail> details;
}
