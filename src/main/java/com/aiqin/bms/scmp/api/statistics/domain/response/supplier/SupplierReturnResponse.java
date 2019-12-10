package com.aiqin.bms.scmp.api.statistics.domain.response.supplier;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2019-09-10
 **/
@Data
public class SupplierReturnResponse {

    @ApiModelProperty(value="退供率的子集")
    @JsonProperty("subset_list")
    private List<SupplierReturnResponse> subsetList;

    @ApiModelProperty(value="供应商code")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value="供应商名")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty(value="采购负责人code")
    @JsonProperty("responsible_person_code")
    private String responsiblePersonCode;

    @ApiModelProperty(value="采购负责人")
    @JsonProperty("responsible_person_name")
    private String responsiblePersonName;

    @ApiModelProperty(value="全国退货数量")
    @JsonProperty("sum_single_count")
    private Long sumSingleCount;

    @ApiModelProperty(value="全国退货金额")
    @JsonProperty("sum_amt")
    private BigDecimal sumAmt;

    @ApiModelProperty(value="全国退货率")
    @JsonProperty("sum_return_rate")
    private BigDecimal sumReturnRate;

    @ApiModelProperty(value="华北退货数量")
    @JsonProperty("hb_single_count")
    private Long hbSingleCount;

    @ApiModelProperty(value="华北退货金额")
    @JsonProperty("hb_amt")
    private BigDecimal hbAmt;

    @ApiModelProperty(value="华北退货率")
    @JsonProperty("hb_return_rate")
    private BigDecimal hbReturnRate;

    @ApiModelProperty(value="华东退货数量")
    @JsonProperty("hd_single_count")
    private Long hdSingleCount;

    @ApiModelProperty(value="华东退货金额")
    @JsonProperty("hd_amt")
    private BigDecimal hdAmt;

    @ApiModelProperty(value="华东退货率")
    @JsonProperty("hd_return_rate")
    private BigDecimal hdReturnRate;

    @ApiModelProperty(value="华南退货数量")
    @JsonProperty("hn_single_count")
    private Long hnSingleCount;

    @ApiModelProperty(value="华南退货金额")
    @JsonProperty("hn_amt")
    private BigDecimal hnAmt;

    @ApiModelProperty(value="华南退货率")
    @JsonProperty("hn_return_rate")
    private BigDecimal hnReturnRate;

    @ApiModelProperty(value="西南退货数量")
    @JsonProperty("xn_single_count")
    private Long xnSingleCount;

    @ApiModelProperty(value="西南退货金额")
    @JsonProperty("xn_amt")
    private BigDecimal xnAmt;

    @ApiModelProperty(value="西南退货率")
    @JsonProperty("xn_return_rate")
    private BigDecimal xnReturnRate;

    @ApiModelProperty(value="华中退货数量")
    @JsonProperty("hz_single_count")
    private Long hzSingleCount;

    @ApiModelProperty(value="华中退货金额")
    @JsonProperty("hz_amt")
    private BigDecimal hzAmt;

    @ApiModelProperty(value="华中退货率")
    @JsonProperty("hz_return_rate")
    private BigDecimal hzReturnRate;
}
