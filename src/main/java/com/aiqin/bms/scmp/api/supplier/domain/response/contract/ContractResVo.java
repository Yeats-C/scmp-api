package com.aiqin.bms.scmp.api.supplier.domain.response.contract;

import com.aiqin.bms.scmp.api.supplier.domain.request.contract.vo.PlanTypeReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.LogData;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 描述:返回合同详情实体
 *
 * @Author: Kt.w
 * @Date: 2018/12/13
 * @Version 1.0
 * @since 1.0
 */
@Data
@ApiModel("返回合同详情实体")
public class ContractResVo {

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

    @ApiModelProperty(value = "采购组编号", hidden = true)
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
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @ApiModelProperty("结束日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @ApiModelProperty("进货价格生效标准(下单日价格,收获日价格)")
    private Byte purchasePriceStandard;

    @ApiModelProperty("删除标记(0:正常 1:删除)")
    private Byte delFlag;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("修改时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty("修改人")
    private String updateBy;

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

    @ApiModelProperty("最小起订额")
    private BigDecimal minAmount;

    @ApiModelProperty("返利条款 0:固定返利 1:目标返利")
    private Byte rebateClause;

    @ApiModelProperty("直属上级编码")
    private String directSupervisorCode;

    @ApiModelProperty("直属上级名称")
    private String directSupervisorName;

    @ApiModelProperty("进货额")
    private  List<ContractPurchaseVolumeResVo> purchaseVolumeReqVos;

    @ApiModelProperty("操作日志列表")
    private List<LogData> logDataList;

    @ApiModelProperty("文件信息")
    private List<ContractFileResVo> fileResVos;

    //新增字段

    @ApiModelProperty("合同类型编码")
    private String contractTypeCode;

    @ApiModelProperty("合同类型名称")
    private String contractTypeName;

    @ApiModelProperty("结账日")
    private String checkoutDate;

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

    @ApiModelProperty("备注2")
    private String comment;

    @ApiModelProperty("合同采购组")
    private List<ContractPurchaseGroupResVo> purchaseGroupResVos;

    @ApiModelProperty("品牌")
    List<ContractBrandResVo> brandResVos;

    @ApiModelProperty("品类")
    List<ContractCategoryResVo> categoryResVos;

    @ApiModelProperty("平均毛利率")
    private BigDecimal averageGrossMargin;

    @ApiModelProperty("合同属性")
    private String contractProperty;

    @ApiModelProperty("合同费用")
    private BigDecimal contractCost;
}
