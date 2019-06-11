package com.aiqin.bms.scmp.api.supplier.domain.request.applycontract.vo;

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
    @NotEmpty(message = "供货单位名称不能为空")
    private String supplierName;

    @ApiModelProperty("供货单位编号")
    @NotEmpty(message = "供货单位编号不能为空")
    private String supplierCode;

    @ApiModelProperty("采购组编号")
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
    private Byte planType;

    @ApiModelProperty("起始日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @NotNull(message = "起始日期不能为空")
    private Date startTime = new Date();

    @ApiModelProperty("结束日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @NotNull(message = "结束日期不能为空")
    private Date endTime = new Date();

    @ApiModelProperty("进货价格生效标准(下单日价格,收获日价格)")
    @NotNull(message = "进货价格生效标准(下单日价格,收获日价格)不能为空")
    private Byte purchasePriceStandard;
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
    private List<ApplyContractPurchaseVolumeReqVo> purchaseVolumeReqVos;

    @ApiModelProperty("文件信息")
    private List<ApplyContractFileReqVo> fileReqVos;

}
