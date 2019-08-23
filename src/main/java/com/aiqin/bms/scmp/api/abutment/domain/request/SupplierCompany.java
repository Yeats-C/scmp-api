package com.aiqin.bms.scmp.api.abutment.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sunx
 * @description
 * @date 2019-08-05
 * TASK	    CHAR	1	0	外部接口：更改公司代码数据的标识		是	必输：创建-I；修改-U；删除-D；创建/更改-M
 * BUKRS	CHAR	4	0	公司代码	是	是
 * ZTERM	CHAR	4	0	付款条件代码	是	是
 * REPRF	CHAR	1	0	检查双重发票或信贷凭单的标志 		是
 * AKONT	CHAR	10	0	总帐中的统驭科目	是	是
 * LOEVM	CHAR	1	0	删除标识符
 * SPERR	CHAR	1	0	实际盘点冻结标识
 */
@Data
@ApiModel("scmp供应商公司信息")
public class SupplierCompany {

    @ApiModelProperty("更改公司代码数据的标识必输：创建-I；修改-U；删除-D；创建/更改-M")
    @JsonProperty("TASK")
    private String flag;

    @ApiModelProperty("公司代码")
    @JsonProperty("BUKRS")
    private String companyCode;

    @ApiModelProperty("付款条件代码")
    @JsonProperty("ZTERM")
    private String payConditionCode;

    @ApiModelProperty(value = "检查双重发票或信贷凭单的标志",hidden = true)
    @JsonProperty("REPRF")
    private String checkInvoiceTag="X";

    @ApiModelProperty(value = "总帐中的统驭科目",hidden = true)
    @JsonProperty("AKONT")
    private String controlSubject;
}
