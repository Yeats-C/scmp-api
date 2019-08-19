package com.aiqin.fms.wechat.api.domain.sap.supplier;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sunx
 * @description
 * @date 2019-08-05
 * TASK	CHAR	1	0	外部接口：更改采购组织标识 		是	必输：创建-I；修改-U；删除-D；创建/更改-M
 * EKORG	CHAR	4	0	采购组织	是	是
 * ZTERM	CHAR	4	0	付款条件代码	是	是
 * WAERS	CUKY	5	0	货币码 	是	是
 * WEBRE	CHAR	1	0	标识：基于收货的发票验证		是
 * LFABC	CHAR	1	0	ABC标识
 */
@Data
@ApiModel("scmp供应商采购数据")
public class SupplierPurchase {
    @ApiModelProperty("更改采购组织标识必输：创建-I；修改-U；删除-D；创建/更改-M")
    @JsonProperty("TASK")
    private String flag;

    @ApiModelProperty("采购组织")
    @JsonProperty("EKORG")
    private String purchaseOrg;

    @ApiModelProperty("付款条件代码")
    @JsonProperty("ZTERM")
    private String payConditionCode;

    @ApiModelProperty("货币码")
    @JsonProperty("WAERS")
    private String currencyCode = "CNY";

    @ApiModelProperty("标识：基于收货的发票验证X")
    @JsonProperty("WEBRE")
    private String receiptTag = "X";

    @ApiModelProperty("ABC标识")
    @JsonProperty("LFABC")
    private String abcTag;
}
