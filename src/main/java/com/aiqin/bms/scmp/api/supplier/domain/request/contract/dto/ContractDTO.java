package com.aiqin.bms.scmp.api.supplier.domain.request.contract.dto;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 描述:合同数据库交互实体
 *
 * @Author: Kt.w
 * @Date: 2018/12/13
 * @Version 1.0
 * @since 1.0
 */
@Data
@ApiModel("合同数据库交互实体")
public class ContractDTO extends CommonBean {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("年度")
    private String year;

    @ApiModelProperty("年度名称")
    private String yearName;

    @ApiModelProperty("供货单位名称")
    private String supplierName;

    @ApiModelProperty("供货单位编号")
    private String supplierCode;

    @ApiModelProperty("采购组编号")
    private String purchasingGroupCode;

    @ApiModelProperty("结算方式")
    private Byte settlementMethod;

    @ApiModelProperty("付款期")
    private Integer paymentPeriod;

    @ApiModelProperty("配送费")
    private BigDecimal shippingFee;

    @ApiModelProperty("送货费承担方(甲方,乙方承担)")
    private Byte deliveryCharges;

    @ApiModelProperty("卸货费(甲方，乙方承担)")
    private BigDecimal unloadingFee;

    @ApiModelProperty("其他约定")
    private String otherConventions;

    @ApiModelProperty("固定返利类型(未税,含税)")
    private Byte fixedRebateType;

    @ApiModelProperty("返利率(按进货金额)")
    private BigDecimal returnRate;

    @ApiModelProperty("目标返利(门店,地区,大区,全国)")
    private Byte targetRebate;

//    @ApiModelProperty("计划类型(月度,季度,半年,全年)")
//    private Byte planType;

    @ApiModelProperty("起始日期")
    private Date startTime;

    @ApiModelProperty("结束日期")
    private Date endTime;

    @ApiModelProperty("进货价格生效标准(下单日价格,收获日价格)")
    private Byte purchasePriceStandard;

    @ApiModelProperty("编号")
    private String contractCode;

    @ApiModelProperty("关联申请合同code")
    private String applyContractCode;

    @ApiModelProperty("审核人")
    private String auditorBy;

    @ApiModelProperty("审核时间")
    private Date auditorTime;

    @ApiModelProperty("申请状态(0 ：待审 1，审批中 2 审批通过 ，3 审批失败 4，已撤销 )")
    private Byte applyStatus;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("采购组名称")
    private String purchasingGroupName;

    @ApiModelProperty("付款方式名称")
    private String settlementMethodName;

    @ApiModelProperty("合同预警月数")
    private Integer earlyWarnNum;

    @ApiModelProperty("预先付款比列")
    private BigDecimal prePaymentRatio;

    @ApiModelProperty("发货付款比例")
    private BigDecimal shipPaymentRatio;

    @ApiModelProperty("到货付款比例")
    private BigDecimal paymentOnDeliveryRatio;

    @ApiModelProperty("返利条款 0:固定返利 1:目标返利")
    private Byte rebateClause;

    @ApiModelProperty("直属上级编码")
    private String directSupervisorCode;

    @ApiModelProperty("直属上级名称")
    private String directSupervisorName;

    @ApiModelProperty("合同类型编码")
    private String contractTypeCode;

    @ApiModelProperty("合同类型名称")
    private String contractTypeName;

    @ApiModelProperty("结账日")
    private String checkoutDate;

    @ApiModelProperty("最小起订金额")
    private BigDecimal minAmount;

    @ApiModelProperty("最高起订金额")
    private BigDecimal maxAmount;

    @ApiModelProperty("送货周期")
    private Integer deliveryCycle;

    @ApiModelProperty("税率")
    private BigDecimal taxRate;

    @ApiModelProperty("折扣")
    private BigDecimal discount;

    @ApiModelProperty("退换货保证(0保证 1不保证)")
    private Byte returnGuarantee;

    @ApiModelProperty("退换货保证天数")
    private Integer returnGuaranteeDay;

    @ApiModelProperty("质保金")
    private BigDecimal warranty;

    @ApiModelProperty("供货渠道编码")
    private String categoriesSupplyChannelsCode;

    @ApiModelProperty("供货渠道名称")
    private String categoriesSupplyChannelsName;

    @ApiModelProperty("备注")
    private String remark;

     @ApiModelProperty("备注2")
    private String comment;

}
