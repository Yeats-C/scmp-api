package com.aiqin.bms.scmp.api.supplier.domain.response.applycontract;

import com.aiqin.bms.scmp.api.supplier.domain.response.LogData;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 描述:查看详情返回实体
 *
 * @Author: Kt.w
 * @Date: 2019/1/2
 * @Version 1.0
 * @since 1.0
 */
@Data
@ApiModel("查看详情返回实体")
public class ApplyContractViewResVo {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("年度")
    private String year;

    @ApiModelProperty("合同编号")
    private String contractCode;

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
    private Long shippingFee;

    @ApiModelProperty("送货费承担方(甲方,乙方承担)")
    private Byte deliveryCharges;

    @ApiModelProperty("卸货费(甲方，乙方承担)")
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
    private Byte planType;

    @ApiModelProperty("起始日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date startTime;

    @ApiModelProperty("结束日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date endTime;

    @ApiModelProperty("进货价格生效标准(下单日价格,收获日价格)")
    private Byte purchasePriceStandard;

    @ApiModelProperty("合同类型编码")
    private String contractTypeCode;

    @ApiModelProperty("合同类型名称")
    private String contractTypeName;

    @ApiModelProperty("结账日")
    private String checkoutDate;


    @ApiModelProperty("申通合同编号")
    private String applyContractCode;

    @ApiModelProperty("申请类型")
    private String applyType;

    @ApiModelProperty("申请人")
    private String applyBy;

    @ApiModelProperty("申请时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Date applyDate;

    @ApiModelProperty("审核人")
    private String auditorBy;

    @ApiModelProperty("审核时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date auditorTime;

    @ApiModelProperty("功能项")
    private String modelType;
    @ApiModelProperty("功能项编号")
    private String modelTypeCode;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("进货额")
    private List<ApplyContractPurchaseVolumeResVo> purchaseList;

    @ApiModelProperty("文件信息")
    private List<ApplyContractFileResVo> fileResVos;

    private List<ApplyContractPurchaseGroupResVo> purchaseGroupResVos;

    @ApiModelProperty("操作日志列表")
    private List<LogData> logDataList;

    @ApiModelProperty("审核申请单号")
    private String formNo;

    @ApiModelProperty("是否禁用")
    private String enable;


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
    private String directSupervisorCode;

    @ApiModelProperty("直属上级名称")
    private String directSupervisorName;

    public void setApplyType(Byte applyType) {
        if(applyType.equals((byte)0)){
            this.applyType = "新增申请";
        }else if(applyType.equals((byte)1)){
            this.applyType = "修改申请";
        }else {
            this.applyType = "";
        }
    }
}

