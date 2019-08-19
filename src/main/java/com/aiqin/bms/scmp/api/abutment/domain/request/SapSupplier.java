package com.aiqin.fms.wechat.api.domain.sap.supplier;

import com.aiqin.fms.wechat.api.domain.sap.SapBaseVO;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author sunx
 * @description
 * @date 2019-08-01
 * VENDX	CHAR	1	0	供应商核心数据更改标识 		是
 * LIFNR	CHAR	10	0	供应商或债权人的帐号	否	是
 * NAME1	CHAR	35	0	名称 1	否	是
 * KTOKK	CHAR	4	0	供应商帐户组	是	是
 */
@Data
@ApiModel("scmp供应商数据1")
public class SapSupplier extends SapBaseVO {

    /**
     * 供应商或债权人的帐号
     */
    @ApiModelProperty("供应商或债权人的帐号")
    @JsonProperty("LIFNR")
    private String supplierCode;

    /**
     * 供应商名称
     */
    @ApiModelProperty("供应商名称")
    @JsonProperty("NAME1")
    private String supplierName;

    /**
     * 供应商核心数据更改标识 I-新增 U-更新 C-不做更改
     */
    @ApiModelProperty("供应商核心数据更改标识I-新增 U-更新 C-不做更改")
    @JsonProperty("VENDX")
    private String flag;

    /**
     * 供应商帐户组
     */
    @ApiModelProperty("供应商帐户组")
    @JsonProperty("KTOKK")
    private String supplierGroupCode;

    @ApiModelProperty(value = "排序字段",hidden = true)
    @JsonProperty("SORTL")
    private String sortIndex = "1";


    @ApiModelProperty(value = "税号")
    @JsonProperty("STCEG")
    private String taxNo;

    @ApiModelProperty(value = "银行数据")
    @JsonProperty("BANKDT")
    private List<SupplierBank> banks;

    @ApiModelProperty(value = "公司信息")
    @JsonProperty("BUKRST")
    private List<SupplierCompany> companyList;

    @ApiModelProperty(value = "采购数据")
    @JsonProperty("EKORGT")
    private List<SupplierPurchase> purchaseList;
}
