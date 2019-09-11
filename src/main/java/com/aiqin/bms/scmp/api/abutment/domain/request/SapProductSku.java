package com.aiqin.bms.scmp.api.abutment.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author sunx
 * @description sap物料主数据
 * @date 2019-08-01
 */
@Data
@ToString(callSuper = true)
@ApiModel("scmp商品sku主数据")
public class SapProductSku extends SapBaseVO {
    /**
     * 物料编号
     */
    @ApiModelProperty("sap返回的物料编码，新增不传,编辑必传")
    @JsonProperty("MATNR")
    private String sapSkuCode;
    /**
     * 行业部门编码
     */
    @ApiModelProperty("行业部门编码，例A")
    @JsonProperty("MBRSH")
    private String industryDepartmentCode;
    /**
     * 物料类型编码
     */
    @ApiModelProperty("物料类型编码，Z005")
    @JsonProperty("MTART")
    private String categoryCode;
    /**
     * 物料描述
     */
    @ApiModelProperty("物料描述，甜心妈咪滋养保湿霜50g（开样试用装）")
    @JsonProperty("MAKTX")
    private String skuName;

    /**
     * 物料主数据-基本视图
     */
    @ApiModelProperty("物料主数据-基本视图")
    @JsonProperty("MARAS")
    private SapProductSkuBase baseInfo;


    @ApiModelProperty("物料主数据-存储与财务数据 ")
    @JsonProperty("MARCT")
    private List<SapSkuStorageFinancial> baseInfo1;


    @ApiModelProperty("物料主数据-销售视图")
    @JsonProperty("MVKET")
    private List<SapSkuSale> baseInfo2;
}
