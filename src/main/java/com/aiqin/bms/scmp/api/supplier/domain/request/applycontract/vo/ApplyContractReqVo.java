package com.aiqin.bms.scmp.api.supplier.domain.request.applycontract.vo;

import com.aiqin.bms.scmp.api.supplier.domain.request.contract.vo.PlanTypeReqVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @description:  申请合同请求保存vo
 * @author:曾兴旺
 * @date: 2018/12/3
 */
@Data
@ApiModel("申请合同保存 请求vo")
public class ApplyContractReqVo{

    @ApiModelProperty("年度")
    @NotEmpty(message = "年度不能为空")
    private String year;

    @ApiModelProperty("合同名称")
    @NotEmpty(message = "合同名称不能为空")
    private String yearName;

    @ApiModelProperty("供货单位名称")
//    @NotEmpty(message = "供货单位名称不能为空")
    private String supplierName;

    @ApiModelProperty("供货单位编号")
//    @NotEmpty(message = "供货单位编号不能为空")
    private String supplierCode;

    @ApiModelProperty(value = "采购组编号",hidden = true)

    private String purchasingGroupCode;

    @ApiModelProperty("结算方式")
    @NotNull(message = "结算方式不能为空")
    private Byte settlementMethod;

    @ApiModelProperty("付款期")
    private Integer paymentPeriod;

    @ApiModelProperty("配送费")
    @NotNull(message = "配送费不能为空")
    private BigDecimal shippingFee;

    @ApiModelProperty("送货费承担方(甲方,乙方承担)")
    @NotNull(message = "送货费承担方不能为空")
    private Byte deliveryCharges;

    @ApiModelProperty("卸货费(甲方，乙方承担)")
    @NotNull(message = "卸货费不能为空")
    private Long unloadingFee;

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

    @ApiModelProperty("计划类型(月度,季度,半年,全年)")
    List<PlanTypeReqVO> planTypeList;

    @ApiModelProperty("起始日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @NotNull(message = "起始日期不能为空")
    private Date startTime = new Date();

    @ApiModelProperty("结束日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @NotNull(message = "结束日期不能为空")
    private Date endTime = new Date();

    @ApiModelProperty(value = "进货价格生效标准(下单日价格,收获日价格)",hidden = true)
//    @NotNull(message = "进货价格生效标准(下单日价格,收获日价格)不能为空")
    private Byte purchasePriceStandard;
    @ApiModelProperty(value = "采购组名称",hidden = true)
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
    @NotEmpty(message = "直属上级编码不能为空！")
    private String directSupervisorCode;

    @ApiModelProperty("直属上级名称")
    @NotEmpty(message = "直属上级名称不能为空！")
    private String directSupervisorName;

    @ApiModelProperty("合同类型编码")
    private String contractTypeCode;

    @ApiModelProperty("合同类型名称")
    private String contractTypeName;

    @ApiModelProperty("结账日")
    private String checkoutDate;

    @ApiModelProperty("最低起订金额")
    private BigDecimal minAmount;

    @ApiModelProperty("最高起订金额")
    private BigDecimal maxAmount;

    @ApiModelProperty("送货周期")
    private Integer deliveryCycle;

    @ApiModelProperty("税率")
    private BigDecimal taxRate;

    @ApiModelProperty("折扣")
    private String discount;

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

    @ApiModelProperty("最下面的备注")
    private String comment;

    @ApiModelProperty("采购组")
    private List<ApplyContractPurchaseGroupReqVo> purchaseGroupReqVos;

    @ApiModelProperty("品牌")
    private List<ApplyContractBrandReqVo> brandReqVos;

    @ApiModelProperty("品类")
    private List<ApplyContractCategoryReqVo> categoryReqVos;

    @ApiModelProperty("进货额")
    @Valid
    private List<ApplyContractPurchaseVolumeReqVo> purchaseVolumeReqVos;

    @ApiModelProperty("文件信息")
    private List<ApplyContractFileReqVo> fileReqVos;

    @ApiModelProperty("判断来源 0:非导入 1:导入, 如果从导入新增/修改 不进入审批流,审批状态待提交")
    private Byte source = 0;

    @ApiModelProperty("职位编码")
    private String positionCode;

    @ApiModelProperty("职位名称")
    private String positionName;

    @ApiModelProperty("平均毛利率")
    private BigDecimal averageGrossMargin;

    @ApiModelProperty("合同属性")
    private String contractProperty;

    @ApiModelProperty("合同费用")
    private BigDecimal contractCost;
}
