package com.aiqin.bms.scmp.api.supplier.domain.request.applycontract.vo;

import com.aiqin.bms.scmp.api.supplier.domain.request.contract.vo.PlanTypeReqVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 描述:修改申请合同接受实体
 *
 * @Author: Kt.w
 * @Date: 2018/12/13
 * @Version 1.0
 * @since 1.0
 */
@Data
@ApiModel("修改申请合同接受实体")
public class UpdateApplyContractReqVo {
    @ApiModelProperty("主键id")
    @NotNull(message = "主键不能为空")
    private Long id;

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
    @NotEmpty(message = "采购组编号不能为空")
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
    @NotNull(message = "卸货费不能为空")
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
    private List<PlanTypeReqVO> planTypeList;

    @ApiModelProperty("起始日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @NotNull(message = "起始日期不能为空")
    private Date startTime;

    @ApiModelProperty("结束日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @NotNull(message = "结束日期不能为空")
    private Date endTime;

    @ApiModelProperty("进货价格生效标准(下单日价格,收获日价格)")
//    @NotNull(message = "进货价格生效标准不能为空")
    private Byte purchasePriceStandard;

    @ApiModelProperty("申通合同编号")
    private String applyContractCode;

    @ApiModelProperty(value = "采购组名称",hidden = true)
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
    private Long minAmount;

    @ApiModelProperty("最高起订金额")
    private Long maxAmount;

    @ApiModelProperty("送货周期")
    private Integer deliveryCycle;

    @ApiModelProperty("税率")
    private Long taxRate;

    @ApiModelProperty("折扣")
    private Long discount;

    @ApiModelProperty("退换货保证(0保证 1不保证)")
    private Byte returnGuarantee;

    @ApiModelProperty("退换货保证天数")
    private Integer returnGuaranteeDay;

    @ApiModelProperty("质保金")
    private Long warranty;

    @ApiModelProperty("供货渠道编码")
    private String categoriesSupplyChannelsCode;

    @ApiModelProperty("供货渠道名称")
    private String categoriesSupplyChannelsName;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("备注2")
    private String comment;

    @ApiModelProperty("采购组")
    private List<ApplyContractPurchaseGroupReqVo> purchaseGroupReqVos;

    @ApiModelProperty("品牌")
    private List<ApplyContractBrandReqVo> brandReqVos;

    @ApiModelProperty("品类")
    private List<ApplyContractCategoryReqVo> categoryReqVos;

    @ApiModelProperty("进货额")
    @NotNull(message = "进货额不能为空")
    private List<UpdateApplyContractPurchaseVolumeReqVo> purchaseVolumeReqVos;

    @ApiModelProperty("文件信息")
    private List<UpdateApplyContractFileReqVo> fileReqVos;
}
