package com.aiqin.bms.scmp.api.product.domain.request.outbound;

import com.aiqin.bms.scmp.api.product.domain.pojo.OutboundBatch;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Classname: OutboundReqVo
 * 描述:
 * @Author: Kt.w
 * @Date: 2019/3/4
 * @Version 1.0
 * @Since 1.0
 */
@Data
@ApiModel("出库单请求保存实体")
public class OutboundReqVo {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("出库状态编码")
    private Byte outboundStatusCode;

    @ApiModelProperty("出库状态名称")
    private String outboundStatusName;

    @ApiModelProperty("出库单编号")
    private String outboundOderCode;

    @ApiModelProperty("出库类型编码")
    private Byte outboundTypeCode;

    @ApiModelProperty("出库类型名称")
    private String outboundTypeName;

    @ApiModelProperty("来源单号")
    private String sourceOderCode;

    @ApiModelProperty("出库时间")
    private Date outboundTime;

    @ApiModelProperty("物流中心编码")
    private String logisticsCenterCode;

    @ApiModelProperty("物流中心名称")
    private String logisticsCenterName;

    @ApiModelProperty("库房编码")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    private String warehouseName;

    @ApiModelProperty("供货单位编码")
    private String supplierCode;

    @ApiModelProperty("供货单位名称")
    private String supplierName;

    @ApiModelProperty("预计到货时间")
    private Date preArrivalTime;

    @ApiModelProperty("WMS单号")
    private String wmsDocumentCode;

    @ApiModelProperty("预计出库数量")
    private Long preOutboundNum;

    @ApiModelProperty("预计主单位数量")
    private Long preMainUnitNum;

    @ApiModelProperty("预计含税总金额")
    private BigDecimal preTaxAmount;

    @ApiModelProperty("预计无税总金额")
    private BigDecimal preAmount;

    @ApiModelProperty("预计税额")
    private BigDecimal preTax;

    @ApiModelProperty("实际出库数量")
    private Long praOutboundNum;

    @ApiModelProperty("实际主单位数量")
    private Long praMainUnitNum;

    @ApiModelProperty("实际含税总金额")
    private BigDecimal praTaxAmount;

    @ApiModelProperty("实际无税总金额")
    private BigDecimal praAmount;

    @ApiModelProperty("实际税额")
    private BigDecimal praTax;

    @ApiModelProperty("收货人")
    private String consignee;

    @ApiModelProperty("收货人电话")
    private String consigneeNumber;

    @ApiModelProperty("收货人邮编")
    private String consigneeRate;

    @ApiModelProperty("省编码")
    private String provinceCode;

    @ApiModelProperty("省编码")
    private String provinceName;

    @ApiModelProperty("市编码")
    private String cityCode;

    @ApiModelProperty("市名称")
    private String cityName;

    @ApiModelProperty("区编码")
    private String countyCode;

    @ApiModelProperty("区名称")
    private String countyName;

    @ApiModelProperty("详细地址")
    private String detailedAddress;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新人")
    private String updateBy;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("sku 列表")
    private List<OutboundProductReqVo> list;

    @ApiModelProperty("sku 批次列表")
    private List<OutboundBatch> outboundBatches;
}
