package com.aiqin.bms.scmp.api.supplier.domain.response.applycontract;

import com.aiqin.bms.scmp.api.supplier.domain.response.contract.ContractFileResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.contract.ContractPurchaseGroupResVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 描述: 修改合同，新的实体对象
 *
 * @Author: Kt.w
 * @Date: 2018/12/20
 * @Version 1.0
 * @since 1.0
 */
@Data
@ApiModel("查看修改申请合同新的对象")
public class ApplyContractUpdateResVo {
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

    @ApiModelProperty("删除标记(0:正常 1:删除)")
    private Byte delFlag;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("修改时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date updateTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("申请合同编号")
    private String applyContractCode;


    @ApiModelProperty(" 合同编号")
    private String contractCode;

    @ApiModelProperty("申请状态(0:等待审核中 1:审核中)")
    private Byte applyStatus;

    @ApiModelProperty("进货额")
    private List<ApplyContractPurchaseVolumeResVo> purchaseList;

    @ApiModelProperty("最小起订额")
    private Long minAmount;

    @ApiModelProperty("合同类型编码")
    private String contractTypeCode;

    @ApiModelProperty("合同类型名称")
    private String contractTypeName;

    @ApiModelProperty("结账日")
    private String checkoutDate;

    @ApiModelProperty("合同采购组")
    private List<ContractPurchaseGroupResVo> purchaseGroupResVos;

    @ApiModelProperty("文件信息")
    private List<ContractFileResVo> fileResVos;


}
