package com.aiqin.bms.scmp.api.abutment.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sunx
 * @description
 * @date 2019-08-05
 * WERKS	CHAR	4	0	工厂
 * LOEKZ	CHAR	1	0	删除标识符
 * EKGRP	CHAR	3	0	采购组
 * LGORT	CHAR	4	0	存储位置
 * XCHPF	CHAR	1	0	批次管理需求的标识
 * LZEIH	UNIT	3	0	最大库存期间单位
 * MTVFP	CHAR	2	0	可用性检查的检查组
 * BKLAZ	CHAR	4	0	评估类
 * VPRSV	CHAR	1	0	价格控制指示符
 * MLAST	CHAR	1	0	物料价格确定: 控制
 * PEINH	DEC	5	0	价格单位
 * VERPR	CURR	11	2	移动平均价格/周期单价
 * MSGTY	CHAR	1	0	消息类型
 * MSAGE	CHAR	220	0	消息文本
 */
@Data
@ApiModel("scmp物料主数据-存储与财务数据")
public class SapSkuStorageFinancial {

    @ApiModelProperty("价格控制指示符，V")
    @JsonProperty("VPRSV")
    private String priceControlTag;

    @ApiModelProperty("评估类，1005")
    @JsonProperty("BKLAZ")
    private String evaluateCategory;

    @ApiModelProperty("移动平均价格/周期单价，34.23")
    @JsonProperty("VERPR")
    private String dynamicAvgPrice;

    @ApiModelProperty("可用性检查的检查组，KP")
    @JsonProperty("MTVFP")
    private String checkGroup;

    @ApiModelProperty("工厂，1001")
    @JsonProperty("WERKS")
    private String factoryCode;

    @ApiModelProperty("存储位置，1000")
    @JsonProperty("LGORT")
    private String storageLocationCode;

    @ApiModelProperty("价格单位，1")
    @JsonProperty("PEINH")
    private String priceUnit;

    @ApiModelProperty("采购组，500")
    @JsonProperty("EKGRP")
    private String purchaseGroupCode;
}
