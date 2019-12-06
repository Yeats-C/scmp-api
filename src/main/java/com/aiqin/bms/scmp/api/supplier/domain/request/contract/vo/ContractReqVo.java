package com.aiqin.bms.scmp.api.supplier.domain.request.contract.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 描述: 合同请求保存和编辑实体
 *
 * @Author: Kt.w
 * @Date: 2018/12/13
 * @Version 1.0
 * @since 1.0
 */
@ApiModel("合同请求保存和编辑实体")
@Data
public class ContractReqVo {

    @ApiModelProperty("年度")
    @NotEmpty(message = "年度不能为空")
    private String year;

    @ApiModelProperty("年度名称")
    @NotEmpty(message = "年度名称不能为空")
    private String yearName;

    @ApiModelProperty("供货单位名称")
    @NotEmpty(message = "供货单位名称不能为空")
    private String supplierName;

    @ApiModelProperty("供货单位编号")
    @NotEmpty(message = "供货单位编码不能为空")
    private String supplierCode;

    @ApiModelProperty("采购组编号")
    @NotEmpty(message = "采购编号不能为空")
    private String purchasingGroupCode;

    @ApiModelProperty("结算方式")
    @NotNull(message = "结算方式不能为空")
    private Byte settlementMethod;

    @ApiModelProperty("付款期")
    private Integer paymentPeriod;

    @ApiModelProperty("配送费")
    @NotNull(message = "配送费不能为空")
    private Long shippingFee;

    @ApiModelProperty("送货费承担方(甲方,乙方承担)")
    @NotNull(message = "送货费承担方不能为空")
    private Byte deliveryCharges;

    @ApiModelProperty("卸货费(甲方，乙方承担)")
    @NotNull(message = "卸货费承担方不能为空")
    private Long unloadingFee;

    @ApiModelProperty("其他约定")
    private String otherConventions;

    @ApiModelProperty("固定返利类型(未税,含税)")
    private Byte fixedRebateType;

    @ApiModelProperty("返利率(按进货金额)")
    private Long returnRate;

    @ApiModelProperty("目标返利(门店,地区,大区,全国)")
    private Byte targetRebate;

    @ApiModelProperty("计划类型(月度,季度,半年,全年)")
    List<PlanTypeReqVO> planTypeList;

    @ApiModelProperty("起始日期")
    @NotNull(message = "起始日期不能为空")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date startTime;

    @ApiModelProperty("结束日期")
    @NotNull(message = "结束日期不能为空")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date endTime;

    @ApiModelProperty("进货价格生效标准(下单日价格,收获日价格)")
//    @NotNull(message = "进货价格生效标准不能为空")
    private Byte purchasePriceStandard;

    @ApiModelProperty("关联申请合同")
    @NotEmpty(message = "关联申请合同编码不能为空")
    private String applyContractCode;

    @ApiModelProperty("审核人")
    private String auditorBy;

    @ApiModelProperty("审核时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
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
    private Long prePaymentRatio;

    @ApiModelProperty("发货付款比例")
    private Long shipPaymentRatio;

    @ApiModelProperty("到货付款比例")
    private Long paymentOnDeliveryRatio;

    @ApiModelProperty("返利条款 0:固定返利 1:目标返利")
    private Byte rebateClause;

    @ApiModelProperty("直属上级编码")
    @NotEmpty(message = "直属上级编码不能为空！")
    private String directSupervisorCode;

    @ApiModelProperty("直属上级名称")
    @NotEmpty(message = "直属上级名称不能为空！")
    private String directSupervisorName;

    @ApiModelProperty("进货额")
    @Valid
    private List<ContractPurchaseVolumeReqVo> purchaseCount;

    @ApiModelProperty("职位编码")
    private String positionCode;

    @ApiModelProperty("职位名称")
    private String positionName;
}
