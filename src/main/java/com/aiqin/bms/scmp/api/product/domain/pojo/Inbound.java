package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("库房入库主表")
@Data
public class Inbound extends PagesRequest{
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("入库状态编码")
    private Byte inboundStatusCode;

    @ApiModelProperty("入库状态名称")
    private String inboundStatusName;

    @ApiModelProperty("入库单编号")
    private String inboundOderCode;

    @ApiModelProperty("入库类型编码")
    private Byte inboundTypeCode;

    @ApiModelProperty("入库类型名称")
    private String inboundTypeName;

    @ApiModelProperty("来源单号")
    private String sourceOderCode;

    @ApiModelProperty("入库时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inboundTime;

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

    @ApiModelProperty("预计入库数量")
    private Long preInboundNum;

    @ApiModelProperty("预计主单位数量")
    private Long preMainUnitNum;

    @ApiModelProperty("预计含税总金额")
    private Long preTaxAmount;

    @ApiModelProperty("预计无税总金额")
    private Long preAmount;

    @ApiModelProperty("预计税额")
    private Long preTax;

    @ApiModelProperty("实际入库数量")
    private Long praInboundNum;

    @ApiModelProperty("实际主单位数量")
    private Long praMainUnitNum;

    @ApiModelProperty("实际含税总金额")
    private Long praTaxAmount;

    @ApiModelProperty("实际无税总金额")
    private Long praAmount;

    @ApiModelProperty("实际税额")
    private Long praTax;

    @ApiModelProperty("发货人")
    private String shipper;

    @ApiModelProperty("发货人电话")
    private String shipperNumber;

    @ApiModelProperty("发货人邮编")
    private String shipperRate;

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

    @ApiModelProperty("采购调用次数")
    private Integer purchaseNum;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新人")
    private String updateBy;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    public Inbound(String sourceOderCode, Integer purchaseNum, Integer pageSize, Integer pageNo) {
        this.sourceOderCode = sourceOderCode;
        this.purchaseNum = purchaseNum;
        this.setPageSize(pageSize);
        this.setPageNo(pageNo);
    }

    public Inbound() {
    }
}